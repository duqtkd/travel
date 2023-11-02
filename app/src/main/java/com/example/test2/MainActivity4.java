package com.example.test2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MainActivity4 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        //개발자 정보 버튼 클릭시 액티비티 전환
//        Button developer_info_btn = (Button) findViewById(R.id.button4);
//        developer_info_btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity5.class);
//                startActivity(intent);
//            }
//        });
        // MainActivity2로부터 가까운 마커 정보 받기
        ArrayList<MarkerInfo> markerInfos = getIntent().getParcelableArrayListExtra("markerInfos");


        if (markerInfos != null) {
            // TextView를 사용하여 화면에 가까운 마커 정보 출력
            TextView searchResultTextView = findViewById(R.id.searchResultTextView);
            StringBuilder infoTexts = new StringBuilder();
            searchResultTextView.setText(infoTexts.toString());


            if (markerInfos != null && markerInfos.size() >= 5) {
                StringBuilder infoText = new StringBuilder();

                for (int i = 0; i < markerInfos.size(); i++) { // 3번째부터 5번째 마커까지
                    MarkerInfo markerInfo = markerInfos.get(i);
                    String itemName = markerInfo.getItemName();
                    double latitude = markerInfo.getLatitude();
                    double longitude = markerInfo.getLongitude();

                    infoText.append(itemName).append("\n\n");

                    if (i < markerInfos.size() - 1) {
                        infoText.append("    ↓").append("\n\n");
                    }


                    TextView markerInfoTextView = findViewById(R.id.markerInfoTextView);
                    markerInfoTextView.setText(infoText.toString());

                    markerInfoTextView.setTypeface(null, Typeface.BOLD);
                    markerInfoTextView.setTextColor(Color.parseColor("#8B4513"));


                }
            }
        }
    }
}