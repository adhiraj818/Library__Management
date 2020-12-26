package com.example.librarymanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.librarymanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {

    LottieAnimationView male_anim,female_anim;
    TextView login_text;
    EditText usermail,userpassword,repassword, name;
    Button registerButton;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        firebase getInstances
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        usermail = findViewById(R.id.usermail);
        userpassword = findViewById(R.id.userpassword);
        registerButton = findViewById(R.id.RegisterButton);
        repassword = findViewById(R.id.re_enter_password);
        name = findViewById(R.id.user_name);

        male_anim = findViewById(R.id.male_anim);
        female_anim = findViewById(R.id.female_anim);



        login_text = findViewById(R.id.login_text);

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fragment_open_enter);
                login_text.startAnimation(anim);

                Intent intent = new Intent(getApplicationContext() , LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fragment_open_enter);
                registerButton.startAnimation(anim);

                registerUser(usermail.getText().toString().trim(),userpassword.getText().toString().trim());
            }
        });


        male_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male_anim.setVisibility(View.INVISIBLE);
                female_anim.setVisibility(View.VISIBLE);
            }
        });
        female_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                female_anim.setVisibility(View.INVISIBLE);
                male_anim.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void registerUser(String Email , String Password){
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && TextUtils.equals(userpassword.getText().toString(),repassword.getText().toString())) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("userRegistered", "createUserWithEmail:success");

                            // store
                           userID = mAuth.getCurrentUser().getUid();

                            DocumentReference documentReference = fStore.collection("userDetails").document(userID);

                            Map<String,Object> userinfo = new HashMap<>();
                            userinfo.put("Email",usermail.getText().toString().trim());
                            userinfo.put("Password",userpassword.getText().toString().trim());
                            userinfo.put("Name",name.getText().toString().trim());

                            documentReference.set(userinfo);

                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            finish();
                           // FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        }else if(TextUtils.isEmpty(usermail.getText().toString()) ||TextUtils.isEmpty(userpassword.getText().toString()) || TextUtils.isEmpty(repassword.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "please enter all details",
                                    Toast.LENGTH_SHORT).show();

                        }else {
                            // If sign in fails, display a message to the user.
                            Log.w("userRegistered", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "password mismatch OR Authentication failed.",
                                    Toast.LENGTH_LONG).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

}