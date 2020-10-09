//package test.DAO;
//
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import test.model.Role;
//import test.model.User;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import java.util.HashSet;
//import java.util.List;
//
//@Repository
//@Transactional
//@EnableAspectJAutoProxy(proxyTargetClass=true)
//public class UserDAOImpl implements UserDAO{
//
//    EntityManager entityManager;
//
//    public EntityManager getEntityManager() {
//        return entityManager;
//    }
//    @PersistenceContext
//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//
//    @Override
//    public List<User> allUser(int page) {
//        Query query = getEntityManager().createQuery("select c from User c");
//       // List<User> resultList = query.getResultList();
//        List<User> resultList = query.setFirstResult(10*(page-1)).setMaxResults(10).getResultList();
//        return resultList;
//    }
//
//
//    @Override
//    public void add(User user) {
//        Long lon = 5L;
//        HashSet<Role> str = new HashSet<>();
//        str.add(new Role(lon, "ROLE_USER"));
//        User user1 = new User (5,"ilya2", "qwe", 15,str);
//        getEntityManager().merge(user1);
//    }
//
//
//    @Override
//    public void delete(int id) {
//        EntityManager em =  getEntityManager();
//        User user = em.find(User.class, id);
//        getEntityManager().remove(user);
//    }
//
//
//    @Override
//    public void edit(User user) {
//        getEntityManager().merge(user);
//    }
//
//
//    @Override
//    public User getById(int id) {
//        return getEntityManager().find(User.class, id);
//    }
//
//
//    @Override
//    public int userCount() {
//        Query query = entityManager.createQuery("SELECT COUNT(*) FROM User " );
//
//        Query query1 = entityManager.createQuery("select count(ut) From User ut");
//        int rez = ((Number) query.getSingleResult()).intValue();
//        System.out.println(rez);
//        return rez;
//    }
//
//    @Override
//    public User getUserByName(String name) {
//        Query query = entityManager.createQuery("FROM User where name = :name");
//        query.setParameter("name", name);
//        return (User)query.getSingleResult();
//
//        //return getEntityManager().find(User.class, name);
//    }
//
//
//}

package ru.lis154.SpringBootSecPr.DAO;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lis154.SpringBootSecPr.Model.Role;
import ru.lis154.SpringBootSecPr.Model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;

@Repository
@Transactional
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class UserDAOImpl implements UserDAO {

    EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<User> allUser(int page) {
        Query query = getEntityManager().createQuery("select c from User c");
       // List<User> resultList = query.setFirstResult(10 * (page - 1)).setMaxResults(10).getResultList();
        List<User> resultList = query.getResultList();
        return resultList;
    }


    @Override
    public void add(User user) {
        EntityManager entityManager = getEntityManager();
        Long lon = 5L;
        HashSet<Role> str = new HashSet<>();
        str.add(new Role(lon, "ROLE_USER"));
        User user1 = new User(10, "ilya2", "qwe", 15, str);
        entityManager.merge(user);
    }


    @Override
    public void delete(int id) {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, id);
        getEntityManager().remove(user);
    }


    @Override
    public void edit(User user) {
        getEntityManager().merge(user);
    }


    @Override
    public User getById(int id) {
        return getEntityManager().find(User.class, id);
    }

    @Override
    public User getUserByName(String name) {
        Query query = entityManager.createQuery("FROM User where name = :name");
        query.setParameter("name", name);
        return (User) query.getSingleResult();


    }


    @Override
    public int userCount() {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM User ");

        int rez = ((Number) query.getSingleResult()).intValue();
        System.out.println(rez);
        return rez;
    }


}
