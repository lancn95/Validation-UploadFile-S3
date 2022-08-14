package com.amigos.awsuploadimage.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserProfileUpdateRequest {
    @NotNull
    @Size(min = 3, max = 100, message = "Độ dài của tên từ 3 đến 100 ký tự")
    private String username;

    @Min(value = 1, message = "Tuổi không được bé hơn 1")
    @Max(value = 100, message = "tuổi không được lớn hơn 100")
    private Integer age;

    private String linkImage;
}
