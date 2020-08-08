package com.example.project.Books;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBook extends Fragment {

    View root;
    EditText title,author,year,price,desc,rating,quantity;
    Spinner category;
    Button upload,save;
    ImageView cover;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public AddBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_add_book, container, false);
        title=root.findViewById(R.id.booktitle);
        author=root.findViewById(R.id.bookauthor);
        year=root.findViewById(R.id.year);
        price=root.findViewById(R.id.price);
        rating=root.findViewById(R.id.rating);
        category=root.findViewById(R.id.category);
        desc=root.findViewById(R.id.desc);
        quantity=root.findViewById(R.id.Quantity);
        upload=root.findViewById(R.id.upload);
        cover=root.findViewById(R.id.img);
        save=root.findViewById(R.id.add);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


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
                if(filePath != null)
                {
                    String id=UUID.randomUUID().toString();
                    databaseReference= FirebaseDatabase.getInstance().getReference("books").child(id);
                    Book b=new Book(id,cover.getDrawable().toString(),title.getText().toString(),author.getText().toString()
                            ,year.getText().toString(),Double.parseDouble(price.getText().toString()),quantity.getText().toString(),Double.parseDouble(rating.getText().toString())
                            ,category.getSelectedItem().toString(),desc.getText().toString());
                    databaseReference.setValue(b);
                    StorageReference ref = storageReference.child("books/"+ id);
                    ref.putFile(filePath)
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
                else{
                    Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
                }
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
