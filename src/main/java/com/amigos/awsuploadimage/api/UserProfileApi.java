package com.amigos.awsuploadimage.api;

import com.amigos.awsuploadimage.entity.UserProfile;
import com.amigos.awsuploadimage.request.UserProfileCreateRequest;
import com.amigos.awsuploadimage.request.UserProfileUpdateRequest;
import com.amigos.awsuploadimage.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/user-profile")
@CrossOrigin(origins = "*")
@Tag(name = "user-profile-well-define-APIs")
public class UserProfileApi {

    private final UserProfileService userProfileService;

    public UserProfileApi(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    @Operation(summary = "get all users")
    public List<UserProfile> getUserProfiles() {
        return userProfileService.findAllUserProfile();
    }

    @PostMapping()
    @Operation(summary = "create new user")
    public ResponseEntity<UserProfile> createNewUserProfile(@RequestBody @Valid UserProfileCreateRequest userProfileRequest,
                                                            BindingResult bindingResult)
            throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        return new ResponseEntity<>(userProfileService.createNewUserProfile(userProfileRequest), HttpStatus.CREATED);
    }

    @PostMapping(path = "{userProfileId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "upload user image ")
    public void uploadUserProfileImage(@PathVariable("userProfileId") Long id,
                                       @RequestParam("file") MultipartFile file) {
        userProfileService.uploadUserProfileImage(id, file);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update user profile ")
    public ResponseEntity<UserProfile> update(@PathVariable("id") Long id,
                                              @RequestBody @Valid UserProfileUpdateRequest request) {
        userProfileService.update(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // upload file in project
    @PostMapping(value = "{userProfileId}/file/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "upload file attach user")
    public ResponseEntity<?> uploadUserFile(@PathVariable("userProfileId") Long id,
                                            @RequestParam("files") MultipartFile[] files) {
        userProfileService.uploadUserFile(id, files);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // download file in project
    @GetMapping(value = "file/download")
    @Operation(summary = "download file attach user")
    public ResponseEntity<?> downloadUserFile(@RequestParam("fileName") String fileName
                                            , @RequestParam("id") Long id,
                                             HttpServletResponse httpServletResponse) throws IOException {
      return userProfileService.downloadUserFile(id, fileName, httpServletResponse);
    }
}
