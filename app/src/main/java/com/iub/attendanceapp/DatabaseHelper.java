package com.iub.attendanceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;

    // Course Table
    public static final String COURSE_TABLE_NAME = "COURSE_TABLE";
    public static final String C_ID = "_CID";
    public static final String COURSE_ID_KEY = "COURSE_ID";
    public static final String COURSE_NAME_KEY = "COURSE_NAME";

    public static final String CREATE_COURSE_TABLE =
            "CREATE TABLE " + COURSE_TABLE_NAME + "(" +
                    C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    COURSE_ID_KEY + " TEXT NOT NULL, " +
                    COURSE_NAME_KEY + " TEXT NOT NULL, " +
                    "UNIQUE (" + COURSE_ID_KEY + "," + COURSE_NAME_KEY + ")" +
                    ");";

    public static final String DROP_COURSE_TABLE = "DROP TABLE IF EXISTS " + COURSE_TABLE_NAME;
    public static final String SELECT_COURSE_TABLE = "SELECT * FROM " + COURSE_TABLE_NAME;

    // Student Table

    public static final String STUDENT_TABLE_NAME = "STUDENT_TABLE";
    public static final String S_ID = "_SID";
    public static final String STUDENT_ID_KEY = "ID";
    public static final String STUDENT_NAME_KEY = "STUDENT_NAME";

    public static final String CREATE_STUDENT_TABLE =
            "CREATE TABLE " + STUDENT_TABLE_NAME +
                    "(" +
                    S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    C_ID + " INTEGER NOT NULL, " +
                    STUDENT_NAME_KEY + " TEXT NOT NULL, " +
                    STUDENT_ID_KEY + " INTEGER, " +
                    " FOREIGN KEY (" + C_ID + ") REFERENCES " + COURSE_TABLE_NAME + "(" + C_ID + ")" +
                    ");";

    public static final String DROP_STUDENT_TABLE = "DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME;
    public static final String SELECT_STUDENT_TABLE = "SELECT * FROM " + STUDENT_TABLE_NAME;

    // Status Table

    public static final String STATUS_TABLE_NAME = "STATUS_TABLE";
    public static final String STATUS_ID = "_STATUS_ID";
    public static final String DATE_KEY = "STATUS_DATE";
    public static final String STATUS_KEY = "STATUS";

    public static final String CREATE_STATUS_TABLE =
            "CREATE TABLE " + STATUS_TABLE_NAME +
                    "(" +
                    STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    S_ID + " INTEGER NOT NULL, " +
                    DATE_KEY + " DATE NOT NULL, " +
                    STATUS_KEY + " TEXT NOT NULL, " +
                    "UNIQUE (" + S_ID + ", " + DATE_KEY + ")," +
                    " FOREIGN KEY (" + S_ID + ") REFERENCES " + STUDENT_TABLE_NAME + "(" + S_ID + ")" +
                    ");";

    public static final String DROP_STATUS_TABLE = "DROP TABLE IF EXISTS " + STATUS_TABLE_NAME;
    public static final String SELECT_STATUS_TABLE = "SELECT * FROM " + STATUS_TABLE_NAME;


    public DatabaseHelper(@Nullable Context context) {
        super(context, "AttendanceDB", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_COURSE_TABLE);
        database.execSQL(CREATE_STUDENT_TABLE);
        database.execSQL(CREATE_STATUS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        try {
            database.execSQL(DROP_COURSE_TABLE);
            database.execSQL(DROP_STUDENT_TABLE);
            database.execSQL(DROP_STATUS_TABLE);
        } catch (SQLiteException sqLiteException) {
            sqLiteException.printStackTrace();
        }
    }

    long addCourse(String courseId, String courseName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_ID_KEY, courseId);
        values.put(COURSE_NAME_KEY, courseName);

        return database.insert(COURSE_TABLE_NAME, null, values);
    }

    Cursor getCourseTable() {
        SQLiteDatabase database = this.getReadableDatabase();

        return database.rawQuery(SELECT_COURSE_TABLE, null);
    }

    int deleteCourse(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(COURSE_TABLE_NAME, C_ID + "=?", new String[]{String.valueOf(cid)});
    }

    long updateCourse(long cid, String courseId, String courseName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_ID_KEY, courseId);
        values.put(COURSE_NAME_KEY, courseName);

        return database.update(COURSE_TABLE_NAME, values, C_ID + "=?", new String[]{String.valueOf(cid)});
    }

    long addStudent(long cid, int id, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_ID, cid);
        values.put(STUDENT_ID_KEY, id);
        values.put(STUDENT_NAME_KEY, name);
        return database.insert(STUDENT_TABLE_NAME, null, values);
    }

    Cursor getStudentTable(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(STUDENT_TABLE_NAME, null, C_ID + "=?",
                new String[]{String.valueOf(cid)}, null, null, STUDENT_ID_KEY);
    }

    int deleteStudent(long sid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(STUDENT_TABLE_NAME, S_ID + "=?", new String[]{String.valueOf(sid)});
    }

    long updateStudent(long sid, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME_KEY, name);
        return database.update(STUDENT_TABLE_NAME, values, S_ID + "=?", new String[]{String.valueOf(sid)});
    }
}
