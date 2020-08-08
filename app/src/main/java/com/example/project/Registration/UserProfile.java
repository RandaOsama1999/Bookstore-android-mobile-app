package com.example.project.Registration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Books.UserProfileBooks;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserProfile extends AppCompatActivity {

    Button btn,btn2,next,map;
    EditText fname,lname,dob,mobile;
    DatabaseReference databaseReference;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        btn=findViewById(R.id.save);
        btn2=findViewById(R.id.logout);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        mobile=findViewById(R.id.number);
        //next=findViewById(R.id.next);
        //map=findViewById(R.id.map);

        dob= findViewById(R.id.dateofbirth);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UserProfile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String user_id=user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                                fname.setText(""+dataSnapshot.child("fname").getValue().toString(), TextView.BufferType.EDITABLE);
                                lname.setText(""+dataSnapshot.child("lname").getValue().toString(),TextView.BufferType.EDITABLE);
                                dob.setText(""+dataSnapshot.child("dateofbirth").getValue().toString(),TextView.BufferType.EDITABLE);
                                mobile.setText("0"+dataSnapshot.child("mobile").getValue().toString(),TextView.BufferType.EDITABLE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("users").child(user_id).child("fname").setValue(fname.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(user_id).child("lname").setValue(lname.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(user_id).child("dateofbirth").setValue(dob.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(user_id).child("mobile").setValue(mobile.getText().toString());

            }
        });
        /*final FirebaseAuth auth=FirebaseAuth.getInstance();
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent=new Intent(UserProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserProfile.this, UserProfileBooks.class);
                startActivity(intent);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserProfile.this, Bookstores_maps.class);
                startActivity(intent);
            }
        });*/
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(), UserProfileBooks.class);
        startActivity(intent);
    }
}
