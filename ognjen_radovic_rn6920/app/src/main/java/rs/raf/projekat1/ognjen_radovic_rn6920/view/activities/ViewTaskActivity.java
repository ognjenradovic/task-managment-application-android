package rs.raf.projekat1.ognjen_radovic_rn6920.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.application.DailyApplication;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Day;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Task;
import rs.raf.projekat1.ognjen_radovic_rn6920.view.recycler.adapter.TasksAdapter;
import rs.raf.projekat1.ognjen_radovic_rn6920.viewmodels.CalendarViewModel;

public class ViewTaskActivity extends AppCompatActivity {

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
    private Button updateBtn;
    private View.OnClickListener clickListener;

    private Task task;

    public static final String EXTRA_TASK = "TASK";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        Task task = (Task) getIntent().getExtras().getSerializable(EXTRA_TASK);
        this.task = task;

        calendarViewModel = ((DailyApplication) getApplication()).getViewModel();
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView timeTextView = findViewById(R.id.timeTextView);
        TextView display1TextView = findViewById(R.id.display1TextView);
        TextView bigTextArea = findViewById(R.id.bigTextArea);
        TextView priorityTextView = findViewById(R.id.priorityTextView);

        calendarViewModel.getDayByDate(this.task.getDay().getDate()).observe(this, day -> {
            this.task = day.getTasks().get(this.task.getId());
            dateTextView.setText(this.task.getDay().getDate().toString());
            timeTextView.setText(this.task.getStartTime().toString() + " - " + task.getEndTime().toString());
            priorityTextView.setText(this.task.getPriority().toString() + " priority");
            display1TextView.setText(this.task.getName());
            bigTextArea.setText(this.task.getDescription());
        });


        initListeners();

    }


    private void initListeners() {
        Button editButton = findViewById(R.id.editButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTaskActivity.this, EditTaskActivity.class);
                intent.putExtra(EditTaskActivity.EXTRA_TASK_1, task);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "Are you sure you want to delete", Snackbar.LENGTH_LONG);
                snackbar.setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calendarViewModel.deleteTask(ViewTaskActivity.this.task.getDay().getDate(), ViewTaskActivity.this.task.getId());
                        finish();
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        });
    }

}