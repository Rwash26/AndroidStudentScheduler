package com.example.reggiewashington.c196;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    long startTerm, endTerm, startCourse, endCourse, startAssessment, endAssessment;
    TextView termAlert;
    Calendar cal, current;
    private ArrayList<Terms> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        myDb = new DatabaseHelper(this);
        Date curDate = new Date();
       // convertTerm();
/*
       int size = list.size();
        String formatDate = formatter.format(curDate);
        for(int i = 0; i <list.size(); i++) {
            if (formatDate.equals(list.get(1))){
                alarmTermStart();
            }
        }
        */

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){

            Log.e("On Config Change","LANDSCAPE");
        }else{

            Log.e("On Config Change","PORTRAIT");
        }
    }

    public void handleTermActivity(View view) {
        Intent intent = new Intent(MainActivity.this, TermActivity.class);
        startActivity(intent);

    }

    public void handleCourseActivity(View view) {
        Intent intent = new Intent(MainActivity.this, CourseActivity.class);
        startActivity(intent);

    }

    public void handleAssessmentsActivity(View view) {
        Intent intent = new Intent(MainActivity.this, AssessmentsActivity.class);
        startActivity(intent);

    }
/*
    public long convertDate(String start) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long milliSeconds = 1390361405210L;
        Date date = new Date(milliSeconds);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        Calendar current = Calendar.getInstance();
        return current.getTimeInMillis();
    }

    public long convertTermStart(){
        Cursor data = myDb.getTermData();
        while(data.moveToNext()){
            String name = data.getString(1);
            String start = data.getString(2);
            String end = data.getString(3);
            convertDate(start);
        }

        return convertDate(startTerm);

    }

    public long convertTermEnd(){
        Cursor data = myDb.getTermData();
        while(data.moveToNext()){
            String name = data.getString(1);
            String end = data.getString(3);
            convertDate(end);
        }
        return endTerm;
    }
*/
/*    public void alarmTermStart() {
        Intent intent = new Intent(MainActivity.this, MyReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, convertTermStart(), sender);
    }

    public void alarmTermEnd() {
        Intent intent = new Intent(MainActivity.this, MyReceiver.class);
        PendingIntent sender1 = PendingIntent.getBroadcast(MainActivity.this, 1, intent, 0);
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager1.set(AlarmManager.RTC_WAKEUP, convertTermEnd(), sender1);
    }
*/
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
