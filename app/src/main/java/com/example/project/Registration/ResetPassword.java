package com.example.project.Registration;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    Button btn;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        btn= findViewById(R.id.loginbtn);
        email=findViewById(R.id.email);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Email has been sent",Toast.LENGTH_SHORT).show();
                                }
                                else if(email.getText().toString() == ""){
                                    Toast.makeText(getApplicationContext(), "No mail is entered",Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "This mail is not registered in our app",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
