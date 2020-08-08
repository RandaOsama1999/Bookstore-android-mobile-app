package com.example.project.Ratings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminRates extends Fragment {

View root;
    DatabaseReference databaseReference3;
    ArrayList<Ratings> ratingsal;
    RatingAdapter ratingAdapter;
    RecyclerView reviewrecycler;
    TextView audi;
    public AdminRates() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_admin_rates, container, false);
        ratingsal = new ArrayList<>();
        audi=root.findViewById(R.id.audi);
        reviewrecycler=root.findViewById(R.id.adminraterecycler);
        databaseReference3= FirebaseDatabase.getInstance().getReference("ratings");
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ratess : dataSnapshot.getChildren()){
                    String abbas=ratess.child("status").getValue().toString();
                    boolean mrAbbas = Boolean.parseBoolean(abbas);
                    if(mrAbbas == false) {
                        String useridi = ratess.child("userid").getValue().toString();
                        String bookidi = ratess.child("bookid").getValue().toString();
                        String commenti = ratess.child("comment").getValue().toString();
                        Float ratei = Float.valueOf(ratess.child("rate").getValue().toString());
                        Boolean statusi = Boolean.valueOf(ratess.child("status").getValue().toString());
                        String idi = ratess.child("id").getValue().toString();
                        ratingsal.add(
                                new Ratings(idi, useridi, bookidi, ratei, commenti, statusi));



                    }


                }


                reviewrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                ratingAdapter= new RatingAdapter(ratingsal, getContext());
                reviewrecycler.setAdapter(ratingAdapter);
                if(ratingsal.isEmpty()){
                    audi.setText("No Ratings");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }

}
