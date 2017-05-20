/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidthings.candle;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

/**
 * Implement a flickering candle using LEDs connected to PWM
 * and Architecture Components.
 */
public class HomeActivity extends LifecycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create and manage the connections to the peripheral LEDs
        final CandleLight yellowCandleLight = CandleLight.Led.yellow();
        final CandleLight orangeCandleLight = CandleLight.Led.orange();
        getLifecycle().addObserver(yellowCandleLight);
        getLifecycle().addObserver(orangeCandleLight);

        // Load our ViewModel
        FlameViewModel viewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(FlameViewModel.class);

        // Observe the flickering flame data and update the peripheral LEDs
        viewModel.getFlames()
                .observe(this, new Observer<Flames>() {
                    @Override
                    public void onChanged(Flames flames) {
                        yellowCandleLight.burn(flames.getYellowFlame());
                        orangeCandleLight.burn(flames.getOrangeFlame());
                    }
                });
    }

}
