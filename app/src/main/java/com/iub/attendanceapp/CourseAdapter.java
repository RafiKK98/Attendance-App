package com.iub.attendanceapp;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    ArrayList<CourseItem> courseItems;
    Context context;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CourseAdapter(Context context, ArrayList<CourseItem> courseItems) {
        this.courseItems = courseItems;
        this.context = context;
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView courseId;
        TextView courseName;
        public CourseViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            courseId = itemView.findViewById(R.id.courseId_tv);
            courseName = itemView.findViewById(R.id.courseName_tv);
            itemView.setOnClickListener(view -> onItemClickListener.onClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(getAdapterPosition(),0,0,"Edit");
            contextMenu.add(getAdapterPosition(),1,0,"Delete");
        }
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item,parent, false);
        return new CourseViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.courseId.setText(courseItems.get(position).getCourseId());
        holder.courseName.setText(courseItems.get(position).getCourseName());

    }

    @Override
    public int getItemCount() {
        return courseItems.size();
    }
}
