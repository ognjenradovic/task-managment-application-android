package rs.raf.projekat1.ognjen_radovic_rn6920.view.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import java.time.LocalTime;
import java.util.Calendar;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.application.DailyApplication;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Day;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Priority;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Task;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.User;
import rs.raf.projekat1.ognjen_radovic_rn6920.viewmodels.CalendarViewModel;

public class EditTaskActivity extends AppCompatActivity {
    public static final String DATE_EXTRA = "TEST";
    private User user;
    private String date;
    private LiveData<Day> liveDay;
    private Day day;
    private CalendarViewModel calendarViewModel;
    private Button createTaskBtn;
    private Button cancelTaskBtn;
    private EditText taskNameInput;
    TimePickerDialog picker;
    EditText eText;
    EditText eText1;
    private EditText taskDescriptionInput;
    private Task task;
    public static final String EXTRA_TASK_1 = "extratask";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        if (intent != null) {
            date = intent.getStringExtra(DATE_EXTRA);
        }


        TextView textView = findViewById(R.id.dateTextView);
        textView.setText(this.date);
        Task task = (Task) getIntent().getExtras().getSerializable(EXTRA_TASK_1);
        this.task = task;
        this.taskNameInput = findViewById(R.id.taskNameInput);
        this.taskDescriptionInput = findViewById(R.id.taskDescriptionInput);
        this.createTaskBtn = findViewById(R.id.createTaskBtn);
        this.cancelTaskBtn = findViewById(R.id.cancelButton);

        eText=(EditText) findViewById(R.id.taskDateInput);
        eText.setInputType(InputType.TYPE_NULL);
        eText1=(EditText) findViewById(R.id.taskDateInput1);
        eText1.setInputType(InputType.TYPE_NULL);

        calendarViewModel =((DailyApplication) getApplication()).getViewModel();
        calendarViewModel.getDayByDate(this.task.getDay().getDate()).observe(this, day -> {
            this.task=day.getTasks().get(this.task.getId());
            taskNameInput.setText(task.getName());
            taskDescriptionInput.setText(task.getDescription());
            eText.setText(String.format("%d:%02d", task.getStartTime().getHour(), task.getStartTime().getMinute()));
            eText1.setText(String.format("%d:%02d", task.getEndTime().getHour(), task.getEndTime().getMinute()));
            taskNameInput.setText(task.getName());
        });

        initView();

    }



    private void initView() {

        initListeners();
    }


    private void initListeners() {
        createTaskBtn.setOnClickListener(v -> {
            String taskName = taskNameInput.getText().toString();
            String taskDescription = taskDescriptionInput.getText().toString();
            LocalTime start = LocalTime.parse(eText.getText());
            LocalTime end = LocalTime.parse(eText1.getText());
            RadioGroup radioGroup1=findViewById(R.id.my_radio_group);

            int selectedId1 = radioGroup1.getCheckedRadioButtonId();
            RadioButton selectedButton = findViewById(selectedId1);
            Priority priority=Priority.LOW;

            if (selectedButton != null && selectedButton.isChecked()) {
                if(selectedButton.getText().equals("LOW")) priority=Priority.LOW;
                if(selectedButton.getText().equals("MEDIUM")) priority=Priority.MEDIUM;
                if(selectedButton.getText().equals("HIGH")) priority=Priority.HIGH;
            }

            calendarViewModel.updateTask(this.task.getDay().getDate(),this.task,new Task(taskName, this.task.getDay(),start,end, taskDescription, priority));
            finish();
        });

        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(EditTaskActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText.setText(String.format("%d:%02d", sHour, sMinute));                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        eText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(EditTaskActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText1.setText(String.format("%d:%02d", sHour, sMinute));                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });


        cancelTaskBtn.setOnClickListener(v -> {
            finish();
        });

    }



}