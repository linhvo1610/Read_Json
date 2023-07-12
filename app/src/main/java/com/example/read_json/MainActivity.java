package com.example.read_json;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button btn_readjson;
    private ProgressBar mprogressbar;
    private String url="http://api.open-notify.org/astros.json";
    private TextView tv_testnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_readjson = findViewById(R.id.btn_readjson);
        mprogressbar = findViewById(R.id.mprogressbar);
        tv_testnumber =findViewById(R.id.tv_testnumber);
        btn_readjson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    mprogressbar.setVisibility(View.VISIBLE);
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest objectRequest=new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{

                                int number = response.getInt("number");
                                String message = response.getString("message");
                                JSONArray people = response.getJSONArray("people");
                                for (int i = 0; i < people.length(); i++) {
                                    JSONObject name = people.getJSONObject(i);

                                    String name1 = name.getString("name");
                                    tv_testnumber.setText(name1);
                                }

                                Toast.makeText(getApplicationContext(),people+"",Toast.LENGTH_SHORT).show();
                                mprogressbar.setVisibility(View.INVISIBLE);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                            Log.e("error",error.toString());
                        }
                    });
                    requestQueue.add(objectRequest);


            }
        });
    }
}