package com.pms.service;

import com.pms.Dao.LoginDao;
import com.pms.entity.User;
import com.pms.utility.BCrypt;
import com.pms.utility.LinkGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Base64;
import java.util.List;

@Service
public class LoginService {

    private LoginDao loginDao;

    @Autowired
    public LoginService(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public User getUser(String username, String password) {
        User user = loginDao.getUser(username);
        if (user != null && user.getPassword() == null) {
            return user;
        } else if (user != null && user.getUsername().equals(username) && (user.getPassword().equals(BCrypt.hashpw(password, user.getSalt())))) {
            return user;
        } else {
            return null;
        }
    }

    public User checkUsername(String username) {
        User user = loginDao.getUser(username);

        if (user != null && user.getUsername().equals(username)) {
            return user;
        }
        return null;
    }

    public boolean validateOtp(String trim, String hashedOtp) {
        return BCrypt.checkpw(trim, new String(Base64.getMimeDecoder().decode(hashedOtp), StandardCharsets.UTF_8));
    }

    public boolean changePassword(String user, String pass) {
        return loginDao.changePassword(user, pass, BCrypt.gensalt());
    }

    public String activateAccount(String user, String encUser, String encEmail, String userSalt, String emailSalt) {
        if (user == null || user.trim().equals("") || encUser == null || encUser.trim().equals("") || encEmail == null || encEmail.trim().equals("") || userSalt == null || userSalt.trim().equals("") || emailSalt == null || emailSalt.trim().equals("")) {
            return "Invalid Link";
        }

        User u = loginDao.getUser(user);
        if (u != null) {

            List<String> ans = LinkGen.encUserandEmail(u.getUsername(), u.getEmail(), userSalt, emailSalt);

            if (u.getActivated() == 0) {
                if (ans.get(0).equals(encUser) && ans.get(1).equals(encEmail)) {
                    return null;
                } else {
                    return "Invalid Link";
                }
            } else {
                return "Already Activated";
            }
        }
        return "Invalid Link";
    }

    public void activateUser(String user, String pass) {
        loginDao.activateAccount(user, pass, BCrypt.gensalt());
    }
}
