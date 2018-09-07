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

public class AssessmentsActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    SQLiteDatabase database;
    private static final String TAG = "AssessmentsActivity";
    private ArrayList<Assessments> list = new ArrayList<>();
    Terms tm;
    Context c;
    private ArrayList<String> listString;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        listView = (ListView) findViewById(R.id.assessments_listView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateView();
        CustomAdapter adapter = new CustomAdapter(this, R.layout.content_assessments, list);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void populateView(){
        Cursor data = myDb.getAssessmentsData();
        while(data.moveToNext()){
            String type = data.getString(1);
            String name = data.getString(2);
            String date = data.getString(3);
            list.add(new Assessments(type,name,date));
        }

    }


    public void handleAddAssessmentsActivity(View view){
        Intent intent = new Intent(AssessmentsActivity.this, AddAssessment.class);
        startActivity(intent);

    }

    public class CustomAdapter extends BaseAdapter {
        private Context context;
        private int layout;
        ArrayList<Assessments> textList;

        public CustomAdapter(Context context, int layout, ArrayList<Assessments> textList){
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
            TextView textType;
            TextView textName;
            TextView textDate;

        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            View row = view;
            ViewHolder holder;

            if (row == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder = new ViewHolder();
                holder.textType = (TextView) row.findViewById(R.id.a_type);
                holder.textName = (TextView) row.findViewById(R.id.a_name);
                holder.textDate = (TextView) row.findViewById(R.id.a_duedate);
                row.setTag(holder);
            }
            else{
                holder = (ViewHolder) row.getTag();
            }
            final Assessments assessment = textList.get(position);
            holder.textType.setText(assessment.getType());
            holder.textName.setText(assessment.getName());
            holder.textDate.setText(assessment.getDate());
            row.setOnClickListener(new AdapterView.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Cursor data = myDb.getAssessmentID(assessment.getName());
                    int assessmentId = -1;
                    String assessmentType = "";
                    String assessmentName = "";
                    String assessmentDate = "";
                    while (data.moveToNext()) {
                        assessmentId = data.getInt(0);
                        assessmentType = data.getString(1);
                        assessmentName = data.getString(2);
                        assessmentDate = data.getString(3);
                        Intent intent = new Intent(AssessmentsActivity.this, AssessmentDetailActivity.class);
                        intent.putExtra("Id", assessmentId);
                        intent.putExtra("Name", assessment.getName());
                        startActivity(intent);
                    }
                }
            });

            return row;
        }
    }


}
