package ru.lis154.SpringBootSecPr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lis154.SpringBootSecPr.Model.Role;
import ru.lis154.SpringBootSecPr.Model.User;
import ru.lis154.SpringBootSecPr.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private int page;
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String allUser(@RequestParam(defaultValue = "1") int page, Model model) {
        List<User> listUser = userService.allUser(page);
        int userCount = userService.userCount();
        int pageCout = (userCount + 9) / 10;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nameUser = auth.getName();
        User user1 = (User) userService.loadUserByUsername(nameUser);
        String roles = user1.getListRoles();
        model.addAttribute("users", listUser);
        model.addAttribute("name", nameUser);
        model.addAttribute("role", roles);

        System.out.println(listUser);

        return "admin";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String OneUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nameUser = auth.getName();
        User user1 = (User) userService.loadUserByUsername(nameUser);
        //  User listUser = (User) userService.loadUserByUsername(name);
        // model.addAttribute("user", listUser);
        model.addAttribute("user", user1);
        return "user";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
    public String UserADD(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nameUser = auth.getName();
        User user1 = (User) userService.loadUserByUsername(nameUser);
        String roles = user1.getListRoles();
        model.addAttribute("name", nameUser);
        model.addAttribute("role", roles);
        return "add";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public String AddUserADD(@RequestParam String name, @RequestParam String password, @RequestParam String age, @RequestParam String role, Model model) {
        int age1 = Integer.valueOf(age);
        User user = new User(name, password, age1);
        user.setRolesOnForm(role);
        System.out.println(user);
        userService.add(user);
        return "redirect:/admin";
    }

    //    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
//    public String delete(@PathVariable("id") int id, Model model) {
//        userService.delete(id);
//        return "redirect:/admin";
//    }
    @RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
    public String delete(@RequestParam String id, Model model) {
        int id1 = Integer.valueOf(id);
        userService.delete(id1);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/edit/{id}", method = RequestMethod.GET)
    public String UserEdit(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        return "edit";
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    public String EdUserEdit(@RequestParam String id, @RequestParam String name, @RequestParam String password, @RequestParam String age, @RequestParam String role, Model model) {
        int age1 = Integer.valueOf(age);
        int id1 = Integer.valueOf(id);
        User user = new User(id1, name, password, age1);
        user.setRolesOnForm(role);
        System.out.println(user);
        userService.edit(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        DefaultOidcUser userOAuth = (DefaultOidcUser) auth.getPrincipal();
        String role = userOAuth.getAuthorities().toArray()[0].toString();
        String email = (String) userOAuth.getAttributes().get("email");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(role));
        User user = new User(email, null, 0);
        user.setRolesOnForm(role);

        userService.add(user);
        return "admin";
    }





}
