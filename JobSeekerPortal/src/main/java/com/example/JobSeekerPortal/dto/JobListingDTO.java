package com.example.JobSeekerPortal.dto;

import com.example.JobSeekerPortal.entity.User;

public class JobListingDTO {
    private String title;
    private String description;
    private String location;
    private Long minPay;
    private Long maxPay;

    private UserDTO userDTO;

    public JobListingDTO() {
    }

    public JobListingDTO(String title, String description, String location, Long minPay, Long maxPay) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.minPay = minPay;
        this.maxPay = maxPay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getMinPay() {
        return minPay;
    }

    public void setMinPay(Long minPay) {
        this.minPay = minPay;
    }

    public Long getMaxPay() {
        return maxPay;
    }

    public void setMaxPay(Long maxPay) {
        this.maxPay = maxPay;
    }
    public UserDTO getUserDTO() {
        return userDTO;
    }
    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
