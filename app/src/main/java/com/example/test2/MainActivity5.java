package com.example.test2;



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.navi.NaviOptions;
import com.kakao.sdk.navi.NaviService;
import com.kakao.sdk.navi.model.*;
public class MainActivity5 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 나머지 액티비티 초기화 또는 작업
        KakaoSdk.init(this, "317cbadd2f78c7286bf66d93956b1dda"); // KakaoSdk.init(this, ${NATIVE_APP_KEY});
    }

}
