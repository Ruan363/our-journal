package com.razor.ourjournal.screens.add_post.viewmodel;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
import com.razor.ourjournal.repository.IUploadRepository;
import com.razor.ourjournal.screens.add_post.view.AddPostActivityView;
import com.razor.ourjournal.repository.IPostRepository;
import com.razor.ourjournal.repository.ISharedPreferencesRepository;
import com.razor.ourjournal.repository.IUserRepository;
import com.razor.ourjournal.screens.timeline.model.Post;

import java.util.List;

public class AddPostActivityViewModel {
    private AddPostActivityView view;
    private String title;
    private String description;
    private IPostRepository postRepository;
    private FirebaseUser firebaseUser;
    private final IUploadRepository uploadRepository;
    private ISharedPreferencesRepository sharedPreferencesRepository;
    private Post post;

    public AddPostActivityViewModel(AddPostActivityView view, IPostRepository postRepository, ISharedPreferencesRepository sharedPreferencesRepository, IUserRepository userRepository, IUploadRepository uploadRepository) {
        this.view = view;
        this.postRepository = postRepository;
        this.sharedPreferencesRepository = sharedPreferencesRepository;
        this.uploadRepository = uploadRepository;
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
    }

    private String getPartnerEmail() {
        return sharedPreferencesRepository.getPartnerEmail();
    }

    public void postOnSuccess() {
        view.hideProgressLoader();

        view.enableUiElements();

        view.closeScreen();
    }

    public void uploadPost(List<String> downloadUrlList) {
        post.setDownloadUrlList(downloadUrlList);
        postRepository.addPost(post);
    }

    public void uploadAttachments(@NonNull List<Uri> imageList) {
        String userEmail = firebaseUser.getEmail();
        String partnerEmail = getPartnerEmail();

        uploadRepository.uploadImageUriList(Post.getPostId(userEmail, partnerEmail), String.valueOf(post.hashCode()), imageList);
    }

    public void generatePost() {
        String userEmail = firebaseUser.getEmail();
        String partnerEmail = getPartnerEmail();

        post = new Post(title, description, userEmail, partnerEmail);
    }
}
