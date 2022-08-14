package com.amigos.awsuploadimage.api;

import com.amigos.awsuploadimage.profile.UserProfile;
import com.amigos.awsuploadimage.request.UserProfileRequest;
import com.amigos.awsuploadimage.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/user-profile")
public class UserProfileApi {

    private final UserProfileService userProfileService;

    public UserProfileApi(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getUserProfiles() {
        return userProfileService.findAllUserProfile();
    }

    @PostMapping()
    public ResponseEntity<UserProfile> createNewUserProfile(@RequestBody @Valid UserProfileRequest userProfileRequest,
                                                            BindingResult bindingResult)
            throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        return new ResponseEntity<>(userProfileService.createNewUserProfile(userProfileRequest), HttpStatus.CREATED);
    }

    @PostMapping(path = "{userProfileId}/image/download",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("userProfileId") Long id,
                                       @RequestParam("file") MultipartFile file) {
        userProfileService.uploadUserProfileImage(id, file);
    }
}
