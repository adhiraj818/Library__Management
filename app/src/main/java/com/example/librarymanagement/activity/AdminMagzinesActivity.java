package com.example.librarymanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class AdminMagzinesActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    Button submit;
    EditText title,date,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_magzines);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        title = findViewById(R.id.magazines_title);
        date = findViewById(R.id.magazines_date);
        type = findViewById(R.id.magazines_type);
        submit = findViewById(R.id.submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fragment_open_enter);
                submit.startAnimation(anim);
                addData(title.getText().toString(),date.getText().toString(),type.getText().toString());
            }
        });

    }

    public void addData( String title, String date , String type ){

        DocumentReference documentReference = fStore.collection("Magazines").document(title);

        Map<String,Object> magazines = new HashMap<>();
        magazines.put("date",date);
        magazines.put("type",type);



        documentReference.set(magazines);

    }

}