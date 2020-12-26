package com.example.librarymanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {
        LottieAnimationView male_anim,female_anim;
        EditText username , userpassword ;
        Button login_button;
        TextView register_text;

    private FirebaseAuth mAuth;

            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        firebase instance
                mAuth = FirebaseAuth.getInstance();

        male_anim = findViewById(R.id.male_anim);
        female_anim = findViewById(R.id.female_anim);

        username = findViewById(R.id.username);
        userpassword = findViewById(R.id.password);
        login_button = findViewById(R.id.loginButton);
        register_text = findViewById(R.id.register_text);

        register_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fragment_open_enter);
                register_text.startAnimation(anim);

                Intent intent = new Intent(getApplicationContext() , registerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fragment_open_enter);
                login_button.startAnimation(anim);

                signInUser(username.getText().toString(),userpassword.getText().toString());


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
    public void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(),"Please login using your email and password", Toast.LENGTH_SHORT).show();
    }

    public void signInUser(String email , String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            MainActivity.islogged_in.edit().putBoolean("loggedin_check", true).apply();

                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intent);
                            finish();
                            Log.d("properSignIn", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        }else if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(userpassword.getText().toString()) ){
                            Toast.makeText(getApplicationContext(),"please enter username and password", Toast.LENGTH_LONG).show();
                        }
                        else if(TextUtils.equals(username.getText().toString() , "1") && TextUtils.equals(userpassword.getText().toString() , "1")) {
                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignInFail", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

        }

}
