package com.example.librarymanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librarymanagement.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer_layout ;
    NavigationView nav_view;
    Button drawer_opener;
    Menu menu;
    FirebaseUser user;
    TextView user_name;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fStore = FirebaseFirestore.getInstance();


//        getting User
        user = FirebaseAuth.getInstance().getCurrentUser();

       userID = userInfo(user);

        user_name = findViewById(R.id.name);



        DocumentReference documentReference = fStore.collection("userDetails").document(userID);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user_name.setText(documentSnapshot.getString("Name"));
                if (TextUtils.equals(user_name.getText().toString(),"Admin Mode")) {
                    Toast.makeText(getApplicationContext(), "click on action from navigation panel to enable admin mode", Toast.LENGTH_LONG).show();
                }

            }
            });

//        user_name.setText("User Name : "+user.getDisplayName().toString());

        drawer_layout = findViewById(R.id.drawer);
        nav_view = (NavigationView)findViewById(R.id.nav_views);
        drawer_opener = findViewById(R.id.drawer_opener);

         menu = nav_view.getMenu();


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawer_layout,R.string.open_drawer,R.string.close_drawer);
        drawer_layout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        nav_view.bringToFront();
        nav_view.setNavigationItemSelectedListener(this);

        drawer_opener.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
                drawer_opener.startAnimation(animation);

                new CountDownTimer(600, 300) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        drawer_layout.openDrawer(GravityCompat.START);
                    }
                }.start();


            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

       if (TextUtils.equals(user_name.getText().toString(),"Admin Mode")){
//           Toast.makeText(getApplicationContext(),"click on action from navigation panel to enable admin mode",Toast.LENGTH_LONG).show();
           menu.findItem(R.id.admin).setVisible(true);
       }

        switch (item.getItemId()){
            case R.id.dashboard :

                Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.admin :
                startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                return true;

            case R.id.actions :
                if(menu.findItem(R.id.logout).isVisible() == false){
                    menu.findItem(R.id.logout).setVisible(true);
                    menu.findItem(R.id.exit).setVisible(true);
                    menu.findItem(R.id.actions).setIcon(R.drawable.downarrow);
                }else{
                    menu.findItem(R.id.logout).setVisible(false);
                    menu.findItem(R.id.exit).setVisible(false);
                    menu.findItem(R.id.actions).setIcon(R.drawable.action);
                }
                return true;

            case R.id.members :
                startActivity(new Intent(getApplicationContext(),MembersActivity.class));
                return true;

            case R.id.books :

                startActivity(new Intent(getApplicationContext(),BooksActivity.class));
                return true;

            case R.id.magzines :
                startActivity(new Intent(getApplicationContext(),MagazinesActivity.class));
                return true;

            case R.id.newspaper :
                startActivity(new Intent(getApplicationContext(),NewspaperActivity.class));
                return true;

//            case R.id.issued :
//                return true;

            case R.id.exit:
                drawer_layout.closeDrawer(GravityCompat.START);
                new AlertDialog.Builder(DashboardActivity.this, android.R.style.Theme_Material_Dialog_Alert).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exiting library")
                        .setMessage("Are You sure ?")
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Welcome Back", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       onBackPressed();
                    }
                }).show();
                return true;

            case R.id.logout :
                MainActivity.islogged_in.edit().putBoolean("loggedin_check",false).apply();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                return true;
            default: return false;
        }
    }

    public String userInfo(FirebaseUser user){
        String uid = "";
        if (user != null) {

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
           uid = user.getUid();

        }
            return uid;
    }

}