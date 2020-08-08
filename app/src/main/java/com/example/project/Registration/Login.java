package com.example.project.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Books.UserProfileBooks;
import com.example.project.R;
import com.example.project.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    Button btn,btn2;
    TextView btn3;
    EditText email,pass;
    //DBConnection db = new DBConnection(this);
    ArrayList<User> users=new ArrayList<>();
    DatabaseReference databaseReference;
    FirebaseAuth auth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn= findViewById(R.id.loginbtn);
        btn2= findViewById(R.id.signup);
        btn3=findViewById(R.id.reset);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                auth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            auth.getCurrentUser().reload();
                            FirebaseUser user = auth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                final String user_id=user.getUid();
                                databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.child("usertype").getValue().toString().equals("1")){
                                            Intent intent=new Intent(getApplicationContext(), ManagerPortal.class);
                                            startActivity(intent);
                                        }
                                        else if(dataSnapshot.child("usertype").getValue().toString().equals("3")){
                                            Intent intent=new Intent(getApplicationContext(), UserProfileBooks.class);
                                            startActivity(intent);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                //Toast.makeText(getApplicationContext(),"You are in =)",Toast.LENGTH_LONG).show();
                                //System.out.println("logged in");
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Check your email first...",Toast.LENGTH_LONG).show();
                                //System.out.println("Check email");
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                //users=db.get();
                /*for (int i=0;i<users.size();i++){
                    /*if(email.getText().toString().equals(users.get(i).getEmail()) && pass.getText().toString().equals(users.get(i).getPass())){
                        Toast.makeText(view.getContext(), "matched",Toast.LENGTH_SHORT).show();
                    }
                }*/
                /*databaseReference= FirebaseDatabase.getInstance().getReference("users");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot bookstore : dataSnapshot.getChildren())
                        {
                            if(email.getText().toString().equals(bookstore.child("email").getValue()) &&
                                    pass.getText().toString().equals(bookstore.child("pass").getValue()))
                            {
                                Toast.makeText(view.getContext(), "matched",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });*/


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,SignUp.class);
                startActivity(intent);            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,ResetPassword.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
