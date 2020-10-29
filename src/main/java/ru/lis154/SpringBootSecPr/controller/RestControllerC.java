package ru.lis154.SpringBootSecPr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.lis154.SpringBootSecPr.Model.User;
import ru.lis154.SpringBootSecPr.service.UserServiceImpl;

import java.util.List;

@RestController

public class RestControllerC {
    private int page;
    private final UserServiceImpl userService;

    @Autowired
    public RestControllerC(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/admin/edit/json", method = RequestMethod.POST)
    public User EdUserEdit(@RequestBody User user) {
        System.out.println("edit_user"  + user);
        userService.edit(user);
        System.out.println("edit_user"  + user);
       // System.out.println(user);
        return user;
    }

    @RequestMapping(value = "/admin/delete/json", method = RequestMethod.POST)
    public User DelUser(@RequestBody User user) {

        userService.delete(user.getId());
      //  System.out.println("DELETE-USER" + user);
        return user;
    }

    @RequestMapping(value = "/admin/json", method = RequestMethod.POST)
    public List<User> EdUserAll() {
        List<User> listUser = userService.allUser(page);

       // System.out.println(listUser);
        User user = listUser.get(1);
       // System.out.println(user);
        return  listUser;
    }


}
