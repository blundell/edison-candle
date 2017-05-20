Edison Candle Android Architecture Components
=============================================

This fork refactors the original EdisonCandle code to take advantage of the Android Architecture Components architectural design.
It keeps all original functionality.

https://developer.android.com/topic/libraries/architecture/guide.html

The calculations for generating the "flame colors" have been moved away from the Activity UI controller and into the domain of our application.
It no longer uses an ObjectAnimator but our own algorithms in a class called FlameCalculator. This architectural change means we have
full control over how the candles flicker and move. Potentially opening up this to be user customisable or even set server side.

We use the [Lifecycle Observer](https://developer.android.com/topic/libraries/architecture/lifecycle.html) pattern to control when we open and close the connections to
our AndroidThings peripherals, and the [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)/[LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html) concepts to create and control our flames.

Although AndroidThings is not likely to have orientation style configuration changes, following best practice means we are helping towards _future proofing_ the app.

To run this codebase on AndroidThings follow the original instructions in the base [README.md](/README.md).

To run this code on a realdevice or an emulator you need to change two lines in the HomeActivity.java

replace

        final CandleLight yellowCandleLight = CandleLight.Led.yellow();
        final CandleLight orangeCandleLight = CandleLight.Led.orange();

with

        final CandleLight yellowCandleLight = new CandleLight.Mock("yellow");
        final CandleLight orangeCandleLight = new CandleLight.Mock("orange");


