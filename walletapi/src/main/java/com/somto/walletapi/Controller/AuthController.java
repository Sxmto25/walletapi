package com.somto.walletapi.Controller;

import com.somto.walletapi.Models.User;
import com.somto.walletapi.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (
            @RequestParam String email,
            @RequestParam String password) {
        return new ResponseEntity<>(authService.login(email, password), HttpStatus.OK);
    }

}
