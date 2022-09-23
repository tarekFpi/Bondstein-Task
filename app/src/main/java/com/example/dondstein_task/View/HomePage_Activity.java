package com.example.dondstein_task.View;

import static com.example.dondstein_task.ApiService.Base_Url.ROOT_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dondstein_task.ApiService.Base_Url;
import com.example.dondstein_task.Controller.StoresShowAdapter;
import com.example.dondstein_task.Model.Datum;
import com.example.dondstein_task.Model.Stores_showModel;
import com.example.dondstein_task.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage_Activity extends AppCompatActivity {

  //  private String stores_url =ROOT_URL+"api/stores?page=1";
    private String stores_url ="http://128.199.215.102:4040/api/stores?page=1";

    private RecyclerView recyclerView_stores;
    //http://128.199.215.102:4040

    private List<Stores_showModel> stores_showModelList=new ArrayList<>();

    private StoresShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView_stores=(RecyclerView) findViewById(R.id.recyclerView_storeShow);
        recyclerView_stores.setHasFixedSize(true);
        recyclerView_stores.setLayoutManager(new LinearLayoutManager(this));

        Show_Stores();
    }

    private void Show_Stores(){

        final ProgressDialog progressDialog = new ProgressDialog(HomePage_Activity.this);
         progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progress);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, stores_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObjectMain=new JSONObject(response);

                     JSONArray jsonArray= jsonObjectMain.getJSONArray("data");

                        for (int i = 0; i <jsonArray.length() ;i++) {

                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                         stores_showModelList.add(new Stores_showModel(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("address")));
                        }

                    adapter=new StoresShowAdapter(stores_showModelList,getApplicationContext());
                    recyclerView_stores.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                    adapter.setOnItemClick(new StoresShowAdapter.onItemClickLisiner() {
                        @Override
                        public void OnClick_Lisiner(int position) {

                            Stores_showModel item=stores_showModelList.get(position);

                            Intent intent=new Intent(HomePage_Activity.this,Single_AddressActivity.class);
                            intent.putExtra("address",item.getAddress());
                            intent.putExtra("name",item.getName());
                            startActivity(intent);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkError(error);
                progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(HomePage_Activity.this);
        requestQueue.add(stringRequest);
    }

    private void netWorkError(VolleyError error) {

        if (error instanceof NetworkError) {
            Toast.makeText(HomePage_Activity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(HomePage_Activity.this, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(HomePage_Activity.this, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(HomePage_Activity.this, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(HomePage_Activity.this, "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(HomePage_Activity.this, "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();
    }

}