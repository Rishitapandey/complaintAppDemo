package com.example.complaintappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String keyRegister = "Registration no";
    private static final String keyName = "Name";
    private static final String keyAddress = "Address";
    private static final String keyIssue = "Issue";
    EditText editTextRegister, editTextName, editTextAddress, editTextIssue;
    private FirebaseFirestore db;
    private ProgressDialog loadingbar;
     private FirebaseAuth mAuth;
     private FirebaseUser currentUser;
     Button sendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        UserDao dao=new UserDao();
        initializefields();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIssue();

            }
        });




    }
    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser==null){
            SendUserToLoginactivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void saveIssue() {

        String RegisterNo = editTextRegister.getText().toString();
        String FullName = editTextName.getText().toString();
        String Address = editTextAddress.getText().toString();
        String Issue = editTextIssue.getText().toString();
        if (TextUtils.isEmpty(RegisterNo)) {
            Toast.makeText(this, "plzz enter Registration number", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(FullName)) {
            Toast.makeText(this, "Full name field is empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Address)) {
            Toast.makeText(this, "Plzz enter address", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Issue)) {
            Toast.makeText(this, "Plzz enter your issue", Toast.LENGTH_SHORT).show();
        } else {
            loadingbar.setTitle("Submitting....");
            loadingbar.setMessage("Please wait....");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();

        Map<String, Object> complaint = new HashMap<>();
         complaint.put(keyRegister, RegisterNo);
            complaint.put(keyName, FullName);
           complaint.put(keyAddress, Address);
            complaint.put(keyIssue, Issue);

            db.collection("Details").add(complaint)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(MainActivity.this, "Thank you !! your complaint is submitted..", Toast.LENGTH_SHORT).show();

                        }
                    });


            SendusertoFinalActivity();
            loadingbar.dismiss();

        }
    }

    private void initializefields() {
        editTextRegister = findViewById(R.id.editText);
        editTextName = findViewById(R.id.name);
        editTextAddress = findViewById(R.id.Address);
        editTextIssue = findViewById(R.id.IssueInput);
        sendBtn=findViewById(R.id.sendButton);
        loadingbar = new ProgressDialog(this);

    }

    private void SendusertoFinalActivity() {
        Intent intent = new Intent(MainActivity.this, FinalActivity.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            SendUserToLoginactivity();
        }
        return true;

    }

    private void SendUserToLoginactivity() {
        Intent loginIntent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }

}