package com.example.loginregis.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregis.Adapter.diadiemAdapter;
import com.example.loginregis.Adapter.diadiemsavedAdapter;
import com.example.loginregis.Fragment.fragmentSaved;
import com.example.loginregis.Fragment.fragmentUser;
import com.example.loginregis.Fragment.fragmentHome;
import com.example.loginregis.Model.itemDiadiem;
import com.example.loginregis.Model.userProfile;
import com.example.loginregis.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private static final String TAG = "RegActivity";
    RecyclerView recyclerView;
    ArrayList<itemDiadiem> mangReview;
    diadiemAdapter reviewAdapter;
    userProfile userProfile=new userProfile();
    diadiemsavedAdapter savedAdapter;
    ArrayList<itemDiadiem> mangsavedReview;
    SearchView sw;
    Toolbar homeTB;
    Button Dangbai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userProfile= (com.example.loginregis.Model.userProfile) getIntent().getSerializableExtra("userprofile");

        Dangbai=findViewById(R.id.btnDangBai);
        Dangbai.setVisibility(View.GONE);
        mangReview=new ArrayList<>();
        mangsavedReview=new ArrayList<>();
        savedAdapter=new diadiemsavedAdapter(HomeActivity.this,mangsavedReview,userProfile);
        reviewAdapter=new diadiemAdapter(getApplicationContext(),mangReview,userProfile);
        GetBaiReviewMoiNhat();
        sw=findViewById(R.id.searchView);
        homeTB=findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.NavBot);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        sw.setSubmitButtonEnabled(true);
        Dangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent postIntent=new Intent(HomeActivity.this,PostActivity.class);
                postIntent.putExtra("userprofile",userProfile);

                startActivity(postIntent);
            }
        });
        sw.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        // Toast.makeText(this, userProfile.getAva(), Toast.LENGTH_SHORT).show();
        sw.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(HomeActivity.this, sw.getQuery(), Toast.LENGTH_SHORT).show();
                mangReview.clear();
                String a=sw.getQuery().toString();
                a=a.replace(' ','+');
                //Toast.makeText(HomeActivity.this, a, Toast.LENGTH_SHORT).show();
                //Toast.makeText(HomeActivity.this, "https://androidapp12.000webhostapp.com/getreview.php?search=mì+ý", Toast.LENGTH_SHORT).show();
                progressDialog = new ProgressDialog(HomeActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Đang xử lý...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                Log.d(TAG,"Register");
                GetKetQuaSearch(a);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new fragmentHome(reviewAdapter,1,userProfile)).commit();
                mangReview.clear();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new fragmentHome(reviewAdapter)).commit();


    }

    private void GetKetQuaSearch(String keyword) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest("http://52.148.113.133/android/getreview.php?search="+keyword, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                if(response!=null)
                {
                    progressDialog.dismiss();
                    int ID=0;
                    double rating;
                    String diachi;
                    String username="";
                    String tieude="";
                    String noidung="";
                    String str="2015-03-31";
                    Date date=Date.valueOf(str);
                    String hinhanhtemp="";

                    String hinhanh="http://52.148.113.133/android/hinhanh/";
                    //Toast.makeText(HomeActivity.this, "here " +response.length(), Toast.LENGTH_SHORT).show();
                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            hinhanhtemp=hinhanh;
                            JSONObject jsonObject=response.getJSONObject(i);
                            ID=jsonObject.getInt("id");

                            username=jsonObject.getString("username");

                            tieude=jsonObject.getString("tieude");


                            noidung=jsonObject.getString("noidung");
                            hinhanhtemp=hinhanh+jsonObject.getString("hinhanh");
                            rating=jsonObject.getDouble("rating");
                            diachi=jsonObject.getString("diachi");

                            mangReview.add(new itemDiadiem(ID,username,tieude,noidung,hinhanhtemp,(float)rating,diachi));
                            String a=Integer.toString(mangReview.size());
                            //Toast.makeText(HomeActivity.this,a,Toast.LENGTH_SHORT).show();
                            reviewAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", "Bearer " + userProfile.getToken());
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);

    }

    private void GetBaiReviewMoiNhat() { //Lấy dữ liệu từ Json cho vào mảng
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest("http://52.148.113.133/android/getreview.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null)
                {
                    int ID=0;
                    double rating;
                    String diachi;
                    String username="";
                    String tieude="";
                    String noidung="";
                    String str="2015-03-31";
                    Date date=Date.valueOf(str);
                    String hinhanhtemp="";

                    String hinhanh="http://52.148.113.133/android/hinhanh/";

                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            hinhanhtemp=hinhanh;
                            JSONObject jsonObject=response.getJSONObject(i);
                            ID=jsonObject.getInt("id");

                            username=jsonObject.getString("username");

                            tieude=jsonObject.getString("tieude");


                            noidung=jsonObject.getString("noidung");
                            hinhanhtemp=hinhanh+jsonObject.getString("hinhanh");
                            rating=jsonObject.getDouble("rating");
                            diachi=jsonObject.getString("diachi");

                            mangReview.add(new itemDiadiem(ID,username,tieude,noidung,hinhanhtemp,(float)rating,diachi));
                            String a=Integer.toString(mangReview.size());
                            //Toast.makeText(HomeActivity.this,a,Toast.LENGTH_SHORT).show();
                            reviewAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", "Bearer " + userProfile.getToken());
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;
            if(menuItem.getItemId()==R.id.nav_home)
            {
                Dangbai.setVisibility(View.GONE);
                //Toast.makeText(HomeActivity.this,userProfile.getAva(),Toast.LENGTH_SHORT).show();
                mangsavedReview.clear();
                mangReview.clear();
                sw.setVisibility(View.VISIBLE);
                savedAdapter.setCheckFragment(0);
                GetBaiReviewMoiNhat();
                selectedFragment=new fragmentHome(reviewAdapter,0,userProfile); //truyền adapter vào cho recyleview trong fragment home
            }
            if (menuItem.getItemId()==R.id.nav_save)
            {
                Dangbai.setVisibility(View.GONE);
                sw.setVisibility(View.GONE);
                homeTB.setTitle("Đã lưu");
                homeTB.setTitleTextColor(-1);
                mangReview.clear();
                mangsavedReview.clear();
                savedAdapter.setCheckFragment(1);
                GetBaiDaLuu();
                //Toast.makeText(HomeActivity.this, "asd"+mangsavedReview.size(), Toast.LENGTH_SHORT).show();
                selectedFragment=new fragmentSaved(mangReview.size(),savedAdapter);

            }
            if(menuItem.getItemId()==R.id.nav_user)
            {
                savedAdapter.setCheckFragment(0);
                //Toast.makeText(HomeActivity.this, userProfile.getNgaysinh(), Toast.LENGTH_SHORT).show();
                sw.setVisibility(View.GONE);
                homeTB.setTitle("Tài khoản");
                Dangbai.setVisibility(View.GONE);
                homeTB.setTitleTextColor(-1);
                mangReview.clear();
                mangsavedReview.clear();
                selectedFragment=new fragmentUser(userProfile);

            }
            if(menuItem.getItemId()==R.id.nav_post)
            {
                savedAdapter.setCheckFragment(2);
                mangReview.clear();
                mangsavedReview.clear();
                homeTB.setTitle("Bài đã đăng");
                homeTB.setTitleTextColor(-1);
                sw.setVisibility(View.GONE);
                Dangbai.setVisibility(View.VISIBLE);
                GetBaiDaDang();
                selectedFragment=new fragmentSaved(mangReview.size(),savedAdapter);
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,selectedFragment).commit();
            return true;
        }
    };

    private void GetBaiDaDang() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest("http://52.148.113.133/android/getreview.php?username="+userProfile.getUsrname(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null)
                {
                    int ID=0;
                    double rating;
                    String diachi;
                    String username="";
                    String tieude="";
                    String noidung="";
                    String str="2015-03-31";
                    Date date=Date.valueOf(str);
                    String hinhanhtemp="";

                    String hinhanh="http://52.148.113.133/android/hinhanh/";

                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            hinhanhtemp=hinhanh;
                            JSONObject jsonObject=response.getJSONObject(i);
                            ID=jsonObject.getInt("id");

                            username=jsonObject.getString("username");

                            tieude=jsonObject.getString("tieude");


                            noidung=jsonObject.getString("noidung");
                            hinhanhtemp=hinhanh+jsonObject.getString("hinhanh");
                            rating=jsonObject.getDouble("rating");
                            diachi=jsonObject.getString("diachi");

                            mangsavedReview.add(new itemDiadiem(ID,username,tieude,noidung,hinhanhtemp,(float)rating,diachi));
                            String a=Integer.toString(mangsavedReview.size());
                            //Toast.makeText(HomeActivity.this,a,Toast.LENGTH_SHORT).show();
                            savedAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", "Bearer "+userProfile.getToken());
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }

    private void GetBaiDaLuu() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest("http://52.148.113.133/android/getreview.php?getsave&username="+userProfile.getUsrname(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null)
                {
                    int ID=0;
                    double rating;
                    String diachi;
                    String username="";
                    String tieude="";
                    String noidung="";
                    String str="2015-03-31";
                    Date date=Date.valueOf(str);
                    String hinhanhtemp="";

                    String hinhanh="http://52.148.113.133/android/hinhanh/";

                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            hinhanhtemp=hinhanh;
                            JSONObject jsonObject=response.getJSONObject(i);
                            ID=jsonObject.getInt("id");

                            username=jsonObject.getString("username");

                            tieude=jsonObject.getString("tieude");


                            noidung=jsonObject.getString("noidung");
                            hinhanhtemp=hinhanh+jsonObject.getString("hinhanh");
                            rating=jsonObject.getDouble("rating");
                            diachi=jsonObject.getString("diachi");

                            mangsavedReview.add(new itemDiadiem(ID,username,tieude,noidung,hinhanhtemp,(float)rating,diachi));
                            String a=Integer.toString(mangsavedReview.size());
                            //Toast.makeText(HomeActivity.this,a,Toast.LENGTH_SHORT).show();
                            savedAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", "Bearer " + userProfile.getToken());
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }
}


