package com.example.project.Books;

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
import com.example.project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

class recylcerview_adapter1 extends RecyclerView.Adapter<recylcerview_adapter1.ViewHolder>{
    protected ArrayList<Book> ImageNames;
    protected ArrayList<String> Images;
    protected Context context;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    public recylcerview_adapter1(ArrayList<Book> imageNames, Context context) {
        ImageNames = imageNames;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_topsellers , parent , false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        StorageReference ref = storageReference.child("books").child(ImageNames.get(position).getId());
        StorageReference photoRef = storage.getReference(ref.getPath());
        photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                Glide.with(context).load(downloadUrl).into(holder.image);
            }
        });
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,bookActivity.class);
                intent.putExtra("Title", ImageNames.get(position).getBookTitle());
                intent.putExtra("category", ImageNames.get(position).getCategory());
                intent.putExtra("Description", ImageNames.get(position).getBookDescription());
                intent.putExtra("Author", ImageNames.get(position).getBookAuthor());
                intent.putExtra("Quantity", ImageNames.get(position).getQuantity());
                intent.putExtra("Price", ImageNames.get(position).getPrice());
                intent.putExtra("Year", ImageNames.get(position).getYearPublished());
                intent.putExtra("Rating", ImageNames.get(position).getRating());
                intent.putExtra("Thumbnail", ImageNames.get(position).getId());
                context.startActivity(intent);
            }
        });
        //holder.image.setImageResource(R.drawable.boks);
        holder.imageName.setText(ImageNames.get(position).getBookTitle());




    }

    @Override
    public int getItemCount() {
        return ImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView imageName;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout =  itemView.findViewById(R.id.parent_layout2);



        }
    }
}


