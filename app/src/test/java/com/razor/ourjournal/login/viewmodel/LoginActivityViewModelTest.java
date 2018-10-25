package com.razor.ourjournal.login.viewmodel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razor.ourjournal.R;
import com.razor.ourjournal.login.view.LoginActivityView;
import com.razor.ourjournal.repository.ISharedPreferencesRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginActivityViewModelTest {
    @Mock
    LoginActivityView view;
    @Mock
    ISharedPreferencesRepository sharedPreferencesRepository;
    @Mock
    FirebaseAuth firebaseAuth;

    LoginActivityViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        viewModel = new LoginActivityViewModel(view, sharedPreferencesRepository);
    }

    @Test
    public void login_when_signInButtonClicked() throws Exception {
        viewModel.signInClicked();

        verify(view).signInViaGoogleApi();
    }

    @Test
    public void checkSharedPreffsForLinkedPartner_if_firebaseUserNotNull() throws Exception {
        FirebaseUser firebaseUser = mock(FirebaseUser.class);

        viewModel.handleFirebaseUser(firebaseUser);

        verify(sharedPreferencesRepository).hasPartner();
    }

    @Test
    public void gotToTimelineActivity_ifUserNotNull_and_hasPartner() throws Exception {
        when(sharedPreferencesRepository.hasPartner()).thenReturn(true);

        FirebaseUser firebaseUser = mock(FirebaseUser.class);

        viewModel.handleFirebaseUser(firebaseUser);

        verify(view).navigateToTimelineActivity();
    }

    @Test
    public void gotToLinkPartnerActivity_ifUserNotNull_and_hasPartnerFalse() throws Exception {
        when(sharedPreferencesRepository.hasPartner()).thenReturn(false);

        FirebaseUser firebaseUser = mock(FirebaseUser.class);

        viewModel.handleFirebaseUser(firebaseUser);

        verify(view).navigateToLinkPartnerActivity();
    }

    @Test
    public void firebaseAuthWithGoogle_when_googleSignIn_successful() throws Exception {
        GoogleSignInResult googleSignInResult = mock(GoogleSignInResult.class);
        GoogleSignInAccount googleSignInAccount = mock(GoogleSignInAccount.class);

        when(googleSignInResult.isSuccess()).thenReturn(true);
        when(googleSignInResult.getSignInAccount()).thenReturn(googleSignInAccount);

        viewModel.handleGoogleSignInResult(googleSignInResult);

        verify(view).firebaseAuthWithGoogle(googleSignInAccount);
    }

    @Test
    public void showSnackbar_when_googleSignIn_unsuccessful() throws Exception {
        GoogleSignInResult googleSignInResult = mock(GoogleSignInResult.class);

        when(googleSignInResult.isSuccess()).thenReturn(false);

        viewModel.handleGoogleSignInResult(googleSignInResult);

        verify(view).showSnackbar(R.string.error_sign_in_failed);
    }

    @Test
    public void goToTimelineActivity_when_firebaseSignIn_successful() throws Exception {
        Task authTask = mock(Task.class);

        when(authTask.isSuccessful()).thenReturn(true);

        viewModel.firebaseSignInComplete(authTask);

        verify(view).navigateToTimelineActivity();
    }

    @Test
    public void showSnackbar_when_firebaseSignIn_unsuccessful() throws Exception {
        Task authTask = mock(Task.class);

        when(authTask.isSuccessful()).thenReturn(false);

        viewModel.firebaseSignInComplete(authTask);

        verify(view).showSnackbar(R.string.error_authentication_failed);
    }

    @Test
    public void showSnackbar_when_connection_failed() throws Exception {
        viewModel.connectionFailed();

        verify(view).showSnackbar(R.string.error_connection_failed);
    }

    @Test
    public void goToSignUpActivity_when_signUpClicked() {
        viewModel.signUpClicked();

        verify(view).goToSignUpActivity();
    }
}