package com.razor.ourjournal.screens.login.viewmodel;


import com.google.firebase.auth.FirebaseUser;
import com.razor.ourjournal.repository.ISharedPreferencesRepository;
import com.razor.ourjournal.screens.login.view.LoginActivityView;

public class LoginActivityViewModel {

    private LoginActivityView view;

    private ISharedPreferencesRepository sharedPreferencesRepository;

    public LoginActivityViewModel(LoginActivityView view, ISharedPreferencesRepository sharedPreferencesRepository) {
        this.view = view;
        this.sharedPreferencesRepository = sharedPreferencesRepository;
    }

    public void handleFirebaseUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            if (sharedPreferencesRepository.hasPartner()) {
                view.navigateToTimelineActivity();
            } else {
                view.navigateToLinkPartnerActivity();
            }
        }
    }

    public void signUpClicked() {
        view.goToSignUpActivity();
    }
}
