package com.razor.ourjournal.repository;


import com.razor.ourjournal.timeline.model.Post;

public interface IPostRepository {
    void getPosts();

    void addPost(Post post);
}
