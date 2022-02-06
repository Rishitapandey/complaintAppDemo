package com.example.complaintappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button loginButton, PhoneButton;
    TextInputLayout email,password;
    private TextView forgetPassword;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializefields();
        mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });





    }


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            SendUsertoMainactivity();
        }
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }
    private void AllowUserToLogin() {
        String Useremail = email.getEditText().getText().toString().trim();
        String Userpass = password.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(Useremail)) {
            Toast.makeText(this, "plzz enter email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Userpass)) {
            Toast.makeText(this, "plzz enter password", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingbar.setTitle("Signing....");
            loadingbar.setMessage("Please wait....");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();
            mAuth.signInWithEmailAndPassword(Useremail,Userpass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                SendUsertoMainactivity();
                                Toast.makeText(LoginActivity.this,"Logged in Sucessfully",Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }else{
                                String message=task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();}

                        }
                    });

        }
    }

    private void SendUsertoMainactivity() {
        Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void initializefields () {
        loginButton = (Button) findViewById(R.id.LoginBtn);
        email = findViewById(R.id.LoginEmail);
        password = findViewById(R.id.LoginPassword);
        forgetPassword = (TextView) findViewById(R.id.forget_password_link);
        loadingbar=new ProgressDialog(this);
    }

    public void onPhoneLogin(View view) {
        Intent mainIntent=new Intent(LoginActivity.this,PhoneLogin.class);
        startActivity(mainIntent);
    }

    public void onGoogleclick(View view) {
        Toast.makeText(LoginActivity.this, "Not implemented yet", Toast.LENGTH_SHORT).show();
    }
}