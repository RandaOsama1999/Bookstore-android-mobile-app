package com.example.project.User;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteUser extends Fragment {

    View rootview;
    Button btn;
    EditText email;
    DatabaseReference databaseReference;
    int g=0;
    public DeleteUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview= inflater.inflate(R.layout.fragment_delete_user, container, false);
        btn=rootview.findViewById(R.id.find);
        email=rootview.findViewById(R.id.email);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference= FirebaseDatabase.getInstance().getReference("users");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot bookstore : dataSnapshot.getChildren()) {
                            if (bookstore.child("email").getValue().toString().equals(email.getText().toString())) {
                                g=0;
                                bookstore.getRef().removeValue();
                            }
                            else {
                                g++;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if(g !=0){
                    Toast.makeText(getContext(),"This mail is not available",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootview;
    }

}
