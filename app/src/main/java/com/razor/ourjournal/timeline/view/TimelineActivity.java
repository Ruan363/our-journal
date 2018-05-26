package com.razor.ourjournal.timeline.view;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.razor.ourjournal.R;
import com.razor.ourjournal.timeline.viewmodel.TimelineActivityViewModel;

public class TimelineActivity extends AppCompatActivity implements TimelineActivityView {

    FrameLayout contentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        contentContainer = (FrameLayout) findViewById(R.id.content_container);

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
