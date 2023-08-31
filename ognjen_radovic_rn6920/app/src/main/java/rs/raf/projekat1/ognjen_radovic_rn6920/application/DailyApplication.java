package rs.raf.projekat1.ognjen_radovic_rn6920.application;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.viewmodels.CalendarViewModel;
import timber.log.Timber;

public class DailyApplication extends Application {

    public static String PASSWORD;


    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        InputStream inputStream = getResources().openRawResource(R.raw.password);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PASSWORD = byteArrayOutputStream.toString();

        try {
            File file = new File(getApplicationContext().getFilesDir(), "password.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(PASSWORD);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private CalendarViewModel viewModel;

    public CalendarViewModel getViewModel() {
        if (viewModel == null) {
            viewModel = new ViewModelProvider.AndroidViewModelFactory(this).create(CalendarViewModel.class);
        }
        return viewModel;
    }

}
