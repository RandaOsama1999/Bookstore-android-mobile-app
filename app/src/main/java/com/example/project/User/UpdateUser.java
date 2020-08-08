package com.example.project.User;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateUser extends Fragment {

    Button btn,btn2;
    EditText fname,lname,dob,mobile,email;
    DatabaseReference databaseReference,databaseReference2;
    View rootview;
    final Calendar myCalendar = Calendar.getInstance();

    public UpdateUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview= inflater.inflate(R.layout.fragment_update_user, container, false);
        btn=rootview.findViewById(R.id.save);
        btn2=rootview.findViewById(R.id.find);

        fname=rootview.findViewById(R.id.fname);
        lname=rootview.findViewById(R.id.lname);
        mobile=rootview.findViewById(R.id.number);
        email=rootview.findViewById(R.id.email);

        dob= rootview.findViewById(R.id.dateofbirth);
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
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        fname.setEnabled(false);
        lname.setEnabled(false);
        dob.setEnabled(false);
        mobile.setEnabled(false);
        btn.setClickable(false);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference= FirebaseDatabase.getInstance().getReference("users");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot bookstore : dataSnapshot.getChildren()) {
                            if (bookstore.child("email").getValue().toString().equals(email.getText().toString())) {
                                email.setEnabled(false);
                                fname.setEnabled(true);
                                lname.setEnabled(true);
                                dob.setEnabled(true);
                                mobile.setEnabled(true);
                                btn.setClickable(true);
                                btn2.setClickable(false);

                                fname.setText("" + bookstore.child("fname").getValue().toString(), TextView.BufferType.EDITABLE);
                                lname.setText("" + bookstore.child("lname").getValue().toString(), TextView.BufferType.EDITABLE);
                                dob.setText("" + bookstore.child("dateofbirth").getValue().toString(), TextView.BufferType.EDITABLE);
                                mobile.setText("0" + bookstore.child("mobile").getValue().toString(), TextView.BufferType.EDITABLE);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference2= FirebaseDatabase.getInstance().getReference("users");
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot bookstore : dataSnapshot.getChildren()) {
                            if (bookstore.child("email").getValue().toString().equals(email.getText().toString())) {
                                bookstore.getRef().child("fname").setValue(fname.getText().toString());
                                bookstore.getRef().child("lname").setValue(lname.getText().toString());
                                bookstore.getRef().child("dateofbirth").setValue(dob.getText().toString());
                                bookstore.getRef().child("mobile").setValue(mobile.getText().toString());
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return rootview;
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }
}
