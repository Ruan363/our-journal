package com.razor.ourjournal.timeline.viewmodel;


import android.os.Bundle;
import android.support.annotation.NonNull;

import com.razor.ourjournal.repository.PostRepository;
import com.razor.ourjournal.timeline.model.Post;
import com.razor.ourjournal.timeline.view.TimelineFragmentView;

import java.util.ArrayList;

import static com.razor.ourjournal.constant.TimelineConstant.BUNDLE_IDENTIFIER.ERROR_MESSAGE;
import static com.razor.ourjournal.constant.TimelineConstant.BUNDLE_IDENTIFIER.RESULT;

public class TimelineFragmentViewModel {

    private TimelineFragmentView view;
    private PostRepository postRepository;

    public TimelineFragmentViewModel(TimelineFragmentView view, PostRepository postRepository) {
        this.view = view;
        this.postRepository = postRepository;
    }

    public void fetchPosts() {
        postRepository.getPosts();
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
