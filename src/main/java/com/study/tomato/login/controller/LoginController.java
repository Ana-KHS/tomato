package com.study.tomato.login.controller;

import com.study.tomato.login.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    @GetMapping("/")
    public ModelAndView sessionCheck(HttpServletRequest httpServletRequest) {
        return loginService.sessionCheck(httpServletRequest);
    }

    @GetMapping("/login")
    public String login() {
        return "pages/login/login";
    }

    @PostMapping("/login")
    public ModelAndView selectSessionUser(HttpServletRequest httpServletRequest) {
        return loginService.loginSession(httpServletRequest);
    }

    @GetMapping("/tomato_main")
    public ModelAndView tomatoMain(HttpSession session) {
        return loginService.tomatoMain(session);
    }
}
