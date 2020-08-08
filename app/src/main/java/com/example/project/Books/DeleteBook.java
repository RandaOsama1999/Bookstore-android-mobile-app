package com.example.project.Books;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteBook extends Fragment {

    View rootview;
    Button btn;
    EditText title,author;
    DatabaseReference databaseReference;
    int g=0;
    Uri filePath;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageReference=FirebaseStorage.getInstance().getReference();
    public DeleteBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview= inflater.inflate(R.layout.fragment_delete_book, container, false);
        btn=rootview.findViewById(R.id.delete);
        title=rootview.findViewById(R.id.bookname);
        author=rootview.findViewById(R.id.bookauthor);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference= FirebaseDatabase.getInstance().getReference("books");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot bookstore : dataSnapshot.getChildren()) {
                            if (bookstore.child("bookTitle").getValue().toString().equals(title.getText().toString())
                                && bookstore.child("bookAuthor").getValue().toString().equals(author.getText().toString())) {
                                g=0;
                                bookstore.getRef().removeValue();
                                String id=bookstore.child("id").getValue().toString();
                                StorageReference ref = storageReference.child("books").child(id);
                                StorageReference photoRef = storage.getReference(ref.getPath());
                                photoRef.delete();
                                Toast.makeText(getContext(),"Deleted",Toast.LENGTH_SHORT).show();
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
        return rootview;
    }

}
