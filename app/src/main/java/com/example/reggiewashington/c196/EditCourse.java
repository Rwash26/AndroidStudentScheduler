package com.example.reggiewashington.c196;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditCourse extends AppCompatActivity {
    int id, pos;
    private EditText editId, editTitle, editStart, editEnd, editStatus, editMentor, editMNumber, editMEmail;
    DatabaseHelper mDatabaseHelper;
    private ListView listView;
    String updateId, updateTitle, updateStart, updateEnd, updateStatus, updateMentor, updateMNumber, updateMEmail;
    public ArrayList<Assessments> assessmentList = new ArrayList<>();
    CheckBox check;
    CustomAdapter adapter = new CustomAdapter(this, R.layout.edit_all_assessments, assessmentList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_course);
        listView = (ListView) findViewById(R.id.all_assessment_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mDatabaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        pos = intent.getExtras().getInt("Position");

        editTitle = (EditText) findViewById(R.id.course_title);
        editStart = (EditText) findViewById(R.id.course_start);
        editEnd = (EditText) findViewById(R.id.course_end);
        editStatus = (EditText) findViewById(R.id.course_status);
        editMentor = (EditText) findViewById(R.id.course_mentor);
        editMNumber = (EditText) findViewById(R.id.course_mentor_number);
        editMEmail = (EditText) findViewById(R.id.course_mentor_email);

        //set data
        id = intent.getIntExtra("Id", -1);

        editTitle.setText(intent.getStringExtra("Title"));
        editStart.setText(intent.getStringExtra("Start"));
        editEnd.setText(intent.getStringExtra("End"));
        editStatus.setText(intent.getStringExtra("Status"));
        editMentor.setText(intent.getStringExtra("Mentor"));
        editMNumber.setText(intent.getStringExtra("Number"));
        editMEmail.setText(intent.getStringExtra("Email"));

        populateView();
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.edit_course_menu, menu);
        return true;
    }

    public void populateView() {
        Cursor data = mDatabaseHelper.getAssessmentsData();
        while (data.moveToNext()) {
            int assessmentId = data.getInt(0);
            String assessmentTitle = data.getString(2);
            assessmentList.add(new Assessments(assessmentId, assessmentTitle));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.edit_course_save && editTitle.length() != 0 && editStart.length() != 0 && editEnd.length() != 0 && editStatus.length() != 0 && editMentor.length() != 0 && editMNumber.length() != 0 && editMEmail.length() != 0) {

            updateTitle = editTitle.getText().toString();
            updateStart = editStart.getText().toString();
            updateEnd = editEnd.getText().toString();
            updateStatus = editStatus.getText().toString();
            updateMentor = editMentor.getText().toString();
            updateMNumber = editMNumber.getText().toString();
            updateMEmail = editMEmail.getText().toString();
            updateCourse(id, updateTitle, updateStart, updateEnd, updateStatus, updateMentor, updateMNumber, updateMEmail);

            int size = assessmentList.size();
            for (Assessments a : assessmentList) {

                if (a.isSelected()) {
                    addCourseAssessments(id,a.getId(),a.getName());

                }
            }
            Intent intent = new Intent(EditCourse.this, CourseActivity.class);
            startActivity(intent);
            editTitle.setText("");
            editStart.setText("");
            editEnd.setText("");
            editStatus.setText("");
            editMentor.setText("");
            editMNumber.setText("");
            editMEmail.setText("");

        }
        if (item.getItemId() == R.id.edit_course_alarm && editTitle.length() != 0 && editStart.length() != 0 && editEnd.length() != 0 && editStatus.length() != 0 && editMentor.length() != 0 && editMNumber.length() != 0 && editMEmail.length() != 0) {
            try {
                alarmCourseStart(updateStart);
                alarmCourseEnd(updateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    public void addCourseAssessments(int courseId, int assessmentId, String assessmentTitle) {
        mDatabaseHelper = new DatabaseHelper(this);

        boolean insert = mDatabaseHelper.addCourseAssessment(courseId, assessmentId, assessmentTitle);
        if (insert == true) {
            toastMessage("Term updated!");

            Intent intent = new Intent(EditCourse.this, CourseDetailActivity.class);
            startActivity(intent);

        } else {
            toastMessage("You have an empty text field");
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

            TextView textTitle;
            CheckBox check;
        }


        @Override
        public View getView(final int position, final View view, ViewGroup parent) {

            View row = view;
            final CustomAdapter.ViewHolder holder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder = new ViewHolder();
                holder.check = (CheckBox) row.findViewById(R.id.checkBox2);
                holder.textTitle = (TextView) row.findViewById(R.id.all_assessments);
                row.setTag(holder);

            } else {
                holder = (ViewHolder) row.getTag();
                holder.check.getTag(position);
            }
            holder.check.setTag(position);
            final Assessments assessment = assessmentList.get(position);
            holder.check.setChecked(assessmentList.contains(position));
            holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    assessmentList.get(position).setSelected(buttonView.isChecked());
                    if (isChecked) {
                        assessment.isSelected();

                    } else {
                        //assessmentList.remove(assessment);
                    }
                }

            });

            holder.check.setTag(position);
            holder.textTitle.setText(assessment.getName());
            holder.check.setChecked(courseAssessmentList.get(position).isSelected());


            return row;
        }

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void updateCourse(int id, String updateTitle, String updateStart, String updateEnd, String updateStatus, String updateMentor, String updateMNumber, String updateMEmail) {

        mDatabaseHelper = new DatabaseHelper(this);
        boolean update = mDatabaseHelper.updateCourse(id, updateTitle, updateStart, updateEnd, updateStatus, updateMentor, updateMNumber, updateMEmail);

        if (update == true) {
            toastMessage("Course updated!");

        } else {
            toastMessage("You have an empty text field");
        }
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

    public void alarmCourseStart(String start) throws ParseException {
        Intent intent = new Intent(EditCourse.this, MyReceiver.class);
        intent.putExtra("Title", editTitle.getText());
        intent.putExtra("Message", "Course is starting");
        PendingIntent sender = PendingIntent.getBroadcast(EditCourse.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, convertStartToMillis(start), sender);
    }

    public void alarmCourseEnd(String end) throws ParseException {
        Intent intent = new Intent(EditCourse.this, MyReceiver.class);
        intent.putExtra("Title", editTitle.getText());
        intent.putExtra("Message", "Course is ending");
        PendingIntent sender = PendingIntent.getBroadcast(EditCourse.this, 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, convertStartToMillis(end), sender);
    }


}
