package com.example.reggiewashington.c196;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "terms.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "terms_table";
    public static final String TABLE_COURSE = "course_table";
    public static final String TABLE_ASSESSMENTS = "assessments_table";
    public static final String TABLE_TERM_COURSE = "terms_course";
    public static final String TABLE_COURSE_ASSESSMENTS = "course_assessments";

    //General column names
    public static final String COL_1 = "ID";

    //Terms - column names
    public static final String COL_2 = "TERM";
    public static final String COL_3 = "START";
    public static final String COL_4 = "END";

    //Course - column name
    public static final String COL_TITLE = "TITLE";
    public static final String COL_CSTART = "CSTART";
    public static final String COL_CEND = "CEND";
    public static final String COL_STATUS = "STATUS";
    public static final String COL_MENTOR = "MENTOR";
    public static final String COL_MNUMBER = "MNUMBER";
    public static final String COL_MEMAIL = "MEMAIL";
    public static final String COL_NOTES = "NOTES";
    public static final String COL_BOOLEAN = "BOOLEAN";

    //Assessments column names
    public static final String COL_TYPE = "TYPE";
    public static final String COL_NAME = "NAME";
    public static final String COL_DATE = "DATE";

    //Terms Course column names
    public static final String COL_TID = "TERMS_ID";
    public static final String COL_CID = "COURSES_ID";

    //Course Assessment column names
    public static final String COL_AID = "ASSESSMENTS_ID";


    public DatabaseHelper(Context context) {
        //Terms table create
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean insertCourse(int termId, int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TID, termId);
        contentValues.put(COL_CID, courseId);


        Log.d(TAG, "addData: Adding " + courseId + " to" + TABLE_TERM_COURSE);

        long result = db.insert(TABLE_TERM_COURSE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //SQLiteDatabase db = getWritableDatabase();
        String createTermTable = "create table " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT," + COL_3 + " TEXT," + COL_4 + " TEXT)";
        String addTerm = "INSERT INTO terms_table VALUES(1, 'TERM 1', '2018-01-01', '2018-06-30')";
        String addTerm2 = "INSERT INTO terms_table VALUES(2, 'TERM 2', '2018-07-01', '2018-12-31')";
        String addTerm3 = "INSERT INTO terms_table VALUES(3, 'TERM 3', '2019-01-01', '2019-06-30')";
        String addTerm4 = "INSERT INTO terms_table VALUES(4, 'TERM 4', '2019-07-01', '2019-12-31')";
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(createTermTable);
        db.execSQL(addTerm);
        db.execSQL(addTerm2);
        db.execSQL(addTerm3);
        db.execSQL(addTerm4);

        //Courses table create
        String createCourseTable = "create table " + TABLE_COURSE + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TITLE + " TEXT," + COL_CSTART + " TEXT," + COL_CEND + " TEXT," + COL_STATUS + " TEXT," + COL_MENTOR + " TEXT," + COL_MNUMBER + " TEXT," + COL_MEMAIL + " TEXT," + COL_NOTES + " TEXT)";
        String addCourse = "INSERT INTO course_table VALUES(1, 'English Composition II - C456', '2018-01-01', '2018-06-30', 'plan to take', 'Mike Smith', '609-680-9090', 'mike.smith@wgu.edu', 'test')";
        String addCourse2 = "INSERT INTO course_table VALUES(2, 'Critical Thinking and Logic - C168', '2018-07-01', '2018-12-31', 'plan to take', 'Mike Smith', '609-680-9090', 'mike.smith@wgu.edu', 'test')";
        String addCourse3 = "INSERT INTO course_table VALUES(3, 'College Algebra - C278', '2019-01-01', '2019-06-30', 'plan to take', 'Mike Smith', '609-680-9090', 'mike.smith@wgu.edu', 'test')";
        String addCourse4 = "INSERT INTO course_table VALUES(4, 'Introducation to Physics - C164', '2019-07-01', '2019-12-31', 'plan to take', 'Mike Smith', '609-680-9090', 'mike.smith@wgu.edu','test')";
        String addCourse5 = "INSERT INTO course_table VALUES(5, 'IT Foundations - C393', '2019-01-01', '2019-06-30', 'plan to take', 'Mike Smith', '609-680-9090', 'mike.smith@wgu.edu', 'test')";
        String addCourse6 = "INSERT INTO course_table VALUES(6, 'Network and Security - Foundations - C172', '2019-07-01', '2019-12-31', 'plan to take', 'Mike Smith', '609-680-9090', 'mike.smith@wgu.edu', 'test')";
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL(createCourseTable);
        db.execSQL(addCourse);
        db.execSQL(addCourse2);
        db.execSQL(addCourse3);
        db.execSQL(addCourse4);
        db.execSQL(addCourse5);
        db.execSQL(addCourse6);

        //Assessments table create
        String createAssessmentsTable = "create table " + TABLE_ASSESSMENTS + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TYPE + " TEXT," + COL_NAME + " TEXT," + COL_DATE+ " TEXT)";
        String addAssessment = "INSERT INTO assessments_table VALUES(1, 'Performance', 'English Comp II', '2018-06-30')";
        String addAssessment2 = "INSERT INTO assessments_table VALUES(2, 'Objective', 'Critical Thinking', '2018-12-31')";
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENTS);
        db.execSQL(createAssessmentsTable);
        db.execSQL(addAssessment);
        db.execSQL(addAssessment2);


        //Term_Courses table create
        String createTermCourseTable = "create table " + TABLE_TERM_COURSE + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TID + " TEXT," + COL_CID + " TEXT UNIQUE," + COL_NAME + " TEXT)";
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM_COURSE);
        db.execSQL(createTermCourseTable);

        //Course_Assessment table create
        String createCourseAssessmentTable = "create table " + TABLE_COURSE_ASSESSMENTS + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_CID + " TEXT UNIQUE," + COL_AID + " TEXT," + COL_NAME + " TEXT)";
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_ASSESSMENTS);
        db.execSQL(createCourseAssessmentTable);
    }

    public boolean deleteTermData(int t) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "deleteTermData: Deleting " + t + " from" + TABLE_NAME);

        long result = db.delete(TABLE_NAME, COL_1
                + " = " + t, null);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteAssessmentData(int t) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "deleteAssessmentData: Deleting " + t + " from" + TABLE_ASSESSMENTS);

        long result = db.delete(TABLE_ASSESSMENTS, COL_1
                + " = " + t, null);
        if (result == -1)
            return false;
        else
            return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENTS);
        onCreate(db);

    }

    public boolean addData(String term, String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, term);
        contentValues.put(COL_3, startDate);
        contentValues.put(COL_4, endDate);

        Log.d(TAG, "addData: Adding " + term + " to" + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else

            return true;

    }

    public boolean addAssessmentsData(String type, String name, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TYPE, type);
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_DATE, date);

        Log.d(TAG, "addData: Adding " + name + " to" + TABLE_ASSESSMENTS);

        long result = db.insert(TABLE_ASSESSMENTS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean addCourse(String Title, String startDate, String endDate, String Status, String Mentor, String mNumber, String mEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TITLE, Title);
        contentValues.put(COL_CSTART, startDate);
        contentValues.put(COL_CEND, endDate);
        contentValues.put(COL_STATUS, Status);
        contentValues.put(COL_MENTOR, Mentor);
        contentValues.put(COL_MNUMBER, mNumber);
        contentValues.put(COL_MEMAIL, mEmail);

        Log.d(TAG, "addData: Adding " + Title + " to" + TABLE_COURSE);

        long result = db.insert(TABLE_COURSE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean addNotes(int id, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NOTES, note);


        try {
            int result = db.update(TABLE_COURSE, cv, "ID = ?",
                    new String[]{String.valueOf(id)});
            if (result > 0) {
                Log.d(TAG, "Adding Notes to " + TABLE_COURSE);
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateData(int id, String newTerm, String newStart, String newEnd) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_2, newTerm);
        cv.put(COL_3, newStart);
        cv.put(COL_4, newEnd);

        try {

            int result = db.update(TABLE_NAME, cv, "ID = ?",
                    new String[]{String.valueOf(id)});
            if (result > 0) {
                Log.d(TAG, "updateData: Updating term in" + TABLE_NAME);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAssessment(int id, String newType, String newName, String newDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_TYPE, newType);
        cv.put(COL_NAME, newName);
        cv.put(COL_DATE, newDate);

        try {


            int result = db.update(TABLE_ASSESSMENTS, cv, "ID = ?",
                    new String[]{String.valueOf(id)});
            if (result > 0) {
                Log.d(TAG, "updateData: Updating assessment in" + TABLE_ASSESSMENTS);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCourse(int id, String newTitle, String newCourseStart, String newCourseEnd, String newStatus, String newMentor, String newMentorNumber, String newMentorEmail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_TITLE, newTitle);
        cv.put(COL_CSTART, newCourseStart);
        cv.put(COL_CEND, newCourseEnd);
        cv.put(COL_STATUS, newStatus);
        cv.put(COL_MENTOR, newMentor);
        cv.put(COL_MNUMBER, newMentorNumber);
        cv.put(COL_MEMAIL, newMentorEmail);

        try {


            int result = db.update(TABLE_COURSE, cv, "ID = ?",
                    new String[]{String.valueOf(id)});
            if (result > 0) {
                Log.d(TAG, "updateData: Updating course in" + TABLE_COURSE);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cursor getTermID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL_2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getTermData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getCourseData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean addTermCourse(int termId, int courseId, String courseTitle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TID, termId);
        cv.put(COL_CID, courseId);
        cv.put(COL_NAME, courseTitle);

        try {
            int result = (int) db.insertWithOnConflict(TABLE_TERM_COURSE, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            if (result > 0) {

                Log.d(TAG, "Adding Courses " + "to " + TABLE_TERM_COURSE);
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }
    public boolean addCourseAssessment(int courseId, int assessmentId, String assessmentTitle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_CID, courseId);
        cv.put(COL_AID, assessmentId);
        cv.put(COL_NAME, assessmentTitle);

        try {
            int result = (int) db.insertWithOnConflict(TABLE_COURSE_ASSESSMENTS, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            if (result > 0) {

                Log.d(TAG, "Adding Assessments " + "to " + TABLE_COURSE_ASSESSMENTS);
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }

    public Cursor getTermCourseData(int term) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TERM_COURSE + " WHERE " + COL_TID + "= '" + term + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getCourseAssessmentData(int course) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSE_ASSESSMENTS + " WHERE " + COL_CID + "= '" + course + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAssessmentsData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ASSESSMENTS;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

        public Cursor getSingleCourse(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSE +
                " WHERE " + COL_1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getCourseID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSE +
                " WHERE " + COL_TITLE + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getSingleAssessment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ASSESSMENTS +
                " WHERE " + COL_1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAssessmentID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ASSESSMENTS +
                " WHERE " + COL_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}