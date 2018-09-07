package com.example.reggiewashington.c196;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    SQLiteDatabase database;
    private static final String TAG = "CourseActivity";
    private ArrayList<Courses> list = new ArrayList<>();
    Courses cs;
    Context c;
    private ArrayList<String> listString;

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        listView = (ListView) findViewById(R.id.course_listView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_course_list);
        setSupportActionBar(toolbar);
        myDb = new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateView();
        CourseActivity.CustomAdapter adapter = new CourseActivity.CustomAdapter(this, R.layout.content_course, list);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void populateView(){
        Cursor data = myDb.getCourseData();
        while(data.moveToNext()){
            String course = data.getString(1);
            list.add(new Courses(course));
        }

    }

    public void handleAddCourseActivity(View view){
        Intent intent = new Intent(CourseActivity.this, AddCourse.class);
        startActivity(intent);

    }

    public class CustomAdapter extends BaseAdapter {
        private Context context;
        private int layout;
        ArrayList<Courses> textList;

        public CustomAdapter(Context context, int layout, ArrayList<Courses> textList){
            this.context = context;
            this.layout = layout;
            this.textList = textList;
        }

        @Override
        public int getCount() {
            return textList.size();
        }

        @Override
        public Object getItem(int position) {
            return textList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder{
            TextView textCourse;

        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            View row = view;
            CourseActivity.CustomAdapter.ViewHolder holder;

            if (row == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder = new CourseActivity.CustomAdapter.ViewHolder();
                holder.textCourse = (TextView) row.findViewById(R.id.t_course);
                row.setTag(holder);
            }
            else{
                holder = (CourseActivity.CustomAdapter.ViewHolder) row.getTag();
            }
            final Courses course = textList.get(position);
            holder.textCourse.setText(course.getCourseTitle());
            row.setOnClickListener(new AdapterView.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Cursor data = myDb.getCourseID(course.getCourseTitle());
                    int courseId = -1;
                    String courseTitle = "";

                    while (data.moveToNext()) {
                        courseId = data.getInt(0);
                        courseTitle = data.getString(1);

                        Intent intent = new Intent(CourseActivity.this, CourseDetailActivity.class);
                        intent.putExtra("Id", courseId);
                        intent.putExtra("Course", course.getCourseTitle());
                        startActivity(intent);
                    }
                }
            });

            return row;
        }
    }


}
