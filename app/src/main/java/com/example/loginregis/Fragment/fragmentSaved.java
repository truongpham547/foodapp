package com.example.loginregis.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregis.Adapter.diadiemAdapter;
import com.example.loginregis.Adapter.diadiemsavedAdapter;
import com.example.loginregis.R;

public class fragmentSaved extends Fragment {
    public diadiemAdapter ra;
    public int itemcount;

    public fragmentSaved(int itemcount, diadiemsavedAdapter ra1) {
        this.itemcount = itemcount;
        this.ra1 = ra1;
    }

    public fragmentSaved(diadiemsavedAdapter ra1) {
        this.ra1 = ra1;


    }

    public diadiemsavedAdapter ra1;

    public diadiemAdapter getReviewAdapter() {
        return ra;
    }

    public fragmentSaved setReviewAdapter(diadiemAdapter reviewAdapter) {
        this.ra = reviewAdapter;

        return this;
    }

    public fragmentSaved(diadiemAdapter ra) {
        this.ra = ra;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.saved_fragment,container,false);
        TextView check=rootView.findViewById(R.id.checkbailuu);
        //if(itemcount==0) check.setText("chưa có bài lưu");
        RecyclerView recyclerView=rootView.findViewById(R.id.savedRecyleView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        recyclerView.setAdapter(ra1);
        if(ra1.getItemCount()!=0) check.setText("");

        return rootView;
    }
}
