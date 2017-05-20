package com.example.androidthings.candle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.google.android.things.pio.PeripheralManagerService;
import com.google.android.things.pio.Pwm;

import java.io.IOException;

import static android.content.ContentValues.TAG;

interface CandleLight extends LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void burn(Flame flame);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    class Mock implements CandleLight {
        private static final String TAG = "EdisonCandle";

        private final String name;

        public Mock(String name) {
            this.name = name;
        }

        @Override
        public void onCreate() {
            Log.d(TAG, "creating candle light");
        }

        @Override
        public void burn(Flame flame) {
            Log.d(TAG, "Flicker flame " + name + " " + flame.getHeight());
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "destroying candle light");
        }
    }

    class Led implements CandleLight {

        // PWM output pin names
        private static final String YELLOW_PWM = BoardDefaults.getYellowPwmPort();
        private static final String ORANGE_PWM = BoardDefaults.getOrangePwmPort();
        // Brightness values
        private static final float BRIGHTNESS_START = 25f;

        private final String ledPinName;

        private Pwm led;

        public static Led yellow() {
            return new Led(YELLOW_PWM);
        }

        public static Led orange() {
            return new Led(ORANGE_PWM);
        }

        public Led(String ledPinName) {
            this.ledPinName = ledPinName;
        }

        @Override
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onCreate() {
            try {
                led = openLed(ledPinName);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to open LED connections", e);
            }
        }

        /**
         * Open a new LED connection to the provided port name
         *
         * @throws IOException
         */
        private Pwm openLed(String name) throws IOException {
            PeripheralManagerService service = new PeripheralManagerService();
            Pwm led = service.openPwm(name);
            led.setPwmFrequencyHz(60.0f);
            led.setPwmDutyCycle(BRIGHTNESS_START);
            led.setEnabled(true);

            return led;
        }

        @Override
        public void burn(Flame flame) {
            try {
                led.setPwmDutyCycle(flame.getHeight());
            } catch (IOException e) {
                Log.w(TAG, "Unable to set led duty cycle", e);
            }
        }

        @Override
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {
            try {
                closeLed(led);
            } catch (IOException e) {
                Log.w(TAG, "Unable to close LED connections", e);
            }
        }

        /**
         * Close the provided PWM connection
         *
         * @throws IOException
         */
        private void closeLed(Pwm pwm) throws IOException {
            if (pwm != null) {
                pwm.setEnabled(false);
                pwm.close();
            }
        }
    }
}
