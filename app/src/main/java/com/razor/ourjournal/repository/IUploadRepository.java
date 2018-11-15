package com.razor.ourjournal.repository;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;

import java.util.List;

public interface IUploadRepository {
    void uploadImageUriList(String postGroupId, String postId, List<Uri> imageUriList);

    StorageReference getDownloadUrlPost(String postId, String userEmailPostedBy, String userEmailPostedFor);
}
