package com.tms.oknapvh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID id;

    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 3, max = 20, message = "Используйте от 3 до 20 символов")
    private String username;

    @NotBlank(message = "Пожалуйста, укажите email")
    @Email(regexp = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Неверный формат электронной почты")
    private String email;

    @NotBlank(message = "Пожалуйста, придумайте пароль")
    @Size(min = 8, message = "Пароль не должен быть короче 8 символов")
    private String password;

    @Pattern(regexp = "^\\+375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$", message = "Введите номер в формате: +375332221100")
    @NotBlank(message = "Пожалуйста, укажите номер телефона")
    private String phone;

    @NotBlank(message = "Пожалуйста, укажите имя")
    @Size(min = 1, max = 50, message = "Неверное количество символов")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s]+$", message = "Пожалуйста, используйте только буквы")
    private String firstName;

    @NotBlank(message = "Пожалуйста, укажите фамилию")
    @Size(min = 1, max = 50, message = "Неверное количество символов")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s]+$", message = "Пожалуйста, используйте только буквы")
    private String lastName;

    private String auth;

    private List<OrderDto> orders;

}
