package com.example.androidthings.candle;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Loads the FlameViewModel when
 * `ViewModelProviders.of(this, new ViewModelFactory()).get(FlameViewModel.class);` is called
 * <p>
 * Only necessary if you don't use Dagger or other DI framework
 */
class ViewModelFactory implements ViewModelProvider.Factory {
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.getCanonicalName().equals(FlameViewModel.class.getCanonicalName())) {
            FlameCalculator orangeCalculator = new FlameCalculator();
            FlameCalculator yellowCalculator = new FlameCalculator();
            FlameRepository repository = new FlameRepository(orangeCalculator, yellowCalculator);
            return (T) new FlameViewModel(repository);
        }
        throw new IllegalStateException("TODO instantiate " + modelClass);
    }
}
