package com.example.reggiewashington.c196;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CourseDetailActivity extends AppCompatActivity {

    int selectedCourseId;
    int MY_PERMISSION_REQUEST_SEND_SMS = 1;
    String selectedCourse, mNote, selectedCourseTitle, selectedCourseStart, selectedCourseEnd;
    TextView CourseTitle, CourseStart, CourseAnticipatedEnd, CourseStatus, CourseMentor, CourseMentorNumber, CourseMentorEmail;
    Courses ca;
    DatabaseHelper myDb;
    EditText mNotes, mNumber;
    Button share;
    View mView;
    private ArrayList<Assessments> assessmentsList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);
        myDb = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.course_assessments);
        Intent receivedIntent = getIntent();
        selectedCourseId = receivedIntent.getIntExtra("Id", -1);
        selectedCourse = receivedIntent.getStringExtra("Course");

        CourseTitle = (TextView) findViewById(R.id.c_title);
        CourseStart = (TextView) findViewById(R.id.c_start);
        CourseAnticipatedEnd = (TextView) findViewById(R.id.c_end);
        CourseStatus = (TextView) findViewById(R.id.c_status);
        CourseMentor = (TextView) findViewById(R.id.mentor_name);
        CourseMentorNumber = (TextView) findViewById(R.id.mentor_number);
        CourseMentorEmail = (TextView) findViewById(R.id.mentor_email);


        //set data
        CourseTitle.setText(selectedCourse);
        populateView();
        CustomAdapter adapter = new CustomAdapter(this, R.layout.course_assessments, assessmentsList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.course_detail_menu, menu);
        return true;
    }

    public void populateView() {
        Cursor data = myDb.getSingleCourse(selectedCourseId);
        while (data.moveToNext()) {
            selectedCourseTitle = data.getString(1);
            selectedCourseStart = data.getString(2);
            selectedCourseEnd = data.getString(3);
            String status = data.getString(4);
            String mentor = data.getString(5);
            String mentorNumber = data.getString(6);
            String mentorEmail = data.getString(7);
            mNote = data.getString(8);

            CourseStart.setText("Start Date: " + selectedCourseStart);
            CourseAnticipatedEnd.setText("Anticipated End Date: " + selectedCourseEnd);
            CourseStatus.setText(status);
            CourseMentor.setText(mentor);
            CourseMentorNumber.setText(mentorNumber);
            CourseMentorEmail.setText(mentorEmail);
        }
        Cursor data2 = myDb.getCourseAssessmentData(selectedCourseId);
        while (data2.moveToNext()) {
            String assessmentTitle = data2.getString(3);
            assessmentsList.add(new Assessments(assessmentTitle));
        }



    }

    public boolean onOptionsItemSelected(MenuItem item) {

        String courseTitle = CourseTitle.getText().toString();
        String courseStart = CourseStart.getText().toString();
        String courseAntiEnd = CourseAnticipatedEnd.getText().toString();
        String courseStatus = CourseStatus.getText().toString();
        String courseMentor = CourseMentor.getText().toString();
        String courseMNumber = CourseMentorNumber.getText().toString();
        String courseMEmail = CourseMentorEmail.getText().toString();

        if (item.getItemId() == R.id.course_edit) {
            Intent intent = new Intent(CourseDetailActivity.this, EditCourse.class);
            intent.putExtra("Id", selectedCourseId);
            intent.putExtra("Title", selectedCourseTitle);
            intent.putExtra("Start", selectedCourseStart);
            intent.putExtra("End", selectedCourseEnd);
            intent.putExtra("Status", courseStatus);
            intent.putExtra("Mentor", courseMentor);
            intent.putExtra("Number", courseMNumber);
            intent.putExtra("Email", courseMEmail);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.course_notes) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(CourseDetailActivity.this);
            final View mView = getLayoutInflater().inflate(R.layout.notes, null);
            TextView mTitle = (TextView) mView.findViewById(R.id.note_title);
            mNotes = (EditText) mView.findViewById(R.id.notes);
            mNumber = (EditText) mView.findViewById(R.id.phoneNumber);
            share = (Button) mView.findViewById(R.id.notes_share_btn);

            if(mNotes != null){
                mNotes.setText(mNote);
            }else {
                mNotes.setText("");
            }
            Button mSave = (Button) mView.findViewById(R.id.notes_save_btn);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
            mSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int id = selectedCourseId;
                        final String note = mNotes.getText().toString();
                        addNotes(id, note);
                       // finish();

                }
            });


        }
        return super.onOptionsItemSelected(item);
    }

    public void handleShareSms(View view){
        final String note = mNotes.getText().toString();
        final String telNum = mNumber.getText().toString();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSION_REQUEST_SEND_SMS);
        }else{
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(telNum, null, note,null, null);
            toastMessage("Message Sent!");
        }
    }

    public class CustomAdapter extends BaseAdapter {
        private Context context;
        private int layout;
        ArrayList<Assessments> courseAssessmentList;

        public CustomAdapter(Context context, int layout, ArrayList<Assessments> courseAssessmentList) {
            this.context = context;
            this.layout = layout;
            this.courseAssessmentList = courseAssessmentList;
        }

        @Override
        public int getCount() {
            return courseAssessmentList.size();
        }

        @Override
        public Object getItem(int position) {
            return courseAssessmentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {

            TextView assessmentTitle;
            TextView assessmentGoal;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            View row = view;
            ViewHolder holder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder = new CustomAdapter.ViewHolder();
                holder.assessmentTitle = (TextView) row.findViewById(R.id.assessment_title);
                holder.assessmentGoal = (TextView) row.findViewById(R.id.assessment_goal_date);
                row.setTag(holder);

            } else {
                holder = (ViewHolder) row.getTag();
            }
            final Assessments assessments = courseAssessmentList.get(position);

            holder.assessmentTitle.setText(assessments.getName());
            holder.assessmentGoal.setText(assessments.getDate());
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor data = myDb.getCourseAssessmentData(selectedCourseId);
                    int assessmentId = -1;
                    String assessmentType = "";
                    String assessmentName = "";
                    String assessmentDate = "";
                    while(data.moveToNext()) {
                        assessmentId = data.getInt(2);
                        assessmentName = data.getString(3);
                        Intent intent = new Intent(CourseDetailActivity.this, AssessmentDetailActivity.class);
                        intent.putExtra("Id", assessmentId);
                        intent.putExtra("Name", assessmentName);
                        startActivity(intent);
                    }
                }
            });

            return row;
        }


    }

    public void addNotes(int id, String notes) {
        final boolean insertData = myDb.addNotes(id, notes);

        if (insertData == true) {
            toastMessage("Notes inserted!");
        } else {
            toastMessage("Something went wrong!");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
