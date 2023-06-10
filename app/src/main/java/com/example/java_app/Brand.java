package com.example.java_app;

public class Brand {

    private String brandName;
    private Integer logoLink;

    public Brand(String brandName, Integer logoLink) {
        this.brandName = brandName;
        this.logoLink = logoLink;
    }

    public String getBrandName() {
        return brandName;
    }

    public Integer getLogoLink() {
        return logoLink;
    }


}

