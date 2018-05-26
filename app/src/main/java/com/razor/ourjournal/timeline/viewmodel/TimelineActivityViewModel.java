package com.razor.ourjournal.timeline.viewmodel;


import com.razor.ourjournal.timeline.view.TimelineActivityView;

public class TimelineActivityViewModel {

    public TimelineActivityViewModel(TimelineActivityView view) {
        view.navigateToTimelineFragment();
    }
}
