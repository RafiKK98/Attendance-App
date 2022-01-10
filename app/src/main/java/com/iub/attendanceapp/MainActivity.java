package com.iub.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    CourseAdapter courseAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<CourseItem> courseItems = new ArrayList<>();
    Toolbar toolbar;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(view -> addCourseDialog());

        loadData();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        courseAdapter = new CourseAdapter(this, courseItems);
        recyclerView.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(position -> goToItemActivity(position));

        setToolBar();
    }

    private void loadData() {
        Cursor cursor = databaseHelper.getCourseTable();

        courseItems.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.C_ID));
            String courseId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COURSE_ID_KEY));
            String courseName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COURSE_NAME_KEY));

            courseItems.add(new CourseItem(id, courseId, courseName));
        }
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);

        title.setText("Attendance App");
        subtitle.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
    }

    private void goToItemActivity(int position) {
        Intent intent = new Intent(this, StudentActivity.class);

        intent.putExtra("courseId", courseItems.get(position).getCourseId());
        intent.putExtra("courseName", courseItems.get(position).getCourseName());
        intent.putExtra("position", position);
        intent.putExtra("cid", courseItems.get(position).getCid());
        startActivity(intent);
    }

    private void addCourseDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.COURSE_ADD_DIALOG);
        dialog.setListener((courseId, courseName) -> addCourse(courseId, courseName));

    }

    private void addCourse(String courseId, String courseName) {
        long cid = databaseHelper.addCourse(courseId, courseName);
        CourseItem courseItem = new CourseItem(cid, courseId, courseName);
        courseItems.add(courseItem);
        courseAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:
                showUpdateDialog(item.getItemId());
                break;
            case 1:
                deleteCourse(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog dialog = new MyDialog(courseItems.get(position).getCourseId(), courseItems.get(position).getCourseName());
        dialog.show(getSupportFragmentManager(),MyDialog.COURSE_UPDATE_DIALOG);
        dialog.setListener((courseId, courseName) -> updateCourse(position, courseId, courseName));
    }

    private void updateCourse(int position, String courseId, String courseName) {
        databaseHelper.updateCourse(courseItems.get(position).getCid(), courseId, courseName);
        courseItems.get(position).setCourseId(courseId);
        courseItems.get(position).setCourseName(courseName);
        courseAdapter.notifyItemChanged(position);
    }

    private void deleteCourse(int position) {
        databaseHelper.deleteCourse(courseItems.get(position).getCid());
        courseItems.remove(position);
        courseAdapter.notifyItemRemoved(position);
    }
}