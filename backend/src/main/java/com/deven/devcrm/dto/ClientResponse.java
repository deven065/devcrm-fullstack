//  This is used when backend sends data back to frontend / Postman

package com.deven.devcrm.dto;

public class ClientResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String companyName;

    public ClientResponse(Long id, String name, String email, String phone, String companyName) {
        this.id = id;
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
}
