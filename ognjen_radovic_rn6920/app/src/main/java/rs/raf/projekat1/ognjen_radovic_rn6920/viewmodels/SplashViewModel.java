package rs.raf.projekat1.ognjen_radovic_rn6920.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

public class SplashViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);

    public SplashViewModel() {
        try {
            Thread.sleep(1000);
            isLoading.setValue(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

}
