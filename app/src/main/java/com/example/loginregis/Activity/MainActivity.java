package com.example.loginregis.Activity;

//import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.example.loginregis.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbarMain);
        viewFlipper=findViewById(R.id.viewFlipMain);
        recyclerView=findViewById(R.id.recyclerView);
        navigationView=findViewById(R.id.naviMain);
        listView=findViewById(R.id.lisViewMain);
        drawerLayout=findViewById(R.id.drawerLayout);
        ActionBar();
        ActionViewFlipper();
    }

    private void ActionViewFlipper() {
        ArrayList<String> arrayQuangCao=new ArrayList<>();
        arrayQuangCao.add("https://static.cuisineaz.com/400x320/i99425-hamburger.jpg");
        arrayQuangCao.add("http://streaming1.danviet.vn/upload/4-2018/images/2018-10-09/pho-viet-nam-1539070965-width1081height720.jpg");
        arrayQuangCao.add("https://i.ytimg.com/vi/rRtcZxAAOhQ/maxresdefault.jpg");
        arrayQuangCao.add("https://www.brandsvietnam.com/upload/news/480px/2018/14381_Trasua.jpg");
        for(int i=0;i<arrayQuangCao.size();i++)
        {
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.get().load(arrayQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animationSlideIn= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.silde_right);
        Animation animationSlideOut= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animationSlideIn);
        viewFlipper.setOutAnimation(animationSlideOut);


    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}
