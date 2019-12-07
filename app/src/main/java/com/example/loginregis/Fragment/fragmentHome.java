package com.example.loginregis.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregis.Adapter.diadiemAdapter;
import com.example.loginregis.Model.itemDiadiem;
import com.example.loginregis.Model.userProfile;
import com.example.loginregis.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fragmentHome extends Fragment {
    public diadiemAdapter ra;
    public diadiemAdapter ra2;

    public fragmentHome(diadiemAdapter ra, int key,userProfile up) {
        this.ra = ra;
        this.key = key;
        this.up=up;
    }
    userProfile up;

    int key;

    public diadiemAdapter getReviewAdapter() {
        return ra;
    }

    public fragmentHome setReviewAdapter(diadiemAdapter reviewAdapter) {
        this.ra = reviewAdapter;
        return this;
    }

    public fragmentHome(diadiemAdapter ra) {
        this.ra = ra;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_home,container,false);
        final RecyclerView recyclerView=rootView.findViewById(R.id.homeRecyleView);
        final TextView Moinhat, Danghot, Gantoi;
        Moinhat=rootView.findViewById(R.id.moinhat);
        Danghot=rootView.findViewById(R.id.danghot);
        Gantoi=rootView.findViewById(R.id.gantoi);
        if(key==1)
        {
            Moinhat.setTypeface(null, Typeface.NORMAL);
            Danghot.setTypeface(null, Typeface.NORMAL);
            Gantoi.setTypeface(null, Typeface.NORMAL);

        }
        final ArrayList<itemDiadiem> mangReview;
        mangReview=new ArrayList<>();
        ra2=new diadiemAdapter(getContext(),mangReview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        recyclerView.setAdapter(ra);
        Moinhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mangReview.clear();


                recyclerView.setAdapter(ra2);
                Moinhat.setTypeface(null, Typeface.BOLD);
               Danghot.setTypeface(null, Typeface.NORMAL);
                Gantoi.setTypeface(null, Typeface.NORMAL);
                RequestQueue requestQueue= Volley.newRequestQueue(getContext());


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
                                    ra2.notifyDataSetChanged();
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
                        headers.put("Authorization", "Bearer " + up.getToken());
                        return headers;
                    }
                };

                requestQueue.add(jsonArrayRequest);


            }

        });
        //Dang hot
        Danghot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mangReview.clear();

                Moinhat.setTypeface(null, Typeface.NORMAL);
                Danghot.setTypeface(null, Typeface.BOLD);
                Gantoi.setTypeface(null, Typeface.NORMAL);
                RequestQueue requestQueue= Volley.newRequestQueue(getContext());
                recyclerView.setAdapter(ra2);

                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest("http://52.148.113.133/android/getreview.php?trending", new Response.Listener<JSONArray>() {

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
                            //Toast.makeText(getContext(), "here " +response.length(), Toast.LENGTH_SHORT).show();
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
                                    ra2.notifyDataSetChanged();
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
                        headers.put("Authorization", "Bearer " + up.getToken());
                        return headers;
                    }
                };

                requestQueue.add(jsonArrayRequest);


            }

        });
        Gantoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mangReview.clear();
                recyclerView.setAdapter(ra2);

                Moinhat.setTypeface(null, Typeface.NORMAL);
                Danghot.setTypeface(null, Typeface.NORMAL);
                Gantoi.setTypeface(null, Typeface.BOLD);

            }
        });
        return rootView;
    }
}
