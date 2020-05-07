package com.boot.pp25.controller;

import com.boot.pp25.model.Role;
import com.boot.pp25.model.User;
import com.boot.pp25.service.RoleServices;
import com.boot.pp25.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class adminController {

    UserService userService;

    RoleServices roleServices;

    Logger logger = LoggerFactory.getLogger(adminController.class);

    @Autowired
    public adminController(UserService userService, RoleServices roleServices) {
        this.userService = userService;
        this.roleServices = roleServices;
    }

    @GetMapping
    public ModelAndView mainAdminControllerGet(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("users",userService.findAll());
        mv.setViewName("adminPages/adminMainPage");
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView addUserControllerGet(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", new User()); //для Thymeleaf нужно передать объект
        mv.setViewName("adminPages/addUserPage");
        return mv;
    }

    @PostMapping("/add")
    public ModelAndView addUserControllerPost(@ModelAttribute("user") @Valid User user,
                                              BindingResult result,
                                              @RequestParam(value = "userRole", required = false) String userRole,
                                              @RequestParam(value = "adminRole",  required = false) String adminRole) {
        ModelAndView mv = new ModelAndView();
        if (result.hasErrors()){
            logger.error(String.valueOf(result.getAllErrors()));
            mv.setViewName("adminPages/addUserPage");
            return mv;
        }

        Set<Role> roles = new HashSet<>();
        if (userRole != null) {
            roles.add(roleServices.getRoleByName("USER"));
        }
        if (adminRole != null) {
            roles.add(roleServices.getRoleByName("ADMIN"));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        logger.info("Add User: " + user.toString());
        mv.setViewName("redirect:/admin");
        return mv;
    }

    @GetMapping("/edit/{id}")
    public String editUserControllerGet(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "adminPages/editUserPage";
    }

    @PostMapping("/edit")
    public ModelAndView editUserControllerPost(@ModelAttribute @Valid User user,
                                               BindingResult result,
                                               @RequestParam(value = "userRole", required = false) String userRole,
                                               @RequestParam(value = "adminRole",  required = false) String adminRole) {
        ModelAndView mv = new ModelAndView();

        if(result.hasErrors()){
            mv.setViewName("adminPages/editUserPage");
            return mv;
        }

        Set<Role> roles = new HashSet<>();
        if (userRole != null) {
            roles.add(roleServices.getRoleByName("USER"));
        }
        if (adminRole != null) {
            roles.add(roleServices.getRoleByName("ADMIN"));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        mv.setViewName("redirect:/admin");
        return mv;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }

}
