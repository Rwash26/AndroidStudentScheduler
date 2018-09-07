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

public class AddAssessment extends AppCompatActivity {
    private static final String TAG = "AddAssessment";
    private EditText editText, editText2, editText3;
    DatabaseHelper mDatabaseHelper;
    private Button addABtn;
    private CheckBox addAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assessment);
        editText = (EditText) findViewById(R.id.addType);
        editText2 = (EditText) findViewById(R.id.addName);
        editText3 = (EditText) findViewById(R.id.addDate);
        addABtn = (Button) findViewById(R.id.addABtn);
        addAlarm = (CheckBox) findViewById(R.id.addAlarm);
        mDatabaseHelper = new DatabaseHelper(this);


        addABtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        final String type = editText.getText().toString();
                        final String name = editText2.getText().toString();
                        final String date = editText3.getText().toString();
                        if(editText.length() != 0 && editText2.length() != 0 && editText3.length() != 0 ){
                            addAssessmentData(type, name, date);
                            editText.setText("");
                            editText2.setText("");
                            editText3.setText("");
                            Intent intent = new Intent(AddAssessment.this, AssessmentsActivity.class);
                            startActivity(intent);
                            if(addAlarm.isChecked()){
                                try {
                                    alarmAssessmentEnd(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                        }else{
                            toastMessage("You have an empty field");
                        }

                    }
                });
    }

    public void addAssessmentData(String type, String name, String date) {
        final boolean insertData = mDatabaseHelper.addAssessmentsData(type, name, date);

        if(insertData == true){
            toastMessage("Assessment inserted!");
        }else{
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


    public void alarmAssessmentEnd(String end) throws ParseException {
        Intent intent = new Intent(AddAssessment.this, MyReceiver.class);
        intent.putExtra("Title", editText2.getText());
        intent.putExtra("Message", "Assessment is ending");
        PendingIntent sender = PendingIntent.getBroadcast(AddAssessment.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, convertStartToMillis(end), sender);
    }

}
