package com.example.reggiewashington.c196;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditAssessment extends AppCompatActivity {
    int pos;
    int id;
    private EditText editId, editType, editName, editDate;
    String updateId, updateType, updateName, updateDate;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_assessment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mDatabaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        pos = intent.getExtras().getInt("Position");

        editType = (EditText) findViewById(R.id.editType);
        editName = (EditText) findViewById(R.id.editName);
        editDate = (EditText) findViewById(R.id.editDate);

        //set data
        id = intent.getIntExtra("Id", -1);
        editType.setText(intent.getStringExtra("Type"));
        editName.setText(intent.getStringExtra("Name"));
        editDate.setText(intent.getStringExtra("Date"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.edit_detail_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.edit_save && editType.length() != 0 && editName.length() != 0 && editDate.length() != 0) {

            updateType = editType.getText().toString();
            updateName = editName.getText().toString();
            updateDate = editDate.getText().toString();
            updateAssessment(id, updateType, updateName, updateDate);
            editType.setText("");
            editName.setText("");
            editDate.setText("");
            Intent intent = new Intent(EditAssessment.this, AssessmentsActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.edit_alarm1 && editType.length() != 0 && editName.length() != 0 && editDate.length() != 0) {
            try {
                alarmAssessmentEnd(updateDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateAssessment(int id, String updateType, String updateName, String updateDate) {

        mDatabaseHelper = new DatabaseHelper(this);
        boolean update = mDatabaseHelper.updateAssessment(id, updateType, updateName, updateDate);


        if (update == true) {
            toastMessage("Term updated!");

            Intent intent = new Intent(EditAssessment.this, TermDetailActivity.class);
            startActivity(intent);

        } else {
            toastMessage("You have an empty text field");
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
        Intent intent = new Intent(EditAssessment.this, MyReceiver.class);
        intent.putExtra("Title", editName.getText());
        intent.putExtra("Message", "Assessment is ending");
        PendingIntent sender = PendingIntent.getBroadcast(EditAssessment.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, convertStartToMillis(end), sender);
    }
}
