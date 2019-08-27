package com.iti.foxmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp extends AppCompatActivity {
    Button button;
    TextView textView;
    Button previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textview);
        previous = findViewById(R.id.previous);


        previous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent myIntent = new Intent(getBaseContext(), OkHttp.class);
                startActivity(myIntent);

            }
        });

        // avoid creating several instances, should be singleon

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/trending/all/day?api_key=1a45f741aada87874aacfbeb73119bae")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        } else {
                            // do something wih the result

                            final String responseData = response.body().string();


                            // Run view-related code back on the main thread
                            OkHttp.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    textView.setText(responseData);
                                }
                            });
                            //-----------
                        }
                    }
                });
            }

        });
    }
    }