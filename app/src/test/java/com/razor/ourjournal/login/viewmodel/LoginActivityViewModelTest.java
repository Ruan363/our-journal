package com.razor.ourjournal.login.viewmodel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razor.ourjournal.R;
import com.razor.ourjournal.screens.login.view.LoginActivityView;
import com.razor.ourjournal.repository.ISharedPreferencesRepository;
import com.razor.ourjournal.screens.login.viewmodel.LoginActivityViewModel;

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
    public void goToSignUpActivity_when_signUpClicked() {
        viewModel.signUpClicked();

        verify(view).goToSignUpActivity();
    }
}