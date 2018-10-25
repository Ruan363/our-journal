package com.razor.ourjournal.screens.signup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.razor.ourjournal.R;
import com.razor.ourjournal.repository.IRepositoryCallback;
import com.razor.ourjournal.repository.UserRepository;
import com.razor.ourjournal.utils.validator.EmailValidator;
import com.razor.ourjournal.utils.validator.MatchValidator;
import com.razor.ourjournal.utils.validator.PasswordValidator;
import com.razor.ourjournal.utils.validator.TextValidator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements IRepositoryCallback {

    @BindView(R.id.first_name_text) TextInputEditText firstNameText;
    @BindView(R.id.first_name_text_layout) TextInputLayout firstNameTextLayout;
    @BindView(R.id.last_name_text) TextInputEditText lastNameText;
    @BindView(R.id.last_name_text_layout) TextInputLayout lastNameTextLayout;
    @BindView(R.id.email_text) AutoCompleteTextView emailText;
    @BindView(R.id.email_text_layout) TextInputLayout emailTextLayout;
    @BindView(R.id.password_text) TextInputEditText passwordText;
    @BindView(R.id.password_text_layout) TextInputLayout passwordTextLayout;
    @BindView(R.id.password_confirm_text) TextInputEditText passwordConfirmText;
    @BindView(R.id.password_confirm_text_layout) TextInputLayout passwordConfirmTextLayout;
    @BindView(R.id.sign_up_button) Button signUpButton;

    private static final int PASSWORD_MIN_LENGTH = 8;
    private SignUpActivityViewModel viewModel;
    private TextValidator firstNameTextValidator;
    private TextValidator lastNameTextValidator;
    private EmailValidator emailTextValidator;
    private PasswordValidator passwordTextValidator;
    private MatchValidator confirmPasswordTextValidator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        viewModel = new SignUpActivityViewModel(new UserRepository());

        setupValidation();
    }

    private void setupValidation() {
        firstNameTextValidator = new TextValidator(firstNameTextLayout, getString(R.string.error_message_required));
        lastNameTextValidator = new TextValidator(lastNameTextLayout, getString(R.string.error_message_required));
        emailTextValidator = new EmailValidator(emailTextLayout, getString(R.string.error_message_required));
        passwordTextValidator = new PasswordValidator(lastNameTextLayout, getString(R.string.error_message_required),
                getString(R.string.error_message_too_short), PASSWORD_MIN_LENGTH);
        confirmPasswordTextValidator = new MatchValidator(passwordConfirmTextLayout, getString(R.string.error_message_required), passwordConfirmText);


    }

    @OnClick(R.id.sign_up_button)
    public void signUpClicked() {
        if (formValid())
        viewModel.signUpClicked();
    }

    private boolean formValid() {
        return firstNameTextValidator.validate(true) && lastNameTextValidator.validate(false) &&
                emailTextValidator.validate(false) && passwordTextValidator.validate(false) &&
                confirmPasswordTextValidator.validate(false);
    }

    @Override
    public void onSuccess(@NonNull Bundle bundle) {

    }

    @Override
    public void onCancelled(@NonNull Bundle bundle) {

    }
}
