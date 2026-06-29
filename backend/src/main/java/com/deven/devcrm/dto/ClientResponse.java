//  This is used when backend sends data back to frontend / Postman

package com.deven.devcrm.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class ClientResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String companyName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ClientResponse(Long id, String name, String email, String phone, String companyName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.companyName = companyName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
