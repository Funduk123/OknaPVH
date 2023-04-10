package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        log.info("Find all users");
        return service.getAll();
    }

    @GetMapping("/byLogin/{login}")
    public UserEntity getByLogin(@PathVariable(name = "login") String login) {
        log.info("Find user by login: " + login);
        return service.getByLogin(login);
    }

    @PostMapping("/save")
    public void save(@RequestBody UserDto user) {
        log.info("Save user: " + user);
        service.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        log.info("Delete user by id: " + id);
        service.deleteUser(id);
    }

}
