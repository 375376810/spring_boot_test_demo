package com.yxm.project.spring_boot_test_demo.domain;


/**
 * Created with IntelliJ IDEA.
 * Author: YXM
 * Date: 2020/2/7
 * Time: 16:47
 * Description:
 */
public class User {

    private Long id;
    private String username;
    private String password;
    private String name;

    public User() {
    }

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", name='" + name + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
