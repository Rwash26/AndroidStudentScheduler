package com.example.reggiewashington.c196;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCourse extends AppCompatActivity {
    private static final String TAG = "AddCourse";
    private EditText editText, editText2, editText3, editText4, editText5, editText6, editText7;
    DatabaseHelper mDatabaseHelper;
    private Button addCourseBtn;
    private CheckBox addCourseAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course);
        editText = (EditText) findViewById(R.id.addCourse);
        editText2 = (EditText) findViewById(R.id.addCourseStart);
        editText3 = (EditText) findViewById(R.id.addCourseEnd);
        editText4 = (EditText) findViewById(R.id.add_course_status);
        editText5 = (EditText) findViewById(R.id.add_course_mentor);
        editText6 = (EditText) findViewById(R.id.add_course_mentor_number);
        editText7 = (EditText) findViewById(R.id.add_course_mentor_email);
        addCourseBtn = (Button) findViewById(R.id.addCourseBtn);
        addCourseAlarm = (CheckBox) findViewById(R.id.addCourseAlarm);
        mDatabaseHelper = new DatabaseHelper(this);

        String start = editText2.getText().toString();
        String end = editText3.getText().toString();

        addCourseBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String course = editText.getText().toString();
                        final String start = editText2.getText().toString();
                        final String end = editText3.getText().toString();
                        final String status = editText4.getText().toString();
                        final String mentor = editText5.getText().toString();
                        final String mNumber = editText6.getText().toString();
                        final String mEmail = editText7.getText().toString();

                        if (editText.length() != 0 && editText2.length() != 0 && editText3.length() != 0 && editText4.length() != 0 && editText5.length() != 0 && editText6.length() != 0 && editText7.length() != 0) {
                            addNewCourse(course, start, end, status, mentor, mNumber, mEmail);
                            editText.setText("");
                            editText2.setText("");
                            editText3.setText("");
                            editText4.setText("");
                            editText5.setText("");
                            editText6.setText("");
                            editText7.setText("");
                            if(addCourseAlarm.isChecked()){
                                try {
                                    alarmTermStart(start);
                                    alarmTermEnd(end);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                            Intent intent = new Intent(AddCourse.this, CourseActivity.class);
                            startActivity(intent);

                        } else {
                            toastMessage("You have an empty field");
                        }

                    }
                });

    }

    public void addNewCourse(String title, String start, String end, String status, String mentor, String mNumber, String mEmail) {
        final boolean insertData = mDatabaseHelper.addCourse(title, start, end, status, mentor, mNumber, mEmail);

        if (insertData == true) {
            toastMessage("Course inserted!");
        } else {
            toastMessage("You have en empty text field");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public long convertStartToMillis(String start) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long milliSeconds = 1390361405210L;
        Date date = formatter.parse(start);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        Calendar current = Calendar.getInstance();
        current.setTime(date);
        return current.getTimeInMillis();
    }

    public void alarmTermStart(String start) throws ParseException {
        Intent intent = new Intent(AddCourse.this, MyReceiver.class);
        intent.putExtra("Title", editText.getText());
        intent.putExtra("Message", "Course is starting");
        PendingIntent sender = PendingIntent.getBroadcast(AddCourse.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, convertStartToMillis(start), sender);
    }

    public void alarmTermEnd(String end) throws ParseException {
        Intent intent = new Intent(AddCourse.this, MyReceiver.class);
        intent.putExtra("Title", editText.getText());
        intent.putExtra("Message", "Course is ending");
        PendingIntent sender = PendingIntent.getBroadcast(AddCourse.this, 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, convertStartToMillis(end), sender);
    }
}