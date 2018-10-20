package com.razor.ourjournal.screens.add_post.viewmodel;


import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
import com.razor.ourjournal.screens.add_post.view.AddPostActivityView;
import com.razor.ourjournal.repository.IPostRepository;
import com.razor.ourjournal.repository.ISharedPreferencesRepository;
import com.razor.ourjournal.repository.IUserRepository;
import com.razor.ourjournal.screens.timeline.model.Post;

public class AddPostActivityViewModel {
    private AddPostActivityView view;
    private String title;
    private String description;
    private IPostRepository postRepository;
    private FirebaseUser firebaseUser;
    private ISharedPreferencesRepository sharedPreferencesRepository;

    public AddPostActivityViewModel(AddPostActivityView view, IPostRepository postRepository, ISharedPreferencesRepository sharedPreferencesRepository, IUserRepository userRepository) {
        this.view = view;
        this.postRepository = postRepository;
        this.sharedPreferencesRepository = sharedPreferencesRepository;
        firebaseUser = userRepository.getFirebaseUser();
    }

    public void titleChanged(String title) {
        this.title = title.trim();
        validateToShowPostButton();
    }

    public void descriptionChanged(String description) {
        this.description = description.trim();
        validateToShowPostButton();
    }

    private void validateToShowPostButton() {
        boolean titleEmpty = TextUtils.isEmpty(title);
        boolean descriptionEmpty = TextUtils.isEmpty(description);
        if (!titleEmpty && !descriptionEmpty) {
            view.showPostButton();
        }
        else {
            view.hidePostButton();
        }
    }

    public void postClicked() {
        view.disableUiElements();

        view.showProgressLoader();

        addPost();
    }

    private void addPost() {
        String userEmail = firebaseUser.getEmail();
        String partnerEmail = getPartnerEmail();

        Post post = new Post(title, description, userEmail, partnerEmail);
        postRepository.addPost(post);
    }

    private String getPartnerEmail() {
        return sharedPreferencesRepository.getPartnerEmail();
    }
}
