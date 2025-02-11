package com.shri.springify.Springify.controller;


import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<User>getUserDetails(
            @RequestHeader("Authorization") String jwt
    )
    {
        try{
            User user=userService.findUserByJwt(jwt);

            return  new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {

            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
