package rs.raf.projekat1.ognjen_radovic_rn6920.view.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.application.DailyApplication;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Day;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Priority;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Task;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.User;
import rs.raf.projekat1.ognjen_radovic_rn6920.viewmodels.CalendarViewModel;

public class NewTaskActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Intent intent = getIntent();
        if (intent != null) {
            date = intent.getStringExtra(DATE_EXTRA);
        }
        TextView textView = findViewById(R.id.dateTextView);
        textView.setText(this.date);

        calendarViewModel =((DailyApplication) getApplication()).getViewModel();
        LocalDate localDate = LocalDate.parse(this.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        calendarViewModel.getDayByDate(localDate).observe(this, day ->{
            this.day=day;
        });
        initView();

    }

    private void initView() {
        this.taskNameInput = findViewById(R.id.taskNameInput);
        this.taskDescriptionInput = findViewById(R.id.taskDescriptionInput);
        this.createTaskBtn = findViewById(R.id.createTaskBtn);
        this.cancelTaskBtn = findViewById(R.id.cancelButton);

        eText=(EditText) findViewById(R.id.taskDateInput);
        eText.setInputType(InputType.TYPE_NULL);
        eText1=(EditText) findViewById(R.id.taskDateInput1);
        eText1.setInputType(InputType.TYPE_NULL);
        initListeners();
    }


    private void initListeners() {
        createTaskBtn.setOnClickListener(v -> {
            String taskName = taskNameInput.getText().toString();
            String taskDescription = taskDescriptionInput.getText().toString();
            LocalTime start = LocalTime.parse(eText.getText());
            LocalTime end = LocalTime.parse(eText1.getText());

            RadioGroup radioGroup=findViewById(R.id.radio_group);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            ToggleButton selectedButton = findViewById(selectedId);
            Priority priority=Priority.LOW;
            if (selectedButton != null && selectedButton.isChecked()) {
                if(selectedButton.getTextOn().equals("LOW")) priority=Priority.LOW;
                if(selectedButton.getTextOn().equals("MEDIUM")) priority=Priority.MEDIUM;
                if(selectedButton.getTextOn().equals("HIGH")) priority=Priority.HIGH;
            }

            calendarViewModel.addTaskToDay(this.day.getDate(),new Task(taskName, day,start,end, taskDescription, priority));
            finish();
        });

        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(NewTaskActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText.setText(String.format("%d:%02d", sHour, sMinute));
                            }
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
                picker = new TimePickerDialog(NewTaskActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText1.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });


        cancelTaskBtn.setOnClickListener(v -> {
            finish();
        });

    }



}