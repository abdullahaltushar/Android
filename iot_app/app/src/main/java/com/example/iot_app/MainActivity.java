package com.example.iot_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView textView,textView2,textView1,wifi_text, d_text;
    String time_value_d,time_value_c;
    EditText editText,editText1;
    String plug_s,plug_w;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.timer1);
        wifi_text=findViewById(R.id.text);
        textView1=findViewById(R.id.toggleButton);
        button=findViewById(R.id.button);
        d_text=findViewById(R.id.timer);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle);
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        DatabaseReference root3= db.getReference("Socket/Plug_Status");
        DatabaseReference root4= db.getReference("WiFi");
        root4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Map map =(Map) snapshot.getValue();
                    plug_w=map.get("Status").toString();
                    int plug1= Integer.parseInt(plug_w);
                    if (plug1>=1){

                        wifi_text.setText(" Connected  ");
                          }
                    else{

                        wifi_text.setText(" Disconnected ");
                        Toast.makeText(getApplicationContext(),"Please on your wifi",Toast.LENGTH_SHORT).show();


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    root3.setValue(1);

                    String checked= String.valueOf(buttonView.isChecked());
                    Toast.makeText(getApplicationContext(),checked,Toast.LENGTH_SHORT).show();
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });
        DatabaseReference root1= db.getReference("Socket");
        root1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map map =(Map) snapshot.getValue();
                    plug_s=map.get("Plug_Status").toString();
                    int plug= Integer.parseInt(plug_s);
                    if( plug != 0){
                        toggle.setChecked(true);
                        DatabaseReference root= db.getReference("Socket/Timer");
                        root.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if( snapshot.exists()){
                                    Map map =(Map) snapshot.getValue();
                                    time_value_d=map.get("Discharge").toString();
                                    time_value_c=map.get("Charge").toString();
                                    textView1.setText(map.get("T_Status").toString());
                                    int time_d= Integer.parseInt(time_value_d);
                                    int time_c= Integer.parseInt(time_value_c);
                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            editText=findViewById(R.id.editText);
                                            editText1=findViewById(R.id.editText1);
                                            String charge= editText.getText().toString().trim();
                                            String discharge= editText1.getText().toString().trim();

                                            FirebaseDatabase db= FirebaseDatabase.getInstance();
                                            DatabaseReference root5= db.getReference("Socket/Timer/Charge");
                                            if (TextUtils.isEmpty(editText.getText().toString())){
                                                Toast.makeText(getApplicationContext(),"Please give Charge value",Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                int value_charge=Integer.parseInt(charge);
                                                root5.setValue(value_charge);

                                                if (TextUtils.isEmpty(editText1.getText().toString())){
                                                    DatabaseReference root6= db.getReference("Socket/Timer/Discharge");
                                                    root6.setValue(0);
                                                    Toast.makeText(getApplicationContext(),"Please give Discharge value",Toast.LENGTH_SHORT).show();

                                                }
                                                else {
                                                    DatabaseReference root6= db.getReference("Socket/Timer/Discharge");
                                                    root6.setValue(Integer.parseInt(discharge));

                                                }

                                            }

                                            //work phase
                                            editText.setText(null);
                                            editText1.setText(null);
                                            Toast.makeText(getApplicationContext(),"Insert",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    if (time_c>=1) {
                                        long duration = TimeUnit.MINUTES.toMillis(time_c);
                                        new CountDownTimer(duration, 1000) {

                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                String sDuration = String.format(Locale.ENGLISH, "%02d : %02d",
                                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                                                textView.setText(sDuration);
                                            }

                                            @Override
                                            public void onFinish() {
                                                if (time_d >= 1) {
                                                    long duration1 = TimeUnit.MINUTES.toMillis(time_d);
                                                    new CountDownTimer(duration1, 1000) {

                                                        @Override
                                                        public void onTick(long millisUntilFinished) {
                                                            String sDuration = String.format(Locale.ENGLISH, "%02d : %02d",
                                                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                                                            d_text.setText(sDuration);
                                                        }

                                                        @Override
                                                        public void onFinish() {
                                                            {
                                                                long duration = TimeUnit.MINUTES.toMillis(time_c);
                                                                new CountDownTimer(duration, 1000) {

                                                                    @Override
                                                                    public void onTick(long millisUntilFinished) {
                                                                        String sDuration = String.format(Locale.ENGLISH, "%02d : %02d",
                                                                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                                                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                                                                        textView.setText(sDuration);
                                                                    }

                                                                    @Override
                                                                    public void onFinish() {
                                                                        Toast.makeText(getApplicationContext(),
                                                                                "Count Time has Ended", Toast.LENGTH_LONG).show();

                                                                    }
                                                                }.start();

                                                            }
                                                        }
                                                    }.start();

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Time Not set", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        }.start();


                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Charge time no Available", Toast.LENGTH_SHORT).show();
                                    }


                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Timer had no value",Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Plz on your plug connection",Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    Toast.makeText(getApplicationContext(),"Plz provide valid socket information",Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

//    public  void process(View view){
//        editText=findViewById(R.id.editText);
//        String time= editText.getText().toString().trim();
//        int value=Integer.parseInt(time);
//        FirebaseDatabase db= FirebaseDatabase.getInstance();
//        DatabaseReference root= db.getReference("Socket/Timer/T_Set");
//        root.setValue(value);
//        editText.setText(" ");
//        Toast.makeText(getApplicationContext(),"Insert",Toast.LENGTH_LONG).show();
//
//
//    }
}