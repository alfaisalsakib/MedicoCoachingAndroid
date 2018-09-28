package com.example.sakib.medicodemo5.model;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.example.sakib.medicodemo5.MainActivity;
import com.example.sakib.medicodemo5.R;

import org.json.JSONArray;
import org.json.JSONException;

import androidbangladesh.bengali.support.BengaliUnicodeString;

public class ProfileLogin extends AppCompatActivity {


    private EditText studentName;
    private EditText password;
    private EditText serialNumber;
    TextView txview;

    Boolean key;

    String decide;

    static final String GET_URL = "http://softgulf-tech.com/Medico_App/medico_getStudentData.php";
    RequestQueue requestQueue;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                {
                    Intent intent = new Intent(ProfileLogin.this,MainActivity.class);
                    startActivity(intent);
                    ProfileLogin.this.finish();
                    return true;
                }
                case R.id.navigation_Login:
                {
                    //Toast.makeText(ProfileLogin.this,studentName.getText() ,Toast.LENGTH_LONG).show();
                    //Toast.makeText(ProfileLogin.this,"Student Profile will available soon",Toast.LENGTH_LONG).show();
                    getDataFromServer();
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_login);

        studentName = findViewById(R.id.editTextUserName);
        password = findViewById(R.id.editTextPassword);
        serialNumber = findViewById(R.id.editTextSerialNumber);
        txview = findViewById(R.id.txtview);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        requestQueue = Volley.newRequestQueue(ProfileLogin.this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            decide = extras.getString("key");
        }
    }

    private void getDataFromServer() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                GET_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                int length = jsonArray.length();

                for(int i = 0; i<length; i++){
                    try {
                        String SL_No = jsonArray.getJSONObject(i).getString("Serial_Number");
                        String sName = jsonArray.getJSONObject(i).getString("Student_Name");
                        String sPassword = jsonArray.getJSONObject(i).getString("Password");
                        String sPhone = jsonArray.getJSONObject(i).getString("Phone_No");

                        if(SL_No.equals(serialNumber.getText().toString()) && sName.equals(studentName.getText().toString()) && sPassword.equals(password.getText().toString()))
                        {
                            if(decide.equals("OnlineExam"))
                            {
                                Intent intent = new Intent(ProfileLogin.this, QuestionItem.class);
                                intent.putExtra("SL_No",SL_No);
                                intent.putExtra("Name",sName);
                                startActivity(intent);
                                ProfileLogin.this.finish();
                                key = true;
                                break;
                            }
                            else if(decide.equals("ExamRecord"))
                            {
                                Toast.makeText(ProfileLogin.this,"Exam Record",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ProfileLogin.this, ExamRecord.class);
                                intent.putExtra("SL_No",SL_No);
                                intent.putExtra("Name",sName);
                                startActivity(intent);
                                ProfileLogin.this.finish();
                                key = true;
                                break;
                            }
                            else if(decide.equals("ProfileLogin"))
                            {
                                String show = "Name : " + sName + "\nSerial Number : " + SL_No + "\nPhone Number : " + sPhone;
                                txview.setText(sPhone);
                                showAwesomeDialog("Student Profile",show);
                                key = true;
                                break;
                            }

                        }
                        else
                        {
                            key = false;
                        }
                    } catch (JSONException e) {
                        Log.e("Error", e.toString());
                    }
                }

                if(key == false)
                    Toast.makeText(ProfileLogin.this,"Profile not found (invalid Entry)" ,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileLogin.this,error.toString() ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void showAwesomeDialog(final String tag, String description) {
        new AwesomeInfoDialog(this)
                .setTitle(tag)
                .setMessage(description)
                .setColoredCircle(R.color.colorPrimary)
                .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                .setCancelable(true)
                .setPositiveButtonText("OK")
                .setPositiveButtonbackgroundColor(R.color.colorPrimary)
                .setPositiveButtonTextColor(R.color.white)
                /*.setNegativeButtonText(getString(R.string.dialog_no_button))
                .setNegativeButtonbackgroundColor(R.color.dialogInfoBackgroundColor)
                .setNegativeButtonTextColor(R.color.white)*/
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        if(tag=="FAQ"){
                            //Intent intent = new Intent(MainActivity.this, BrandActivity.class);
                            //startActivity(intent);
                            //Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNeutralButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                    }
                })
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                    }
                })
                .show();
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if ((KeyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent intent = new Intent(ProfileLogin.this, MainActivity.class);
            startActivity(intent);
            ProfileLogin.this.finish();
            return true;
        }
        return super.onKeyDown(KeyCode, event);
    }

}
