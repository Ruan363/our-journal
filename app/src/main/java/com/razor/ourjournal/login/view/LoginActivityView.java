package com.razor.ourjournal.login.view;


import android.support.annotation.StringRes;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface LoginActivityView {
    void signInViaGoogleApi();

    void navigateToTimelineActivity();

    void firebaseAuthWithGoogle(GoogleSignInAccount account);

    void showSnackbar(@StringRes int message);

    void navigateToLinkPartnerActivity();
}
