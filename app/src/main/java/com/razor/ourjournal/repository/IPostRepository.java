package com.razor.ourjournal.repository;


import com.razor.ourjournal.screens.timeline.model.Post;

public interface IPostRepository {
    void getPosts(String postId);

    void addPost(Post post);
}
