package com.tms.oknapvh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordForm {

    private String oldPassword;

    @Size(min = 8, message = "Пароль должен включать не менее 8 символов")
    private String newPassword;

}
