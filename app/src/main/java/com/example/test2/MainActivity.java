package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t);


        //개발자 정보 버튼 클릭시 액티비티 전환
        Button developer_info_btn = (Button) findViewById(R.id.t_button);
        developer_info_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });


        //닉네임규정 버튼 클릭시 액티비티 전환
        Button name_rule_btn = (Button) findViewById(R.id.button2);
        name_rule_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                startActivity(intent);
            }
        });

        //닉네임규정 버튼 클릭시 액티비티 전환
        Button list_rule_btn = (Button) findViewById(R.id.button5);
        list_rule_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
            }
        });
    }
}
