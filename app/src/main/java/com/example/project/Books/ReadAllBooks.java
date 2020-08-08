package com.example.project.Books;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
public class ReadAllBooks extends Fragment {

    TableLayout tableLayout;
    DatabaseReference databaseReference;
    View rootView;
    public ReadAllBooks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_read_all_books, container, false);

        databaseReference= FirebaseDatabase.getInstance().getReference("books");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot bookstore : dataSnapshot.getChildren())
                {
                        tableLayout= rootView.findViewById(R.id.table);
                        TableRow tableRow1= new TableRow(getContext());

                        TextView label2 = new TextView(getContext());
                        label2.setText(bookstore.child("bookTitle").getValue().toString(), TextView.BufferType.NORMAL);
                        label2.setTextColor(Color.BLACK);
                        label2.setPadding(3,3,3,3);
                        tableRow1.addView(label2);// add the column to the table row here

                        TextView label3 = new TextView(getContext());
                        label3.setText(""+bookstore.child("bookAuthor").getValue().toString(), TextView.BufferType.NORMAL);
                        label3.setTextColor(Color.BLACK);
                        label3.setPadding(3,3,3,3);
                        tableRow1.addView(label3);// add the column to the table row here

                        TextView label4 = new TextView(getContext());
                        label4.setText(""+bookstore.child("category").getValue().toString(), TextView.BufferType.NORMAL);
                        label4.setTextColor(Color.BLACK);
                        label4.setPadding(3,3,3,3);
                        tableRow1.addView(label4);// add the column to the table row here

                        TextView label5 = new TextView(getContext());
                        label5.setText(""+bookstore.child("price").getValue().toString(), TextView.BufferType.NORMAL);
                        label5.setTextColor(Color.BLACK);
                        label5.setGravity(Gravity.CENTER);
                        label5.setPadding(3,3,3,3);
                        tableRow1.addView(label5);// add the column to the table row here

                        TextView label6 = new TextView(getContext());
                        label6.setText(""+bookstore.child("quantity").getValue().toString(), TextView.BufferType.NORMAL);
                        label6.setTextColor(Color.BLACK);
                        label6.setGravity(Gravity.CENTER);
                        label6.setPadding(3,3,3,3);
                        tableRow1.addView(label6);// add the column to the table row here

                        TextView label7 = new TextView(getContext());
                        label7.setText(""+bookstore.child("rating").getValue().toString(), TextView.BufferType.NORMAL);
                        label7.setTextColor(Color.BLACK);
                        label7.setGravity(Gravity.CENTER);
                        label7.setPadding(3,3,3,3);
                        tableRow1.addView(label7);// add the column to the table row here

                        TextView label8 = new TextView(getContext());
                        label8.setText(""+bookstore.child("yearPublished").getValue().toString(), TextView.BufferType.NORMAL);
                        label8.setTextColor(Color.BLACK);
                        label8.setGravity(Gravity.CENTER);
                        label8.setPadding(3,3,3,3);
                        tableRow1.addView(label8);// add the column to the table row here

                        tableLayout.addView(tableRow1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        return rootView;
    }

}
