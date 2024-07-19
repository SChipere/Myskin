package com.example.myskin;

public class Doctor {
    private String doctorId;
    private String name;
    private String lastName;
    private String location;
    private String profilePictureUrl;
    private String phone;

    public Doctor(String doctorId, String name, String lastName, String location, String profilePictureUrl, String phone) {
        this.doctorId = doctorId;
        this.name = name;
        this.lastName = lastName;
        this.location = location;
        this.profilePictureUrl = profilePictureUrl;
        this.phone = phone;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocation() {
        return location;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public String getPhone() {
        return phone;
    }
}
