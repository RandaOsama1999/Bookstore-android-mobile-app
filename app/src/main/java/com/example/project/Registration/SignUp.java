package com.example.project.Registration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUp extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText fname,lname,email,mob,pass,edittext,dateofbirth;
    RadioGroup gendergroup;
    RadioButton gender;
    final Calendar myCalendar = Calendar.getInstance();
    Button btn;
    TextView btn2;
    int g=0;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    //DBConnection db = new DBConnection(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edittext= findViewById(R.id.dateofbirth);
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

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUp.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        databaseReference= FirebaseDatabase.getInstance().getReference("users");

        btn= findViewById(R.id.signupbtn);
        btn2=findViewById(R.id.login);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        dateofbirth=findViewById(R.id.dateofbirth);
        email=findViewById(R.id.email);
        mob=findViewById(R.id.number);
        pass=findViewById(R.id.pass);

        gendergroup=findViewById(R.id.gender);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("users");
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot bookstore : dataSnapshot.getChildren()) {
                            if (email.getText().toString().equals(bookstore.child("email").getValue()) ||
                                    mob.getText().toString().equals(bookstore.child("mobile").getValue())) {
                                g = 0;
                                Toast.makeText(getApplicationContext(), "An account with this mobile or email already exist", Toast.LENGTH_SHORT).show();
                            } else {
                                g++;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });
                if (g != 0) {
                    int genderID = gendergroup.getCheckedRadioButtonId();
                    gender = findViewById(genderID);
               /* boolean i=db.insert(1 ,Integer.parseInt(id.getText().toString()),fname.getText().toString(),
                        mname.getText().toString(),lname.getText().toString(),email.getText().toString(),
                        Integer.parseInt(mob.getText().toString()),Integer.parseInt(home.getText().toString()),pass.getText().toString());
                if(i==true){
                    Toast.makeText(view.getContext(), "sign up",Toast.LENGTH_SHORT).show();
                }*/
                /*db.insertUser(Integer.parseInt(id.getText().toString()),fname.getText().toString(),
                        mname.getText().toString(),lname.getText().toString(),email.getText().toString(),
                        Integer.parseInt(mob.getText().toString()),Integer.parseInt(home.getText().toString()),pass.getText().toString());*/
                    auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                                            String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            User user2 = new User(currentuser, fname.getText().toString(), lname.getText().toString(), dateofbirth.getText().toString(),
                                                    gender.getText().toString(), Integer.parseInt(mob.getText().toString()), email.getText().toString(),3);
                                            databaseReference.child(currentuser).setValue(user2);
                                            Intent intent = new Intent(SignUp.this, VerifyMail.class);
                                            startActivity(intent);
                                        } else {
                                            Log.i("Success", "No");
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    auth.signOut();
                    //db.insert("Users","userID,FirstName,MiddleName,LastName,Email,MobileNumber,HomeNumber,Password",Integer.parseInt(id.getText().toString())+",'"+fname.getText().toString()+"','"+ mname.getText().toString()+"','"+lname.getText().toString()+"','"+email.getText().toString()+"',"+ Integer.parseInt(mob.getText().toString())+","+Integer.parseInt(home.getText().toString())+",'"+pass.getText().toString()+"'");
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUp.this,Login.class);
                startActivity(intent);            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
