package com.amigos.awsuploadimage.api;

import com.amigos.awsuploadimage.constants.RoleCons;
import com.amigos.awsuploadimage.entity.Role;
import com.amigos.awsuploadimage.entity.UserProfile;
import com.amigos.awsuploadimage.enumeration.AuthenticateStatus;
import com.amigos.awsuploadimage.enumeration.ERole;
import com.amigos.awsuploadimage.repository.RoleRepository;
import com.amigos.awsuploadimage.repository.UserProfileRepository;
import com.amigos.awsuploadimage.request.login.LoginRequest;
import com.amigos.awsuploadimage.request.login.SignupRequestForm;
import com.amigos.awsuploadimage.response.AuthenticateResponse;
import com.amigos.awsuploadimage.response.MessageResponse;
import com.amigos.awsuploadimage.service.impl.UserDetailsImpl;
import com.amigos.awsuploadimage.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class AuthApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest request, HttpServletRequest httpRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                                            .map(x -> x.getAuthority())
                                            .collect(Collectors.toList());
            return ResponseEntity.ok(new AuthenticateResponse(
                    jwt,
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles,
                    AuthenticateStatus.AUTHENTICATED
            ));
        }catch (BadCredentialsException e){
            e.printStackTrace();
            return new ResponseEntity<>("Username or password is not valid!",HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestForm signupRequest){
        if(userProfileRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already existed"));
        }
        if(userProfileRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already existed"));
        }

        // creating new user account
        UserProfile user = new UserProfile(signupRequest.getUsername(),
                                            signupRequest.getEmail(),
                                            passwordEncoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if(strRoles == null){
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }else{
            strRoles.forEach(strRole ->{
                switch (strRole){
                    case RoleCons.ADMIN:
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: " + strRole +  " is not found."));
                        roles.add(adminRole);
                        break;
                    case RoleCons.MODERATOR:
                        Role moderatorRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: " + strRole +  " is not found."));
                        roles.add(moderatorRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: " + strRole +  " is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userProfileRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
}
