package com.razor.ourjournal.screens.timeline.view;


import com.razor.ourjournal.screens.timeline.model.Post;

import java.util.List;

public interface TimelineFragmentView {
    void displayPosts(List<Post> posts);

    void showSnackbar(String message);

    void navigateToAddPostActivity();

    void navigateToErrorFragment(String errorMessage);
}
