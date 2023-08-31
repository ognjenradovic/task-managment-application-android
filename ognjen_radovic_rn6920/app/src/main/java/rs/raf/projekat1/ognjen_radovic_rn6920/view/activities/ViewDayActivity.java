package rs.raf.projekat1.ognjen_radovic_rn6920.view.activities;

import static rs.raf.projekat1.ognjen_radovic_rn6920.view.activities.NewTaskActivity.DATE_EXTRA;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.application.DailyApplication;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Day;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Priority;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Task;
import rs.raf.projekat1.ognjen_radovic_rn6920.view.recycler.adapter.TasksAdapter;
import rs.raf.projekat1.ognjen_radovic_rn6920.view.recycler.differ.TaskDiffItemCallBack;
import rs.raf.projekat1.ognjen_radovic_rn6920.viewmodels.CalendarViewModel;

public class ViewDayActivity extends AppCompatActivity {

    private CalendarViewModel calendarViewModel;
    private String date;
    private LiveData<Day> dayLiveData;
    private List<Task> tasks;
    private Day day;
    private RecyclerView recyclerView;
    private TasksAdapter tasksAdapter;
    private FloatingActionButton newTaskBtn;
    private Button filterButton1;
    private Button filterButton2;
    private Button filterButton3;
    private EditText searchView;
    private View.OnClickListener clickListener;
    private CheckBox checkBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_preview);

        this.date = getIntent().getStringExtra("day");

        calendarViewModel = ((DailyApplication) getApplication()).getViewModel();

        TextView textView = findViewById(R.id.task_date);
        textView.setText(this.date);
        LocalDate localDate = LocalDate.parse(this.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        dayLiveData = calendarViewModel.getDayByDate(localDate);
        dayLiveData.observe(this, day -> {
            this.tasks = day.getTasks();
            if (tasksAdapter != null) {
                tasksAdapter.submitList(tasks);
            }
            initRecycler();
        });

        initView();
        initRecycler();
        initListeners();

    }


    private void initView() {
        this.newTaskBtn = findViewById(R.id.new_task_btn);
        recyclerView = findViewById(R.id.task_list_view);
        this.filterButton1 = findViewById(R.id.filterButton1);
        this.filterButton2 = findViewById(R.id.filterButton2);
        this.filterButton3 = findViewById(R.id.filterButton3);
        this.checkBox = findViewById(R.id.checkBox);
        this.checkBox.setChecked(true);
        searchView = findViewById(R.id.searchView);
    }

    private void initListeners() {
        newTaskBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewTaskActivity.class);
            intent.putExtra(DATE_EXTRA, this.date);
            startActivity(intent);
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                List<Task> filteredTasks = filterTasksByName(tasks, query);
                tasksAdapter.submitList(filteredTasks);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        filterButton1.setOnClickListener(v -> {
            List<Task> filteredTasks = filterTasksByPriority(tasks, Priority.LOW);
            tasksAdapter.submitList(filteredTasks);
        });
        filterButton2.setOnClickListener(v -> {
            List<Task> filteredTasks = filterTasksByPriority(tasks, Priority.MEDIUM);
            tasksAdapter.submitList(filteredTasks);
        });
        filterButton3.setOnClickListener(v -> {
            List<Task> filteredTasks = filterTasksByPriority(tasks, Priority.HIGH);
            tasksAdapter.submitList(filteredTasks);
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tasksAdapter.submitList(tasks);
                } else {
                    List<Task> filteredTasks = filterTasksByTime(tasks);
                    tasksAdapter.submitList(filteredTasks);
                }
            }
        });
        List<Task> filteredTasks = filterTasksByTime(tasks);
        if (filteredTasks != null) tasksAdapter.submitList(filteredTasks);
    }

    public static List<Task> filterTasksByName(List<Task> tasks, String query) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public static List<Task> filterTasksByTime(List<Task> tasks) {
        List<Task> filteredTasks = new ArrayList<>();
        LocalTime now = LocalTime.now();
        if (tasks != null) {
            for (Task task : tasks) {
                if (task.getEndTime().isAfter(now)) {
                    filteredTasks.add(task);
                }
            }
        }
        return filteredTasks;
    }


    public static List<Task> filterTasksByPriority(List<Task> tasks, Priority priority) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority().equals(priority)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initRecycler() {

        tasksAdapter = new TasksAdapter(new TaskDiffItemCallBack(), task1 -> {
            Intent intent = new Intent(this, ViewTaskActivity.class);
            intent.putExtra(ViewTaskActivity.EXTRA_TASK, task1);
            startActivity(intent);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(tasksAdapter);
        tasksAdapter.submitList(tasks);
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }
}