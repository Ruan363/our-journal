package com.razor.ourjournal.timeline.view;


import com.razor.ourjournal.timeline.model.Post;

import java.util.ArrayList;
import java.util.List;

public interface TimelineFragmentView {
    void displayPosts(List<Post> posts);

    void showSnackbar(String message);

    void navigateToAddPostActivity();

    void navigateToErrorFragment(String errorMessage);
}
