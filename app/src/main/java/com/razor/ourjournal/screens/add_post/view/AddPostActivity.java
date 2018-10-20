package com.razor.ourjournal.screens.add_post.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.razor.ourjournal.R;
import com.razor.ourjournal.screens.add_post.viewmodel.AddPostActivityViewModel;
import com.razor.ourjournal.repository.IRepositoryCallback;
import com.razor.ourjournal.repository.PostRepository;
import com.razor.ourjournal.repository.SharedPreferencesRepository;
import com.razor.ourjournal.repository.UserRepository;

public class AddPostActivity extends AppCompatActivity implements AddPostActivityView, View.OnClickListener, IRepositoryCallback {

    TextInputEditText titleEditText;
    TextInputEditText detailEditText;
    DatabaseReference postDatabaseReference;
    ProgressBar progPost;
    Button postButton;
    AddPostActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        initUiElements();

        viewModel = new AddPostActivityViewModel(this, new PostRepository(this, getApplicationContext()), new SharedPreferencesRepository(this), new UserRepository());
    }

    private void initUiElements() {
        titleEditText = (TextInputEditText) findViewById(R.id.edtPostTitle);
        detailEditText = (TextInputEditText) findViewById(R.id.edtPostDetail);
        postButton = (Button) findViewById(R.id.btnPost);
        progPost = (ProgressBar) findViewById(R.id.progPost);

        titleEditText.addTextChangedListener(titleTextWatcher);
        detailEditText.addTextChangedListener(detailTextWatcher);
        postButton.setOnClickListener(this);
    }

    TextWatcher titleTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            viewModel.titleChanged(editable.toString());
        }
    };

    TextWatcher detailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            viewModel.descriptionChanged(editable.toString());
        }
    };

    @Override
    public void showPostButton() {
        postButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePostButton() {
        postButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgressLoader() {
        progPost.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressLoader() {
        progPost.setVisibility(View.GONE);
    }

    @Override
    public void closeScreen() {
        finish();
    }

    @Override
    public void disableUiElements() {
        titleEditText.setEnabled(false);
        detailEditText.setEnabled(false);
        postButton.setEnabled(false);
    }

    @Override
    public void enableUiElements() {
        titleEditText.setEnabled(true);
        detailEditText.setEnabled(true);
        postButton.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        viewModel.postClicked();
    }

    @Override
    public void onSuccess(@NonNull Bundle bundle) {
        hideProgressLoader();

        enableUiElements();

        closeScreen();
    }

    @Override
    public void onCancelled(@NonNull Bundle bundle) {

    }
}
