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
import ru.lis154.SpringBootSecPr.Model.User;
import ru.lis154.SpringBootSecPr.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    //    int userCount = userService.userCount();
     //   int pageCout = (userCount + 9) / 10;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

       // System.out.println("++++++++++++++" + auth);


        String nameUser= "";
        if (auth.getClass().toString().equals("class org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken")){
            DefaultOidcUser userOAuth = (DefaultOidcUser) auth.getPrincipal();
             nameUser = (String) userOAuth.getAttributes().get("email");
         //   System.out.println("if ==== " + nameUser);
        } else {
             nameUser = auth.getName();
       //     System.out.println("else ====" + nameUser);
        }

      //  System.out.println("Class ------ " + auth.getClass());

       // String nameUser = auth.getName();

//        DefaultOidcUser userOAuth = (DefaultOidcUser) auth.getPrincipal();
//        String nameUser = (String) userOAuth.getAttributes().get("email");
    //    System.out.println("Name-for-GOOGLE-auth---------" + nameUser);
   //     System.out.println(auth);
        User user1 = (User) userService.loadUserByUsername(nameUser);
        String roles = user1.getListRoles();
        model.addAttribute("users", listUser);
        model.addAttribute("email", nameUser);
        model.addAttribute("role", roles);

        System.out.println(listUser);

        return "admin";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String OneUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nameUser= "";
        if (auth.getClass().toString().equals("class org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken")){
            DefaultOidcUser userOAuth = (DefaultOidcUser) auth.getPrincipal();
            nameUser = (String) userOAuth.getAttributes().get("email");
            System.out.println("if googel ==== " + nameUser);
        } else {
            nameUser = auth.getName();
            System.out.println("else local ====" + nameUser);
        }


        User user1 = (User) userService.loadUserByUsername(nameUser);
        //  User listUser = (User) userService.loadUserByUsername(name);
        // model.addAttribute("user", listUser);
        System.out.println("getAuthoritis +  USER " + user1.getAuthorities());
        model.addAttribute("user", user1);
        return "user";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
    public String UserADD(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nameUser= "";
        if (auth.getClass().toString().equals("class org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken")){
            DefaultOidcUser userOAuth = (DefaultOidcUser) auth.getPrincipal();
            nameUser = (String) userOAuth.getAttributes().get("email");
        //    System.out.println("if ==== " + nameUser);
        } else {
            nameUser = auth.getName();
        //    System.out.println("else ====" + nameUser);
        }
       // System.out.println("__________________________" + nameUser);
        User user1 = (User) userService.loadUserByUsername(nameUser);
        String roles = user1.getListRoles();
        model.addAttribute("email", nameUser);
        model.addAttribute("role", roles);
        return "add";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public String AddUserADD(@RequestParam String email, @RequestParam String password, @RequestParam String age, @RequestParam String role, Model model) {
        int age1 = Integer.valueOf(age);
        User user = new User(email, password, age1);
        user.setRolesOnForm(role);
      //  System.out.println(user);
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
    public String EdUserEdit(@RequestParam String id, @RequestParam String email, @RequestParam String password, @RequestParam String age, @RequestParam String role, Model model) {
        int age1 = Integer.valueOf(age);
        int id1 = Integer.valueOf(id);
        User user = new User(id1, email, password, age1);
        user.setRolesOnForm(role);
      //  System.out.println(user);
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
      //  System.out.println("test");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      //  System.out.println(auth);
        DefaultOidcUser userOAuth = (DefaultOidcUser) auth.getPrincipal();
        String role = userOAuth.getAuthorities().toArray()[0].toString();
        String email = (String) userOAuth.getAttributes().get("email");
        String name = (String) userOAuth.getAttributes().get("name");

        String nameUser = auth.getName();
      //  System.out.println("Name-for-GOOGLE-auth---------" + nameUser);
      //  System.out.println(auth.toString());


      //  System.out.println(email);



        if (userService.hasNmaOnDB(email) == false) {
            User user = new User(email, null, 0);
            user.setRolesOnForm(role);
            userService.add(user);
        }
       // System.out.println(userService.hasNmaOnDB(email));

        return "admin";
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public String test2() {
        return "test2";
    }




}
