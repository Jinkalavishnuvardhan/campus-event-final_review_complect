package com.example.campus.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_table")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    private String password;
    private String department;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imageDp;
    private String number;
    @Column(length = 2000)
    private String basicDetails;

    private String otp;
    private boolean verified = false;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getImageDp() { return imageDp; }
    public void setImageDp(String imageDp) { this.imageDp = imageDp; }
    
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    
    public String getBasicDetails() { return basicDetails; }
    public void setBasicDetails(String basicDetails) { this.basicDetails = basicDetails; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
}
