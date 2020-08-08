package com.example.project.Ratings;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.Registration.ManagerPortal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {


    String TAG = "mytag";
    protected ArrayList<Ratings> ratings;
    protected Context context;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference;
    DatabaseReference buttondatabaseref,buttondatabaseref2;
    FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
    final String useruid=user2.getUid();
    public RatingAdapter(ArrayList<Ratings> ratings, Context context) {
        this.ratings = ratings;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.ratingrecyclerview,parent,false);
        return new ViewHolder(view);


//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item , parent , false);
//        ViewHolder holder = new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        databaseReference2= FirebaseDatabase.getInstance().getReference().child("books");
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            String bookid=ratings.get(position).getBookid();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ratess : dataSnapshot.getChildren()){
                    if (bookid.equals(ratess.child("id").getValue().toString()))
                    {
                        holder.bookname.setText(ratess.child("bookTitle").getValue().toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {
            String userid=ratings.get(position).getUserid();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ratess : dataSnapshot.getChildren()){
                    if (userid.equals(ratess.child("id").getValue().toString()))
                    {
                        holder.username.setText(ratess.child("fname").getValue().toString()+" "+ratess.child("lname").getValue().toString());
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(ratings.get(position).getComment().isEmpty())
            holder.commentarea.setText("No data yet");
        else {
            String comment = ratings.get(position).getComment();
            Log.d(TAG, "onBindViewHolder: "+comment);
            holder.commentarea.setText(comment);
            holder.ratearea.setRating(ratings.get(position).getRate());
        }
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(useruid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("usertype").getValue().toString().equals("1")){
                    holder.approve.setVisibility(View.VISIBLE);
                    holder.decline.setVisibility(View.VISIBLE);
                    holder.bookname.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Boolean approved = true;
                buttondatabaseref = FirebaseDatabase.getInstance().getReference("ratings");
                buttondatabaseref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot newstatus : dataSnapshot.getChildren()) {
                            if (ratings.get(position).getUserid().equals(newstatus.child("userid").getValue().toString())
                                    && ratings.get(position).getBookid().equals(newstatus.child("bookid").getValue().toString())
                            ) {
                                newstatus.child("status").getRef().setValue(approved);
                                Intent in = new Intent(context, ManagerPortal.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(in);
                            }
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttondatabaseref2 = FirebaseDatabase.getInstance().getReference("ratings");
                buttondatabaseref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot newstatus : dataSnapshot.getChildren()) {
                            if (ratings.get(position).getUserid().equals(newstatus.child("userid").getValue().toString())
                                    && ratings.get(position).getBookid().equals(newstatus.child("bookid").getValue().toString())
                            ) {
                                newstatus.getRef().removeValue();
                                Intent in = new Intent(context, ManagerPortal.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(in);
                            }
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });




    }



    @Override
    public int getItemCount() {
        return ratings.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        RatingBar ratearea;
        TextView username,commentarea;
        Button approve,decline;
        TextView bookname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratearea=itemView.findViewById(R.id.ratingarea);
            username=itemView.findViewById(R.id.username);
            commentarea = itemView.findViewById(R.id.commentarea);
            approve=itemView.findViewById(R.id.approve);
            decline=itemView.findViewById(R.id.decline);
            bookname=itemView.findViewById(R.id.bookname);
        }
    }
}
