package ru.lis154.SpringBootSecPr.service;

import org.springframework.data.repository.CrudRepository;
import ru.lis154.SpringBootSecPr.Model.User;


public interface UsersRepo extends CrudRepository<User, Integer> {
}
