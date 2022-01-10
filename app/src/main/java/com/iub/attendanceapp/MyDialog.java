package com.iub.attendanceapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class MyDialog extends DialogFragment {
    public static final String COURSE_ADD_DIALOG = "addCourse";
    public static final String COURSE_UPDATE_DIALOG = "updateCourse";
    public static final String STUDENT_ADD_DIALOG = "addStudent";
    public static final String STUDENT_UPDATE_DIALOG = "updateStudent";

    private OnClickListener listener;
    private int id;
    private String name;
    private String courseId;
    private String courseName;

    public MyDialog() {

    }

    public MyDialog(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public MyDialog(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public interface OnClickListener {
        void onClick(String text1, String text2);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if (getTag().equals(COURSE_ADD_DIALOG))
            dialog = getAddCourseDialog();
        if (getTag().equals(STUDENT_ADD_DIALOG))
            dialog = getAddStudentDialog();
        if (getTag().equals(COURSE_UPDATE_DIALOG))
            dialog = getUpdateCourseDialog();
        if (getTag().equals(STUDENT_UPDATE_DIALOG))
            dialog = getUpdateStudentDialog();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    private Dialog getUpdateStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Update Student");

        EditText id_edt = view.findViewById(R.id.edt01);
        EditText name_edt = view.findViewById(R.id.edt02);

        id_edt.setHint("ID");
        name_edt.setHint("Name");
        Button cancel = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);
        add.setText("Update");
        id_edt.setText(id + "");
        id_edt.setEnabled(false);
        name_edt.setText(name);
        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String id = id_edt.getText().toString();
            String name = name_edt.getText().toString();
            listener.onClick(id, name);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getUpdateCourseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Update Course Info");

        EditText courseId_edt = view.findViewById(R.id.edt01);
        EditText courseName_edt = view.findViewById(R.id.edt02);

        courseId_edt.setHint("Course ID");
        courseId_edt.setText(courseId);
        courseName_edt.setHint("Course Name");
        courseName_edt.setText(courseName);
        Button cancel = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);
        add.setText("Update");

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String courseId = courseId_edt.getText().toString();
            String courseName = courseName_edt.getText().toString();
            listener.onClick(courseId, courseName);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Add New Student");

        EditText id_edt = view.findViewById(R.id.edt01);
        EditText name_edt = view.findViewById(R.id.edt02);

        id_edt.setHint("ID");
        name_edt.setHint("Name");
        Button cancel = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String id = id_edt.getText().toString();
            String name = name_edt.getText().toString();
            id_edt.setText("");
            name_edt.setText("");
            listener.onClick(id, name);
        });

        return builder.create();
    }

    private Dialog getAddCourseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Add New Course");

        EditText courseId_edt = view.findViewById(R.id.edt01);
        EditText courseName_edt = view.findViewById(R.id.edt02);

        courseId_edt.setHint("Course ID");
        courseName_edt.setHint("Course Name");
        Button cancel = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String courseId = courseId_edt.getText().toString();
            String courseName = courseName_edt.getText().toString();
            listener.onClick(courseId, courseName);
            dismiss();
        });

        return builder.create();
    }
}
