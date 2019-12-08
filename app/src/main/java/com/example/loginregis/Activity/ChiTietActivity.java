package com.example.loginregis.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregis.Adapter.userReviewAdapter;
import com.example.loginregis.Model.itemDiadiem;
import com.example.loginregis.Model.userProfile;
import com.example.loginregis.Model.userReview;
import com.example.loginregis.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.example.loginregis.Activity.VietBaiDanhGia_activity.*;

public class ChiTietActivity extends AppCompatActivity {
    private static final String TAG = "ChiTietActivity";
    androidx.appcompat.widget.Toolbar toolbarChiTiet;
    ImageView imgviewChiTiet;
    TextView tvTieude, tvNoiDung,tvDiachi,chiduong,vietdanhgia,luu;
    RatingBar rtb;
    RecyclerView recyclerView;
    ArrayList<userReview> mangDanhGia;
    userReviewAdapter userReviewAdapter;
    userProfile userProfile=new userProfile();
    itemDiadiem brv=new itemDiadiem();
    int id=0;
    String address="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiec);
        AnhXa();
        mangDanhGia=new ArrayList<>();
        userReviewAdapter=new userReviewAdapter(getApplicationContext(),mangDanhGia);

        ActionToolBar();
        GetInformation();


        getBinhLuan();
        recyclerView.setHasFixedSize(true);
        try{

            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
            recyclerView.setAdapter(userReviewAdapter);}
        catch (Exception e){
            Toast.makeText( ChiTietActivity.this,"lỗi", Toast.LENGTH_SHORT).show();
        }
        chiduong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    address = address.replace(' ', '+');
                    Intent geoIntent = new Intent(
                            android.content.Intent.ACTION_VIEW, Uri
                            .parse("geo:0,0?q=" + address));
                    startActivity(geoIntent);

                } catch (Exception e) { }
            }
        });
        vietdanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent vietdanhgiaIntent=new Intent(ChiTietActivity.this,VietBaiDanhGia_activity.class);
                vietdanhgiaIntent.putExtra("userprofile",userProfile);
                vietdanhgiaIntent.putExtra("diadiem",brv);
                startActivityForResult(vietdanhgiaIntent,1);
            }
        });
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuDiaDiem();
            }
        });

    }
    public void showSavedSuccess()
    {
        LayoutInflater flat = getLayoutInflater();
        View layout = flat.inflate(R.layout.save_post_success, (ViewGroup) findViewById(R.id.toast_type) );
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,100);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }
    public void showSavedFail()
    {
        LayoutInflater flat = getLayoutInflater();
        View layout = flat.inflate(R.layout.saved_post_fail, (ViewGroup) findViewById(R.id.toast_type) );
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,100);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }
    private void LuuDiaDiem() {
        final ProgressDialog progressDialog = new ProgressDialog(ChiTietActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        Log.d(TAG,"Save");
        final String un=userProfile.getUsrname();
        final int idrv=brv.getID();


        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://52.148.113.133/android/savereview.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject jsonObject=new JSONObject(response);



                    String success=jsonObject.getString("success");

                    if(success.equals("1")){

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        progressDialog.dismiss();
                                        showSavedSuccess();
                                    }
                                }, 500);



                    } else
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed

                                        progressDialog.dismiss();
                                        showSavedFail();
                                    }
                                }, 3000);


                } catch (JSONException e)
                {
                    e.printStackTrace();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {

                                    progressDialog.dismiss();
                                    showSavedFail();
                                }
                            }, 3000);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {

                                        progressDialog.dismiss();
                                        showSavedFail();
                                    }
                                }, 3000);
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",un);
                params.put("idreview",String.valueOf(idrv));

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", "Bearer " + userProfile.getToken());
                return headers;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(ChiTietActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getBinhLuan() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest("http://52.148.113.133/android/getrating.php?id="+id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null)
                {
                    String userName;
                    String noidungReview;
                    double userRating;
                    String userAvatar;
                    String postDate;
                    String hinhanhtemp="";

                    String hinhanh=null;

                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            hinhanhtemp=hinhanh;
                            JSONObject jsonObject=response.getJSONObject(i);


                            userName=jsonObject.getString("hoten");

                            noidungReview=jsonObject.getString("noidung");


                            postDate=jsonObject.getString("ngaydang");
                            hinhanh="http://52.148.113.133/android/avatar/"+jsonObject.getString("ava");
                            userRating=jsonObject.getDouble("rating");
                            String img1="http://52.148.113.133/android/hinhanh/"+jsonObject.getString("hinhanh");

                            mangDanhGia.add(new userReview(userName,noidungReview,(float)userRating,hinhanh,postDate,img1));
                            //String a=Integer.toString(mangReview.size());
                            //Toast.makeText(HomeActivity.this,a,Toast.LENGTH_SHORT).show();
                            userReviewAdapter.notifyDataSetChanged();
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

    private void GetInformation() {

        String TieuDe="";
        String NoiDung="";
        String HinhAnh="";
         brv= (itemDiadiem) getIntent().getSerializableExtra("chitiet");
        TieuDe=brv.getTieude();
        NoiDung=brv.getNoidung();
        HinhAnh=brv.getHinhanh();
        id=brv.getID();
        tvTieude.setText(TieuDe);
        tvNoiDung.setText(NoiDung);
        Picasso.get().load(HinhAnh).into(imgviewChiTiet);
        rtb.setRating((brv.getRating()));
        tvDiachi.setText(brv.getDiachi());
        address=brv.getDiachi();
        userProfile= (com.example.loginregis.Model.userProfile) getIntent().getSerializableExtra("userprofile");

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void AnhXa() {
        toolbarChiTiet=findViewById(R.id.chitietToolbar);
        imgviewChiTiet=findViewById(R.id.chitietImage);
        tvTieude=findViewById(R.id.tieude);
        tvNoiDung=findViewById(R.id.chitietMota);
        rtb=findViewById(R.id.avgRating);
        tvDiachi=findViewById(R.id.diachi);
        chiduong=findViewById(R.id.chiduong);
        recyclerView=findViewById(R.id.RecycleViewDanhGia);
        vietdanhgia=findViewById(R.id.vietdanhgia);
        luu=findViewById(R.id.saveDiaDiem);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
        if(requestCode == 1) {


            if(resultCode == Activity.RESULT_OK) {
                mangDanhGia.clear();
                getBinhLuan();

            } else {

            }
        }
    }
}
