package com.campresv.dto;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class UserDto {

    @Id
    @GeneratedValue
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name ="first_name")
    private String firstName;

    @Column(name ="lastName")
    private String lastName;

    @Column(name ="email")
    private String email;

    public UserDto() { }

    public UserDto(String firstName, String last_name, String email) {
        this.firstName = firstName;
        this.lastName = last_name;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

