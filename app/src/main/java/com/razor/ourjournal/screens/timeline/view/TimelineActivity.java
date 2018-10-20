package com.razor.ourjournal.screens.timeline.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.razor.ourjournal.R;
import com.razor.ourjournal.screens.BaseDrawerActivity;
import com.razor.ourjournal.screens.timeline.viewmodel.TimelineActivityViewModel;

public class TimelineActivity extends BaseDrawerActivity implements TimelineActivityView {

    FrameLayout contentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContent(R.layout.activity_timeline);

        contentContainer = findViewById(R.id.content_container);

        new TimelineActivityViewModel(this);
    }

    @Override
    public void navigateToTimelineFragment() {
        TimelineFragment timelineFragment = new TimelineFragment();

        if (findViewById(R.id.content_container) != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content_container, timelineFragment).commit();
        }
    }
}
