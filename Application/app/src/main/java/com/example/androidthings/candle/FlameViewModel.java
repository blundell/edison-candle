package com.example.androidthings.candle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

/**
 * A ViewModel provides the data for a specific UI component,
 * such as an activity,
 * and handles the communication with the business part of data handling,
 * such as calling other components to load the data.
 * The ViewModel does not know about the View
 * and is not affected by Android configuration changes.
 */
class FlameViewModel extends ViewModel {

    // mediator for the LED flickering flames data source
    private final FlameRepository repository;

    //observable data holder for the LED flickering flames
    private LiveData<Flames> flames;

    public FlameViewModel(FlameRepository repository) {
        this.repository = repository;
    }

    public LiveData<Flames> getFlames() {
        // ViewModel is created per Activity so we only
        // need to instantiate this once
        if (flames == null) {
            flames = repository.getFlames();
        }
        return flames;
    }

    @Override
    protected void onCleared() {
        repository.tearDown();
    }
}
