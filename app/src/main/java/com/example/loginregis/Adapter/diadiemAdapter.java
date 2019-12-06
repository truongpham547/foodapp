package com.example.loginregis.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregis.Activity.ChiTietActivity;
import com.example.loginregis.Model.itemDiadiem;
import com.example.loginregis.Model.userProfile;
import com.example.loginregis.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class diadiemAdapter extends RecyclerView.Adapter<diadiemAdapter.itemHolder> {
    Context context;
    ArrayList<itemDiadiem> arrayReview;
    public userProfile up;
    int checkFragment=0;

    public void setCheckFragment(int checkFragment) {
        this.checkFragment = checkFragment;
    }

    public diadiemAdapter(Context context, ArrayList<itemDiadiem> arrayReview, userProfile up) {
        this.context = context;
        this.arrayReview = arrayReview;
        this.up = up;

    }

    public diadiemAdapter(Context context, ArrayList<itemDiadiem> arrayReview) {
        this.context = context;
        this.arrayReview = arrayReview;
    }

    @NonNull
    @Override
    public itemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homescreen_item,null);
        itemHolder itemHolder=new itemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemHolder holder, int position) {
        itemDiadiem baiReview=arrayReview.get(position);
        String textNoidung = baiReview.getNoidung();
        if (baiReview.getNoidung().length() > 80) textNoidung = textNoidung.substring(0, 80) + "...";
        holder.NoiDung.setText(textNoidung);

        String textTieude = baiReview.getTieude();
        if(textTieude.length()>25)
        textTieude = textTieude.substring(0, 25) + "...";
        holder.TieuDe.setText(textTieude);
        Picasso.get().load(baiReview.getHinhanh()).into(holder.HinhReview);

    }

    @Override
    public int getItemCount() {
        return arrayReview.size();
    }

    public class itemHolder extends RecyclerView.ViewHolder{
        public ImageView HinhReview;
        public TextView TieuDe;
        public TextView NoiDung;

        public itemHolder(@NonNull View itemView) {
            super(itemView);

            HinhReview=itemView.findViewById(R.id.hinhanhReview);
            TieuDe=itemView.findViewById(R.id.tenbaiReview);
            NoiDung=itemView.findViewById(R.id.motaReview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, ChiTietActivity.class);

                   intent.putExtra("chitiet",arrayReview.get(getAdapterPosition()));

                   intent.putExtra("userprofile",up);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
