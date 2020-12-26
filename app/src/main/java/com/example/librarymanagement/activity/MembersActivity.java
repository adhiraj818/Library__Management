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

public class MembersActivity extends AppCompatActivity {

    TableLayout tableLayout;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        fStore = FirebaseFirestore.getInstance();


        tableLayout = findViewById(R.id.datalayout);

        fStore.collection("Members")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // use each document

                                String  all_name,all_id,all_contact;


                                all_name = document.getId();
                                all_id = document.getString("id");
                                all_contact = document.getString("contact");

                                addTableRow(tableLayout,all_name,all_id,all_contact);

                            }
                        } else {
                            Log.d("Firebase_error", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void addTableRow(TableLayout tableLayout, String all_name, String all_id, String all_contact) {
        TableRow tableRow = new TableRow(MembersActivity.this);
        final TextView names = new TextView(MembersActivity.this);
        final TextView id = new TextView(MembersActivity.this);
        final TextView contact = new TextView(MembersActivity.this);

        names.setText(all_name);
        id.setText(all_id);
        contact.setText(all_contact);

        names.setWidth(300);
        names.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        names.setGravity(Gravity.CENTER_HORIZONTAL);

        id.setWidth(400);
        id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        id.setGravity(Gravity.CENTER_HORIZONTAL);

        contact.setWidth(350);
        contact.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        contact.setGravity(Gravity.CENTER_HORIZONTAL);

        tableRow.addView(names);
        tableRow.addView(id);
        tableRow.addView(contact);

        tableLayout.addView(tableRow);


    }
}