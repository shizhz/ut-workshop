package org.shizhz.domain;

import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.AUTO;

/**
 * Created by zzshi on 8/31/14.
 */
@Entity
@Table
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 50, nullable = false, unique = true)
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
        this.password = new StrongPasswordEncryptor().encryptPassword(password);
    }

    @Column(length = 100, nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
