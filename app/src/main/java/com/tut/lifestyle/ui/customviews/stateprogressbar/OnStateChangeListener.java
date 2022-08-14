package com.tut.lifestyle.ui.customviews.stateprogressbar;

public interface OnStateChangeListener {
    void onStateChange(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState);
}
