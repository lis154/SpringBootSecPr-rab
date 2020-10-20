package ru.lis154.SpringBootSecPr.service;


import ru.lis154.SpringBootSecPr.Model.User;

import java.util.List;

public interface UserService {
    List<User> allUser(int page);

    void add(User user);

    void delete(int id);

    void edit(User user);

    User getById(int id);

    User getByName(String name);

    public int userCount();
}
