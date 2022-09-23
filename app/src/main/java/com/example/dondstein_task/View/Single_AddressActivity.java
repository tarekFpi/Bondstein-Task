package com.example.dondstein_task.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dondstein_task.R;

public class Single_AddressActivity extends AppCompatActivity {

   private TextView textView_name,textView_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_address);

        textView_name=(TextView)findViewById(R.id.Single_storesName);

        textView_address=(TextView)findViewById(R.id.Single_storesDetails);

        getData();
    }

    private void  getData(){

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String name = intent.getStringExtra("name");

        textView_name.setText("Name:"+name);

        textView_address.setText("Address: "+address);
    }
}