package com.razor.ourjournal.timeline.viewmodel;

import com.razor.ourjournal.screens.timeline.view.TimelineActivityView;
import com.razor.ourjournal.screens.timeline.viewmodel.TimelineActivityViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


public class TimelineActivityViewModelTest {

    @Mock
    TimelineActivityView view;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void verify_navigateToTimelineFragment() throws Exception {
        new TimelineActivityViewModel(view);

        verify(view).navigateToTimelineFragment();
    }
}