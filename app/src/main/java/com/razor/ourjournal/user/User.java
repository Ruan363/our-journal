package com.razor.ourjournal.user;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String partnerEmail;
    private String password;

    public User(String username, String partnerEmail) {
        this.username = username;
        this.partnerEmail = partnerEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }

    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }
}
