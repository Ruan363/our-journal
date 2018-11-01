package com.razor.ourjournal.screens.signup;

import com.razor.ourjournal.repository.IUserRepository;
import com.razor.ourjournal.user.User;

class SignUpActivityViewModel {

    private final IUserRepository userRepository;

    SignUpActivityViewModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUpClicked() {
        userRepository.registerUser(new User("", ""));
    }
}
