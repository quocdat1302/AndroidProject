package com.example.bai6;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baitapbuoi1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private EditText edtNote;
    private FloatingActionButton btnSave;
    private ListView lvNotes;
    private DatabaseHandler db;
    private List<Note> noteList;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Khởi tạo views
        db = new DatabaseHandler(this);
        edtNote = findViewById(R.id.edtNote);
        btnSave = findViewById(R.id.btnSave);
        lvNotes = findViewById(R.id.lvNotes);

        // Load dữ liệu lên list
        loadNotes();

        // Xử lý sự kiện lưu
        btnSave.setOnClickListener(v -> {
            String content = edtNote.getText().toString().trim();
            if (!content.isEmpty()) {
                db.addNote(new Note(content));
                edtNote.setText("");
                loadNotes();
                Toast.makeText(this, "Đã thêm ghi chú mới", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Vui lòng nhập nội dung", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNotes() {
        noteList = db.getAllNotes();
        
        // Sử dụng Adapter tùy chỉnh để giao diện đẹp hơn
        adapter = new NoteAdapter(this, noteList, new NoteAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Note note) {
                confirmDelete(note);
            }
        });
        lvNotes.setAdapter(adapter);
    }

    private void confirmDelete(Note note) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa ghi chú")
                .setMessage("Bạn có muốn xóa ghi chú này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    db.deleteNote(note);
                    loadNotes();
                    Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
