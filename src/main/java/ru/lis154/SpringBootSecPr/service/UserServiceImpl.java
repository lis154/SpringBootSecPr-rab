package ru.lis154.SpringBootSecPr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lis154.SpringBootSecPr.DAO.UserDAO;
import ru.lis154.SpringBootSecPr.Model.User;

import java.util.List;

@Service
@Transactional
//@EnableAspectJAutoProxy(proxyTargetClass=true)
public class UserServiceImpl implements UserDetailsService, UserService {


    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public List<User> allUser(int page) {
        return userDAO.allUser(page);
    }

    @Transactional
    public void add(User user) {
        userDAO.add(user);
    }


    @Transactional
    public void delete(int id) {
        userDAO.delete(id);
    }

    @Transactional
    public void edit(User user) {
        userDAO.edit(user);
    }

    @Transactional
    public User getById(int id) {
        return userDAO.getById(id);
    }

    @Transactional
    public int userCount() {
        return userDAO.userCount();
    }

    public User loadUserByUsername(String s) {
        return userDAO.getUserByName(s);
    }

}
