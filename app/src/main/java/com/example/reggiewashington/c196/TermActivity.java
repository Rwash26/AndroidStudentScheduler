package com.example.reggiewashington.c196;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TermActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    SQLiteDatabase database;
    private static final String TAG = "TermActivity";
    private ArrayList<Terms> list = new ArrayList<>();
    Terms tm;
    Context c;
    private ArrayList<String> listString;

   private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        listView = (ListView) findViewById(R.id.term_listView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateView();
        CustomAdapter adapter = new CustomAdapter(this, R.layout.content_term, list);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void populateView(){
        Cursor data = myDb.getTermData();
        while(data.moveToNext()){
            String term = data.getString(1);
            String start = data.getString(2);
            String end = data.getString(3);
            list.add(new Terms(term,start,end));
        }

    }


    public void handleAddTermActivity(View view){
        Intent intent = new Intent(TermActivity.this, AddTerm.class);
        startActivity(intent);

    }

    public class CustomAdapter extends BaseAdapter{
        private Context context;
        private int layout;
        ArrayList<Terms> textList;

        public CustomAdapter(Context context, int layout, ArrayList<Terms> textList){
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
            TextView textTerm;
            TextView textStart;
            TextView textEnd;

        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            View row = view;
            ViewHolder holder;

            if (row == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder = new ViewHolder();
                holder.textTerm = (TextView) row.findViewById(R.id.t_term);
                holder.textStart = (TextView) row.findViewById(R.id.t_start);
                holder.textEnd = (TextView) row.findViewById(R.id.t_end);
                row.setTag(holder);
            }
            else{
                holder = (ViewHolder) row.getTag();
            }
            final Terms term = textList.get(position);
            holder.textTerm.setText(term.getTerm());
            holder.textStart.setText(term.getStart());
            holder.textEnd.setText(term.getEnd());
            row.setOnClickListener(new AdapterView.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Cursor data = myDb.getTermID(term.getTerm());
                    int termId = -1;
                    String termTerm = "";
                    String termStart = "";
                    String termEnd = "";
                    while (data.moveToNext()) {
                        termId = data.getInt(0);
                        termTerm = data.getString(1);
                        termStart = data.getString(2);
                        termEnd = data.getString(3);
                        Intent intent = new Intent(TermActivity.this, TermDetailActivity.class);
                        intent.putExtra("Id", termId);
                        intent.putExtra("Term", term.getTerm());
                        intent.putExtra("Start", term.getStart());
                        intent.putExtra("End", term.getEnd());
                        startActivity(intent);
                    }
                }
            });

            return row;
        }
    }


}
