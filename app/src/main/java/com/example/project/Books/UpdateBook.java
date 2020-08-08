package com.example.project.Books;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateBook extends Fragment {

    Button search,upload,save;
    EditText titlesearch,authorsearch,title,author,year,price,desc,rating,quantity;
    Spinner category;
    DatabaseReference databaseReference,databaseReference2;
    View root;
    int g=0;
    ImageView cover;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    ArrayAdapter<String> array_spinner;
    public UpdateBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_update_book, container, false);
        search=root.findViewById(R.id.search);
        titlesearch=root.findViewById(R.id.titlesearch);
        authorsearch=root.findViewById(R.id.authorsearch);
        title=root.findViewById(R.id.booktitle);
        author=root.findViewById(R.id.bookauthor);
        year=root.findViewById(R.id.year);
        price=root.findViewById(R.id.price);
        rating=root.findViewById(R.id.rating);
        category=root.findViewById(R.id.category);
        array_spinner=(ArrayAdapter<String>)category.getAdapter();
        quantity=root.findViewById(R.id.quantity);
        desc=root.findViewById(R.id.desc);
        upload=root.findViewById(R.id.upload);
        cover=root.findViewById(R.id.img);
        save=root.findViewById(R.id.add);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        title.setEnabled(false);
        author.setEnabled(false);
        year.setEnabled(false);
        price.setEnabled(false);
        rating.setEnabled(false);
        category.setEnabled(false);
        quantity.setEnabled(false);
        desc.setEnabled(false);
        upload.setEnabled(false);
        save.setEnabled(false);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference= FirebaseDatabase.getInstance().getReference("books");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot bookstore : dataSnapshot.getChildren()) {
                            if (bookstore.child("bookTitle").getValue().toString().equals(titlesearch.getText().toString())
                                    && bookstore.child("bookAuthor").getValue().toString().equals(authorsearch.getText().toString())) {
                                g=0;
                                authorsearch.setEnabled(false);
                                titlesearch.setEnabled(false);
                                search.setEnabled(false);
                                title.setEnabled(true);
                                author.setEnabled(true);
                                year.setEnabled(true);
                                price.setEnabled(true);
                                quantity.setEnabled(true);
                                rating.setEnabled(true);
                                category.setEnabled(true);
                                desc.setEnabled(true);
                                upload.setEnabled(true);
                                save.setEnabled(true);

                                title.setText("" + bookstore.child("bookTitle").getValue().toString(), TextView.BufferType.EDITABLE);
                                author.setText("" + bookstore.child("bookAuthor").getValue().toString(), TextView.BufferType.EDITABLE);
                                year.setText("" + bookstore.child("yearPublished").getValue().toString(), TextView.BufferType.EDITABLE);
                                price.setText("" + bookstore.child("price").getValue().toString(), TextView.BufferType.EDITABLE);
                                rating.setText("" + bookstore.child("rating").getValue().toString(), TextView.BufferType.EDITABLE);
                                quantity.setText("" + bookstore.child("quantity").getValue().toString(), TextView.BufferType.EDITABLE);
                                category.setSelection(array_spinner.getPosition(bookstore.child("category").getValue().toString()));
                                desc.setText("" + bookstore.child("bookDescription").getValue().toString(), TextView.BufferType.EDITABLE);
                                String id=bookstore.child("id").getValue().toString();
                                StorageReference ref = storageReference.child("books").child(id);
                                StorageReference photoRef = storage.getReference(ref.getPath());
                                //getDownloadUrl().getResult().toString()
                                photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                                {
                                    @Override
                                    public void onSuccess(Uri downloadUrl)
                                    {
                                        Glide.with(getContext()).load(downloadUrl).into(cover);
                                    }
                                });

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
                    Toast.makeText(getContext(),"This book is not available",Toast.LENGTH_SHORT).show();
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 71);

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(filePath != null) {

                    databaseReference2 = FirebaseDatabase.getInstance().getReference("books");
                    databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot bookstore : dataSnapshot.getChildren()) {
                                if (bookstore.child("bookTitle").getValue().toString().equals(titlesearch.getText().toString())
                                        && bookstore.child("bookAuthor").getValue().toString().equals(authorsearch.getText().toString())) {
                                    String id=bookstore.child("id").getValue().toString();

                                    bookstore.getRef().child("bookTitle").setValue(title.getText().toString());
                                    bookstore.getRef().child("bookAuthor").setValue(author.getText().toString());
                                    bookstore.getRef().child("yearPublished").setValue(year.getText().toString());
                                    bookstore.getRef().child("quantity").setValue(quantity.getText().toString());
                                    bookstore.getRef().child("price").setValue(price.getText().toString());
                                    bookstore.getRef().child("rating").setValue(rating.getText().toString());
                                    bookstore.getRef().child("category").setValue(category.getSelectedItem().toString());
                                    bookstore.getRef().child("bookDescription").setValue(desc.getText().toString());

                                    if(filePath != null) {
                                        StorageReference ref = storageReference.child("books").child(id);
                                        StorageReference photoRef = storage.getReference(ref.getPath());
                                        photoRef.delete();
                                        StorageReference ref2 = storageReference.child("books/"+ id);
                                        ref2.putFile(filePath)
                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getContext(), "Failed ", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                    }
                                                });

                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
               // }
            }
        });


        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 71 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                cover.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
