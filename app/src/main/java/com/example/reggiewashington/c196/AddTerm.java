package com.example.reggiewashington.c196;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddTerm extends AppCompatActivity {
    private static final String TAG = "AddTerm";
    private EditText editText, editText2, editText3;
    DatabaseHelper mDatabaseHelper;
    private Button addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_term);
        editText = (EditText) findViewById(R.id.addTerm);
        editText2 = (EditText) findViewById(R.id.addStart);
        editText3 = (EditText) findViewById(R.id.addEnd);
        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn = (Button) findViewById(R.id.addBtn);
        mDatabaseHelper = new DatabaseHelper(this);

        addBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        String term = editText.getText().toString();
                        String start = editText2.getText().toString();
                        String end = editText3.getText().toString();
                        if(editText.length() != 0 && editText2.length() != 0 && editText3.length() != 0 ){
                            addNewData(term, start, end);
                            editText.setText("");
                            editText2.setText("");
                            editText3.setText("");
                            Intent intent = new Intent(AddTerm.this, TermActivity.class);
                            startActivity(intent);

                        }else{
                            toastMessage("You have an empty field");
                        }

                }
                });
    }

    public void addNewData(String term, String start, String end) {
        final boolean insertData = mDatabaseHelper.addData(term, start, end);

                if(insertData == true){
                    toastMessage("Term inserted!");
                }else{
                    toastMessage("You have en empty text field");
                }
            }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
