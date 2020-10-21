package ru.lis154.SpringBootSecPr.DAO;


import ru.lis154.SpringBootSecPr.Model.User;

import java.util.List;

public interface UserDAO {

    List<User> allUser(int page);

    void add(User user);

    void delete(int id);

    void edit(User user);

    User getById(int id);

    public int userCount();

    public User getUserByName(String name);

    public boolean hasNmaOnDB(String name);
}
