package com.pms.controller;

import com.pms.entity.User;
import com.pms.service.LoginService;
import com.pms.utility.otpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/")
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
        User user = loginService.getUser(username.trim().toUpperCase(), password.trim());

        if (user == null) {
            model.addAttribute("message", "Invalid Password");
            return "login";
        } else if (user.getActivated() == 0) {
            model.addAttribute("message", "Account Activation");
            return "login";
        } else if (user.getFlag() == 0) {
            session.setAttribute("user", user);
            return "redirect:admin/index";
        } else if (user.getFlag() == 1) {
            session.setAttribute("user", user);
            return "redirect:guide/index";
        } else if (user.getFlag() == 2) {
            session.setAttribute("user", user);
            return "redirect:student/index";
        } else {
            model.addAttribute("message", "Invalid Login Method");
            return "login";
        }
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String showForgotPasswordPage() {
        return "password/forgot";
    }

    @RequestMapping(value = "/fpUsernameSubmit", method = RequestMethod.POST)
    public String checkUsername(Model model, HttpSession session, @RequestParam("username") String username) {
        User user = loginService.checkUsername(username.trim().toUpperCase());
        if (user != null && user.getUsername().equals(username.trim().toUpperCase())) {
            session.setAttribute("email", user.getEmail());
            session.setAttribute("user", user.getUsername());
            return "password/sendOtpPage";
        }
        model.addAttribute("message", "Username Does not Exists");
        return "password/forgot";
    }

    @RequestMapping(value = "/fpUser", method = RequestMethod.GET)
    public String backtoChoice(HttpSession session, Model model) {
        String user = (String) session.getAttribute("user");
        String email = (String) session.getAttribute("email");
        if (email != null && user != null) {
            return "password/sendOtpPage";
        }
        model.addAttribute("message", "Invalid Login Method");
        return "login";
    }

    @RequestMapping(value = "/sendOtpC1")
    public String sendEmailOtp(Model model, HttpSession session) {

        String user = (String) session.getAttribute("user");
        String email = (String) session.getAttribute("email");

        if (email != null && user != null) {
            String otp = otpSender.sendOtpEmail(user, email);
            session.setAttribute("otp", otp);
            return "password/enterOtp";
        }
        model.addAttribute("message", "Invalid Login Method");
        return "login";
    }


    @RequestMapping(value = "/validateOtp", method = RequestMethod.POST)
    public String validateOtp(Model model, HttpSession session, @RequestParam("otp") String enteredOtp) {
        String user = (String) session.getAttribute("user");
        String hashedOtp = (String) session.getAttribute("otp");
        session.setAttribute("otp", null);

        if (user != null && session.getAttribute("email") != null && hashedOtp != null) {
            if (loginService.validateOtp(String.valueOf(enteredOtp).trim(), hashedOtp)) {
                session.setAttribute("email", null);
                return "password/resetPassword";
            } else {
                model.addAttribute("message", "Invalid Otp");
                return "password/sendOtpPage";
            }
        }
        model.addAttribute("message", "Invalid Login Method");
        return "login";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(Model model, HttpSession session, @RequestParam("pass") String pass, @RequestParam("cpass") String cpass) {
        String user = (String) session.getAttribute("user");
        if (pass == null || cpass == null || pass.trim().equals("") || cpass.trim().equals("")) {
            model.addAttribute("message", "Passwords cannot be only white spaces");
            return "password/resetPassword";
        }
        pass = pass.trim();
        cpass = cpass.trim();
        if (user != null) {
            if (pass.equals(cpass)) {
                if (loginService.changePassword(user, pass)) {
                    model.addAttribute("message", "Password Reset");
                    return "login";
                } else {
                    model.addAttribute("message", "Ërror Occur");
                    return "password/resetPassword";
                }
            } else {
                model.addAttribute("message", "Passwords do not match");
                return "password/resetPassword";
            }
        } else {
            model.addAttribute("message", "Invalid Login Method");
            return "login";
        }
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        return "login";
    }


    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public String activateAccount(HttpSession session, Model model, @RequestParam("u") String user, @RequestParam("i") String encUser, @RequestParam("k") String encEmail, @RequestParam("m") String userSalt, @RequestParam("n") String emailSalt) {
        String answer = loginService.activateAccount(user, encUser, encEmail, userSalt, emailSalt);
        if (answer != null) {
            model.addAttribute("message", answer);
            return "login";
        }
        session.setAttribute("addPassUser", user);
        return "password/addPassword";
    }

    @RequestMapping(value = "/addPassword", method = RequestMethod.POST)
    public String addPass(Model model, HttpSession session, @RequestParam("pass") String pass, @RequestParam("cpass") String cpass) {
        String user = (String) session.getAttribute("addPassUser");
        if (pass == null || cpass == null || pass.trim().equals("") || cpass.trim().equals("")) {
            model.addAttribute("message", "Passwords cannot be only white spaces");
            return "password/addPassword";
        }
        pass = pass.trim();
        cpass = cpass.trim();
        if (user != null) {
            if (pass.equals(cpass)) {
                loginService.activateUser(user, pass);
                model.addAttribute("message", "Activation Done");
                return "login";
            } else {
                model.addAttribute("message", "Passwords do not match");
                return "password/resetPassword";
            }
        } else {
            model.addAttribute("message", "Invalid Login Method");
            return "login";
        }

    }

}
