package com.example.newproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText,editText1;
    TextView textView,textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.text2);
        textView1=findViewById(R.id.text1);

        FirebaseDatabase db= FirebaseDatabase.getInstance();
        DatabaseReference root= db.getReference("Name");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.exists()){
                    Map map =(Map) snapshot.getValue();
                    textView.setText(map.get("firstname").toString());
                    textView1.setText(map.get("lastName").toString());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public  void process(View view){
        editText=findViewById(R.id.edit);
        editText1=findViewById(R.id.edit2);
        String firstName= editText.getText().toString().trim();
        String lastName=editText1.getText().toString().trim();
        dataHolder obj= new dataHolder(firstName,lastName);
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        DatabaseReference root= db.getReference("Name");
        root.setValue(obj);
        editText.setText(" ");
        editText1.setText(" ");
        Toast.makeText(getApplicationContext(),"Insert",Toast.LENGTH_LONG).show();


    }


}