package com.example.loginregis.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregis.Activity.HomeActivity;
import com.example.loginregis.Activity.LoginActivity;
import com.example.loginregis.Activity.RegActivity;
import com.example.loginregis.Model.userProfile;
import com.example.loginregis.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;

public class fragmentUser extends Fragment {
    public userProfile up;
    private  CircleImageView circleImageView;




    public fragmentUser(userProfile up) {
        this.up = up;
    }
    private  Bitmap bitmap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_2,container,false);
        circleImageView=rootView.findViewById(R.id.avaFragment);
        TextView usrname=rootView.findViewById(R.id.username_userFragment);
        Picasso.get().load(up.getAva()).placeholder(R.drawable.useravatar).error(R.drawable.useravatar).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(circleImageView);
        usrname.setText(up.getHoten());
        final Dialog dialog;


        ///////////////////////////
        ///////////////////////////
        ///////////////////////////
        ///////////LOG OUT/////////
        ///////////////////////////
        ///////////////////////////
        ///////////////////////////

        TextView Dangxuat=rootView.findViewById(R.id.dangxuat);
        Dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });



        ///////////////////////////
        ///////////////////////////
        ///////////////////////////
        //HIEN THI THONG TIN USER//
        ///////////////////////////
        ///////////////////////////
        ///////////////////////////


        TextView viewInfo = rootView.findViewById(R.id.thongtinnguoidung);
        viewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext(),android.R.style.Theme_Material_Light_Dialog_MinWidth);
                dialog.setContentView(R.layout.view_info);

                final TextView viewName=dialog.findViewById(R.id.view_name);
                final TextView viewDate=dialog.findViewById(R.id.view_date);
                final TextView viewPhone=dialog.findViewById(R.id.view_phone);

                viewName.setText(up.getHoten());
                viewDate.setText(up.getNgaysinh());
                viewPhone.setText(up.getSdt());

                CircleImageView circleImageView2=dialog.findViewById(R.id.view_avatar);
                Picasso.get().load(up.getAva()).placeholder(R.drawable.useravatar).error(R.drawable.useravatar).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(circleImageView2);

                Button OK =dialog.findViewById(R.id.OK);
                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        ///////////////////////////
        ///////////////////////////
        ///////////////////////////
        //THAY ĐỔI PASSWORD CHO USER
        ///////////////////////////
        ///////////////////////////
        ///////////////////////////
        TextView changePass=rootView.findViewById(R.id.doimatkhau);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Material_Light_Dialog_MinWidth);
                dialog.setContentView(R.layout.password_dialog);


                Button thaydoi=dialog.findViewById(R.id.changePass);
                Button huy=dialog.findViewById(R.id.cancelPassChange);

                thaydoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText EToldpass=dialog.findViewById(R.id.passhientai);
                        final EditText ETnewpass=dialog.findViewById(R.id.passmoi);
                        final EditText ETconfirmnewpass=dialog.findViewById(R.id.passmoi2);
                        final String oldpass=EToldpass.getText().toString();
                        final String newpass=ETnewpass.getText().toString();
                        final String confirmpass=ETconfirmnewpass.getText().toString();
                        boolean valid=true;
                        boolean temp=false;
                        if(newpass.isEmpty() || newpass.length() <8 || newpass.length()>16 )
                        {
                            ETnewpass.setError("Mật khẩu có 8 đến 16 ký tự");
                            valid = false;
                        }
                        else if(newpass.equals(oldpass) )
                        {
                            ETnewpass.setError("Mật khẩu mới không được trùng mật khẩu cũ");
                            valid = false;
                        } else {
                            ETnewpass.setError(null);
                        }

                        if(confirmpass.isEmpty() ||  !confirmpass.equals(newpass) )
                        {
                            ETconfirmnewpass.setError("Mật khẩu không khớp");
                            valid = false;
                        } else{
                            ETconfirmnewpass.setError(null);
                        }
                        if(valid)
                        {
                            StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://52.230.70.150/android/updateprofile.php", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{

                                        JSONObject jsonObject=new JSONObject(response);
                                        String success=jsonObject.getString("success");
                                        if(success.equals("1")){
                                            Toast.makeText(getContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();

                                        } else if (success.equals("-1")){
                                            EToldpass.setError("Mật khẩu hiện tại không đúng");

                                        }
                                        else
                                            Toast.makeText(getContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();

                                    } catch (JSONException e)
                                    {
                                        e.printStackTrace();;
                                        Toast.makeText(getContext(),"Cập nhật thất bại "+e.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            )
                            {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params=new HashMap<>();
                                    params.put("username",up.getUsrname());
                                    params.put("oldpass",oldpass);

                                    params.put("newpass",newpass);
                                    return params;
                                }
                            };
                            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
                            requestQueue.add(stringRequest);


                        }

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
        ///////////////////////////
        ///////////////////////////
        ///////////////////////////
        //THAY ĐỔI THÔNG TIN CHO USER
        ///////////////////////////
        ///////////////////////////
        ///////////////////////////

        TextView changeInfo=rootView.findViewById(R.id.thaydoithongtin);
        changeInfo.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener birthdayListener;
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Material_Light_Dialog_MinWidth);
                dialog.setContentView(R.layout.info_dialog);


                Button thaydoi=dialog.findViewById(R.id.changeInfo);
                Button huy=dialog.findViewById(R.id.cancelInfoChange);
                final EditText ETname=dialog.findViewById(R.id.nameChange);
                ETname.setText(up.getHoten());

                final EditText ETdate=dialog.findViewById(R.id.dateChange);
                ETdate.setText(up.getNgaysinh());
                ETdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog dialog = new DatePickerDialog(
                                getContext(),
                                android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,birthdayListener,
                                year,month,day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });
                birthdayListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;

                        Log.d(TAG,"onDataset: dd/mm/yyyy"+day+"/"+month+"/"+year);
                        String date = day +"/"+month+"/"+year;
                        ETdate.setText(date);
                    }
                };



                final EditText ETphone=dialog.findViewById(R.id.phoneChange);
                ETphone.setText(up.getSdt());

                thaydoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String name=ETname.getText().toString();
                        final String date=ETdate.getText().toString();
                        final String phone=ETphone.getText().toString();
                        boolean valid=true;
                        boolean temp=false;
                        if(name.isEmpty()||name.length()<3 || name.length()>40)
                        {
                            ETname.setError("Tên từ 3 đến 40 ký tự");
                            valid = false;
                        } else {
                            ETname.setError(null);
                        }



                        if(date.isEmpty())
                        {
                            ETdate.setError("Vui lòng nhập ngày sinh");
                            valid = false;
                        }else
                        {
                            ETdate.setError(null);
                        }

                        if(phone.isEmpty())
                        {
                            ETphone.setError("Nhập số điện thoại không hợp lệ ");
                            valid = false;
                        } else {
                            ETphone.setError(null);
                        }
                        if(valid)
                        {
                            StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://52.230.70.150/android/updateprofile.php", new Response.Listener<String>() {


                                @Override
                                public void onResponse(String response) {
                                    try{

                                        JSONObject jsonObject=new JSONObject(response);
                                        String success=jsonObject.getString("success");
                                        if(success.equals("1")){
                                            Toast.makeText(getContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();

                                        }


                                        else
                                            Toast.makeText(getContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();

                                    } catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                        Toast.makeText(getContext(),"Cập nhật thất bại "+e.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            )
                            {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params=new HashMap<>();
                                    params.put("username",up.getUsrname());
                                    params.put("hoten",name);
                                    up.setHoten(name);
                                    params.put("sdt",phone);
                                    up.setSdt(phone);
                                    String[] temp=date.split("/");
                                    String datestring=temp[2]+"/"+temp[1]+"/"+temp[0];

                                    params.put("ngaysinh",datestring);
                                    up.setNgaysinh(date);
                                    return params;
                                }
                            };
                            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
                            requestQueue.add(stringRequest);


                        }

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
        ///////////////////////////
        ///////////////////////////
        ///////////////////////////
        //THAY ĐỔI AVATAR//////////
        ///////////////////////////
        ///////////////////////////
        ///////////////////////////
        TextView changeImage=rootView.findViewById(R.id.capnhatanhdaidien);
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Material_Light_Dialog_MinWidth);
                dialog.setContentView(R.layout.image_dialog);
                Button anhcosan=dialog.findViewById(R.id.chonanh);
                Button chupanh=dialog.findViewById(R.id.chupanh);
                CircleImageView circleImageView1=dialog.findViewById(R.id.avatarhientai);
                Picasso.get().load(up.getAva()).placeholder(R.drawable.useravatar).error(R.drawable.useravatar).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(circleImageView1);
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

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null)
        {
            Uri path=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
                circleImageView.setImageBitmap(bitmap);
                circleImageView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] imgbytes=byteArrayOutputStream.toByteArray();
                final String imgPost= Base64.encodeToString(imgbytes,Base64.DEFAULT);
                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://52.230.70.150/android/updateprofile.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("success");
                            if(success.equals("1")){

                                Toast.makeText(getContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();


                            }


                            else
                                Toast.makeText(getContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();

                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getContext(),"Cập nhật thất bại "+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<>();
                        param.put("username",up.getUsrname());
                        up.setAva("http://52.230.70.150/android/avatar/"+up.getUsrname()+".jpg");
                        param.put("ava",imgPost);
                        return param;
                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);

            }
            catch (IOException e){

                e.printStackTrace();
            }
        }
        else if (requestCode==0&&resultCode==RESULT_OK&&data!=null)
        {
            bitmap= (Bitmap) data.getExtras().get("data");
            circleImageView.setImageBitmap(bitmap);
            circleImageView.setVisibility(View.VISIBLE);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] imgbytes=byteArrayOutputStream.toByteArray();
            final String imgPost= Base64.encodeToString(imgbytes,Base64.DEFAULT);
            StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://52.230.70.150/android/updateprofile.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{

                        JSONObject jsonObject=new JSONObject(response);
                        String success=jsonObject.getString("success");
                        if(success.equals("1")){
                            Toast.makeText(getContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();


                        }


                        else
                            Toast.makeText(getContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getContext(),"Cập nhật thất bại "+e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param=new HashMap<>();
                    param.put("username",up.getUsrname());
                    up.setAva("http://52.230.70.150/android/avatar/"+up.getUsrname()+".jpg");
                    param.put("ava",imgPost);
                    return param;
                }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

        }
    }

    private boolean checkPass(String a, String b, String c) {
        boolean valid = true;
        return false;
    }


    private void showDialog() {


    }
}