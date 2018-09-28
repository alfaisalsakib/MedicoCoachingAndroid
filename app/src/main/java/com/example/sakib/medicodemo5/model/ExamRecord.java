package com.example.sakib.medicodemo5.model;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sakib.medicodemo5.MainActivity;
import com.example.sakib.medicodemo5.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ExamRecord extends AppCompatActivity {

    String name;
    String sl_No;
    String date;

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    static final String GET_URL = "http://softgulf-tech.com/Medico_App/medico_getExamData.php";
    RequestQueue requestQueue;

    String Name;
    String[] Date;
    String[] Subject;
    String[] Marks;

    TextView N;
    TextView s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_record);

        N = findViewById(R.id.name);
        s = findViewById(R.id.slNo);

        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(ExamRecord.this, android.R.layout.simple_list_item_1,
                arrayList);
        listView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(ExamRecord.this);

        try
        {
            Bundle extras = getIntent().getExtras();
            if(extras !=null)
            {
                name = extras.getString("Name");
                sl_No = extras.getString("SL_No");

                N.setText("Name : " + name);
                s.setText("SL No : " + sl_No);
            }
        }
        catch (Exception e)
        {

        }
        getDataFromServer();

        //Toast.makeText(ExamRecord.this,"here " + name + " , " + SL_No,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if ((KeyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent intent = new Intent(ExamRecord.this, MainActivity.class);
            startActivity(intent);
            ExamRecord.this.finish();

            return true;
        }
        return super.onKeyDown(KeyCode, event);
    }

    private void getDataFromServer() {

        HashMap<String, String> mRequestParams = new HashMap<String, String>();
        mRequestParams.put("name",name);
        mRequestParams.put("sl_No", sl_No);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,
                GET_URL, new JSONObject(mRequestParams) ,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                int length = jsonArray.length();
                for(int i = 0; i<length; i++){
                    try {
                        //String Serial_Number = jsonArray.getJSONObject(i).getString("SL_No");
                        String Subject = jsonArray.getJSONObject(i).getString("Subject");
                        //String Name = jsonArray.getJSONObject(i).getString("Name");
                        String Date = jsonArray.getJSONObject(i).getString("Date");
                        String Marks = jsonArray.getJSONObject(i).getString("Marks");

                        //Toast.makeText(ExamRecord.this,Name,Toast.LENGTH_LONG).show();

                        arrayList.add("Exam : "+Subject+"\n"+"Date  : " + Date+"\n"+"Marks : "+ Marks);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.i("Error", e.toString());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ExamRecord.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}
