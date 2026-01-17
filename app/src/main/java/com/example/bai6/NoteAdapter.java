package com.example.bai6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baitapbuoi1.R;

import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<Note> noteList;
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Note note);
    }

    public NoteAdapter(Context context, List<Note> noteList, OnDeleteClickListener deleteListener) {
        this.context = context;
        this.noteList = noteList;
        this.deleteListener = deleteListener;
    }

    @Override
    public int getCount() { return noteList.size(); }

    @Override
    public Object getItem(int position) { return noteList.get(position); }

    @Override
    public long getItemId(int position) { return noteList.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        }

        Note note = noteList.get(position);
        TextView tvContent = convertView.findViewById(R.id.tvNoteContent);
        ImageView ivDelete = convertView.findViewById(R.id.ivDelete);

        tvContent.setText(note.getContent());
        ivDelete.setOnClickListener(v -> deleteListener.onDeleteClick(note));

        return convertView;
    }
}
