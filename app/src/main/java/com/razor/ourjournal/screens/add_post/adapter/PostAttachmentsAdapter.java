package com.razor.ourjournal.screens.add_post.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.razor.ourjournal.R;

import java.util.List;

public class PostAttachmentsAdapter extends Adapter<PostAttachmentsAdapter.PostAttachmentItemViewHolder> {

    private List<Uri> items;

    public PostAttachmentsAdapter(List<Uri> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PostAttachmentItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new PostAttachmentItemViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.attachment_item_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAttachmentItemViewHolder postAttachmentItemViewHolder, int position) {
        Context context = postAttachmentItemViewHolder.attachmentImage.getContext();
        Glide.with(context)
                .load(items.get(position))
                .into(postAttachmentItemViewHolder.attachmentImage);
    }

    @Override
    public int getItemCount() {
        return items == null? 0 : items.size();
    }

    public void addAttachments(List<Uri> uriList) {
        int startAddPosition = items.size();
        items.addAll(startAddPosition, uriList);
        notifyItemRangeInserted(startAddPosition, uriList.size());
    }

    public List<Uri> getData() {
        return items;
    }

    static class PostAttachmentItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView attachmentImage;
        private PostAttachmentItemViewHolder(View view) {
            super(view);
            attachmentImage = view.findViewById(R.id.attachment_image);
        }
    }
}
