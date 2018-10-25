package com.razor.ourjournal.screens.login.viewmodel;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.razor.ourjournal.R;
import com.razor.ourjournal.screens.login.view.LoginActivityView;
import com.razor.ourjournal.repository.ISharedPreferencesRepository;

public class LoginActivityViewModel {

    private LoginActivityView view;

    private ISharedPreferencesRepository sharedPreferencesRepository;

    public LoginActivityViewModel(LoginActivityView view, ISharedPreferencesRepository sharedPreferencesRepository) {
        this.view = view;
        this.sharedPreferencesRepository = sharedPreferencesRepository;
    }

    public void signInClicked() {
        view.signInViaGoogleApi();
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

    public void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            view.firebaseAuthWithGoogle(account);
        } else {
            view.showSnackbar(R.string.error_sign_in_failed);
        }
    }

    public void firebaseSignInComplete(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            view.navigateToTimelineActivity();
        } else {
            view.showSnackbar(R.string.error_authentication_failed);
        }
    }

    public void connectionFailed() {
        view.showSnackbar(R.string.error_connection_failed);
    }

    public void signUpClicked() {
        view.goToSignUpActivity();
    }
}
