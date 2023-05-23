package com.tms.oknapvh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {

    @Email(regexp = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Неверный формат электронной почты")
    private String email;

    @Pattern(regexp = "^\\+375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$", message = "Введите номер в формате: +375332221100")
    private String phone;

    private String message;

}
