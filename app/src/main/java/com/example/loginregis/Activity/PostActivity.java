package com.example.loginregis.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.loginregis.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostActivity extends AppCompatActivity {

    Toolbar toolbarpost;
    Button dangBtn;
    CircleImageView ava;
    TextView usrName;
    ImageView imgDexuat;
    TextInputEditText tendiadiem;
    TextInputEditText diachi;
    TextInputEditText mota;
    Button themAnh;
    Bitmap bitmap;

    userProfile userInfo=new userProfile();
     String imgPost="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Anhxa();
        ActionToolBar();
        dangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten,direct,motachitiet;
                ten=tendiadiem.getText().toString();
               direct=  diachi.getText().toString();
               motachitiet=mota.getText().toString();
                boolean valid=true;

                if(ten.isEmpty())
                {
                    tendiadiem.setError("Nhập tên địa điểm");
                    valid = false;
                } else {
                    tendiadiem.setError(null);
                }



                if(direct.isEmpty())
                {
                    diachi.setError("Nhập địa chỉ của địa điểm");
                    valid = false;
                }else
                {
                   diachi.setError(null);
                }

                if(motachitiet.isEmpty())
                {
                    mota.setError("Nhập mô tả chi tiết ");
                    valid = false;
                } else {
                    mota.setError(null);
                }
                if(imgPost=="")
                {
                    Toast.makeText(PostActivity.this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
                    valid=false;
                }
                if(valid)
                {
                    StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://52.148.113.133/android/postreview.php", new Response.Listener<String>() {


                        @Override
                        public void onResponse(String response) {
                            try{

                                JSONObject jsonObject=new JSONObject(response);
                                String success=jsonObject.getString("success");
                                if(success.equals("1")){
                                    Toast.makeText(PostActivity.this,"Đăng thành công",Toast.LENGTH_SHORT).show();

                                }

                                else
                                    Toast.makeText(PostActivity.this,"Đăngt thất bại",Toast.LENGTH_SHORT).show();

                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                                Toast.makeText(PostActivity.this,"Đăng thất bại "+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(PostActivity.this,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                                }
                            }
                    )
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<>();
                            params.put("username",userInfo.getUsrname());
                            params.put("tieude",ten);

                            params.put("diachi",direct);



                            params.put("noidung",motachitiet);
                            params.put("hinhanh",imgPost);
                            return params;
                        }
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();

                            headers.put("Content-Type", "application/x-www-form-urlencoded");
                            headers.put("Authorization", "Bearer " + userInfo.getToken());
                            return headers;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(PostActivity.this);
                    requestQueue.add(stringRequest);


                }



            }
        });
        themAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(PostActivity.this);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null)
        {
            Uri path=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imgDexuat.setImageBitmap(bitmap);
               imgDexuat.setVisibility(View.VISIBLE);
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] imgbytes=byteArrayOutputStream.toByteArray();
                imgPost= Base64.encodeToString(imgbytes,Base64.DEFAULT);


            }
            catch (IOException e){

                e.printStackTrace();
            }
        }
        else if (requestCode==0&&resultCode==RESULT_OK&&data!=null)
        {
            bitmap= (Bitmap) data.getExtras().get("data");
            imgDexuat.setImageBitmap(bitmap);
           imgDexuat.setVisibility(View.VISIBLE);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] imgbytes=byteArrayOutputStream.toByteArray();
            imgPost= Base64.encodeToString(imgbytes,Base64.DEFAULT);


        }
    }

    private void Anhxa() {
        toolbarpost=findViewById(R.id.toolbarDexuat);
        dangBtn=findViewById(R.id.btnDeXuat);
        ava=findViewById(R.id.dexuatAva);
        usrName=findViewById(R.id.dexuatUser);
        tendiadiem=findViewById(R.id.tendiadiem);
        diachi=findViewById(R.id.diachidexuat);
        mota=findViewById(R.id.motachitiet);
        themAnh=findViewById(R.id.dexuatThemAnh);
        imgDexuat=findViewById(R.id.dexuatImage);
        userInfo= (userProfile) getIntent().getSerializableExtra("userprofile");
        usrName.setText(userInfo.getHoten());
        Picasso.get().load(userInfo.getAva()).placeholder(R.drawable.useravatar).error(R.drawable.useravatar).into(ava);
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarpost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarpost.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

    }
}
