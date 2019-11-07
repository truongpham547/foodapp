package com.example.loginregis.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegActivity extends AppCompatActivity {
    private static final String TAG = "RegActivity";
    EditText editTextUser;
    EditText editTextPass;
    EditText editTextConfirmPass;
    TextView textViewLog;
    EditText editTextName;
    EditText editTextPhoneNumber;
    private EditText editBirthday;
    String[]temp;
    String myDateOfBirth;
    String hoten,ngaysinh,sdt,usrname,ava;

    private DatePickerDialog.OnDateSetListener birthdayListener;
    Button btnReg;
    String URL_REGIST="http://52.230.70.150/android/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        editTextName = findViewById(R.id.nameText);
        editTextUser= findViewById(R.id.userText);
        editTextPass= findViewById(R.id.passText);
        editTextPhoneNumber = findViewById(R.id.PhoneNumber);
        editTextConfirmPass= findViewById(R.id.passConfirm);
        textViewLog= findViewById(R.id.loginText);
        btnReg= findViewById(R.id.regBtn);
        textViewLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent=new Intent(RegActivity.this, LoginActivity.class);
                startActivity(logIntent);
            }
        });
        editBirthday = (EditText) findViewById(R.id.birthdayText);
        editBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        RegActivity.this,
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
                editBirthday.setText(date);
            }
        };
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate() )Regis();
            }
        });
    }
    private boolean validate(){
        boolean valid = true;
        String name = editTextName.getText().toString();
        String username = editTextUser.getText().toString().trim();
        String mobile = editTextPhoneNumber.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();
        String DateOfBirth = editBirthday.getText().toString();



        String reEnterPassword = editTextConfirmPass.getText().toString().trim();
        hoten=name;
        usrname=username;
        ngaysinh=myDateOfBirth;
        sdt=mobile;

        if(name.isEmpty()||name.length()<3 || name.length()>40)
        {
            editTextName.setError("Tên từ 3 đến 40 ký tự");
            valid = false;
        } else {
            editTextName.setError(null);
        }

        if(username.isEmpty() || username.length() < 3 || username.length() >40 )
        {
            editTextUser.setError("Từ 6 đến 40 ký tự");
            valid = false;
        } else {
            editTextUser.setError(null);
        }

        if(DateOfBirth.isEmpty())
        {
            editBirthday.setError("Vui lòng nhập ngày sinh");
            valid = false;
        }else
        {
            temp=DateOfBirth.split("/");
            myDateOfBirth=temp[2]+"/"+temp[1]+"/"+temp[0];
            editBirthday.setError(null);
        }

        if(mobile.isEmpty())
        {
            editTextPhoneNumber.setError("Nhập số điện thoại không hợp lệ ");
            valid = false;
        } else {
            editTextPhoneNumber.setError(null);
        }

        if(password.isEmpty() || password.length() <8 || password.length()>16 )
        {
            editTextPass.setError("Mật khẩu có 8 đến 16 ký tự");
            valid = false;
        } else {
            editTextPass.setError(null);
        }

        if(reEnterPassword.isEmpty() || reEnterPassword.length() < 8 || reEnterPassword.length()>16 || !reEnterPassword.equals(password) )
        {
            editTextConfirmPass.setError("Mật khẩu không khớp");
            valid = false;
        } else{
            editTextConfirmPass.setError(null);
        }

        return valid;
    }
    public void OnRegSuccess(){
        btnReg.setEnabled(true);
        btnReg.setClickable(true);
        finish();
    }
    public void OnRegFail(){
        btnReg.setEnabled(true);
        btnReg.setClickable(true);
    }
    public void showRegToastSuccess()
    {
        LayoutInflater flat = getLayoutInflater();
        View layout = flat.inflate(R.layout.success_reg_toast, (ViewGroup) findViewById(R.id.toast_type) );
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,300);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();;

    }

    public void showRegToastFail()
    {
        LayoutInflater flat = getLayoutInflater();
        View layout = flat.inflate(R.layout.fail_reg_toast, (ViewGroup) findViewById(R.id.toast_type) );
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,700);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }
    public void showRegToastExistUsername()
    {
        LayoutInflater flat = getLayoutInflater();
        View layout = flat.inflate(R.layout.exist_username, (ViewGroup) findViewById(R.id.toast_type) );
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,700);
        toast.setDuration(toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }
    private void Regis()
    {
        final ProgressDialog progressDialog = new ProgressDialog(RegActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        Log.d(TAG,"Register");
        final String username=editTextUser.getText().toString().trim();
        final String password=editTextPass.getText().toString().trim();
        final String confirmpass=editTextConfirmPass.toString().trim();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    btnReg.setClickable(false);
                    btnReg.setEnabled(false);
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");
                    if(success.equals("1")){
                        Toast.makeText(RegActivity.this,"Đăng Ký Thành Công",Toast.LENGTH_SHORT).show();
                        Intent homeIntent=new Intent(RegActivity.this, HomeActivity.class);
                        userProfile userProfile=new userProfile(hoten,ngaysinh,sdt,ava,usrname);
                        homeIntent.putExtra("userprofile",userProfile);
                        startActivity(homeIntent);
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        OnRegSuccess();
                                        progressDialog.dismiss();
                                        showRegToastSuccess();
                                    }
                                }, 500);

                    } else if (success.equals("-1")) {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        OnRegFail();
                                        progressDialog.dismiss();
                                        showRegToastExistUsername();
                                    }
                                }, 500);
                    }
                    else {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        OnRegFail();
                                        progressDialog.dismiss();
                                        showRegToastFail();
                                    }
                                }, 500);
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    OnRegFail();
                                    progressDialog.dismiss();
                                    showRegToastFail();
                                }
                            }, 500);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        OnRegFail();
                                        progressDialog.dismiss();
                                        showRegToastFail();
                                    }
                                }, 500);
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                params.put("hoten",editTextName.getText().toString());
                params.put("ngaysinh",myDateOfBirth);
                params.put("sdt",editTextPhoneNumber.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(RegActivity.this);
        requestQueue.add(stringRequest);

    }
}