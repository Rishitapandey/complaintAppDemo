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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private Button CreateButton;
    private TextInputLayout email, password;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth= FirebaseAuth.getInstance();
        initializefields();
        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }
    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }
    private void CreateNewAccount() {
        String useremail = email.getEditText().getText().toString().trim();
        String userpass = password.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(useremail)) {
            Toast.makeText(this, "plzz enter email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(userpass)) {
            Toast.makeText(this, "plzz enter password", Toast.LENGTH_SHORT).show();
        } else {
            loadingbar.setTitle("Creating New Account..");
            loadingbar.setMessage("Please wait....");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();
            mAuth.createUserWithEmailAndPassword(useremail, userpass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String currentUserID = mAuth.getCurrentUser().getUid();


                                SendUsertoMainactivity();
                                Toast.makeText(RegisterActivity.this, "Account created successfully..", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
                    });
        }
    }

    private void initializefields() {
        CreateButton=(Button)findViewById(R.id.RegisterBtn);
        email=findViewById(R.id.RegisterEmail);
        password=findViewById(R.id.RegisterPassword);
        loadingbar=new ProgressDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser!=null){
            SendUsertoMainactivity(); }
    }

    private void SendUsertoMainactivity() {
        Intent mainIntent=new Intent(RegisterActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}