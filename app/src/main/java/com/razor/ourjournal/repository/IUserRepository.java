package com.razor.ourjournal.repository;


import com.google.firebase.auth.FirebaseUser;
import com.razor.ourjournal.user.User;

public interface IUserRepository {
    void linkWithPartner(String partnerEmail);

    FirebaseUser getFirebaseUser();

    void registerUser(User user);
}
