package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test2.R;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity3 extends AppCompatActivity {
    private LinearLayout containerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        containerLayout = findViewById(R.id.containerLayout);

        // AssetManager를 사용하여 assets 폴더에서 JSON 파일을 읽습니다.
        String json = loadJSONFromAsset("travel.json");

        try {
            JSONObject jsonObject = new JSONObject(json);

            // "item" 배열 가져오기
            JSONArray itemArray = jsonObject.getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items")
                    .getJSONArray("item");

            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject itemObject = itemArray.getJSONObject(i);

                String title = itemObject.getString("title");
                String address = itemObject.getString("addr1");
                String eventStartDate = itemObject.getString("eventstartdate");
                String eventEndDate = itemObject.getString("eventenddate");
                String phoneNumber = itemObject.getString("tel");

                // 이미지를 Picasso를 사용하여 ImageView에 표시
                String imageKey = "fes" + (i + 1); // 이미지 키 생성
                int imageResourceId = getResources().getIdentifier(imageKey, "drawable", getPackageName());

                ImageView imageView = new ImageView(this);

                if (imageResourceId != 0) {
                    Picasso.get().load(imageResourceId).into(imageView); // 이미지 리소스 ID를 사용하여 이미지 로드
                } else {
                    // 이미지가 없는 경우 대체 이미지를 표시할 수 있습니다.
                    imageView.setImageResource(R.drawable.error_image);
                }

                // 텍스트 정보를 TextView에 표시
                TextView textView = new TextView(this);
                textView.setText("축제명: " + title + "\n"
                        + "주소: " + address + "\n"
                        + "시작일: " + eventStartDate + "\n"
                        + "종료일: " + eventEndDate + "\n"
                        + "전화번호: " + phoneNumber + "\n"
                        + "----------------------------"+"\n");
                textView.setTypeface(null, Typeface.BOLD);
                textView.setTextColor(Color.parseColor("#8B4513"));

                // 이미지와 텍스트 정보를 담는 레이아웃을 생성
                LinearLayout itemLayout = new LinearLayout(this);
                itemLayout.setOrientation(LinearLayout.VERTICAL);
                itemLayout.addView(imageView);
                itemLayout.addView(textView);

                // 전체 레이아웃에 아이템 레이아웃 추가
                containerLayout.addView(itemLayout);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAsset(String filename) {
        String json;
        try {
            AssetManager assetManager = getAssets();
            InputStream is = assetManager.open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
