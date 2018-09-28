package com.example.sakib.medicodemo5.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.sakib.medicodemo5.MainActivity;
import com.example.sakib.medicodemo5.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class AdmissionInfo extends AppCompatActivity {

    TextView medicoAdm;
    TextView medicoFee;
    TextView medicoLastDate;

    TextView medicalAdm;
    TextView medicalFee;
    TextView medicalLastDate;

    Calendar calendar = Calendar.getInstance();
    String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_info);

        medicoAdm = findViewById(R.id.medicoAdm);
        medicoFee = findViewById(R.id.medicoFee);
        medicoLastDate = findViewById(R.id.medicoLastDate);

        medicalAdm = findViewById(R.id.medicalAdm);
        medicalFee = findViewById(R.id.medicalFee);
        medicalLastDate = findViewById(R.id.medicalLastDate);

        year = Integer.toString(calendar.get(Calendar.YEAR));

        medicoAdm.setText("Medico Admission "+year+" Information");
        medicalAdm.setText("Medical Admission "+year+" Information");

        readFromFireBase();
    }

    private void readFromFireBase() {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference medicoFeeRef = database.getReference("Medico Fee");
        DatabaseReference medicalFeeRef = database.getReference("Medical Fee");

        //myRef.setValue("Hello, World!");

        // Read from the database
            medicoFeeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                medicoFee.setText(value);
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        medicalFeeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                medicalFee.setText(value);
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if ((KeyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent intent = new Intent(AdmissionInfo.this, MainActivity.class);
            startActivity(intent);
            AdmissionInfo.this.finish();
            return true;
        }
        return super.onKeyDown(KeyCode, event);
    }
}
