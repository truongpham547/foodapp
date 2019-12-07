package com.example.loginregis.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregis.Model.userProfile;
import com.example.loginregis.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText editTextUser;
    EditText editTextPass;
    TextView textViewReg;
    Button btnLogin;
    String token;
    String URL_REGIST="http://52.148.113.133/android/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUser= findViewById(R.id.userText);
        editTextPass= findViewById(R.id.passText);
        textViewReg= findViewById(R.id.regText);
        btnLogin= findViewById(R.id.loginBtn);
        textViewReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent=new Intent(LoginActivity.this, RegActivity.class);
                startActivity(regIntent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { if(validate())
                Login();
            }
        });
    }
    public boolean validate(){
        boolean valid = true;
        String username = editTextUser.getText().toString();
        String password = editTextPass.getText().toString();
        if(username.isEmpty()){
            editTextUser.setError("Vui lòng nhập tên đăng nhập");
            valid = false;
        } else if(username.length()<3  || username.length() > 40){
            editTextUser.setError("Tên đăng nhập có 3 - 40 ký tự");
            valid = false;
        } else {
            editTextUser.setError(null);
        }

        if(password.isEmpty()){
            editTextPass.setError("Vui lòng nhập mật khẩu");
            valid = false;
        } else if(password.length()<8 || password.length() >40 ) {
            editTextPass.setError("mật khẩu từ 8 đến 40 ký tự");
            valid = false;
        }else {
            editTextPass.setError(null);
        }
        return valid;
    }
    public void showToastLoginSuccess(final ProgressDialog dialog)
    {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        dialog.dismiss();
                        //Show Toast
                        LayoutInflater flat = getLayoutInflater();
                        View layout = flat.inflate(R.layout.success_toast, (ViewGroup) findViewById(R.id.toast_type) );
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM,0,200);
                        toast.setDuration(toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();;
                    }
                }, 500);


    }

    public void showToastLoginFail(final ProgressDialog dialog)
    {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginFailed

                        onLoginFailed();
                        dialog.dismiss();
                        // show toast
                        LayoutInflater flat = getLayoutInflater();
                        View layout = flat.inflate(R.layout.fail_toast, (ViewGroup) findViewById(R.id.toast_type) );
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM,0,200);
                        toast.setDuration(toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                    }
                }, 3000);


    }
    public void showToastWrongInfo(final ProgressDialog dialog)
    {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginFailed

                        onLoginFailed();
                        dialog.dismiss();
                        // show toast
                        LayoutInflater flat = getLayoutInflater();
                        View layout = flat.inflate(R.layout.wrong_info, (ViewGroup) findViewById(R.id.toast_type) );
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM,0,200);
                        toast.setDuration(toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                    }
                }, 3000);

    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);
        btnLogin.setClickable(true);
        finish();
    }
    public void onLoginFailed() {

        btnLogin.setEnabled(true);
        btnLogin.setClickable(true);
    }
    private void Login()
    {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        Log.d(TAG,"Login");
        final String username=editTextUser.getText().toString().trim();
        final String password=editTextPass.getText().toString().trim();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    btnLogin.setEnabled(false);
                    btnLogin.setClickable(false);
                    JSONObject jsonObject=new JSONObject(response);

                    String success=jsonObject.getString("success");
                    if(success.equals("1")){
                        JSONArray infoArray=jsonObject.getJSONArray("info");
                        JSONObject info=infoArray.getJSONObject(0);
                        token=jsonObject.getString("token");
                        Intent homeIntent=new Intent(LoginActivity.this, HomeActivity.class);
                        userProfile userProfile=new userProfile(info.getString("hoten"),info.getString("ngaysinh"),info.getString("sdt"),
                                info.getString("ava"),info.getString("username"),token);
                        //Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                        homeIntent.putExtra("userprofile",userProfile);
//                        homeIntent.putExtra("hoten",info.getString("hoten"));
//                        homeIntent.putExtra("ngaysinh",info.getString("ngaysinh"));
//                        homeIntent.putExtra("sdt",info.getString("sdt"));
//                        homeIntent.putExtra("ava",info.getString("ava"));
//                        homeIntent.putExtra("username",info.getString("username"));




                        showToastLoginSuccess(progressDialog);

                        startActivity(homeIntent);

                    } else if(success.equals("0"))
                    {
                        showToastWrongInfo(progressDialog);
                    } else{
                        showToastLoginFail(progressDialog);
                    }
                } catch (JSONException e)
                {
                    showToastLoginFail(progressDialog);
                    e.printStackTrace();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToastLoginFail(progressDialog);
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);

    }
}
