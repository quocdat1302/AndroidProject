package com.example.bai7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitapbuoi1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity implements StudentAdapter.OnStudentActionListener {

    private RecyclerView rvStudents;
    private FloatingActionButton fabAdd;
    private DatabaseHelper db;
    private List<Student> studentList;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        db = new DatabaseHelper(this);
        rvStudents = findViewById(R.id.rvStudents);
        fabAdd = findViewById(R.id.fabAdd);

        // Khởi tạo dữ liệu mẫu nếu DB trống (yêu cầu 10 sinh viên)
        initSampleData();

        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        loadData();

        fabAdd.setOnClickListener(v -> showStudentDialog(null));
    }

    private void initSampleData() {
        if (db.getAllStudents().isEmpty()) {
            for (int i = 1; i <= 10; i++) {
                db.addStudent(new Student("Sinh viên " + i, "sinhvien" + i + "@gmail.com"));
            }
        }
    }

    private void loadData() {
        studentList = db.getAllStudents();
        adapter = new StudentAdapter(studentList, this);
        rvStudents.setAdapter(adapter);
    }

    private void showStudentDialog(Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_student, null);
        builder.setView(view);

        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtEmail = view.findViewById(R.id.edtEmail);

        if (student != null) {
            edtName.setText(student.getName());
            edtEmail.setText(student.getEmail());
        }

        builder.setPositiveButton(student == null ? "Thêm" : "Cập nhật", (dialog, which) -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            if (!name.isEmpty() && !email.isEmpty()) {
                if (student == null) {
                    db.addStudent(new Student(name, email));
                    Toast.makeText(this, "Đã thêm sinh viên", Toast.LENGTH_SHORT).show();
                } else {
                    student.setName(name);
                    student.setEmail(email);
                    db.updateStudent(student);
                    Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                }
                loadData();
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.create().show();
    }

    @Override
    public void onEdit(Student student) {
        showStudentDialog(student);
    }

    @Override
    public void onDelete(Student student) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Bạn có chắc muốn xóa sinh viên này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    db.deleteStudent(student.getId());
                    loadData();
                    Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
