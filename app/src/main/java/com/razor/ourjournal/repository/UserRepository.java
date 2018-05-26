package com.razor.ourjournal.repository;


import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razor.ourjournal.user.User;

import static com.razor.ourjournal.constant.FirebaseConstant.PATHS.USERS;

public class UserRepository implements IUserRepository {

    FirebaseUser firebaseUser;
    private DatabaseReference usersDatabaseReference;
    private IRepositoryCallback repositoryCallback;

    public UserRepository() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public UserRepository(IRepositoryCallback repositoryCallback) {
        this.repositoryCallback = repositoryCallback;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child(USERS);
    }

    @Override
    public void linkWithPartner(String partnerEmail) {
        usersDatabaseReference.child(firebaseUser.getUid()).setValue(new User(firebaseUser.getEmail(), partnerEmail)).addOnCompleteListener(partnerLinkListener);
    }

    @Override
    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    OnCompleteListener partnerLinkListener = new OnCompleteListener() {
        @Override
        public void onComplete(@NonNull Task task) {
            repositoryCallback.onSuccess(new Bundle());
        }
    };
}
