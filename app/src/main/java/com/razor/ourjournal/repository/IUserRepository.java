package com.razor.ourjournal.repository;


import com.google.firebase.auth.FirebaseUser;

public interface IUserRepository {
    void linkWithPartner(String partnerEmail);

    FirebaseUser getFirebaseUser();
}
