package com.example.reggiewashington.c196;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditTerm extends AppCompatActivity {
    int pos;
    int id, cid;
    private EditText editId, editTerm, editStart, editEnd;
    Button saveEditBtn;
    String courseTitle, updateTerm, updateStart, updateEnd;
    DatabaseHelper mDatabaseHelper;
    Courses cs;
    TermActivity ta;
    private ArrayList<String> list;
    private ArrayList<Courses> courseList = new ArrayList<>();
    private ArrayList<Courses> notInTerm = new ArrayList<>();
    private ListView listView;
    public ArrayList<Courses> termCourseList = new ArrayList<>();
    //ArrayList<Courses> termCourseList = new ArrayList<>();
    CheckBox check;
    CustomAdapter adapter = new CustomAdapter(this, R.layout.edit_all_courses, notInTerm);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_term);
        listView = (ListView) findViewById(R.id.all_course_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final String TAG = "xy";
        mDatabaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        pos = intent.getExtras().getInt("Position");

        editTerm = (EditText) findViewById(R.id.editTerm);
        editStart = (EditText) findViewById(R.id.editStart);
        editEnd = (EditText) findViewById(R.id.editEnd);

        //set data
        id = intent.getIntExtra("Id", -1);
        editTerm.setText(intent.getStringExtra("Term"));
        editStart.setText(intent.getStringExtra("Start"));
        editEnd.setText(intent.getStringExtra("End"));

        populateView();
        populateYesInTerm();
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        populateNotInTerm();

    }

    public boolean onCourseList(Courses c){
        boolean b = false;
        for(Courses c2 : termCourseList){
            if(c2.getCourseTitle().equals(c.getCourseTitle())){
                b = true;
                return b;
            }
        }
        return false;
    }

    public void populateNotInTerm(){
        for(Courses c : courseList){
            Courses temp = c;
            boolean b = onCourseList(c);

            if(!b){
                notInTerm.add(c);
            }
        }
    }
    public void populateYesInTerm() {
        Cursor data = mDatabaseHelper.getTermCourseData(id);
        while (data.moveToNext()) {
            String courseTitle = data.getString(3);
            termCourseList.add(new Courses(courseTitle));
        }
    }

    public void populateView() {
        Cursor data = mDatabaseHelper.getCourseData();
        while (data.moveToNext()) {
            int courseId = data.getInt(0);
            String courseTitle = data.getString(1);
            courseList.add(new Courses(courseId,courseTitle));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.edit_detail_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.edit_save && editTerm.length() != 0 && editStart.length() != 0 && editEnd.length() != 0) {

            updateTerm = editTerm.getText().toString();
            updateStart = editStart.getText().toString();
            updateEnd = editEnd.getText().toString();
            updateData(id, updateTerm, updateStart, updateEnd);


            int size = courseList.size();
            for (Courses c : courseList) {

                if (c.isSelected()) {
                    addTermCourse(id,c.getCourseid(),c.getCourseTitle());

                }
            }

            //addTermCourse(id,courseId, termCourseList.indexOf(0));

            editTerm.setText("");
            editStart.setText("");
            editEnd.setText("");
            notInTerm.clear();
            Intent intent = new Intent(EditTerm.this, TermActivity.class);
            startActivity(intent);
        } else {
            toastMessage("Something went wrong");
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTermCourse(int termId, int courseId, String courseTitle) {
        mDatabaseHelper = new DatabaseHelper(this);

        boolean insert = mDatabaseHelper.addTermCourse(termId, courseId, courseTitle);
        if (insert == true) {
            toastMessage("Term updated!");

            Intent intent = new Intent(EditTerm.this, TermDetailActivity.class);
            startActivity(intent);

        } else {
            toastMessage("You have an empty text field");
        }

    }

    public class CustomAdapter extends BaseAdapter {
        private Context context;
        private int layout;
        ArrayList<Courses> notInTerm;

        public CustomAdapter(Context context, int layout, ArrayList<Courses> notInTerm) {
            this.context = context;
            this.layout = layout;
            this.notInTerm = notInTerm;
        }

        @Override
        public int getCount() {
            return notInTerm.size();
        }

        @Override
        public Object getItem(int position) {
            return notInTerm.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {

            TextView textTitle;
            CheckBox check;
        }

        public boolean[] checkedHolder;

        private void createCheckedHolder() {
            checkedHolder = new boolean[getCount()];
        }

        @Override
        public View getView(final int position, final View view, ViewGroup parent) {

            View row = view;
            final ViewHolder holder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder = new ViewHolder();
                holder.check = (CheckBox) row.findViewById(R.id.checkBox1);
                holder.textTitle = (TextView) row.findViewById(R.id.all_courses);
                row.setTag(holder);

            } else {
                holder = (ViewHolder) row.getTag();
                holder.check.getTag(position);
            }
            holder.check.setTag(position);
            final Courses course = notInTerm.get(position);
            holder.check.setChecked(notInTerm.contains(position));
            holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    notInTerm.get(position).setSelected(buttonView.isChecked());
                    if (isChecked) {
                        course.isSelected();
                        //int howManyCourses = termCourseList.size();
                    } else {

                        //int howManyCourses = termCourseList.size();
                    }
                }

            });

            holder.check.setTag(position);
            holder.textTitle.setText(course.getCourseTitle());
            holder.check.setChecked(notInTerm.get(position).isSelected());


            return row;
        }

    }

    private void getSelectedCourses() {
        StringBuffer sb = new StringBuffer();
        for (Courses courses : termCourseList) {
            if (courses.isSelected()) {
                sb.append(courses.getCourseid());
                sb.append(courses.getCourseTitle());
            }
        }
    }

    public void updateTermCourses(ArrayList<Courses> textCourseList) {
        this.termCourseList = textCourseList;
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void updateData(int id, String updateTerm, String updateStart, String updateEnd) {

        mDatabaseHelper = new DatabaseHelper(this);
        boolean update = mDatabaseHelper.updateData(id, updateTerm, updateStart, updateEnd);

        if (update == true) {
            toastMessage("Term updated!");

            Intent intent = new Intent(EditTerm.this, TermDetailActivity.class);
            startActivity(intent);

        } else {
            toastMessage("You have an empty text field");
        }
    }

}
