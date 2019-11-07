package com.example.loginregis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregis.Model.userReview;
import com.example.loginregis.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class userReviewAdapter extends RecyclerView.Adapter<userReviewAdapter.itemHolder> {
    Context context;
    ArrayList<userReview> arrayUserReview;

    public userReviewAdapter(Context context, ArrayList<userReview> arrayUserReview) {
        this.context = context;
        this.arrayUserReview = arrayUserReview;
    }

    @NonNull
    @Override
    public itemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,null);
        itemHolder itemHolder=new itemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemHolder holder, int position) {
        userReview userReview=arrayUserReview.get(position);
        holder.userName.setText(userReview.getUserName());
        holder.userRating.setRating(userReview.getUserRating());
        holder.postDate.setText(( userReview.getNgaydang()));
        holder.content.setText(userReview.getNoidungReview());
        Picasso.get().load(userReview.getUserAvatar()).placeholder(R.drawable.useravatar).error(R.drawable.useravatar).into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return arrayUserReview.size();
    }

    public class itemHolder extends RecyclerView.ViewHolder {
        //public ImageView imgAvatar;
       public CircleImageView imgAvatar;
        public TextView userName;
        public TextView postDate;
        public TextView content;
        public RatingBar userRating;
        public itemHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar=itemView.findViewById(R.id.userAvatar);
            userName=itemView.findViewById(R.id.userName);
            postDate=itemView.findViewById(R.id.ngaydang);
            content=itemView.findViewById(R.id.noidungReview);
            userRating=itemView.findViewById(R.id.userRating);
        }
    }
}
