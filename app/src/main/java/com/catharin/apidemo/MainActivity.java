package com.catharin.apidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.andreilisun.swipedismissdialog.OnSwipeDismissListener;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDirection;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.ACTION_VIEW;

public class MainActivity extends AppCompatActivity {
    SwipeDismissDialog swipeDismissDialog;
    Button success;
    public static TextView title;
    public static ImageView image;
    String imageURL, success_url, title_api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View dialog = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_box, null);
        title = dialog.findViewById(R.id.title);
        image = dialog.findViewById(R.id.image);
        image.setImageBitmap(null);



                final String URL = "https://backend-test-zypher.herokuapp.com/testData";



                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("Rest Response", response.toString());
                                try {

                                    imageURL = response.getString("imageURL");
                                    title_api = response.getString("title");

                                   dialogBox dialogBox = com.catharin.apidemo.dialogBox.getInstance();
                                   dialogBox.showDialogBox(imageURL, title_api);



                                    success_url = response.getString("success_url");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Rest Response", error.toString());
                            }
                        }
                );

                requestQueue.add(objectRequest);




                swipeDismissDialog = new SwipeDismissDialog.Builder(MainActivity.this)
                        .setView(dialog)
                        .setOnSwipeDismissListener(new OnSwipeDismissListener() {
                            @Override
                            public void onSwipeDismiss(View view, SwipeDismissDirection direction) {
                                Toast.makeText(MainActivity.this, "Dismissed at "+direction, Toast.LENGTH_LONG).show();
                            }
                        })
                        .build()
                        .show();
                success = dialog.findViewById(R.id.success);
                success.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ACTION_VIEW);
                        i.setData(Uri.parse(success_url));
                        startActivity(i);
                        swipeDismissDialog.dismiss();
                    }
                });

            }


}
