package com.example.project.Books;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.example.project.Ratings.RatingAdapter;
import com.example.project.Ratings.Ratings;
import com.example.project.Ratings.bookrating;
import com.example.project.ShoppingCart.CartActivity;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class bookActivity extends AppCompatActivity {

    TextView tvtitle,tvdescription,tvcategory,txtauthor,txtyear,txtquantity,quantity_text_view,audiencerating;
    RatingBar rating;
    int count=0;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final String user_id=user.getUid();
    int mQuantity = 1;
    double mTotalPrice;
    TextView costTextView;
    private int mNotificationsCount = 0;
    Button cart_button,decrement_button,increment_button;
    TextView textCartItemCount;
    ImageView finalbookimage;
    DatabaseReference databaseReference,databaseReference2;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    String Title,category,Description,Author,Year,image,quantity;
    Double Price,Rating;

    RatingAdapter ratingAdapter;
    DatabaseReference databaseReference3;
    private RecyclerView reviewrecycler;
    ArrayList<Ratings> ratingsal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        tvtitle = findViewById(R.id.txttitle);
        tvdescription = findViewById(R.id.txtDesc);
        tvcategory =  findViewById(R.id.txtCat);
        txtauthor =  findViewById(R.id.txtauthor);
        txtyear =  findViewById(R.id.txtyear);
        rating=findViewById(R.id.rating);
        txtquantity=findViewById(R.id.quantity);
        finalbookimage =  findViewById(R.id.bookthumbnail);
        cart_button = findViewById(R.id.cart_button);
        costTextView = findViewById(R.id.cost_text_view);
        decrement_button=findViewById(R.id.decrement_button);
        increment_button=findViewById(R.id.increment_button);
        quantity_text_view=findViewById(R.id.quantity_text_view);
        audiencerating=findViewById(R.id.audiencerating);
        reviewrecycler=findViewById(R.id.reviewsrecycler);
        ratingsal=new ArrayList<>();

        Intent intent = getIntent();
        Title = intent.getExtras().getString("Title");
        category=intent.getExtras().getString("category");
        Description = intent.getExtras().getString("Description");
        Author = intent.getExtras().getString("Author");
        Price=intent.getExtras().getDouble("Price");
        Year = intent.getExtras().getString("Year");
        Rating = intent.getExtras().getDouble("Rating");
        image = intent.getExtras().getString("Thumbnail") ;
        quantity=intent.getExtras().getString("Quantity");

        tvtitle.setText(Title);
        tvdescription.setText(Description);
        txtauthor.setText(Author);
        if(Integer.parseInt(quantity)==0) {
            txtquantity.setText("No books left.");
        }
        else if(Integer.parseInt(quantity)<5 && Integer.parseInt(quantity)>0) {
            txtquantity.setText("Only "+quantity+" books left.");
        }
        txtyear.setText("Published on "+Year);
        rating.setRating(Float.parseFloat(Rating.toString()));
        tvcategory.setText(category);
        StorageReference ref = storageReference.child("books").child(image);
        StorageReference photoRef = storage.getReference(ref.getPath());
        photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                Glide.with(getApplicationContext()).load(downloadUrl).into(finalbookimage);
            }
        });

        if (mQuantity == 1){

            mTotalPrice = Price;
            costTextView.setText(mTotalPrice+" EGP");
        }
        if (Integer.parseInt(quantity) == 0){
            cart_button.setEnabled(false);
            decrement_button.setEnabled(false);
            increment_button.setEnabled(false);
        }
        final DecimalFormat decimalFormat=new DecimalFormat("#.##");
        decrement_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mQuantity > 1){
                    mQuantity= mQuantity - 1;
                    quantity_text_view.setText(String.valueOf(mQuantity));
                    mTotalPrice = Double.valueOf(decimalFormat.format(mQuantity * Price));
                    costTextView.setText(mTotalPrice+" EGP");
                }
            }
        });
        increment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mQuantity < Integer.parseInt(quantity)) {
                    mQuantity= mQuantity + 1;
                    quantity_text_view.setText(String.valueOf(mQuantity));
                    mTotalPrice = Double.valueOf(decimalFormat.format(mQuantity * Price));
                    costTextView.setText(mTotalPrice+" EGP");
                }

            }
        });
        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //databaseReference= FirebaseDatabase.getInstance().getReference("cart");
                final String mquan=String.valueOf(mQuantity);


                final int newQuantity=Integer.parseInt(quantity)-mQuantity;
                databaseReference2= FirebaseDatabase.getInstance().getReference("cart");
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int c=0;
                        for (DataSnapshot bookstore : dataSnapshot.getChildren()) {
                            if (bookstore.child("bothid").getValue().toString().equals(image+user_id)) {
                                int q=Integer.parseInt(bookstore.child("quantity").getValue().toString())+mQuantity;
                                Double dob = Double.valueOf(decimalFormat.format(q * Price));
                                bookstore.getRef().child("quantity").setValue(String.valueOf(q));
                                bookstore.getRef().child("price").setValue(String.valueOf(dob));
                                c=1;
                                break;
                            }
                            else{
                                c=0;
                            }
                        }
                        if(c!=1){
                            Book ob=new Book(image,user_id,image+user_id,Title,mTotalPrice,mquan);
                            databaseReference2.child(image+user_id).setValue(ob);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //FirebaseDatabase.getInstance().getReference("books").child(image).child("quantity").setValue(String.valueOf(newQuantity));

                Toast.makeText(getApplicationContext(), "Successfully added to Cart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), UserProfileBooks.class);
                startActivity(intent);
            }
        });

        databaseReference3=FirebaseDatabase.getInstance().getReference("ratings");
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ratess : dataSnapshot.getChildren()){
                    String abbas=ratess.child("status").getValue().toString();
                    boolean mrAbbas = Boolean.parseBoolean(abbas);
                    if(image.equals(ratess.child("bookid").getValue().toString())) {
                        if (mrAbbas == true) {
                            String useridi = ratess.child("userid").getValue().toString();
                            String bookidi = ratess.child("bookid").getValue().toString();
                            String commenti = ratess.child("comment").getValue().toString();
                            Float ratei = Float.valueOf(ratess.child("rate").getValue().toString());
                            Boolean statusi = Boolean.valueOf(ratess.child("status").getValue().toString());
                            String idi = ratess.child("id").getValue().toString();
                            ratingsal.add(new Ratings(idi, useridi, bookidi, ratei, commenti, statusi));

                        }
                    }
                }
                reviewrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                ratingAdapter= new RatingAdapter(ratingsal,bookActivity.this);
                reviewrecycler.setAdapter(ratingAdapter);
                if(ratingsal.isEmpty()){
                    audiencerating.setText("No Ratings");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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
        }
        return super.onCreateOptionsMenu(menu);
    }
    public void rate(View view) {
        Intent in =new Intent(this, bookrating.class);
        in.putExtra("title",Title);
        in.putExtra("category",category);
        in.putExtra("author",Author);
        in.putExtra("image",image);
        startActivity(in);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(), UserProfileBooks.class);
        startActivity(intent);
    }
}
