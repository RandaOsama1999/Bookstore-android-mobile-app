package com.example.project.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyMail extends AppCompatActivity {
    Button btn;
    TextView btn1;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mail);
        btn=findViewById(R.id.mail);
        btn1=findViewById(R.id.button1);
        auth.signOut();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
               // startActivity(intent);
                startActivity(Intent.createChooser(intent, getString(R.string.ChoseEmailClient)));
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VerifyMail.this,Login.class);
                startActivity(intent);
            }
        });
    }
}
