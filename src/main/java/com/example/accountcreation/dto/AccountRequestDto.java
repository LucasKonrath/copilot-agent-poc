package com.example.accountcreation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class AccountRequestDto {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Zip code is required")
    @Pattern(regexp = "\\d{5}(-\\d{4})?", message = "Invalid zip code format")
    private String zipCode;
    
    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Must be at least 18 years old")
    private Integer age;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;
    
    // Constructors
    public AccountRequestDto() {}
    
    public AccountRequestDto(String name, String zipCode, Integer age, String phoneNumber) {
        this.name = name;
        this.zipCode = zipCode;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}