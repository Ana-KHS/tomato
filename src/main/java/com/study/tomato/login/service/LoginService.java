package com.study.tomato.login.service;

import com.study.tomato.login.repository.LoginRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String sessionCheck(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String sessionUserId = (String) session.getAttribute("session_user_id");
        if(sessionUserId == null || sessionUserId.equals("")) {
            return "redirect:/login";
        }else {
            session.removeAttribute("session_user_id");
            return "pages/tomatoMain";
        }
    }

    public List<Map<String, Object>> selectSessionUser(Map<String, Object> param) {
        return loginRepository.sessionLogin(param);
    }

    public ModelAndView loginSession(HttpServletRequest httpServletRequest) {
        ModelAndView mv = new ModelAndView("pages/login/login");

        String loginId = httpServletRequest.getParameter("loginId");
        String loginPassword = httpServletRequest.getParameter("loginPassword");

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("session_user_id", loginId);

        System.out.println(">>>>>>>>>> session.getAttribute: " + session.getAttribute("session_user_id"));

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("loginId", loginId);
        param.put("loginPassword", loginPassword);

        List<Map<String, Object>> selectSessionUser = selectSessionUser(param);

        if(!selectSessionUser.isEmpty()) {
            mv.setViewName("redirect:/tomato_main");
            mv.addObject("test", "Success");
            mv.addObject("session_user_id", loginId);
        }else {
            mv.setViewName("redirect:/login");
            mv.addObject("test", "Fail");
        }
        System.out.println(mv);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + param);
        System.out.println(">>>>>>>>>>>>> 조회결과: " + selectSessionUser);

        return mv;
    }

    public ModelAndView tomatoMain(HttpSession session) {
        ModelAndView mv = new ModelAndView("pages/tomatoMain");
        String sessionUserId = (String) session.getAttribute("session_user_id");
        System.out.println(">>>>>>>>>>>>>>>>> sessionUserId: " + sessionUserId);
        mv.addObject("sessionUserId", sessionUserId);
        return mv;
    }
}
