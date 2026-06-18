package com.test.springtest.controller;

import com.test.springtest.entity.UserEntity;
import com.test.springtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserEntity> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserEntity getById(
            @PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping
    public UserEntity create(
            @RequestBody UserEntity request) {

        return userService.create(request);
    }

    @PutMapping("/{id}")
    public UserEntity update(
            @PathVariable Long id,
            @RequestBody UserEntity request) {

        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        userService.delete(id);
    }
}
