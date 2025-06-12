package com.cropdeal.orderservice.dto;

public class DealerDTO {
    private String id;
    private String name;
    private String company;

    public DealerDTO() {}

    public DealerDTO(String id, String name, String company) {
        this.id = id;
        this.name = name;
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompany(){
        return company;
    }
}
