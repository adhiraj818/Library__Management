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

public class NewspaperActivity extends AppCompatActivity {

    TableLayout tableLayout;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper);

        fStore = FirebaseFirestore.getInstance();
        tableLayout = findViewById(R.id.datalayout);

        fStore.collection("Newspaper")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // use each document

                                String  all_name,all_lang,all_pages;


                                all_name = document.getId();
                                all_lang = document.getString("language");
                                all_pages = document.getString("pages");

                                addTableRow(tableLayout,all_name,all_lang,all_pages);

                            }
                        } else {
                            Log.d("Firebase_error", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    private void addTableRow(TableLayout tableLayout, String all_name, String all_lang, String all_pages) {
        TableRow tableRow = new TableRow(NewspaperActivity.this);
        final TextView names = new TextView(NewspaperActivity.this);
        final TextView lang = new TextView(NewspaperActivity.this);
        final TextView pages = new TextView(NewspaperActivity.this);

        names.setText(all_name);
        lang.setText(all_lang);
        pages.setText(all_pages);

        names.setWidth(300);
        names.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        names.setGravity(Gravity.CENTER_HORIZONTAL);

        lang.setWidth(400);
        lang.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        lang.setGravity(Gravity.CENTER_HORIZONTAL);

        pages.setWidth(350);
        pages.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        pages.setGravity(Gravity.CENTER_HORIZONTAL);

        tableRow.addView(names);
        tableRow.addView(lang);
        tableRow.addView(pages);

        tableLayout.addView(tableRow);

    }


}