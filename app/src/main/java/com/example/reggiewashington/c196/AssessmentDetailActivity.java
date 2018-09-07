package com.example.reggiewashington.c196;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AssessmentDetailActivity extends AppCompatActivity {
    int selectedId;
    String selectedType, selectedAssessmentEnd, selectedAssessmentTitle, selectedName;
    TextView Id, Type, Name, Date;
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        myDb = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();
        selectedId = receivedIntent.getIntExtra("Id",-1);
        selectedName = receivedIntent.getStringExtra("Name");


        Assessments adapter = new Assessments();
        Id = (TextView) findViewById(R.id.assessment_id);
        Type = (TextView) findViewById(R.id.assessment_type);
        Name = (TextView) findViewById(R.id.assessment_name);
        Date = (TextView) findViewById(R.id.assessment_date);

        populateView();
        //set data
        Id.setText(String.valueOf(selectedId));
        Type.setText(selectedType);
        Name.setText(selectedName);
        Date.setText("Goal Date: " + selectedAssessmentEnd);


    }

    public void populateView() {
        Cursor data = myDb.getSingleAssessment(selectedId);
        while (data.moveToNext()) {
            selectedAssessmentTitle = data.getString(1);
            selectedAssessmentEnd = data.getString(3);


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.edit_assessment_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int sId = Integer.parseInt(Id.getText().toString());
        String sType = Type.getText().toString();
        String sName = Name.getText().toString();
        String sDate = selectedAssessmentEnd;


        if(item.getItemId() == R.id.action_assessment_edit){
            Intent intent = new Intent(AssessmentDetailActivity.this, EditAssessment.class);
            intent.putExtra("Id", sId);
            intent.putExtra("Type", sType);
            intent.putExtra("Name", sName);
            intent.putExtra("Date", sDate);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.action_assessment_delete){
            deleteAssessmentData(sId);
            Intent intent = new Intent(AssessmentDetailActivity.this, AssessmentsActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteAssessmentData(int id) {
        final boolean deleteData = myDb.deleteAssessmentData(id);

        if(deleteData == true){
            toastMessage("Term deleted!");
        }else{
            toastMessage("Can't delete term with attached courses");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
