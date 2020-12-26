package com.example.librarymanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.librarymanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MagazinesActivity extends AppCompatActivity {

    TableLayout tableLayout;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazines);

        fStore = FirebaseFirestore.getInstance();
        tableLayout = findViewById(R.id.datalayout);

        fStore.collection("Magazines")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // use each document

                                String  all_name,all_date,all_type;


                                all_name = document.getId();
                                all_date = document.getString("date");
                                all_type = document.getString("type");

                                addTableRow(tableLayout,all_name,all_date,all_type);

                            }
                        } else {
                            Log.d("Firebase_error", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private void addTableRow(TableLayout tableLayout, String all_name, String all_date, String all_type) {
        TableRow tableRow = new TableRow(MagazinesActivity.this);
        final TextView names = new TextView(MagazinesActivity.this);
        final TextView date = new TextView(MagazinesActivity.this);
        final TextView type = new TextView(MagazinesActivity.this);

        names.setText(all_name);
        date.setText(all_date);
        type.setText(all_type);

        names.setWidth(300);
        names.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        names.setGravity(Gravity.CENTER_HORIZONTAL);

        date.setWidth(400);
        date.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        date.setGravity(Gravity.CENTER_HORIZONTAL);

        type.setWidth(350);
        type.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        type.setGravity(Gravity.CENTER_HORIZONTAL);

        tableRow.addView(names);
        tableRow.addView(date);
        tableRow.addView(type);

        tableLayout.addView(tableRow);
        
    }
}