package com.razor.ourjournal.screens.login.view;


import android.support.annotation.StringRes;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface LoginActivityView {
    void navigateToTimelineActivity();

    void navigateToLinkPartnerActivity();

    void goToSignUpActivity();
}
