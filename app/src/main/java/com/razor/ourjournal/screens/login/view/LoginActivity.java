package com.razor.ourjournal.screens.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razor.ourjournal.R;
import com.razor.ourjournal.repository.SharedPreferencesRepository;
import com.razor.ourjournal.screens.link_partner.view.LinkPartnerActivity;
import com.razor.ourjournal.screens.login.viewmodel.LoginActivityViewModel;
import com.razor.ourjournal.screens.timeline.view.TimelineActivity;

import java.util.Arrays;
import java.util.List;

/**
 * A signInViaGoogleApi screen that offers signInViaGoogleApi via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginActivityView, OnClickListener {

    private static final int FIREBASE_UI_SIGN_UP = 123;
    private static final String TAG = LoginActivity.class.getSimpleName();

    private FirebaseAuth firebaseAuth;
    private LoginActivityViewModel viewModel;

    // UI references.
    LinearLayout superLayout;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpUiElements();

        firebaseAuth = FirebaseAuth.getInstance();

        viewModel = new LoginActivityViewModel(this, new SharedPreferencesRepository(getApplicationContext()));
    }

    @Override
    public void onStart() {
        super.onStart();

        viewModel.handleFirebaseUser(firebaseAuth.getCurrentUser());
    }

    private void setUpUiElements() {
        superLayout = findViewById(R.id.superLayout);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        findViewById(R.id.sign_up_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_button:
                viewModel.signUpClicked();
        }
    }

    @Override
    public void navigateToTimelineActivity() {
        Intent destination = new Intent(this, TimelineActivity.class);
        startActivity(destination);
        finish();
    }

    @Override
    public void navigateToLinkPartnerActivity() {
        Intent destination = new Intent(this, LinkPartnerActivity.class);
        startActivity(destination);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FIREBASE_UI_SIGN_UP) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                viewModel.handleFirebaseUser(FirebaseAuth.getInstance().getCurrentUser());
            } else {
                if (response == null) {
                    // User cancelled sign-in flow
                } else if (response.getError() != null) {
                    // Handle log-in error
                }
            }
        }
    }

    @Override
    public void goToSignUpActivity() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.mipmap.our_journal_app_icon)
                        .build(),
                FIREBASE_UI_SIGN_UP);

    }
}

