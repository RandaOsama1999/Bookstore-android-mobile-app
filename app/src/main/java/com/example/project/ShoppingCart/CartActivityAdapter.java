package com.example.project.ShoppingCart;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Books.Book;
import com.example.project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CartActivityAdapter extends RecyclerView.Adapter<CartActivityAdapter.ViewHolder>{

    protected ArrayList<Book> cartbookdetails;
    protected Context context;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    DatabaseReference databaseReference2;

    public CartActivityAdapter(ArrayList<Book> cartbookdetails, Context context) {
            this.cartbookdetails = cartbookdetails;
            this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item , parent , false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        StorageReference ref = storageReference.child("books").child(cartbookdetails.get(position).getId());
        StorageReference photoRef = storage.getReference(ref.getPath());
        photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                Glide.with(context).load(downloadUrl).into(holder.image);
            }
        });
        holder.imageName.setText(cartbookdetails.get(position).getBookTitle());
        holder.quantity.setText(cartbookdetails.get(position).getQuantity());
        holder.price.setText(String.valueOf(cartbookdetails.get(position).getPrice()));
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference2= FirebaseDatabase.getInstance().getReference("cart");
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot bookstore : dataSnapshot.getChildren()) {
                            if (bookstore.child("bothid").getValue().toString().equals(cartbookdetails.get(position).getBothid())) {
                                bookstore.getRef().removeValue();
                                Intent intent = new Intent(context, CartActivity.class);
                                context.startActivity(intent);
                                break;
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
            return cartbookdetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView imageName,quantity,price,close;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cartImage);
            imageName = itemView.findViewById(R.id.title);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            close=itemView.findViewById(R.id.close);
            parentLayout =  itemView.findViewById(R.id.cartparent);
        }
    }
}