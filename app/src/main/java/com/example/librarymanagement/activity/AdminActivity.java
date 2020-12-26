package com.example.librarymanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.librarymanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    EditText book_title,book_author,book_qty,book_date,book_edition,book_isbn;
    Button submit,submit_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        book_title = findViewById(R.id.book_title);
        book_author = findViewById(R.id.book_author);
        book_qty = findViewById(R.id.book_Qty);
        book_date = findViewById(R.id.book_date);
        book_edition = findViewById(R.id.book_edition);
        book_isbn = findViewById(R.id.book_isbn);

        submit = findViewById(R.id.submit_button);
        submit_more = findViewById(R.id.submit_more);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fragment_open_enter);
                submit.startAnimation(anim);
           addData(book_isbn.getText().toString(),book_title.getText().toString(),book_author.getText().toString(),book_qty.getText().toString(),book_date.getText().toString(),book_edition.getText().toString());
            }
        });

        submit_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,AdminNewspaperActivity.class));
            }
        });

    }

    public void addData(String isbn ,String title ,String author ,String qty ,String date ,String edition  ){

        DocumentReference documentReference = fStore.collection("Books").document(title);

        Map<String,Object> book = new HashMap<>();
        book.put("author",author);
        book.put("date",date);
        book.put("edition",edition);
        book.put("isbn",isbn);
        book.put("qty",qty);

        documentReference.set(book);

    }

}