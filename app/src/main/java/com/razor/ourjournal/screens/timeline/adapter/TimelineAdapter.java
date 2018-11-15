package com.razor.ourjournal.screens.timeline.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;
import com.razor.ourjournal.R;
import com.razor.ourjournal.repository.IUploadRepository;
import com.razor.ourjournal.screens.timeline.model.Post;
import com.razor.ourjournal.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineItemViewHolder> {
    private ArrayList<Post> posts;
    private final IUploadRepository uploadRepository;

    public static class TimelineItemViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public View viewLeftDivider;
        public View viewLeftDividerBottom;
        public View viewRightDivider;
        public View viewRightDividerBottom;
        public CardView cardLeftPost;
        public CardView cardRightPost;
        public TextView tvLeftPostTitle;
        public TextView tvRightPostTitle;
        public TextView tvPostDate;
        public TextView tvLeftPostContent;
        public TextView tvRightPostContent;
        public ImageView imgLeftPicture;
        public ImageView imgRightPicture;

        public TimelineItemViewHolder(View v) {
            super(v);
            this.view = v;
            this.viewLeftDivider = v.findViewById(R.id.viewLeftDivider);
            this.viewLeftDividerBottom = v.findViewById(R.id.viewLeftDividerBottom);
            this.viewRightDivider = v.findViewById(R.id.viewRightDivider);
            this.viewRightDividerBottom = v.findViewById(R.id.viewRightDividerBottom);
            this.tvLeftPostTitle = v.findViewById(R.id.tvLeftPostTitle);
            this.tvRightPostTitle = v.findViewById(R.id.tvRightPostTitle);
            this.tvPostDate = v.findViewById(R.id.tvPostDate);
            this.tvLeftPostContent = v.findViewById(R.id.tvLeftPostContent);
            this.tvRightPostContent = v.findViewById(R.id.tvRightPostContent);
            this.cardLeftPost = v.findViewById(R.id.cardLeftPost);
            this.cardRightPost = v.findViewById(R.id.cardRightPost);
            this.imgLeftPicture = v.findViewById(R.id.imgLeftPicture);
            this.imgRightPicture = v.findViewById(R.id.imgRightPicture);
        }
    }

    public TimelineAdapter(List<Post> posts, IUploadRepository uploadRepository) {
        this.posts = (ArrayList<Post>) posts;
        this.uploadRepository = uploadRepository;
    }

    @NonNull
    @Override
    public TimelineItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_row, parent, false);
        return new TimelineItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TimelineItemViewHolder holder, int position) {
        Post post = posts.get(position);

        if (position % 2 == 0) {
            leftSideVisible(holder);

            rightSideInvisible(holder);

            setLeftContent(holder, post);

            loadPicture(holder.imgLeftPicture, post);
        } else {
            rightSideVisible(holder);

            leftSideInvisible(holder);

            setRightContent(holder, post);

            loadPicture(holder.imgRightPicture, post);
        }

        holder.tvPostDate.setText(DateUtils.formatDate(DateUtils.DateFormat.DAY_MON, new Date(post.getDate())));
    }

    private void loadPicture(ImageView imageView, Post post) {
        StorageReference storageReference = uploadRepository.getDownloadUrlPost(post.getPostId(), post.getUserEmailPostedBy(),
                post.getUserEmailPostedFor());

        Glide.with(imageView.getContext())
                .load(storageReference)
                .into(imageView);
    }

    private void setRightContent(TimelineItemViewHolder holder, Post post) {
        holder.tvRightPostTitle.setText(post.getTitle());
        holder.tvRightPostContent.setText(post.getDescription());
    }

    private void leftSideInvisible(TimelineItemViewHolder holder) {
        holder.cardLeftPost.setVisibility(View.INVISIBLE);
        holder.viewLeftDivider.setVisibility(View.INVISIBLE);
        holder.viewLeftDividerBottom.setVisibility(View.INVISIBLE);
    }

    private void rightSideVisible(TimelineItemViewHolder holder) {
        holder.cardRightPost.setVisibility(View.VISIBLE);
        holder.viewRightDivider.setVisibility(View.VISIBLE);
        holder.viewRightDividerBottom.setVisibility(View.VISIBLE);
    }

    private void setLeftContent(TimelineItemViewHolder holder, Post post) {
        holder.tvLeftPostTitle.setText(post.getTitle());
        holder.tvLeftPostContent.setText(post.getDescription());
    }

    private void rightSideInvisible(TimelineItemViewHolder holder) {
        holder.cardRightPost.setVisibility(View.INVISIBLE);
        holder.viewRightDivider.setVisibility(View.INVISIBLE);
        holder.viewRightDividerBottom.setVisibility(View.INVISIBLE);
    }

    private void leftSideVisible(TimelineItemViewHolder holder) {
        holder.cardLeftPost.setVisibility(View.VISIBLE);
        holder.viewLeftDivider.setVisibility(View.VISIBLE);
        holder.viewLeftDividerBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
