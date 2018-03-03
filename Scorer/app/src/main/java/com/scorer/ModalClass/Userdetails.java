package com.scorer.ModalClass;

/**
 * Created by impowersolutionspvtltd on 31/05/17.
 */

public class Userdetails {
    private String id;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String Location;

    public Userdetails() {
    }

    public Userdetails(String id, String name, String email, String password, String gender, String Location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.Location = Location;
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

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }
    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }
    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.gender = Location;
    }
}
