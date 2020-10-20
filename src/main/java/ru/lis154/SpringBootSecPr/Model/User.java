package ru.lis154.SpringBootSecPr.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@Entity
@Table(name = "users")
public class User  implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "password")
    String password;

    @Column(name = "age")
    int age;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    Set<Role> roles;

    public User(int id, String name, String password, int age, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }

    public User(String name, String password, int age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public User(int id, String name, String password, int age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public User(String name, String password, int age, Set<Role> roles) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }

    public User(String email, Object name, Object password, Set<Role> roles) {
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", roles=" + roles +
                '}';
    }

    public Set<Role> setRolesOnForm(String str) {
        String[] role = str.split(" ");
        Set<Role> roles = new HashSet<Role>();
        for (String s : role) {
            roles.add(new Role(s));
        }
        this.roles = roles;
        return roles;
    }

    public String getListRoles() {
        String rol = "";
        for (Role r : roles) {
            rol = rol + r.getRole() + " ";
        }
        return rol;
    }

    public boolean isAdmin() {
        for (Role r : this.getRoles()) {
            System.out.println("+++++++++++++" + r.getRole());
            if (r.getRole().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        System.out.println("false");
        return false;
    }


    public boolean getIsAdmin() {
        for (Role r : this.getRoles()) {
            System.out.println("+++++++++++++" + r.getRole());
            if (r.getRole().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        System.out.println("false");
        return false;
    }


}
