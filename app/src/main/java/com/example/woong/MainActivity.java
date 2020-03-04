package com.example.woong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText EditTextEmail;
    private EditText EditTextPassword;
    private Button SignButton;
    private Button LogButton;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userId;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditTextEmail = (EditText) findViewById(R.id.email);
        EditTextPassword = (EditText) findViewById(R.id.password);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user !=null){
                    //user sign in
                }else {
                    //user sign out
                }
            }
        };

        SignButton = (Button)findViewById(R.id.reg);
        SignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stEmail = EditTextEmail.getText().toString();
                String stPass  = EditTextPassword.getText().toString();
                Toast.makeText(MainActivity.this,"Email :" +stEmail+", pass : "+stPass,Toast.LENGTH_SHORT).show();
                if(stEmail.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please insert Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(stPass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please insert Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(stEmail, stPass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                                // ...
                            }
                        });
            }
        });

        /////////////////////////Login/////////////////////////////////////////////////
        LogButton = (Button)findViewById(R.id.login);
        LogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stEmail = EditTextEmail.getText().toString();
                String stPass  = EditTextPassword.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                userId  = mAuth.getCurrentUser().getUid();
                DocumentReference ref = db.collection("open").document(userId);
                mAuth.signInWithEmailAndPassword(stEmail, stPass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(MainActivity.this, "Log in: success!! :)", Toast.LENGTH_LONG).show();

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(MainActivity.this, OpenActivity.class);
                                    intent.putExtra("uid",userId);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "Log_In:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Log in : failed. check again plz :)", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



}
