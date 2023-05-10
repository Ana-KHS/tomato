package com.study.tomato.login.service;

import com.study.tomato.login.repository.LoginRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }


    /**
     * session user check
     */
    public ModelAndView sessionCheck(HttpServletRequest httpServletRequest) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("session_user_id");
        String sessionUserId = (String) session.getAttribute("session_user_id");

        if(sessionUserId == null || sessionUserId.equals("")) {
            mv.setViewName("redirect:/login");
        }else {
            mv.addObject("sessionUserId", sessionUserId);
            mv.addObject("sessionUserName", session.getAttribute("session_user_name"));
            mv.setViewName("pages/tomatoMain");
        }

        return mv;
    }


    public ModelAndView loginSession(HttpServletRequest httpServletRequest) {
        ModelAndView mv = new ModelAndView("pages/login/login");

        String loginId = httpServletRequest.getParameter("loginId");
        String loginPassword = httpServletRequest.getParameter("loginPassword");

        HttpSession session = httpServletRequest.getSession();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("loginId", loginId);
        param.put("loginPassword", loginPassword);

        List<Map<String, Object>> selectSessionUser = loginRepository.sessionLogin(param);
        System.out.println(">>>>>>>>>>>>>>> selectSessionUser: " + selectSessionUser);
        mv.addObject("sessionInfo", selectSessionUser);

        if(selectSessionUser.isEmpty()) {
            mv.addObject("result", "Fail");
        }else {
            session.setAttribute("session_user_id", selectSessionUser.get(0).get("user_id"));
            session.setAttribute("session_user_name", selectSessionUser.get(0).get("user_name"));
            mv.addObject("result", "Success");
            mv.setViewName("redirect:/tomato_main");
        }

        System.out.println(">>>>>>>>>>>>>> : " + mv);
        return mv;
    }

    public ModelAndView tomatoMain(HttpSession session) {
        ModelAndView mv = new ModelAndView("pages/tomatoMain");
        String sessionUserId = (String) session.getAttribute("session_user_id");

        mv.addObject("sessionUserId", sessionUserId);
        mv.addObject("sessionUserName", session.getAttribute("session_user_name"));

        return mv;
    }
}
