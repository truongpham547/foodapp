package com.example.loginregis.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregis.Model.itemDiadiem;
import com.example.loginregis.Model.userProfile;
import com.example.loginregis.Model.userReview;
import com.example.loginregis.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class VietBaiDanhGia_activity extends AppCompatActivity {
    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_DATA1 = "EXTRA_DATA1";
    private static final String TAG = "Post Review";
    Toolbar toolbardanhgia;
    Button dangBtn;
    CircleImageView ava;
    TextView usrName;
    RatingBar ratingBar;
    TextInputEditText noidungdanhgia;
    itemDiadiem brv=new itemDiadiem();
    userProfile userInfo=new userProfile();
    double ratingvalue;
    float result_rating;
    int IDdiadiem;
    final Intent data = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viet_bai_danh_gia_activity);
        toolbardanhgia = findViewById(R.id.diadiemdanhgia);
        ActionToolBar();
        AnhXa();


        dangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangBai();




                setResult(Activity.RESULT_OK,data);


            }

        });
    }
    @Override
    public void onBackPressed() {

        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    public void showPostSuccess()
    {
        LayoutInflater flat = getLayoutInflater();
        View layout = flat.inflate(R.layout.post_success, (ViewGroup) findViewById(R.id.toast_type) );
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,200);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void showPostFail()
    {
        LayoutInflater flat = getLayoutInflater();
        View layout = flat.inflate(R.layout.post_fail, (ViewGroup) findViewById(R.id.toast_type) );
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,200);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }
    public void OnPostSuccess(){
        dangBtn.setEnabled(true);
        dangBtn.setClickable(true);
        finish();
    }
    public void showToastFail(final ProgressDialog dialog)
    {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onPostFail
                    OnPostFail();
                      dialog.dismiss();
                      showPostFail();
                    }
                }, 500);
    }
    public void showToastSuccess(final ProgressDialog dialog)
    {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onPostSuccess
                        OnPostSuccess();
//                     dialog.dismiss();
                    showPostSuccess();
                    }
                }, 500);
    }
    public void OnPostFail(){
        dangBtn.setEnabled(true);
        dangBtn.setClickable(true);
    }

    private void DangBai() {
        final ProgressDialog progressDialog = new ProgressDialog(VietBaiDanhGia_activity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        Log.d(TAG,"Register");
        final String un=userInfo.getUsrname();
        final int idrv=brv.getID();
        final float userrating=ratingBar.getRating();
        final String noidung=noidungdanhgia.getText().toString();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://52.148.113.133/android/postrating.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject jsonObject=new JSONObject(response);



                    String success=jsonObject.getString("success");
                    ratingvalue=jsonObject.getDouble("value");
                    setrating(ratingvalue);
                    if(success.equals("1")){

                        showToastSuccess(progressDialog);
                        userReview ur=new userReview(userInfo.getHoten(),noidungdanhgia.getText().toString(),ratingBar.getRating(),userInfo.getAva(),"Vừa xong");

                        data.putExtra(EXTRA_DATA,ur);

                        data.putExtra(EXTRA_DATA1,(float)ratingvalue);

                        //Toast.makeText(VietBaiDanhGia_activity.this,"rating"+ratingvalue,Toast.LENGTH_SHORT).show();
                        finish();

                    } else
                    {  showToastFail(progressDialog);finish();}


                } catch (JSONException e)
                {
                    e.printStackTrace();
                    showToastFail(progressDialog);
                    finish();
                }
                //Toast.makeText(VietBaiDanhGia_activity.this,"rating"+ratingvalue,Toast.LENGTH_SHORT).show();


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToastFail(progressDialog);
                        finish();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",un);
                params.put("idreview",String.valueOf(idrv));
                params.put("noidung",noidung);
                params.put("rating",String.valueOf(userrating));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(VietBaiDanhGia_activity.this);
        requestQueue.add(stringRequest);
    }

    private void setrating(double ratingvalue) {
        result_rating= (float) ratingvalue;
    }


    private void AnhXa() {

        dangBtn=findViewById(R.id.dangbtn);
        ava=findViewById(R.id.danhgiaAva);
        usrName=findViewById(R.id.danhgiaUser);
        ratingBar=findViewById(R.id.danhgiaRating);
        noidungdanhgia=findViewById(R.id.danhgiaInput);
        brv= (itemDiadiem) getIntent().getSerializableExtra("diadiem");
        userInfo= (userProfile) getIntent().getSerializableExtra("userprofile");
        usrName.setText(userInfo.getHoten());
        Picasso.get().load(userInfo.getAva()).placeholder(R.drawable.useravatar).error(R.drawable.useravatar).into(ava);
        IDdiadiem=brv.getID();

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbardanhgia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardanhgia.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

    }
}