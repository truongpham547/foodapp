package com.example.loginregis.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregis.Activity.ChiTietActivity;
import com.example.loginregis.Model.itemDiadiem;
import com.example.loginregis.Model.userProfile;
import com.example.loginregis.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class diadiemsavedAdapter extends RecyclerView.Adapter<diadiemsavedAdapter.itemHolder> {
    public void setCheckFragment(int checkFragment) {
        this.checkFragment = checkFragment;
    }

    int checkFragment=0;
    Context context;
    ArrayList<itemDiadiem> arrayReview;
    public userProfile up;

    public diadiemsavedAdapter(Context context, ArrayList<itemDiadiem> arrayReview, userProfile up) {
        this.context = context;
        this.arrayReview = arrayReview;
        this.up = up;

    }

    public diadiemsavedAdapter(Context context, ArrayList<itemDiadiem> arrayReview) {
        this.context = context;
        this.arrayReview = arrayReview;
    }

    @NonNull
    @Override
    public itemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_item,null);
        itemHolder itemHolder=new itemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemHolder holder, int position) {
        itemDiadiem baiReview=arrayReview.get(position);
        String textNoidung = baiReview.getNoidung();
        if (baiReview.getNoidung().length() > 80) textNoidung = textNoidung.substring(0, 80) + "...";
        holder.NoiDung.setText(textNoidung);

        String textTieude = baiReview.getTieude();
        if(textTieude.length()>25)
        textTieude = textTieude.substring(0, 25) + "...";
        holder.TieuDe.setText(textTieude);
        Picasso.get().load(baiReview.getHinhanh()).into(holder.HinhReview);

    }

    @Override
    public int getItemCount() {
        return arrayReview.size();
    }

    public class itemHolder extends RecyclerView.ViewHolder{
        public ImageView HinhReview;
        public TextView TieuDe;
        public TextView NoiDung;
        public Button XoaBai;
        public itemHolder(@NonNull View itemView) {
            super(itemView);

            HinhReview=itemView.findViewById(R.id.hinhanhReview1);
            TieuDe=itemView.findViewById(R.id.tenbaiReview1);
            NoiDung=itemView.findViewById(R.id.motaReview1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, ChiTietActivity.class);
                   intent.putExtra("chitiet",arrayReview.get(getAdapterPosition()));

                   intent.putExtra("userprofile",up);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int delete_item=getAdapterPosition();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn có muốn xóa địa điểm này ?")
                            .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if(checkFragment==1)
                                    {
                                        LuuDiaDiem1(arrayReview.get(getAdapterPosition()).ID);
                                    }
                                    arrayReview.remove(delete_item);
                                    notifyItemRemoved(delete_item);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();


                    return true;

                }
            });

        }
    }
    private void LuuDiaDiem1(int id) {


        final String un=up.getUsrname();
        final int idrv=id;


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


                                    }
                                }, 500);



                    } else
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed



                                    }
                                }, 3000);


                } catch (JSONException e)
                {
                    e.printStackTrace();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {



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



                                    }
                                }, 3000);
                    }
                }
        )
        {
            @Override
            protected Map<String  , String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",un);
                params.put("idreview",String.valueOf(idrv));
                params.put("delete","1");

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    private void LuuDiaDiem2(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(context,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        final String un=up.getUsrname();
        final int idrv=id;


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

                                    }
                                }, 500);



                    } else
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed

                                        progressDialog.dismiss();

                                    }
                                }, 3000);


                } catch (JSONException e)
                {
                    e.printStackTrace();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {

                                    progressDialog.dismiss();

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
                params.put("delete","1");

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
