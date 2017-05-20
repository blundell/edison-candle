package com.example.androidthings.candle;

class Flames {

    private final Flame orangeFlame;
    private final Flame yellowFlame;

    public Flames(Flame orangeFlame, Flame yellowFlame) {
        this.orangeFlame = orangeFlame;
        this.yellowFlame = yellowFlame;
    }

    public Flame getOrangeFlame() {
        return orangeFlame;
    }

    public Flame getYellowFlame() {
        return yellowFlame;
    }

}
