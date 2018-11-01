package com.razor.ourjournal.screens.signup;

import com.razor.ourjournal.repository.IUserRepository;
import com.razor.ourjournal.user.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class SignUpActivityViewModelTest {

    @Mock
    private IUserRepository userRepository;

    private SignUpActivityViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        viewModel = new SignUpActivityViewModel(userRepository);
    }

    @Test
    public void signUpClicked() {
        viewModel.signUpClicked();

        verify(userRepository).registerUser(any(User.class));
    }
}