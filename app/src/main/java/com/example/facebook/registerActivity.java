package com.example.facebook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static java.lang.Thread.sleep;

public class registerActivity extends AppCompatActivity {

    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText phoneNo;
    DatePicker datePicker;

    DatabaseReference dbUsers;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbUsers = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        final Button regd = findViewById(R.id.registered);
        firstname = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastName);
        email = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        phoneNo = findViewById(R.id.phoneNo);
        datePicker = findViewById(R.id.dateOfBirth);

        regd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String phone = phoneNo.getText().toString();
                final String f_name = firstname.getText().toString();
                final String l_name = lastname.getText().toString();
                final String emailId = email.getText().toString();
                final String pass = password.getText().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                final String date = day + "/" + month + "/" + year;

                if(f_name.isEmpty() || l_name.isEmpty() || emailId.isEmpty() || pass.isEmpty() || phone.isEmpty()){

                    Toast.makeText(registerActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();

                } else {

                    firebaseAuth.createUserWithEmailAndPassword(emailId, pass)
                            .addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        String id = dbUsers.push().getKey();
                                        Users user = new Users(f_name, l_name, emailId, pass, phone, date);
                                        dbUsers.child(id).setValue(user);
                                        Toast.makeText(registerActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(registerActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        Toast.makeText(registerActivity.this, "Registration Failed, Try Again", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
