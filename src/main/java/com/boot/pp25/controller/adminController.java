package com.boot.pp25.controller;

import com.boot.pp25.model.User;
import com.boot.pp25.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class adminController {

    UserService userService;

    @Autowired
    public adminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView mainAdminControllerGet(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("users",userService.findAll());
        mv.setViewName("adminPages/adminMainPage");
        return mv;
    }

    @GetMapping("/add")
    public String addUserControllerGet(){
        return "adminPages/addUserPage";
    }

    @PostMapping("/add")
    public ModelAndView addUserControllerPost(@ModelAttribute("user") User user) {
        ModelAndView mv = new ModelAndView();
        userService.saveUser(user);
        mv.setViewName("redirect:/admin");
        return mv;
    }

    @GetMapping("/edit/{id}")
    public String editUserControllerGet(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "adminPages/editUserPage";
    }

    @PostMapping("/edit")
    public ModelAndView editUserControllerPost(@ModelAttribute User user) {
        ModelAndView mv = new ModelAndView();
        userService.saveUser(user);
        mv.setViewName("redirect:/admin");
        return mv;
    }

//    @GetMapping("/delete/{id}")
//    public String deleteUserControllerGet(){
//        return "adminPages/deleteUserPage";
//    }
//
//    @PostMapping("/delete")
//    public ModelAndView deleteUserControllerPost(@RequestParam("id") Long id) {
//        ModelAndView mv = new ModelAndView();
//        userService.deleteById(id);
//        mv.setViewName("redirect:/admin");
//        return mv;
//    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }

}
