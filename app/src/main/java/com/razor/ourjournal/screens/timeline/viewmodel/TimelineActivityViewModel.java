package com.razor.ourjournal.screens.timeline.viewmodel;


import com.razor.ourjournal.screens.timeline.view.TimelineActivityView;

public class TimelineActivityViewModel {

    public TimelineActivityViewModel(TimelineActivityView view) {
        view.navigateToTimelineFragment();
    }
}
