package com.example.androidthings.candle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;

/**
 * Repository modules are responsible for handling data operations.
 * They provide a clean API to the rest of the app.
 * They know where to get the data from and what API calls to make when data is updated.
 * You can consider them as mediators between different data sources
 * (persistent model, web service, cache, etc.).
 */
class FlameRepository {

    private final Handler handler = new Handler();

    // Could be a webservice or other source of flame realism
    private final FlameCalculator orangeCalculator;
    private final FlameCalculator yellowCalculator;

    // our flickering flame data source that we can modify
    private MutableLiveData<Flames> data;

    FlameRepository(FlameCalculator orangeCalculator, FlameCalculator yellowCalculator) {
        this.orangeCalculator = orangeCalculator;
        this.yellowCalculator = yellowCalculator;
    }

    public LiveData<Flames> getFlames() {
        data = new MutableLiveData<>();

        handler.post(calculateFlameValue);

        return data;
    }

    private final Runnable calculateFlameValue = new Runnable() {
        @Override
        public void run() {
            // Use whatever flame changing algorithm you want
            Flame orangeFlame = orangeCalculator.generateFlame();
            Flame yellowFlame = yellowCalculator.generateFlame(150);
            Flames flames = new Flames(orangeFlame, yellowFlame);
            // Update our state
            data.setValue(flames);
            // Loop infinitely
            handler.postDelayed(this, 100L);
        }
    };

    /**
     * release any resources we where holding
     * (not always applicable, perhaps if you where using websockets though)
     */
    public void tearDown() {
        handler.removeCallbacks(calculateFlameValue);
    }
}
