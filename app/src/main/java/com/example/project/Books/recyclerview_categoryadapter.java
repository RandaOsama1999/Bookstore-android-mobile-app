package com.example.project.Books;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

class recyclerview_categoryadapter extends RecyclerView.Adapter<recyclerview_categoryadapter.MyViewHolder>implements Filterable {


    protected Context mContext;
    protected List<Book> mData;
    protected List<Book> filterdata;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    public recyclerview_categoryadapter(Context mContext, ArrayList<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;
        filterdata = new ArrayList<>(mData);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.category_listitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(mData.get(position).getBookTitle());
        StorageReference ref = storageReference.child("books").child(mData.get(position).getId());
        StorageReference photoRef = storage.getReference(ref.getPath());
        photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                Glide.with(mContext).load(downloadUrl).into(holder.img_book_thumbnail);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, bookActivity.class);
                intent.putExtra("Title", mData.get(position).getBookTitle());
                intent.putExtra("category", mData.get(position).getCategory());
                intent.putExtra("Description", mData.get(position).getBookDescription());
                intent.putExtra("Author", mData.get(position).getBookAuthor());
                intent.putExtra("Quantity", mData.get(position).getQuantity());
                intent.putExtra("Price", mData.get(position).getPrice());
                intent.putExtra("Year", mData.get(position).getYearPublished());
                intent.putExtra("Rating", mData.get(position).getRating());
                intent.putExtra("Thumbnail", mData.get(position).getId());

                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return filterarion;
    }

    private Filter filterarion = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Book> filteredlist = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredlist.addAll(filterdata);
            } else {
                String filterpattern = charSequence.toString().toLowerCase().trim();
                for (Book item : filterdata) {
                    if (item.getBookTitle().toLowerCase().contains(filterpattern)) {
                        filteredlist.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            mData.clear();
            mData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.booktitle);
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.bookimg);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }
}