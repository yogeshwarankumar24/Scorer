package com.scorer.ModalClass;

/**
 * Created by impowersolutionspvtltd on 31/05/17.
 */

public class ContactModel {
    private String id;
    private String name;
    private String email;
    private String mobile;
    private String gender;
    private String image;

    public ContactModel() {
    }

    public ContactModel(String id, String name, String email, String mobile, String gender, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.image = image;
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
    public String getmobile() {
        return mobile;
    }

    public void setmobile(String mobile) {
        this.mobile = mobile;
    }

    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }
    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.gender = image;
    }
}
