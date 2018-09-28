package com.example.sakib.medicodemo5.model;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.sakib.medicodemo5.MainActivity;
import com.example.sakib.medicodemo5.R;

import org.json.JSONArray;
import org.json.JSONException;

public class SplashScreen extends AppCompatActivity {

    public static final long DURATION = 1000;
    private ImageView imageView;

    Boolean key;

    ProgressBar progressBar;
    ProgressDialog progressDialog;
    private  int i=0;

    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


//        progressDialog = new ProgressDialog(SplashScreen.this);
//        progressDialog.setMessage("Progressing..");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setProgress(0);
//        progressDialog.setMax(100);
//        progressDialog.show();
        progressBar = findViewById(R.id.progressBar);
//
        thread = new Thread(){
            @Override
            public void run(){
                try{
                        while (i<100) {
                            sleep(80);
                            i++;

                            progressBar.setProgress(i);
                        }

                    }catch (Exception e){

                    }finally {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        SplashScreen.this.finish();
                    }

                }
        };
        thread.start();




//        imageView = findViewById(R.id.imageView);
//

//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(intent);
//                SplashScreen.this.finish();
//            }
//        }, DURATION);
//
//        YoYo.with(Techniques.Pulse).duration(1000).repeat(4).playOn(imageView);
//        getDataFromServer();
    }
}
