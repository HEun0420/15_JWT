package com.ohgiraffers.jwt.test.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    // user일 때 사용가능함
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    public String testUser() {
        return "user 권한만 접근 가능한 test success";
    }

    // admin일 때 사용가능함
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String testAdmin() {
        return "Admin 권한만 접근 가능한 test success";
    }
}
