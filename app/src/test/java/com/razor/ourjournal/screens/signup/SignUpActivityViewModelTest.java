package com.razor.ourjournal.screens.signup;

import com.razor.ourjournal.repository.IUserRepository;
import com.razor.ourjournal.user.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class SignUpActivityViewModelTest {

    @Mock
    private IUserRepository userRepository;

    private SignUpActivityViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        viewModel = new SignUpActivityViewModel(userRepository);
    }

    @Test
    public void signUpClicked() {
        viewModel.signUpClicked();

        User user = new User("", "");

        verify(userRepository).registerUser(user);
    }
}