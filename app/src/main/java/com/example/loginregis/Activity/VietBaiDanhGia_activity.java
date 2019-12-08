package com.example.loginregis.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class VietBaiDanhGia_activity extends AppCompatActivity {
    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_DATA1 = "EXTRA_DATA1";
    private static final String TAG = "Post Review";
    Toolbar toolbardanhgia;
    Button dangBtn,dangAnh;
    Bitmap bitmap;
    ImageView anhDang;
    String imgPost=null;
    CircleImageView ava;
    TextView usrName;
    String hinhAnhResult="";
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
        dangAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(VietBaiDanhGia_activity.this);
                dialog.setContentView(R.layout.image_review);
                CircleImageView circleImageView2=dialog.findViewById(R.id.avatarhientai);

                Button anhcosan=dialog.findViewById(R.id.chonanh);
                Button chupanh=dialog.findViewById(R.id.chupanh);

                Button huy=dialog.findViewById(R.id.huycapnhatanh);
                anhcosan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent imgintent=new Intent();
                        imgintent.setType("image/*");
                        imgintent.setAction(Intent.ACTION_GET_CONTENT);
                        dialog.dismiss();
                        startActivityForResult(imgintent,1);

                    }
                });
                chupanh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent imgintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        dialog.dismiss();
                        startActivityForResult(imgintent,0);


                    }
                });
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    @Override
    public void onBackPressed() {

        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null)
        {
            Uri path=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                anhDang.setImageBitmap(bitmap);
                anhDang.setVisibility(View.VISIBLE);
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] imgbytes=byteArrayOutputStream.toByteArray();
                imgPost= Base64.encodeToString(imgbytes,Base64.DEFAULT);
                //Toast.makeText(this, imgPost, Toast.LENGTH_SHORT).show();


            }
            catch (IOException e){

                e.printStackTrace();
            }
        }
        else if (requestCode==0&&resultCode==RESULT_OK&&data!=null)
        {
            bitmap= (Bitmap) data.getExtras().get("data");
            anhDang.setImageBitmap(bitmap);
            anhDang.setVisibility(View.VISIBLE);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] imgbytes=byteArrayOutputStream.toByteArray();
            imgPost= Base64.encodeToString(imgbytes,Base64.DEFAULT);
            //Toast.makeText(this, imgPost, Toast.LENGTH_SHORT).show();

        }
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

        final String ImgPost=imgPost;
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://52.148.113.133/android/postrating.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject jsonObject=new JSONObject(response);


                    //Toast.makeText(VietBaiDanhGia_activity.this, "here", Toast.LENGTH_SHORT).show();
                    String success=jsonObject.getString("success");
                    ratingvalue=jsonObject.getDouble("value");
                    //Toast.makeText(VietBaiDanhGia_activity.this, success, Toast.LENGTH_SHORT).show();
                    setrating(ratingvalue);
                    if(success.equals("1")){

                        showToastSuccess(progressDialog);

                       hinhAnhResult="http://52.148.113.133/android/"+"hinhanh/"+userInfo.getUsrname()+brv.getID();
                        userReview ur=new userReview(userInfo.getHoten(),noidungdanhgia.getText().toString(),ratingBar.getRating(),userInfo.getAva(),"Vừa xong",hinhAnhResult);

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", "Bearer " + userInfo.getToken());
                return headers;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",un);
                params.put("idreview",String.valueOf(idrv));
                params.put("noidung",noidung);
                params.put("rating",String.valueOf(userrating));
                if(imgPost!=null&&imgPost!="")
                params.put("hinhanh",ImgPost);
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
        dangAnh=findViewById(R.id.themanhBinhLuan);
        anhDang=findViewById(R.id.anhBinhLuan);
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