package com.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Data
public class User {

    public static String TABLE_NAME = "users";

    private long id;

    private String userName;

    @JsonIgnore
    private String encryptedPassword;

    @JsonIgnore
    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private State state = State.enable;

    private long roleId;

    public enum State {
        enable, disable
    }

    public boolean isAdmin() {
        return userName.equals("admin");
    }

    public boolean isEnable() {
        return state == State.enable;
    }

    public User() {}

    public User(String userName, String password) {
        this.userName = userName;
        this.setEncoderPassword(password);
    }

    public void setEncoderPassword(String password) {
        if (password == null || password.length() == 0) return;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.encryptedPassword = encoder.encode(password);
    }

    public boolean validatePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, this.encryptedPassword);
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static void setTableName(String tableName) {
        TABLE_NAME = tableName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
