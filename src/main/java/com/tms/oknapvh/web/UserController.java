package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/store/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return service.getAll();
    }

    @PostMapping("/byLogin")
    public String getByLogin(@RequestParam("username") String login) {
        return "redirect:/store/profile/" + login;
    }

    @PostMapping("/save")
    public void save(@RequestBody UserDto user) {
        service.saveUser(user);
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") UUID id, Principal principal) {
        var currentUsername = principal.getName();
        var username = service.getById(id).getUsername();
        var check = service.check(username, currentUsername);
        if (!check) {
            service.deleteUser(id);
        }
        return "redirect:/store/users-list";
    }
}
