package com.example.project.Books;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.Registration.MainActivity;
import com.example.project.Registration.UserProfile;
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

public class UserProfileBooks extends AppCompatActivity {

    int count=0;
    TextView textCartItemCount;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final String user_id=user.getUid();
    protected ArrayList<Book> topsellersnames = new ArrayList<>();
    protected ArrayList<Book> actionnames = new ArrayList<>();
    protected ArrayList<Book> classicnames = new ArrayList<>();
    protected ArrayList<Book> comicbooksnames = new ArrayList<>();
    protected ArrayList<Book> cookbooksnames = new ArrayList<>();
    protected ArrayList<Book> dramanames = new ArrayList<>();
    protected ArrayList<Book> fairytalenames = new ArrayList<>();
    protected ArrayList<Book> fantasynames = new ArrayList<>();
    protected ArrayList<Book> horrornames = new ArrayList<>();
    protected ArrayList<Book> humornames = new ArrayList<>();
    protected ArrayList<Book> kidsnames = new ArrayList<>();
    protected ArrayList<Book> mysterynames = new ArrayList<>();
    protected ArrayList<Book> poetrynames = new ArrayList<>();
    public ArrayList<Book> romancenames = new ArrayList<>();
    protected ArrayList<Book> scifinames = new ArrayList<>();
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    Button moretopsellers,moreaction,moreclassic, morecomic,morecookbooks,moredrama,morefairytale,
            morefantasy,morehorror,morehumor,morekidsbooks,moremystery,morepoetry,moreromance,moresciencefiction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_books);
        moretopsellers=findViewById(R.id.moretopsellers);
        moreaction=findViewById(R.id.moreaction);
        moreclassic=findViewById(R.id.moreclassic);
        morecomic=findViewById(R.id.morecomic);
        morecookbooks=findViewById(R.id.morecookbooks);
        moredrama=findViewById(R.id.moredrama);
        morefairytale=findViewById(R.id.morefairytale);
        morefantasy=findViewById(R.id.morefantasy);
        morehorror=findViewById(R.id.morehorror);
        morehumor=findViewById(R.id.morehumor);
        morekidsbooks=findViewById(R.id.morekidsbooks);
        moremystery=findViewById(R.id.moremystery);
        morepoetry=findViewById(R.id.morepoetry);
        moreromance=findViewById(R.id.moreromance);
        moresciencefiction=findViewById(R.id.moresciencefiction);

        moretopsellers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Top Seller");
                startActivity(in);
            }
        });
        moreaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Action and Adventure");
                startActivity(in);
            }
        });
        moreclassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Classic");
                startActivity(in);
            }
        });
        morecomic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Comic and Graphic Novel");
                startActivity(in);
            }
        });
        morecookbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Cook");
                startActivity(in);
            }
        });
        moredrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Drama");
                startActivity(in);
            }
        });
        morefairytale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Fairy Tale");
                startActivity(in);
            }
        });
        morefantasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Fantasy");
                startActivity(in);
            }
        });
        morehorror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Horror");
                startActivity(in);
            }
        });
        morehumor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Humor");
                startActivity(in);
            }
        });
        morekidsbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Kids");
                startActivity(in);
            }
        });
        moremystery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Mystery");
                startActivity(in);
            }
        });
        morepoetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Poetry");
                startActivity(in);
            }
        });
        moreromance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Romance");
                startActivity(in);
            }
        });
        moresciencefiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CategoryActivity.class);
                in.putExtra("Category","Sci-Fi");
                startActivity(in);
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("books");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bookstore : dataSnapshot.getChildren()) {
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
                    if (bookstore.child("category").getValue().toString().equals("Action and Adventure")) {
                         actionnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Classic")) {
                        classicnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Comic and Graphic Novel")) {
                        comicbooksnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Cook")) {
                        cookbooksnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Drama")) {
                        dramanames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Fairy Tale")) {
                        fairytalenames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Fantasy")) {
                        fantasynames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Horror")) {
                        horrornames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Humor")) {
                        humornames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Kids")) {
                        kidsnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Mystery")) {
                        mysterynames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Poetry")) {
                        poetrynames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Romance")) {
                        romancenames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                    else if (bookstore.child("category").getValue().toString().equals("Sci-Fi")) {
                        scifinames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        if(Double.parseDouble(bookstore.child("rating").getValue().toString()) >= 4.5){
                            topsellersnames.add(new Book(id,imageUrl,BookTitle,BookAuthor,YearPublished,Price,Quantity,rating,Category,BookDescription));
                        }
                    }
                }
                initRecyclerView_topsellers();
                initRecyclerView_action();
                initRecyclerView_classic();
                initRecyclerView_comicbooks();
                initRecyclerView_cookbooks();
                initRecyclerView_drama();
                initRecyclerView_fairytale();
                initRecyclerView_fantasy();
                initRecyclerView_horror();
                initRecyclerView_humor();
                initRecyclerView_kidsbooks();
                initRecyclerView_mystery();
                initRecyclerView_poetry();
                initRecyclerView_romance();
                initRecyclerView_scienceficiton();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    protected void initRecyclerView_topsellers() {
        RecyclerView recyclerView = findViewById(R.id.topsellersrecylcerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(topsellersnames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_action() {
        RecyclerView recyclerView = findViewById(R.id.actionrecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(actionnames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_classic() {
        RecyclerView recyclerView = findViewById(R.id.classicrecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(classicnames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_comicbooks() {
        RecyclerView recyclerView = findViewById(R.id.comicrecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(comicbooksnames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_cookbooks() {
        RecyclerView recyclerView = findViewById(R.id.cookbooksrecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(cookbooksnames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_drama() {
        RecyclerView recyclerView = findViewById(R.id.dramarecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(dramanames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_fairytale() {
        RecyclerView recyclerView = findViewById(R.id.fairytalerecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(fairytalenames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_fantasy() {
        RecyclerView recyclerView = findViewById(R.id.fantasyrecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(fantasynames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_horror() {
        RecyclerView recyclerView = findViewById(R.id.horrorrecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(horrornames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_humor() {
        RecyclerView recyclerView = findViewById(R.id.humorrecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(humornames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_kidsbooks() {
        RecyclerView recyclerView = findViewById(R.id.kidsbooksrecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(kidsnames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_mystery() {
        RecyclerView recyclerView = findViewById(R.id.mysteryrecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(mysterynames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_poetry() {
        RecyclerView recyclerView = findViewById(R.id.poetryrecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(poetrynames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_romance()
    {
        RecyclerView recyclerView = findViewById(R.id.romancerecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(romancenames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
    }
    protected void initRecyclerView_scienceficiton() {
        RecyclerView recyclerView = findViewById(R.id.sciencefictionrecyclerview);
        recylcerview_adapter1 adapter = new recylcerview_adapter1(scifinames ,UserProfileBooks.this);
        recyclerView.setAdapter(adapter);
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
            else if (item.getItemId() == R.id.profile) {
                    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            Intent intent=new Intent(getApplicationContext(), UserProfile.class);
                            startActivity(intent);
                            return true;
                        }
                    });
            }
            else if (item.getItemId() == R.id.logout) {
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        final FirebaseAuth auth=FirebaseAuth.getInstance();
                        auth.signOut();
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;
                    }
                });
            }
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
