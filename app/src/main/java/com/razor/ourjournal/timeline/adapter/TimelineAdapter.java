package com.razor.ourjournal.timeline.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.razor.ourjournal.R;
import com.razor.ourjournal.timeline.model.Post;
import com.razor.ourjournal.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineItemViewHolder> {
    private ArrayList<Post> posts;

    public static class TimelineItemViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public View viewLeftDivider;
        public View viewRightDivider;
        public TextView tvLeftPostTitle;
        public TextView tvRightPostTitle;
        public TextView tvPostDate;
        public TextView tvLeftPostContent;
        public TextView tvRightPostContent;

        public TimelineItemViewHolder(View v) {
            super(v);
            this.view = v;
            this.viewLeftDivider = v.findViewById(R.id.viewLeftDivider);
            this.viewRightDivider = v.findViewById(R.id.viewRightDivider);
            this.tvLeftPostTitle = v.findViewById(R.id.tvLeftPostTitle);
            this.tvRightPostTitle = v.findViewById(R.id.tvRightPostTitle);
            this.tvPostDate = v.findViewById(R.id.tvPostDate);
            this.tvLeftPostContent = v.findViewById(R.id.tvLeftPostContent);
            this.tvRightPostContent = v.findViewById(R.id.tvRightPostContent);
        }
    }

    public TimelineAdapter(List<Post> posts) {
        this.posts = (ArrayList<Post>) posts;
    }

    @Override
    public TimelineItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_row, parent, false);
        TimelineItemViewHolder vh = new TimelineItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TimelineItemViewHolder holder, int position) {
        Post post = posts.get(position);

        if (position % 2 == 0) {
            leftSideVisible(holder);

            rightSideInvisible(holder);

            setLeftContent(holder, post);
        } else {
            rightSideVisible(holder);

            leftSideInvisible(holder);

            setRightContent(holder, post);
        }

        holder.tvPostDate.setText(DateUtils.formatDate(DateUtils.DateFormat.DAY_MON, post.getDate()));
    }

    private void setRightContent(TimelineItemViewHolder holder, Post post) {
        holder.tvRightPostTitle.setText(post.getTitle());
        holder.tvRightPostContent.setText(post.getDescription());
    }

    private void leftSideInvisible(TimelineItemViewHolder holder) {
        holder.tvLeftPostTitle.setVisibility(View.INVISIBLE);
        holder.tvLeftPostContent.setVisibility(View.INVISIBLE);
        holder.viewLeftDivider.setVisibility(View.INVISIBLE);
    }

    private void rightSideVisible(TimelineItemViewHolder holder) {
        holder.tvRightPostTitle.setVisibility(View.VISIBLE);
        holder.tvRightPostContent.setVisibility(View.VISIBLE);
        holder.viewRightDivider.setVisibility(View.VISIBLE);
    }

    private void setLeftContent(TimelineItemViewHolder holder, Post post) {
        holder.tvLeftPostTitle.setText(post.getTitle());
        holder.tvLeftPostContent.setText(post.getDescription());
    }

    private void rightSideInvisible(TimelineItemViewHolder holder) {
        holder.tvRightPostTitle.setVisibility(View.INVISIBLE);
        holder.tvRightPostContent.setVisibility(View.INVISIBLE);
        holder.viewRightDivider.setVisibility(View.INVISIBLE);
    }

    private void leftSideVisible(TimelineItemViewHolder holder) {
        holder.tvLeftPostTitle.setVisibility(View.VISIBLE);
        holder.tvLeftPostContent.setVisibility(View.VISIBLE);
        holder.viewLeftDivider.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
