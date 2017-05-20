package com.example.androidthings.candle;

import android.support.annotation.NonNull;

/**
 * A crude attempt to recreate the original code use of ObjectAnimator in pure java
 */
class FlameCalculator {

    private static final int DURATION = 350;
    private static final float INCREMENT = 1F / DURATION;
    private static final float MIN = 25F;
    private static final float MAX = 100.0F;

    private float incrementalValue;
    private int offsetCount;

    @NonNull
    public Flame generateFlame() {
        return generateFlame(0);
    }

    @NonNull
    public Flame generateFlame(int offset) {
        if (offsetCount < offset) {
            offsetCount++;
            return new Flame(MIN);
        }
        if (incrementalValue > 1) {
            incrementalValue = 0;
        }

        incrementalValue += INCREMENT;

        float flameHeight = getInterpolation(incrementalValue) * (MAX - MIN) + MIN;
        return new Flame(flameHeight);
    }

    /**
     * https://github.com/android/platform_frameworks_base/blob/master/core/java/android/view/animation/BounceInterpolator.java
     */
    private static float getInterpolation(float t) {
        t *= 1.1226f;
        if (t < 0.3535f) {
            return bounce(t);
        } else if (t < 0.7408f) {
            return bounce(t - 0.54719f) + 0.7f;
        } else if (t < 0.9644f) {
            return bounce(t - 0.8526f) + 0.9f;
        } else {
            return bounce(t - 1.0435f) + 0.95f;
        }
    }

    private static float bounce(float t) {
        return t * t * 8.0f;
    }
}
