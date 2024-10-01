package com.example.casebe.Model;


import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usn;
    private String pass;
    private String dob;
    private String phone;
    private String address;


    @ManyToOne
    private Role role;


    public Person() {
    }

    public Person(Long id, String usn, String pass, String phoneNumber, String dob, String phone, String address, Role role) {
        this.id = id;
        this.usn = usn;
        this.pass = pass;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
