package com.example.librarymanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.librarymanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    TableLayout tableLayout1,tableLayout2;
    FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        fStore = FirebaseFirestore.getInstance();


        tableLayout1 = findViewById(R.id.datalayout1);
        tableLayout2 = findViewById(R.id.datalayout2);
//        for (int j = 0; j<2; j++) {
//            addTableRow(tableLayout1, tableLayout2);
//        }
        fStore.collection("Books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // use each document

                                String all_isbn , all_title,all_author,all_qty,all_date,all_edition;

                                all_isbn = document.getString("isbn");
                                all_title = document.getId();
                                all_author = document.getString("author");
                                all_qty = document.getString("qty");
                                all_date = document.getString("date");
                                all_edition = document.getString("edition");

                                addTableRow(tableLayout1, tableLayout2,all_isbn,all_title,all_author,all_qty,all_date,all_edition);

                            }
                        } else {
                            Log.d("Firebase_error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void addTableRow(TableLayout table1, TableLayout table2, String all_isbn, String all_title, String all_author , String all_qty, String all_date , String all_edition){

        TableRow tableRow1 = new TableRow(BooksActivity.this);
        TableRow tableRow2 = new TableRow(BooksActivity.this);
        final TextView isbn = new TextView(BooksActivity.this);
        final TextView book_title = new TextView(BooksActivity.this);
        final TextView author = new TextView(BooksActivity.this);
        final TextView qty = new TextView(BooksActivity.this);
        final TextView purchase_date = new TextView(BooksActivity.this);
        final TextView edition = new TextView(BooksActivity.this);

//        DocumentReference documentReference = fStore.collection("Books").document(BookId);
//        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                DocumentSnapshot document = task.getResult();
//                String group_string= String.valueOf(document.getData());
//
//                isbn.setText(group_string);
//                Log.d("myTag", group_string);
//            }
//        });
//

//        final List<String> all_isbn , all_title,all_author,all_qty,all_date,all_edition;
//        all_isbn = new ArrayList<String>();
//        all_title = new ArrayList<String>();
//        all_author = new ArrayList<String>();
//        all_qty = new ArrayList<String>();
//        all_date = new ArrayList<String>();
//        all_edition = new ArrayList<String>();

//        fStore.collection("Books")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                // use each document
//                                all_isbn.add(document.getString("isbn"));
//                                all_title.add(document.getId());
//                                all_author.add(document.getString("author"));
//                                all_qty.add(document.getString("qty"));
//                                all_date.add(document.getString("date"));
//                                all_edition.add(document.getString("edition"));
//
//                            }
//                        } else {
//                            Log.d("Firebase_error", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });


            isbn.setText(all_isbn);
            book_title.setText(all_title);
            author.setText(all_author);
            qty.setText(all_qty);
            purchase_date.setText(all_date);
            edition.setText(all_edition);


//        isbn.setText("4564653");
        isbn.setWidth(300);
        isbn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        isbn.setGravity(Gravity.CENTER_HORIZONTAL);


//        isbn.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

//        book_title.setText("my book");
        book_title.setWidth(400);
        book_title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        book_title.setGravity(Gravity.CENTER_HORIZONTAL);

//        book_title.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

//        author.setText("adhiraj");
        author.setWidth(350);
        author.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        author.setGravity(Gravity.CENTER_HORIZONTAL);
//        author.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

//        qty.setText("33");
        qty.setWidth(300);
        qty.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        qty.setGravity(Gravity.CENTER_HORIZONTAL);

//        qty.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

//        purchase_date.setText("24/08/20");
        purchase_date.setWidth(400);
        purchase_date.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        purchase_date.setGravity(Gravity.CENTER_HORIZONTAL);
//        purchase_date.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

//        edition.setText("3");
        edition.setWidth(350);
        edition.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        edition.setGravity(Gravity.CENTER_HORIZONTAL);

//        edition.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);


        tableRow1.addView(isbn);
        tableRow1.addView(book_title);
        tableRow1.addView(author);
        tableRow1.setDividerPadding(10);

        table1.addView(tableRow1);

        tableRow2.addView(qty);
        tableRow2.addView(purchase_date);
        tableRow2.addView(edition);

        table2.addView(tableRow2);

    }

}