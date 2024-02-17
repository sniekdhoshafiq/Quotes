package com.sniekdho.jsonwithvolley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Quotes> quotesArrayList;
    private QuotesAdapter quotesAdapter;

    private Toolbar my_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        quotesArrayList = new ArrayList<>();


        initiateQuotes();
    }

    public void initiateQuotes()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading....");
        progressDialog.setMessage("Please Wait.");
        progressDialog.show();

        String url = "https://dummyjson.com/quotes";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("quotes");

                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject quotesObject = jsonArray.getJSONObject(i);
                                String quote = quotesObject.getString("quote");
                                String author = quotesObject.getString("author");

                                Quotes quotes = new Quotes(quote, author);
                                quotesArrayList.add(quotes);
                            }

                            quotesAdapter = new QuotesAdapter(MainActivity.this, quotesArrayList);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(quotesAdapter);

                        } catch (JSONException e)
                        {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                    }
                }
        );

        requestQueue.add(stringRequest);
    }

}