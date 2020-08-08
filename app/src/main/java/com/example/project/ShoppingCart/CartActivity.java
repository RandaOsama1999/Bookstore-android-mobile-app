package com.example.project.ShoppingCart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Books.Book;
import com.example.project.Books.UserProfileBooks;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    TextView totalprice;
    Button pay;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String user_id=user.getUid();
    double mTotalPrice;
    final DecimalFormat decimalFormat=new DecimalFormat("#.##");
    RecyclerView recycler;
    ArrayList<Book> listcart;
    CartActivityAdapter myAdapter ;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference,databaseReference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recycler=findViewById(R.id.cart_recycler);
        totalprice=findViewById(R.id.totalPrice);
        pay=findViewById(R.id.button_payment);
        listcart = new ArrayList<>();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("cart");
        databaseReference2= FirebaseDatabase.getInstance().getReference("books");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bookstore : dataSnapshot.getChildren()) {
                    if (bookstore.child("userid").getValue().toString().equals(user_id)) {
                        String id = bookstore.child("id").getValue().toString();
                        String BookTitle=bookstore.child("bookTitle").getValue().toString();
                        String Quantity=bookstore.child("quantity").getValue().toString();
                        double Price=Double.parseDouble(bookstore.child("price").getValue().toString());
                        mTotalPrice=mTotalPrice+Price;
                        listcart.add(new Book(id,user_id,id+user_id,BookTitle,Price,Quantity));
                    }
                }
                totalprice.setText(decimalFormat.format(mTotalPrice));
                recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                myAdapter= new CartActivityAdapter(listcart,CartActivity.this);
                recycler.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CartActivity.this, paymentmethod.class);
                startActivity(intent);
            }
        });

    }

}
