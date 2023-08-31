package rs.raf.projekat1.ognjen_radovic_rn6920.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.time.LocalDate;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.view.activities.ViewDayActivity;
import timber.log.Timber;

public class SecondFragment extends Fragment {

    public SecondFragment() {
        super(R.layout.fragment_second);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.e("ON CREATE");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.e("ON CREATE VIEW");

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.e("ON VIEW CREATED");
        Button button=view.findViewById(R.id.todayTask);
        button.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), ViewDayActivity.class);
            intent.putExtra("day", LocalDate.now().toString());
            startActivity(intent);
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Timber.e("ON DESTROY VIEW");
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.e("ON DESTROY");
    }
}
