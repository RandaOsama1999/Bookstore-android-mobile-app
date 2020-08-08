package com.example.project.Books;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.ShoppingCart.CartActivity;
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

public class CategoryActivity extends AppCompatActivity {

    ArrayList<Book> listbook;
    int count=0;
    TextView textCartItemCount;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final String user_id=user.getUid();
    recyclerview_categoryadapter myAdapter ;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent intent = getIntent();
        final String Title = intent.getExtras().getString("Category");
        listbook = new ArrayList<>();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("books");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bookstore : dataSnapshot.getChildren()) {
                    if (bookstore.child("category").getValue().toString().equals(Title)) {
                        String id = bookstore.child("id").getValue().toString();
                        String imageUrl=bookstore.child("imageUrl").getValue().toString();
                        String BookTitle=bookstore.child("bookTitle").getValue().toString();
                        String BookAuthor=bookstore.child("bookAuthor").getValue().toString();
                        String Quantity=bookstore.child("quantity").getValue().toString();
                        String YearPublished=bookstore.child("yearPublished").getValue().toString();
                        double Price=Double.parseDouble(bookstore.child("price").getValue().toString());
                        Float rating=Float.parseFloat(bookstore.child("rating").getValue().toString());
                        String Category=bookstore.child("category").getValue().toString();
                        String BookDescription=bookstore.child("bookDescription").getValue().toString();
                        listbook.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                    }
                    else if(Title.equals("Top Seller") &&
                            Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5)
                    {
                        String id = bookstore.child("id").getValue().toString();
                        String imageUrl=bookstore.child("imageUrl").getValue().toString();
                        String BookTitle=bookstore.child("bookTitle").getValue().toString();
                        String BookAuthor=bookstore.child("bookAuthor").getValue().toString();
                        String Quantity=bookstore.child("quantity").getValue().toString();
                        String YearPublished=bookstore.child("yearPublished").getValue().toString();
                        double Price=Double.parseDouble(bookstore.child("price").getValue().toString());
                        Float rating=Float.parseFloat(bookstore.child("rating").getValue().toString());
                        String Category=bookstore.child("category").getValue().toString();
                        String BookDescription=bookstore.child("bookDescription").getValue().toString();
                        listbook.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                    }
                }
                RecyclerView myrv =findViewById(R.id.categoryrecyclerview);
                myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
                myAdapter= new recyclerview_categoryadapter(getApplicationContext(),listbook);
                myrv.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        View itemChooser;
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.getItemId() == R.id.action_notifications) {
                itemChooser = item.getActionView();
                if (itemChooser != null) {
                    textCartItemCount = itemChooser.findViewById(R.id.cart_badge);
                    databaseReference= FirebaseDatabase.getInstance().getReference("cart");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot bookstore : dataSnapshot.getChildren()) {
                                if(bookstore.child("userid").getValue().toString().equals(user_id)){
                                    count++;
                                }
                            }
                            if (count == 0) {
                                if (textCartItemCount.getVisibility() != View.GONE) {
                                    textCartItemCount.setVisibility(View.GONE);
                                }
                            }
                            else {
                                textCartItemCount.setText(String.valueOf(count));
                                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                                    textCartItemCount.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    itemChooser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
            else if(item.getItemId() == R.id.action_search){
                SearchView searchview=(SearchView)item.getActionView();
                searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        myAdapter.getFilter().filter(s);
                        return false;
                    }
                });
            }
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(), UserProfileBooks.class);
        startActivity(intent);
    }
}
