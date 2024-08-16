package com.onjul.onjul.controller;

import com.onjul.onjul.dto.UserCreateDto;
import com.onjul.onjul.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateDto userCreateDto) {
        return "user/signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateDto userCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/signup_form";
        }

        if (!userCreateDto.getPassword1().equals(userCreateDto.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect"
                    , "2개의 비밀번호가 일치하지 않습니다.");
            return "user/signup_form";
        }

        userService.create(userCreateDto.getUsername(), userCreateDto.getEmail(),
                userCreateDto.getPassword1(), userCreateDto.getNickname());

        return "redirect:/";
    }
}
