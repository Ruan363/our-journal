package com.razor.ourjournal.screens.timeline.viewmodel;


import android.os.Bundle;
import android.support.annotation.NonNull;

import com.razor.ourjournal.repository.PostRepository;
import com.razor.ourjournal.repository.SharedPreferencesRepository;
import com.razor.ourjournal.repository.UserRepository;
import com.razor.ourjournal.screens.timeline.model.Post;
import com.razor.ourjournal.screens.timeline.view.TimelineFragmentView;

import java.util.ArrayList;

import static com.razor.ourjournal.constant.TimelineConstant.BUNDLE_IDENTIFIER.ERROR_MESSAGE;
import static com.razor.ourjournal.constant.TimelineConstant.BUNDLE_IDENTIFIER.RESULT;

public class TimelineFragmentViewModel {

    private final TimelineFragmentView view;
    private final PostRepository postRepository;
    private final SharedPreferencesRepository sharedPreferencesRepository;
    private final UserRepository userRepository;

    public TimelineFragmentViewModel(TimelineFragmentView view, PostRepository postRepository, SharedPreferencesRepository sharedPreferencesRepository, UserRepository userRepository) {
        this.view = view;
        this.postRepository = postRepository;
        this.sharedPreferencesRepository = sharedPreferencesRepository;
        this.userRepository = userRepository;
    }

    public void fetchPosts() {
        String userEmail = userRepository.getFirebaseUser().getEmail();
        String partnerEmail = sharedPreferencesRepository.getPartnerEmail();
        postRepository.getPosts(Post.getPostId(userEmail, partnerEmail));
    }

    public void onFetchPostsCancelled(@NonNull Bundle bundle) {
        String errorMessage = bundle.getString(ERROR_MESSAGE);
        view.showSnackbar(errorMessage);

        view.navigateToErrorFragment(errorMessage);
    }

    public void onFetchPostsSuccess(@NonNull Bundle bundle) {
        ArrayList<Post> posts = bundle.getParcelableArrayList(RESULT);
        view.displayPosts(posts);
    }

    public void addPost() {
        view.navigateToAddPostActivity();
    }
}
