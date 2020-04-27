package com.example.facebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    TextInputEditText email;
    TextInputEditText password;
    Button login, reg;

    DatabaseReference dbUsers;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String emailId = email.getText().toString();
                final String pass = password.getText().toString();

                if (emailId.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                } else {

                    firebaseAuth.signInWithEmailAndPassword(emailId, pass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(MainActivity.this, "Successfully logged in..",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, mainpageActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();

                                    }

                                }
                            });

                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, registerActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initialize(){
        dbUsers = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.logInBtn);
        reg = findViewById(R.id.register);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }
}
