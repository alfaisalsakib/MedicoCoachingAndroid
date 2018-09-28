package com.example.sakib.medicodemo5.model;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.example.sakib.medicodemo5.MainActivity;
import com.example.sakib.medicodemo5.R;
import com.example.sakib.medicodemo5.itemmodel.QuestionType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class QuestionItem extends AppCompatActivity {

    //Chronometer chronometer;
    TextView questionNo;
    TextView question;
    TextView time;
    TextView set;
    TextView subject;

    String Set;
    String Subject;
    String name;
    String SL_No;

    Button exmButton;

    BottomNavigationView navigation;

    CheckBox opA;
    CheckBox opB;
    CheckBox opC;
    CheckBox opD;

    int currentIndex=0;
    int totalQuestions = 3;
    double rightAnswer = 0;
    double wrongAnswer = 0;

    ArrayList<QuestionType> q;
    ArrayList<String> CurrectAnswer;
    ArrayList<String> SubmittedAnswer;

    String date;

    CountDownTimer cdt;

    static final String GET_URL = "http://softgulf-tech.com/Medico_App/medico_getQuestions.php";
    static final String POST_URL = "http://softgulf-tech.com/Medico_App/medico_insertMarks.php";
    RequestQueue requestQueue;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_back:
                {

                    try
                    {
                        questionNo.setText(q.get(currentIndex-1).getQuestionNo());
                        question.setText(q.get(currentIndex-1).getQuestion());
                        opA.setText(q.get(currentIndex-1).getOptionA());
                        opB.setText(q.get(currentIndex-1).getOptionB());
                        opC.setText(q.get(currentIndex-1).getOptionC());
                        opD.setText(q.get(currentIndex-1).getOptionD());

                        currentIndex -= 1;

                        if(q.get(currentIndex).getCheckedOption().equals("-"))
                        {
                            opA.setChecked(false);
                            opB.setChecked(false);
                            opC.setChecked(false);
                            opD.setChecked(false);
                        }
                        else
                        {
                            if(q.get(currentIndex).getCheckedOption().equals("A"))
                            {
                                opA.setChecked(true);
                                opB.setChecked(false);
                                opC.setChecked(false);
                                opD.setChecked(false);

                            }
                            else if(q.get(currentIndex).getCheckedOption().equals("B"))
                            {
                                opB.setChecked(true);

                                opA.setChecked(false);
                                opC.setChecked(false);
                                opD.setChecked(false);
                            }
                            else if(q.get(currentIndex).getCheckedOption().equals("C"))
                            {
                                opC.setChecked(true);

                                opA.setChecked(false);
                                opB.setChecked(false);
                                opD.setChecked(false);
                            }
                            else if(q.get(currentIndex).getCheckedOption().equals("D"))
                            {
                                opD.setChecked(true);

                                opA.setChecked(false);
                                opB.setChecked(false);
                                opC.setChecked(false);
                            }
                        }

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(QuestionItem.this,"No More Questions",Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
                case R.id.navigation_submit:
                {
                    try
                    {
                        cdt.cancel();
                        time.setText("Done");
                        //chronometer.stop();
                        int countEmpty = 0;
                        for(int i=0;i<totalQuestions;i++)
                        {
                            if(CurrectAnswer.get(i).equals(q.get(i).getCheckedOption()) )
                            {
                                rightAnswer++;
                            }
                            else if(q.get(i).getCheckedOption().equals("-"))
                            {
                                countEmpty++;
                            }
                            else
                            {
                                rightAnswer = rightAnswer - .25;
                            }
                        }

                        final AlertDialog.Builder dialog = new AlertDialog.Builder(QuestionItem.this);
                        dialog.setTitle("Medico Coaching").setMessage("Total Score : " + Double.toString(rightAnswer));
                        dialog.setCancelable(false);

                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                marksUpload();

                                dialog.cancel();
                                Intent intent = new Intent(QuestionItem.this, MainActivity.class);
                                startActivity(intent);
                                QuestionItem.this.finish();
                            }
                        });
                        dialog.show();

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(QuestionItem.this,"start exam",Toast.LENGTH_LONG).show();
                    }

                    return true;
                }

                case R.id.navigation_next:
                {

                    try
                    {
                        questionNo.setText(q.get(currentIndex+1).getQuestionNo());
                        question.setText(q.get(currentIndex+1).getQuestion());
                        opA.setText(q.get(currentIndex+1).getOptionA());
                        opB.setText(q.get(currentIndex+1).getOptionB());
                        opC.setText(q.get(currentIndex+1).getOptionC());
                        opD.setText(q.get(currentIndex+1).getOptionD());

                        currentIndex += 1;

                        if(q.get(currentIndex).getCheckedOption().equals("-"))
                        {
                            opA.setChecked(false);
                            opB.setChecked(false);
                            opC.setChecked(false);
                            opD.setChecked(false);
                        }
                        else
                        {
                            if(q.get(currentIndex).getCheckedOption().equals("A"))
                            {
                                opA.setChecked(true);

                                opB.setChecked(false);
                                opC.setChecked(false);
                                opD.setChecked(false);
                            }
                            else if(q.get(currentIndex).getCheckedOption().equals("B"))
                            {
                                opB.setChecked(true);

                                opA.setChecked(false);
                                opC.setChecked(false);
                                opD.setChecked(false);
                            }
                            else if(q.get(currentIndex).getCheckedOption().equals("C"))
                            {
                                opC.setChecked(true);

                                opA.setChecked(false);
                                opB.setChecked(false);
                                opD.setChecked(false);
                            }
                            else if(q.get(currentIndex).getCheckedOption().equals("D"))
                            {
                                opD.setChecked(true);

                                opA.setChecked(false);
                                opB.setChecked(false);
                                opC.setChecked(false);
                            }
                        }

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(QuestionItem.this,"No More Questions",Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            }
            return false;
        }
    };

    private void marksUpload()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, POST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("Serial_Number", SL_No );
                    params.put("Name", name );
                    params.put("Date", date );
                    params.put("Subject", Subject );
                    params.put("Marks", Double.toString(rightAnswer) );
             } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_item);

        //chronometer = findViewById(R.id.timer);
        set = findViewById(R.id.set);
        subject = findViewById(R.id.subject);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.findViewById(R.id.navigation_next).setEnabled(false);
        navigation.findViewById(R.id.navigation_back).setEnabled(false);
        navigation.findViewById(R.id.navigation_submit).setEnabled(false);

        questionNo = findViewById(R.id.questionNo);
        question = findViewById(R.id.questions);
        time = findViewById(R.id.time);
        exmButton = findViewById(R.id.startExmButton);

        opA = findViewById(R.id.optionA);
        opB = findViewById(R.id.optionB);
        opC = findViewById(R.id.optionC);
        opD = findViewById(R.id.optionD);


        q = new ArrayList<>();
        CurrectAnswer = new ArrayList<>();
        SubmittedAnswer = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(QuestionItem.this);

        exmButton.setEnabled(false);
        Toast.makeText(QuestionItem.this,"Please Wait!!",Toast.LENGTH_SHORT).show();
        getDataFromServer();
        Toast.makeText(QuestionItem.this,"You Can Start Now",Toast.LENGTH_LONG).show();
        exmButton.setEnabled(true);

        questionNo.setVisibility(View.INVISIBLE);
        opA.setVisibility(View.INVISIBLE);
        opB.setVisibility(View.INVISIBLE);
        opC.setVisibility(View.INVISIBLE);
        opD.setVisibility(View.INVISIBLE);

        opA.setChecked(false);
        opB.setChecked(false);
        opC.setChecked(false);
        opD.setChecked(false);

        try
        {
            Bundle extras = getIntent().getExtras();
            if(extras !=null)
            {
                name = extras.getString("Name");
                SL_No = extras.getString("SL_No");

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
                date = mdformat.format(calendar.getTime());
            }
        }
        catch (Exception e)
        {

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
                        String Ques_Number = jsonArray.getJSONObject(i).getString("Ques_Number");
                        String Question = jsonArray.getJSONObject(i).getString("Question");
                        String OptionA = jsonArray.getJSONObject(i).getString("OptionA");
                        String OptionB = jsonArray.getJSONObject(i).getString("OptionB");
                        String OptionC = jsonArray.getJSONObject(i).getString("OptionC");
                        String OptionD = jsonArray.getJSONObject(i).getString("OptionD");
                        String Curr_Option = jsonArray.getJSONObject(i).getString("Curr_Option");
                        Set = jsonArray.getJSONObject(i).getString("Set");
                        Subject = jsonArray.getJSONObject(i).getString("Subject");

                        QuestionType qt = new QuestionType(Ques_Number,Question,OptionA,
                                OptionB,OptionC,OptionD,"-");
                        q.add(qt);
                        CurrectAnswer.add(Curr_Option);



                    } catch (JSONException e) {
                        Log.e("Error", e.toString());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(QuestionItem.this,"No Internet Connection!!" ,Toast.LENGTH_LONG).show();

                new AwesomeInfoDialog(QuestionItem.this)
                        .setTitle("Error")
                        .setMessage("No Internet Connection!!")
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
                                Intent intent = new Intent(QuestionItem.this, MainActivity.class);
                                startActivity(intent);
                                QuestionItem.this.finish();
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
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void setdata() {

        questionNo.setText(q.get(currentIndex).getQuestionNo());
        question.setText(q.get(currentIndex).getQuestion());
        opA.setText(q.get(currentIndex).getOptionA());
        opB.setText(q.get(currentIndex).getOptionB());
        opC.setText(q.get(currentIndex).getOptionC());
        opD.setText(q.get(currentIndex).getOptionD());

        currentIndex = 0;
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if ((KeyCode == KeyEvent.KEYCODE_BACK))
        {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(QuestionItem.this);
            dialog.setTitle("Medico Coaching").setMessage("Your Exam Will be Canceled\nReally want to Cancel !!");
            dialog.setCancelable(false);

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            })
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Intent intent = new Intent(QuestionItem.this, MainActivity.class);
                    startActivity(intent);
                    QuestionItem.this.finish();
                    //chronometer.stop();
                }
            });
            dialog.show();

            return true;
        }
        return super.onKeyDown(KeyCode, event);
    }

    public void checkedA(View view) {
        if(opA.isChecked())
        {
            //Toast.makeText(QuestionItem.this,"A",Toast.LENGTH_LONG).show();
            SubmittedAnswer.add("A");
            q.get(currentIndex).setCheckedOption("A");
        }
        else if(opA.isChecked() == false)
        {
            SubmittedAnswer.add("-");
            q.get(currentIndex).setCheckedOption("-");
        }
    }

    public void checkedB(View view) {
        if(opB.isChecked())
        {
            //Toast.makeText(QuestionItem.this,"B",Toast.LENGTH_LONG).show();
            SubmittedAnswer.add("B");
            q.get(currentIndex).setCheckedOption("B");
        }
        else if(opB.isChecked() == false)
        {
            SubmittedAnswer.add("-");
            q.get(currentIndex).setCheckedOption("-");
        }
    }

    public void checkedC(View view) {
        if(opC.isChecked())
        {
            //Toast.makeText(QuestionItem.this,"C",Toast.LENGTH_LONG).show();
            SubmittedAnswer.add("C");
            q.get(currentIndex).setCheckedOption("C");
        }
        else if(opC.isChecked() == false)
        {
            SubmittedAnswer.add("-");
            q.get(currentIndex).setCheckedOption("-");
        }
    }
    public void checkedD(View view) {
        if(opD.isChecked())
        {
            //Toast.makeText(QuestionItem.this,"D",Toast.LENGTH_LONG).show();
            SubmittedAnswer.add("D");
            q.get(currentIndex).setCheckedOption("D");
        }
        else if(opD.isChecked() == false)
        {
            SubmittedAnswer.add("-");
            q.get(currentIndex).setCheckedOption("-");
        }
    }

    public void StartExam(View view) {

        try
        {
            questionNo.setVisibility(View.VISIBLE);
            opA.setVisibility(View.VISIBLE);
            opB.setVisibility(View.VISIBLE);
            opC.setVisibility(View.VISIBLE);
            opD.setVisibility(View.VISIBLE);
            set.setText("Set : " + Set);
            subject.setText("Subject : " + Subject);

            navigation.findViewById(R.id.navigation_next).setEnabled(true);
            navigation.findViewById(R.id.navigation_back).setEnabled(true);
            navigation.findViewById(R.id.navigation_submit).setEnabled(true);

            setdata();

            cdt = new CountDownTimer(3600000,1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    time.setText("" + millisUntilFinished/60000 + " Min");
                    //chronometer.start();
                }

                @Override
                public void onFinish() {
                    time.setText("Done");
                    // chronometer.stop();

                    int countEmpty = 0;
                    for(int i=0;i<totalQuestions;i++)
                    {
                        if(CurrectAnswer.get(i).equals(q.get(i).getCheckedOption()) )
                        {
                            rightAnswer++;
                        }
                        else if(q.get(i).getCheckedOption().equals("-"))
                        {
                            countEmpty++;
                        }
                        else
                        {
                            rightAnswer = rightAnswer - .25;
                        }
                    }
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(QuestionItem.this);
                    dialog.setTitle("Medico Coaching").setMessage("Total Score : " + Double.toString(rightAnswer));
                    dialog.setCancelable(false);

                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent = new Intent(QuestionItem.this, MainActivity.class);
                            startActivity(intent);
                            QuestionItem.this.finish();
                        }
                    });
                    dialog.show();
                }
            }.start();

            exmButton.setVisibility(View.INVISIBLE);
        }
        catch (Exception e)
        {
            Toast.makeText(QuestionItem.this,"Network error!!",Toast.LENGTH_LONG).show();
        }

    }
}
