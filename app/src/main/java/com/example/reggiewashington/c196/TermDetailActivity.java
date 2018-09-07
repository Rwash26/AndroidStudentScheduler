package com.example.reggiewashington.c196;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import static com.example.reggiewashington.c196.DatabaseHelper.COL_2;
import static com.example.reggiewashington.c196.DatabaseHelper.TABLE_NAME;

public class TermDetailActivity extends AppCompatActivity{

    int selectedId;
    String selectedTerm, selectedStart, selectedEnd;
    TextView Id, Term, Start, End;
    TermActivity ta;
    DatabaseHelper myDb;
    private ArrayList<String> list;
    private ArrayList<Courses> courseList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_detail);
        listView = (ListView) findViewById(R.id.term_courses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        myDb = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();
        selectedId = receivedIntent.getIntExtra("Id",-1);
        selectedTerm = receivedIntent.getStringExtra("Term");
        selectedStart = receivedIntent.getStringExtra("Start");
        selectedEnd = receivedIntent.getStringExtra("End");

        Terms adapter = new Terms();
        Id = (TextView) findViewById(R.id.term_id);
        Term = (TextView) findViewById(R.id.term_title);
        Start = (TextView) findViewById(R.id.term_start1);
        End = (TextView) findViewById(R.id.term_end1);


        //set data
        Id.setText(String.valueOf(selectedId));
        Term.setText(selectedTerm);
        Start.setText("Start: " + selectedStart);
        End.setText("End: " + selectedEnd);

        populateView();
        CustomAdapter adapterC = new CustomAdapter(this, R.layout.term_courses, courseList);
        listView.setAdapter(adapterC);
        adapterC.notifyDataSetChanged();
    }

    public void populateView() {
        Cursor data = myDb.getTermCourseData(selectedId);
        while (data.moveToNext()) {
            String courseTitle = data.getString(3);
            courseList.add(new Courses(courseTitle));
        }
    }

    public class CustomAdapter extends BaseAdapter {
        private Context context;
        private int layout;
        ArrayList<Courses> textCourseList;

        public CustomAdapter(Context context, int layout, ArrayList<Courses> textCourseList) {
            this.context = context;
            this.layout = layout;
            this.textCourseList = textCourseList;
        }

        @Override
        public int getCount() {
            return textCourseList.size();
        }

        @Override
        public Object getItem(int position) {
            return textCourseList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {

            TextView textTitle;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            View row = view;
            ViewHolder holder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder = new CustomAdapter.ViewHolder();
                holder.textTitle = (TextView) row.findViewById(R.id.terms_courses);
                row.setTag(holder);

            } else {
                holder = (CustomAdapter.ViewHolder) row.getTag();
            }
            final Courses course = textCourseList.get(position);

            holder.textTitle.setText(course.getCourseTitle());
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor data = myDb.getTermCourseData(selectedId);
                    int courseId = -1;
                    String courseTitle = "";
                    while(data.moveToNext()) {
                        courseId = data.getInt(2);
                        courseTitle = data.getString(3);
                        Intent intent = new Intent(TermDetailActivity.this, CourseDetailActivity.class);
                        intent.putExtra("Id", courseId);
                        intent.putExtra("Course", courseTitle);
                        startActivity(intent);
                    }
                }
            });

            return row;
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.edit_term_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int sId = Integer.parseInt(Id.getText().toString());
        String sTerm = Term.getText().toString();
        String sStart = selectedStart;
        String sEnd = selectedEnd;


        if(item.getItemId() == R.id.action_edit){
            Intent intent = new Intent(TermDetailActivity.this, EditTerm.class);
            intent.putExtra("Id", sId);
            intent.putExtra("Term", sTerm);
            intent.putExtra("Start", sStart);
            intent.putExtra("End", sEnd);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.action_delete){
            deleteTermData(sId);
            Intent intent = new Intent(TermDetailActivity.this, TermActivity.class);
            startActivity(intent);
           //Delete Terms if there are no courses included

        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTermData(int id) {
        if (courseList.size() >= 1) {
            toastMessage("Can't delete term with attached courses");
            return;

        } else {
            final boolean deleteData = myDb.deleteTermData(id);
            toastMessage("Term deleted!");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
