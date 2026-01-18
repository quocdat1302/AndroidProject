package com.example.bai9;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitapbuoi1.R;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private RecyclerView rvContacts;
    private ContactAdapter adapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        rvContacts = findViewById(R.id.rvContacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<>();

        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            loadContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền truy cập danh bạ để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadContacts() {
        contactList.clear();
        ContentResolver cr = getContentResolver();
        
        // Truy vấn trực tiếp vào bảng Phone để lấy cả tên và số điện thoại nhanh hơn
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (cur != null) {
            int nameIndex = cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int numberIndex = cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            while (cur.moveToNext()) {
                String name = cur.getString(nameIndex);
                String number = cur.getString(numberIndex);
                contactList.add(new Contact(name, number));
            }
            cur.close();
        }

        Log.d("ContactActivity", "Số lượng danh bạ tìm thấy: " + contactList.size());

        if (contactList.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy danh bạ nào trong máy", Toast.LENGTH_LONG).show();
        }

        adapter = new ContactAdapter(contactList);
        rvContacts.setAdapter(adapter);
    }
}
