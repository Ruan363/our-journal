package com.razor.ourjournal.screens.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.razor.ourjournal.R;
import com.razor.ourjournal.screens.link_partner.view.LinkPartnerActivity;
import com.razor.ourjournal.screens.login.viewmodel.LoginActivityViewModel;
import com.razor.ourjournal.repository.SharedPreferencesRepository;
import com.razor.ourjournal.screens.signup.SignUpActivity;
import com.razor.ourjournal.screens.timeline.view.TimelineActivity;

/**
 * A signInViaGoogleApi screen that offers signInViaGoogleApi via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginActivityView, GoogleApiClient.OnConnectionFailedListener, OnClickListener {

    private static final int SIGN_IN_CODE = 100;
    private static final String TAG = LoginActivity.class.getSimpleName();

    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private LoginActivityViewModel viewModel;

    // UI references.
    LinearLayout superLayout;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpUiElements();

        setupGoogleSignIn();

        viewModel = new LoginActivityViewModel(this, new SharedPreferencesRepository(getApplicationContext()));
    }

    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        viewModel.handleFirebaseUser(firebaseAuth.getCurrentUser());
    }

    TextView.OnEditorActionListener passwordEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
            if (id == 12345 || id == EditorInfo.IME_NULL) {
                    viewModel.signInClicked();
                return true;
            }
            return false;
        }
    };

    private void setUpUiElements() {
        superLayout = findViewById(R.id.superLayout);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mPasswordView.setOnEditorActionListener(passwordEditorActionListener);

//        SignInButton signInButton = findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);
//        signInButton.setOnClickListener(this);
        findViewById(R.id.sign_up_button).setOnClickListener(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        viewModel.connectionFailed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.sign_in_button:
//                viewModel.signInClicked();
//                break;
            case R.id.sign_up_button:
                viewModel.signUpClicked();
        }
    }

    @Override
    public void signInViaGoogleApi() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, SIGN_IN_CODE);
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

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            viewModel.handleGoogleSignInResult(result);
        }
    }

    @Override
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        viewModel.firebaseSignInComplete(task);
                    }
                });
    }

    @Override
    public void showSnackbar(@StringRes int message) {
        Snackbar.make(superLayout, getString(message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void goToSignUpActivity() {
        Intent destination = new Intent(this, SignUpActivity.class);
        startActivity(destination);
    }
}

