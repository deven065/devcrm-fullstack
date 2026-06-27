package com.deven.devcrm.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "clients")
public class Client {

    //  Create a database table named Client for this java class
    @Id //  id is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  database will generate id automatically
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String companyName;

    public Client() {
    }

    public Client(String name, String email, String phone, String companyName) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.companyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
