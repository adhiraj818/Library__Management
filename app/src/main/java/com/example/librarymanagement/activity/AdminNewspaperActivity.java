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

public class AdminNewspaperActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    Button submit,submit_more;
    EditText title,lang,pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_newspaper);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        title = findViewById(R.id.newspaper_title);
        lang = findViewById(R.id.newspaper_lang);
        pages = findViewById(R.id.newspaper_pages);

        submit = findViewById(R.id.submit_button);
        submit_more = findViewById(R.id.submit_more);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fragment_open_enter);
                submit.startAnimation(anim);
                addData(title.getText().toString(),lang.getText().toString(),pages.getText().toString());
            }
        });

        submit_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fragment_open_enter);
                submit_more.startAnimation(anim);
                startActivity(new Intent(AdminNewspaperActivity.this,AdminMagzinesActivity.class));
            }
        });

    }

    public void addData( String title, String lang , String pages ){

        DocumentReference documentReference = fStore.collection("Newspaper").document(title);

        Map<String,Object> newspaper = new HashMap<>();
        newspaper.put("language",lang);
        newspaper.put("pages",pages);



        documentReference.set(newspaper);

    }

}