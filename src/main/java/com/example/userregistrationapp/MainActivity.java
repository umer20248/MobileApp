package com.example.userregistrationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username;
    EditText password;
    Button register_btn;
    TextView textViewSignin;


    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        register_btn = (Button) findViewById(R.id.register_btn);
        textViewSignin=(TextView)findViewById(R.id.textViewSignin);

        register_btn.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){
        String Username = username.getText().toString().trim();
        String Password = password.getText().toString().trim();

        if(TextUtils.isEmpty(Username)){
            //email is empty
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(Password)){
            //email is empty
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        //if validations are ok , we will first show a progressbar
            progressDialog.setMessage("Registering User. . .");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(Username,Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //user is successfully registered and logged in
                                //we will start the profile activity here
                                //right now lets display  a toast only
                                Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();

                            }else{
                                Toast.makeText(MainActivity.this,"Could not register. Please try again",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

    }

    @Override
    public void onClick(View view) {
        if(view == register_btn){
            registerUser();
            Intent intent = new Intent(MainActivity.this, Firstpage.class);
            startActivity(intent);
        }

        if(view == textViewSignin){
            //will open login activity here

        }
    }
}
