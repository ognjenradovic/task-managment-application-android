package rs.raf.projekat1.ognjen_radovic_rn6920.view.fragments;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.application.DailyApplication;
import rs.raf.projekat1.ognjen_radovic_rn6920.view.activities.ViewDayActivity;
import rs.raf.projekat1.ognjen_radovic_rn6920.view.recycler.differ.CalendarDiffItemCallback;
import rs.raf.projekat1.ognjen_radovic_rn6920.view.recycler.adapter.CalendarAdapter;
import rs.raf.projekat1.ognjen_radovic_rn6920.viewmodels.CalendarViewModel;

public class FirstFragment extends Fragment {

    // View components
    private RecyclerView recyclerView;
    private LinearLayout mainLayout;
    private TextView monthTextView;

    private CalendarViewModel calendarViewModel;
    private CalendarAdapter calendarAdapter;

    public FirstFragment() {
        super(R.layout.fragment_first);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        calendarViewModel = ((DailyApplication) requireActivity().getApplication()).getViewModel();

        initView(view);
        initListeners();
        initObservers();
        initRecycler();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }




    private void initView(View view) {
        mainLayout = view.findViewById(R.id.outerFragmentFcv);
        recyclerView = view.findViewById(R.id.grid_view);
        monthTextView = view.findViewById(R.id.month_view);
    }

    private void initListeners() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();


                LocalDate date = ((CalendarAdapter) recyclerView.getAdapter()).getItem(firstVisibleItemPosition).getDate();
                String month="Calendar";
                if(date==null){
                     month= LocalDate.now().getMonth().toString();
                }
                else{
                     month=date.getMonth().toString();
                }


                monthTextView.setText(month);
            }
        });
    }



    private void initObservers() {
        calendarViewModel.getDays().observe(getViewLifecycleOwner(), days -> {
            calendarAdapter.submitList(days);
        });
    }

    private void initRecycler() {
        calendarAdapter = new CalendarAdapter(new CalendarDiffItemCallback(), day -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("day", day.getDate().toString());
//
//            ViewDayFragment viewDayFragment = new ViewDayFragment();
//            viewDayFragment.setArguments(bundle);
//
//      //      FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//               FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//
//            transaction.add(R.id.outerFragmentFcv, viewDayFragment);
//            transaction.commit();
            Intent intent = new Intent(getActivity(), ViewDayActivity.class);
            intent.putExtra("day", day.getDate().toString());
            startActivity(intent);

            Toast.makeText(requireContext(), day.getDate().toString() + "", Toast.LENGTH_SHORT).show();
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));

        recyclerView.setPadding(0, 0, 0, 0);
        recyclerView.setClipToPadding(false);

        recyclerView.setAdapter(calendarAdapter);
    }



}