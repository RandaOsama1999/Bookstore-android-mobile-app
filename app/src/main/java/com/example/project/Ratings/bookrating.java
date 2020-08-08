package com.example.project.Ratings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project.R;
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

import java.util.UUID;

public class bookrating extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final  String user_id=user.getUid();
    ImageView finalbookimage;
    TextView tvtitle,tvcategory,txtauthor;
    String Title,Category,Author,Image;
    RatingBar stars;
    EditText comment;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookrating);

        tvtitle = findViewById(R.id.txttitle);
        tvcategory =  findViewById(R.id.txtCat);
        txtauthor =  findViewById(R.id.txtauthor);
        finalbookimage =  findViewById(R.id.bookthumbnail);
        stars=findViewById(R.id.rating);
        comment=findViewById(R.id.comment);

        Intent intent = getIntent();
        Title = intent.getExtras().getString("title");
        Category=intent.getExtras().getString("category");
        Author = intent.getExtras().getString("author");
        Image = intent.getExtras().getString("image") ;


        tvtitle.setText(Title);
        tvcategory.setText(Category);
        txtauthor.setText(Author);

        StorageReference ref = storageReference.child("books").child(Image);
        StorageReference photoRef = storage.getReference(ref.getPath());
        photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                Glide.with(getApplicationContext()).load(downloadUrl).into(finalbookimage);
            }
        });
        final DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("ratings");
        databaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String rakam = "", comments="";
                for (DataSnapshot ratess : dataSnapshot.getChildren()){
                    if (
                            user_id.equals(ratess.child("userid").getValue()) &&
                                    Image.equals(ratess.child("bookid").getValue())
                    ){
                        rakam=ratess.child("rate").getValue().toString();
                        comments=ratess.child("comment").getValue().toString();
                    }
                }
                if(comments!="" && rakam!="") {
                    comment.setText(comments);
                    stars.setRating(Float.parseFloat(rakam));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void addrate(View view) {
        final String id= UUID.randomUUID().toString();
        final float ratenumber=stars.getRating();
        final Boolean status = false;
        if(ratenumber>0.0) {
            final DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("ratings");

            databaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    Boolean flag = false;
                    for (DataSnapshot ratess : dataSnapshot.getChildren())
                    {

                        if (
                                user_id.equals(ratess.child("userid").getValue()) &&
                                        Image.equals(ratess.child("bookid").getValue())
                        )
                        {
                            // Momken hena n7ot Alert Box 2n el comment bta3o Lsa hayt3mlo accept law 3amal update Lel rate bta3o

                            DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("ratings").child(ratess.child("id").getValue().toString()).child("comment");
                            databaseReference3.setValue(comment.getText().toString());
                            databaseReference3 = FirebaseDatabase.getInstance().getReference().child("ratings").child(ratess.child("id").getValue().toString()).child("rate");
                            databaseReference3.setValue(ratenumber);
                            databaseReference3 = FirebaseDatabase.getInstance().getReference().child("ratings").child(ratess.child("id").getValue().toString()).child("status");
                            databaseReference3.setValue(status);
                            flag = true;

                            Toast.makeText(bookrating.this, "Thank You", Toast.LENGTH_SHORT).show();


                        }
                    }
                    if(flag==false)
                    { databaseReference = FirebaseDatabase.getInstance().getReference("ratings").child(id);
                        Ratings rate = new Ratings(id, user_id, Image, ratenumber, comment.getText().toString(), status);
                        databaseReference.setValue(rate);
                        Toast.makeText(bookrating.this, "Thank you", Toast.LENGTH_SHORT).show();


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            Toast.makeText(this, "Please add a rate for the book", Toast.LENGTH_SHORT).show();
        }


    }
}
