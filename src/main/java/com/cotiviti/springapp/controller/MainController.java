package com.cotiviti.springapp.controller;

import com.cotiviti.springapp.dao.UserDao;
import com.cotiviti.springapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@SessionAttributes("userid")
public class MainController {

    @Autowired
    //UserDao uDao;
    UserDao userDao;

    @Autowired
    User usr;

    @GetMapping(value={"/login","/userlogin"})
    public String loginPage(HttpSession session, Model model){
        if(session.getAttribute("userid") != null){
            return "welcome";
        }
        model.addAttribute("usr",usr);
        return "index";
    }

    @GetMapping("/welcome")
        public String welcomePage(HttpSession session){
        if(session.getAttribute("userid") == null){
            return "index";
        }
        return "welcome";
    }

    @GetMapping("/register")
    public String registerPage(Model model)
    {
        model.addAttribute("user", usr);
        return "register";
    }

    @PostMapping("/home")
    public ModelAndView userValidation(@ModelAttribute("usr") User user,
                                       @RequestParam(value="rememberMe", defaultValue = "off") String rememberMe, Model model, HttpServletResponse response){
        ModelAndView modelAndView;
        if(("on").equalsIgnoreCase(rememberMe)){
            Cookie usernameCookie = new Cookie("username", user.getEmail());
            Cookie passwordCookie = new Cookie("password", user.getPassword());

            usernameCookie.setMaxAge(300000);
            passwordCookie.setMaxAge(300000);

            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);
        }

        String passwordFromDb = userDao.getPassword(user.getEmail());
        if(passwordFromDb.equals(user.getPassword())){
            modelAndView = new ModelAndView("mainDashboard");
            //as userid is declared as sessionAttribute, assigning value to userid, bydefault maintains session
            model.addAttribute("userid",user.getEmail());
            return modelAndView;
        }
        else {
            modelAndView = new ModelAndView("index");
            modelAndView.addObject("errmsg","Invalid username and password");
            return modelAndView;
        }
    }

    @PostMapping("/registration")
    public String userRegistration(@ModelAttribute("user") User uuser, Model model) {
        if(!uuser.getPassword().equals(uuser.getPassword1())) {
            model.addAttribute("errmsg","Two passwords do not match!");
            model.addAttribute("name",uuser.getName());
            model.addAttribute("dob",uuser.getDob());
            model.addAttribute("gender",uuser.getGender());
            model.addAttribute("email",uuser.getEmail());
            model.addAttribute("password",uuser.getPassword());
            model.addAttribute("password1",uuser.getPassword1());
           return "register";
        }
        boolean isSaved = userDao.registerUser(uuser.getName(),uuser.getDob(),uuser.getGender(),uuser.getEmail(), uuser.getPassword());
        if(isSaved) {
            return "index";
        }
        else {
            model.addAttribute("errmsg","User creation failed!!");
            return "register";
        }
    }

    @GetMapping("/changepassword")
    public String changePassword()
    {
        return "changePassword";
    }

    @PostMapping("/updatepassword")
    public String updatePassword(Model model, @RequestParam("oldpassword") String oldpass, @RequestParam("newfpassword") String newpass1,
                                 @RequestParam("newspassword") String newpass2, HttpSession session) {
        boolean isUpdate = false;
        String msg ="";
        String userid = session.getAttribute("userid").toString();
        System.out.println("Logged in user  "+userid);
        String passwordFromDB = userDao.getPassword(userid);
        if(oldpass.equals(passwordFromDB)){
            if(newpass1.equals(newpass2)){
                isUpdate = userDao.updateUserInfo(userid, newpass1);
            }else {
                msg ="New passwords does not match !";
            }
        }else {
            msg ="Incorrect old password!";
        }
        if(isUpdate) {
            model.addAttribute("errmsg", "Password updated successfully!");
            return "index";
        }else {
            model.addAttribute("msg", msg);
            model.addAttribute("oldpassword",oldpass);
            model.addAttribute("newpassword1",newpass1);
            model.addAttribute("newpassword2",newpass2);
            return "changePassword";
        }
    }

    @GetMapping("/userList")
    public String userList(HttpSession session, Model model)
    {
        if(session.getAttribute("userid") == null){
            return "index";
        }
        List<User> usrList = userDao.getUserList();
        model.addAttribute("userList",usrList);
        return "userList";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session, Model model){
        model.addAttribute("userid",null);
        session.removeAttribute("userid");
        session.invalidate();
        return "index";
    }

    @GetMapping("/delete/{uid}")
    public String deleteUser(@PathVariable("uid") int userId, RedirectAttributes redirectAttributes) {
        userDao.deleteUser(userId);
        redirectAttributes.addAttribute("msg","User deleted successfully!");
        return "redirect:/userList";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int uId, Model model){
        User user = userDao.getUserById(uId);
        model.addAttribute("user",user);
        return "editUser";
    }

    @PostMapping("/updateUser")
    public String updateUserInfo(@ModelAttribute("user") User usr,Model model, RedirectAttributes redirectAttributes){
        boolean isUpdated = userDao.updateUserDetails(usr);
        if(isUpdated) {
            redirectAttributes.addAttribute("msg","User info updated successfully!");
            return "redirect:/userList";
        }else{
            model.addAttribute("user",usr);
            model.addAttribute("errmsg","User info not updated!");
            return "editUser";
        }
    }
}
