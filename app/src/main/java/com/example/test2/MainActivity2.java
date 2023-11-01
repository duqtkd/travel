package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity2 extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener {

    private MapView mapView;
    private ViewGroup mapViewContainer;

    private EditText edSearch;
    private ListView listNearMarker;

    private ArrayList<MapPOIItem> markerList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edSearch = findViewById(R.id.edSearch);
        listNearMarker = findViewById(R.id.listSearchList);

        Button developer_info_btn = (Button) findViewById(R.id.button3);
        developer_info_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainActivity4.class);
                startActivity(intent);
            }
        });
        Button developer_info_btns = (Button) findViewById(R.id.btn_start);
        developer_info_btns.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startTracking();
            }
        });


        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 권한 체크
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int permission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED || permission3 == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 권한 요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            }
            return;
        }

        // 지도 초기화
        mapView = new MapView(this);
        mapViewContainer = findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);   // 이부분 주석처리되어있었는데 확인 부탁드려요

        //region ---- 커스텀 마커 추가 Section  ---

//        // 커스텀 마커 추가
        addCustomMarker(mapView, "국립충주기상과학관", 1, MapPoint.mapPointWithGeoCoord(36.9856858899357, 127.948768222583), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "산모랭이 풀내음 농장", 2, MapPoint.mapPointWithGeoCoord(36.511032728211, 127.637340251904), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "다누리센터", 3, MapPoint.mapPointWithGeoCoord(36.9854106363708, 128.370751882825), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "청주시립미술관", 4, MapPoint.mapPointWithGeoCoord(36.634836, 127.478379), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "청주 동보원자연휴양림", 5, MapPoint.mapPointWithGeoCoord(36.658812192101784, 127.70062173644754), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "문광 저수지 은행나무길", 6, MapPoint.mapPointWithGeoCoord(36.766063, 127.748100), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "청남대", 7, MapPoint.mapPointWithGeoCoord(36.46224737088981, 127.49009483316492), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "속리산국립공원", 8, MapPoint.mapPointWithGeoCoord(36.51737133895241, 127.81721986071967), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "구병산", 9, MapPoint.mapPointWithGeoCoord(36.45041210479316, 127.86796970961937), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "좌구산천문대", 10, MapPoint.mapPointWithGeoCoord(36.763886704705705, 127.5955697477588), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "동부창고", 11, MapPoint.mapPointWithGeoCoord(36.6545173575658, 127.491317472263), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "문화예술체험촌", 12, MapPoint.mapPointWithGeoCoord(37.0277699658471, 127.630075112163), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "솔부엉이 캠핑장(카페, 실내놀이터)", 13, MapPoint.mapPointWithGeoCoord(36.9727607137891, 127.625114865764), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "청인약방 박물관", 14, MapPoint.mapPointWithGeoCoord(36.7860772950846, 127.864406764077), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "카페 몽도래", 15, MapPoint.mapPointWithGeoCoord(36.8124857218702, 127.799867886418), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "해듬카페", 16, MapPoint.mapPointWithGeoCoord(36.7726321906678, 127.647369232795), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "어라운드빌리지 카페(캠핑장)", 17, MapPoint.mapPointWithGeoCoord(36.4459526628619, 127.753378663858), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "일상화카페(캠핑장)", 18, MapPoint.mapPointWithGeoCoord(36.5851376905276, 127.697763562393), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "엽연초 하우스(카페)", 19, MapPoint.mapPointWithGeoCoord(37.1366438035191, 128.207011256608), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "광덕산", 20, MapPoint.mapPointWithGeoCoord(36.6939717, 127.0342684), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "팔봉산", 21, MapPoint.mapPointWithGeoCoord(36.81045, 126.370827), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "가야산(서산)", 22, MapPoint.mapPointWithGeoCoord(36.7080102, 126.6103935), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "황금산", 23, MapPoint.mapPointWithGeoCoord(36.990122, 126.330715), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "도비산", 24, MapPoint.mapPointWithGeoCoord(36.70288, 126.416076), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "대둔산", 25, MapPoint.mapPointWithGeoCoord(36.1440246, 127.3050642), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "계룡산", 26, MapPoint.mapPointWithGeoCoord(36.35202, 127.202176), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "천마산", 27, MapPoint.mapPointWithGeoCoord(36.265556, 127.243056), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "향적산", 28, MapPoint.mapPointWithGeoCoord(36.292888, 127.201844), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "아미망루(아미산)", 29, MapPoint.mapPointWithGeoCoord(36.845756, 126.664825), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "진악산", 30, MapPoint.mapPointWithGeoCoord(36.077696, 127.458825), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "서대산", 31, MapPoint.mapPointWithGeoCoord(36.2247735, 127.5406074), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "국사봉", 32, MapPoint.mapPointWithGeoCoord(36.1776681, 127.5585222), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "천태산", 33, MapPoint.mapPointWithGeoCoord(36.157035, 127.6126385), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "천방산", 34, MapPoint.mapPointWithGeoCoord(36.1421786, 126.7521359), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "칠갑산도립공원", 35, MapPoint.mapPointWithGeoCoord(36.413368, 126.884288), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "용봉산", 36, MapPoint.mapPointWithGeoCoord(36.643648, 126.649178), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "오서산", 37, MapPoint.mapPointWithGeoCoord(36.466578, 126.654854), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "백화산", 38, MapPoint.mapPointWithGeoCoord(36.7674999, 126.3024759), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "대천해수욕장", 39, MapPoint.mapPointWithGeoCoord(36.305597, 126.516049), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "무창포 신비의 바닷길", 40, MapPoint.mapPointWithGeoCoord(36.2448568, 126.53615), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "죽도", 41, MapPoint.mapPointWithGeoCoord(36.516732, 126.4407492), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "외연도", 42, MapPoint.mapPointWithGeoCoord(36.2278042, 126.0791445), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "고파도", 43, MapPoint.mapPointWithGeoCoord(36.9065367, 126.3373125), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "신두사구", 44, MapPoint.mapPointWithGeoCoord(36.786406, 126.142343), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "난지섬해수욕장", 45, MapPoint.mapPointWithGeoCoord(37.0470409, 126.4248919), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "춘장대해수욕장", 46, MapPoint.mapPointWithGeoCoord(36.1636815, 126.5226424), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "간월도관광지", 47, MapPoint.mapPointWithGeoCoord(36.603807, 126.411164), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "만리포해수욕장", 48, MapPoint.mapPointWithGeoCoord(36.7863416, 126.1423105), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "가의도", 49, MapPoint.mapPointWithGeoCoord(36.675406, 126.070325), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "몽산해변", 50, MapPoint.mapPointWithGeoCoord(36.6698967, 126.2870291), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "꽃지해수욕장", 51, MapPoint.mapPointWithGeoCoord(36.497028, 126.33507), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "대천항", 52, MapPoint.mapPointWithGeoCoord(36.327136, 126.5109855), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "오천항", 53, MapPoint.mapPointWithGeoCoord(36.4390883, 126.5197161), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "홍원항", 55, MapPoint.mapPointWithGeoCoord(36.157853, 126.500155), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "마량포구", 56, MapPoint.mapPointWithGeoCoord(36.129184, 126.503985), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "장항항", 57, MapPoint.mapPointWithGeoCoord(36.007418, 126.6858628), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "남당항", 58, MapPoint.mapPointWithGeoCoord(36.539146, 126.4690706), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "궁리포구", 59, MapPoint.mapPointWithGeoCoord(36.594772, 126.456907), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "금강", 60, MapPoint.mapPointWithGeoCoord(36.433396, 127.212843), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "금학생태공원", 61, MapPoint.mapPointWithGeoCoord(36.4334735, 127.1241787), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "정안천생태공원", 62, MapPoint.mapPointWithGeoCoord(36.4939894, 127.1355835), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "보령호", 63, MapPoint.mapPointWithGeoCoord(36.243735, 126.6598535), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "신정호관광지", 64, MapPoint.mapPointWithGeoCoord(36.7705939, 126.9752491), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "용현계곡", 65, MapPoint.mapPointWithGeoCoord(36.7500127, 126.6093153), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "탑정호", 66, MapPoint.mapPointWithGeoCoord(36.1798158, 127.1714044), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "숫용추", 67, MapPoint.mapPointWithGeoCoord(36.3204369, 127.2121793), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "암용추", 68, MapPoint.mapPointWithGeoCoord(36.3312589, 127.2286159), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "적벽강", 69, MapPoint.mapPointWithGeoCoord(36.0492503, 127.5910681), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "성치산십이폭포", 70, MapPoint.mapPointWithGeoCoord(36.0158406, 127.4797001), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "지천구곡", 71, MapPoint.mapPointWithGeoCoord(36.3981797, 126.8390156), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "천장호", 72, MapPoint.mapPointWithGeoCoord(36.4144768, 126.9163826), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "역재방죽", 73, MapPoint.mapPointWithGeoCoord(36.5923265, 126.6762498), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "예당저수지", 74, MapPoint.mapPointWithGeoCoord(36.6361343, 126.7994544), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "창벽수상레저", 75, MapPoint.mapPointWithGeoCoord(36.4343314, 127.21276), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "공주한옥마을", 76, MapPoint.mapPointWithGeoCoord(36.4647028, 127.1082182), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "고마나루명승길", 77, MapPoint.mapPointWithGeoCoord(36.4684031, 127.1064225), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "계룡산도예촌", 78, MapPoint.mapPointWithGeoCoord(36.3891968, 127.2164038), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "마곡사솔바람길", 79, MapPoint.mapPointWithGeoCoord(36.559086, 127.0122689), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "보령 냉풍욕장", 80, MapPoint.mapPointWithGeoCoord(36.3742077, 126.6725135), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "아산레일바이크", 81, MapPoint.mapPointWithGeoCoord(36.7612188, 126.8674886), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "서산 아라메길", 82, MapPoint.mapPointWithGeoCoord(36.8255256, 126.5730572), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "나비아이", 83, MapPoint.mapPointWithGeoCoord(36.8202122, 126.4867705), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "강경자전거길", 84, MapPoint.mapPointWithGeoCoord(36.1542814, 127.01545), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "사계솔바람길", 85, MapPoint.mapPointWithGeoCoord(36.2657053, 127.2717458), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "도비도해양체험", 86, MapPoint.mapPointWithGeoCoord(37.021151, 126.46236), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "삼길포항", 54, MapPoint.mapPointWithGeoCoord(37.003564, 126.453388), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "칠갑산오토캠핑장", 87, MapPoint.mapPointWithGeoCoord(36.3958142, 126.8636477), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "솔향기길", 88, MapPoint.mapPointWithGeoCoord(36.9667956, 126.3047397), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "개화예술공원", 89, MapPoint.mapPointWithGeoCoord(36.3117295, 126.657359), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "서산한우목장", 90, MapPoint.mapPointWithGeoCoord(36.7701412, 126.5643239), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "옥녀봉", 91, MapPoint.mapPointWithGeoCoord(36.1643686, 127.0115125), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "왜목마을", 92, MapPoint.mapPointWithGeoCoord(37.0474613, 126.5275455), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "서해대교", 93, MapPoint.mapPointWithGeoCoord(36.943241, 126.819263), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "삽교호방조제", 94, MapPoint.mapPointWithGeoCoord(36.8914176, 126.8231249), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "구드래국민관광지", 95, MapPoint.mapPointWithGeoCoord(36.2871209, 126.9073784), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "마량리동백나무숲", 96, MapPoint.mapPointWithGeoCoord(36.1366829, 126.4939535), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "금강하구둑", 97, MapPoint.mapPointWithGeoCoord(36.0227449, 126.7426452), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "신성리갈대밭", 98, MapPoint.mapPointWithGeoCoord(36.068281, 126.8618077), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "천장호출렁다리", 99, MapPoint.mapPointWithGeoCoord(36.4144682, 126.9163585), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "삽교평야", 100, MapPoint.mapPointWithGeoCoord(36.7151412, 126.6716766), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "안면송림", 101, MapPoint.mapPointWithGeoCoord(36.549538, 126.3292739), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "할미,할아비바위", 102, MapPoint.mapPointWithGeoCoord(36.5008437, 126.333729), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "유관순열사 사적지", 103, MapPoint.mapPointWithGeoCoord(36.7594749, 127.30802), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "망향의동산", 104, MapPoint.mapPointWithGeoCoord(36.8521486, 127.1881306), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "공산성", 105, MapPoint.mapPointWithGeoCoord(36.4647404, 127.1238917), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "무령왕릉", 106, MapPoint.mapPointWithGeoCoord(36.4607423, 127.1125621), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "우금치전적지", 107, MapPoint.mapPointWithGeoCoord(36.4366555, 127.1148285), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "현충사", 108, MapPoint.mapPointWithGeoCoord(36.8065436, 127.0322299), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "해미읍성", 109, MapPoint.mapPointWithGeoCoord(36.71312, 126.54917), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "마애여래삼존상", 110, MapPoint.mapPointWithGeoCoord(36.7732737, 126.604718), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "보원사지", 111, MapPoint.mapPointWithGeoCoord(36.7618273, 126.6026902), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "계백장군유적지", 112, MapPoint.mapPointWithGeoCoord(36.1921429, 127.1804193), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "노성산성", 113, MapPoint.mapPointWithGeoCoord(36.2997913, 127.1188653), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "신도내주초석", 114, MapPoint.mapPointWithGeoCoord(36.252977, 127.260153), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "개삼터공원", 115, MapPoint.mapPointWithGeoCoord(36.07472, 127.476747), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "칠백의총", 116, MapPoint.mapPointWithGeoCoord(36.129956, 127.491788), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "궁남지", 117, MapPoint.mapPointWithGeoCoord(36.2695432, 126.9124961), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "낙화암", 118, MapPoint.mapPointWithGeoCoord(36.292429, 126.912196), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "능산리고분군", 119, MapPoint.mapPointWithGeoCoord(36.277018, 126.941775), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "송국리유적", 120, MapPoint.mapPointWithGeoCoord(36.270332, 127.031522), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "수북정", 121, MapPoint.mapPointWithGeoCoord(36.275365, 126.890645), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "우산성", 122, MapPoint.mapPointWithGeoCoord(36.458069, 126.809129), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "모덕사", 123, MapPoint.mapPointWithGeoCoord(36.446143, 127.008551), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "서정리구층석탑", 124, MapPoint.mapPointWithGeoCoord(36.410674, 126.953068), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "홍주성과여하정", 125, MapPoint.mapPointWithGeoCoord(36.601302, 126.660885), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "만해한용운생가", 126, MapPoint.mapPointWithGeoCoord(36.563014, 126.553836), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "백야김좌진장군생가", 127, MapPoint.mapPointWithGeoCoord(36.59733, 126.546809), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "임존성", 128, MapPoint.mapPointWithGeoCoord(36.592377, 126.775521), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "충의사", 129, MapPoint.mapPointWithGeoCoord(36.68646, 126.651451), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "안흥성", 130, MapPoint.mapPointWithGeoCoord(36.682617, 126.156277), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "각원사", 131, MapPoint.mapPointWithGeoCoord(36.834157, 127.196966), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "광덕사", 132, MapPoint.mapPointWithGeoCoord(36.675708, 127.042267), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "갑사", 133, MapPoint.mapPointWithGeoCoord(36.3656, 127.187518), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "마곡사", 134, MapPoint.mapPointWithGeoCoord(36.558543, 127.012035), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "동학사", 135, MapPoint.mapPointWithGeoCoord(36.3533133, 127.2200704), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "신원사", 136, MapPoint.mapPointWithGeoCoord(36.335242, 127.183973), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "황새바위성지", 137, MapPoint.mapPointWithGeoCoord(36.4636834, 127.1199757), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "공주중동성당", 138, MapPoint.mapPointWithGeoCoord(36.4548, 127.127456), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "갈매못 순교성지", 139, MapPoint.mapPointWithGeoCoord(36.427957, 126.50755), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "서짓골 천주교성지", 140, MapPoint.mapPointWithGeoCoord(36.233327, 126.657624), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "공세리성당", 141, MapPoint.mapPointWithGeoCoord(36.882925, 126.913515), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "봉곡사", 142, MapPoint.mapPointWithGeoCoord(36.698741, 126.966478), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "간월암", 143, MapPoint.mapPointWithGeoCoord(36.603807, 126.411164), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "개심사", 144, MapPoint.mapPointWithGeoCoord(36.747069, 126.590298), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "일락사", 145, MapPoint.mapPointWithGeoCoord(36.72805, 126.588762), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "부석사", 146, MapPoint.mapPointWithGeoCoord(36.703734, 126.412379), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "해미순교성지", 147, MapPoint.mapPointWithGeoCoord(36.712687, 126.538031), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "관촉사", 148, MapPoint.mapPointWithGeoCoord(36.188285, 127.113122), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "쌍계사", 149, MapPoint.mapPointWithGeoCoord(36.105596, 127.205769), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "개태사", 150, MapPoint.mapPointWithGeoCoord(36.240759, 127.229439), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "솔뫼성지", 151, MapPoint.mapPointWithGeoCoord(36.8192904, 126.7853808), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "보석사", 152, MapPoint.mapPointWithGeoCoord(36.054451, 127.475808), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "장곡사", 153, MapPoint.mapPointWithGeoCoord(36.417289, 126.859567), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "다락골줄무덤성지", 154, MapPoint.mapPointWithGeoCoord(36.442898, 126.692051), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "수덕사", 155, MapPoint.mapPointWithGeoCoord(36.6581556, 126.6218841), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "외암민속마을", 156, MapPoint.mapPointWithGeoCoord(36.731278, 127.013209), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "돈암서원", 157, MapPoint.mapPointWithGeoCoord(36.209225, 127.180763), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "명재고택", 158, MapPoint.mapPointWithGeoCoord(36.282038, 127.131098), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "은농재", 159, MapPoint.mapPointWithGeoCoord(36.265631, 127.271735), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "한산모시마을", 160, MapPoint.mapPointWithGeoCoord(36.080368, 126.79959), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "문헌서원", 161, MapPoint.mapPointWithGeoCoord(36.091992, 126.785295), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "추사고택", 162, MapPoint.mapPointWithGeoCoord(36.746847, 126.799764), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "독립기념관", 163, MapPoint.mapPointWithGeoCoord(36.7783072, 127.2308099), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "홍대용과학관", 164, MapPoint.mapPointWithGeoCoord(36.739485, 127.296246), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "천안흥타령관", 165, MapPoint.mapPointWithGeoCoord(36.78486, 127.165641), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "국립공주박물관", 166, MapPoint.mapPointWithGeoCoord(36.465584, 127.112111), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "석장리박물관", 167, MapPoint.mapPointWithGeoCoord(36.446755, 127.189643), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "박동진판소리전수관", 168, MapPoint.mapPointWithGeoCoord(36.458109, 127.170775), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "계룡산자연사박물관", 169, MapPoint.mapPointWithGeoCoord(36.366391, 127.246341), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "임립미술관", 170, MapPoint.mapPointWithGeoCoord(36.395865, 127.139611), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "충남역사박물관", 171, MapPoint.mapPointWithGeoCoord(36.45369, 127.127166), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "석탄박물관", 172, MapPoint.mapPointWithGeoCoord(36.325974, 126.652881), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "보령에너지월드", 173, MapPoint.mapPointWithGeoCoord(36.402648, 126.495787), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "보령문화의전당", 174, MapPoint.mapPointWithGeoCoord(36.3517905, 126.589365), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "온양민속박물관", 175, MapPoint.mapPointWithGeoCoord(36.7918859, 127.0063198), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "장영실과학관", 176, MapPoint.mapPointWithGeoCoord(36.798725, 126.977654), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "생태곤충원", 177, MapPoint.mapPointWithGeoCoord(36.797771, 126.9778776), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "옹기발효음식전시체험관", 178, MapPoint.mapPointWithGeoCoord(36.754468, 126.880715), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "세계꽃식물원", 179, MapPoint.mapPointWithGeoCoord(36.750464, 126.857611), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "피나클랜드", 180, MapPoint.mapPointWithGeoCoord(36.873951, 126.927023), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "아산코미디홀", 181, MapPoint.mapPointWithGeoCoord(36.75901, 126.867761), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "서산버드랜드", 182, MapPoint.mapPointWithGeoCoord(36.6310374, 126.3783771), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "류방택천문기상과학관", 183, MapPoint.mapPointWithGeoCoord(36.727331, 126.413186), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "안견기념관", 184, MapPoint.mapPointWithGeoCoord(36.864025, 126.438402), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "백제군사박물관", 185, MapPoint.mapPointWithGeoCoord(36.19233, 127.180685), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "함상공원", 186, MapPoint.mapPointWithGeoCoord(36.890665, 126.82445), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "기지시줄다리기박물관", 187, MapPoint.mapPointWithGeoCoord(36.896281, 126.694754), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "백제문화단지", 188, MapPoint.mapPointWithGeoCoord(36.306745, 126.906927), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "국립부여박물관", 189, MapPoint.mapPointWithGeoCoord(36.276383, 126.918964), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "서동요테마파크", 190, MapPoint.mapPointWithGeoCoord(36.144798, 126.823597), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "정림사지박물관", 191, MapPoint.mapPointWithGeoCoord(36.278587, 126.91586), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "국립생태원", 192, MapPoint.mapPointWithGeoCoord(36.039905, 126.721823), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "국립해양생물자원관", 193, MapPoint.mapPointWithGeoCoord(36.0167272, 126.6693485), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "칠갑산천문대", 194, MapPoint.mapPointWithGeoCoord(36.4337405, 126.8898763), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "고운식물원", 195, MapPoint.mapPointWithGeoCoord(36.435268, 126.76088), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "그림이있는정원", 196, MapPoint.mapPointWithGeoCoord(36.518913, 126.629181), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "결성농사박물관", 197, MapPoint.mapPointWithGeoCoord(36.532719, 126.549014), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "고암이응노생가기념관", 198, MapPoint.mapPointWithGeoCoord(36.622935, 126.631854), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "조류탐사과학관", 199, MapPoint.mapPointWithGeoCoord(36.593543, 126.458774), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "홍성민속테마박물관", 200, MapPoint.mapPointWithGeoCoord(36.585841, 126.631818), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "홍주성역사관", 201, MapPoint.mapPointWithGeoCoord(36.600263, 126.66082), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "테딘워터파크", 202, MapPoint.mapPointWithGeoCoord(36.757092, 127.223096), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "천안상록리조트", 203, MapPoint.mapPointWithGeoCoord(36.739159, 127.288349), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "성주산 자연휴양림", 204, MapPoint.mapPointWithGeoCoord(36.334772, 126.663834), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "오서산자연휴양림", 205, MapPoint.mapPointWithGeoCoord(36.4526129, 126.6731572), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "파라다이스스파도고", 206, MapPoint.mapPointWithGeoCoord(36.7661295, 126.8860334), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "아산스파비스", 207, MapPoint.mapPointWithGeoCoord(36.855309, 126.978152), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "금산산림문화타운", 208, MapPoint.mapPointWithGeoCoord(36.0644416, 127.3723984), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "만수산자연휴양림", 209, MapPoint.mapPointWithGeoCoord(36.338126, 126.711309), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "희리산자연휴양림", 210, MapPoint.mapPointWithGeoCoord(36.1130506, 126.6646707), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "장항송림", 211, MapPoint.mapPointWithGeoCoord(36.015657, 126.665046), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "칠갑산자연휴양림", 212, MapPoint.mapPointWithGeoCoord(36.431487, 126.840956), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "홍성온천", 213, MapPoint.mapPointWithGeoCoord(36.599901, 126.664542), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "덕산온천", 214, MapPoint.mapPointWithGeoCoord(36.6911384, 126.6581047), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "봉수산자연휴양림", 215, MapPoint.mapPointWithGeoCoord(36.597631, 126.78184), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "안면도자연휴양림", 216, MapPoint.mapPointWithGeoCoord(36.49721, 126.360336), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "천안삼거리", 217, MapPoint.mapPointWithGeoCoord(36.783373, 127.169065), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "아리리오광장", 218, MapPoint.mapPointWithGeoCoord(36.819251, 127.156467), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "병천순대거리", 219, MapPoint.mapPointWithGeoCoord(36.759748, 127.297656), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "입장 거봉포도마을", 220, MapPoint.mapPointWithGeoCoord(36.914332, 127.221702), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "서산동부시장", 221, MapPoint.mapPointWithGeoCoord(36.782399, 126.455343), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "금산인삼약초시장", 222, MapPoint.mapPointWithGeoCoord(36.100203, 127.49605), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "장승공원", 223, MapPoint.mapPointWithGeoCoord(36.4104634, 126.8522429), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "토굴새우젓단지", 224, MapPoint.mapPointWithGeoCoord(36.487609, 126.616498), R.drawable.map_marker_icon);
        addCustomMarker(mapView, "대흥슬로시티", 225, MapPoint.mapPointWithGeoCoord(36.606666, 126.790366), R.drawable.map_marker_icon);

        //endregion


        setEventListener();
    }

    private void addCustomMarker(MapView mapView, String title, int tag, MapPoint mapPoint, int customImageResource) {
        MapPOIItem customMarker = new MapPOIItem();
        customMarker.setItemName(title);
        customMarker.setTag(tag);
        customMarker.setMapPoint(mapPoint);
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        customMarker.setCustomImageResourceId(customImageResource);
        customMarker.setCustomImageAutoscale(false);
        customMarker.setCustomImageAnchor(0.5f, 1.0f);

        mapView.addPOIItem(customMarker);

        markerList.add(customMarker);
    }


    // 권한 체크 이후로직
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        // READ_PHONE_STATE의 권한 체크 결과를 불러온다
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == 1000) {
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            // 권한 체크에 동의를 하지 않으면 안드로이드 종료
            if (check_result == false) {
                finish();
            }
        }
    }


    private void setEventListener() {
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(edSearch.getWindowToken(), 0);


                    String searchText = v.getText().toString();
                    MapPOIItem searchMapPOIItem = null;
                    double searchLatitude = 0.0;
                    double searchLongitude = 0.0;

                    // 검색어에 해당하는 마커 찾기
                    for (MapPOIItem mapPOIItem : markerList) {
                        if (mapPOIItem.getItemName().contains(searchText)) {
                            searchMapPOIItem = mapPOIItem;
                            searchLatitude = searchMapPOIItem.getMapPoint().getMapPointGeoCoord().latitude;
                            searchLongitude = searchMapPOIItem.getMapPoint().getMapPointGeoCoord().longitude;
                            break;
                        }
                    }

                    if (searchMapPOIItem != null) {
                        List<MapPOIItem> nearMarker = new ArrayList<>();

                        // 모든 마커와의 거리 계산 및 비교
                        for (MapPOIItem item : markerList) {
                            if (item != searchMapPOIItem) {  // 자기 자신과의 거리는 비교하지 않음
                                double latitude = item.getMapPoint().getMapPointGeoCoord().latitude;
                                double longitude = item.getMapPoint().getMapPointGeoCoord().longitude;

                                // 두 지점 간의 거리 계산
                                double distance = calculateDistance(searchLatitude, searchLongitude, latitude, longitude);

                                // 반경 20킬로미터 이내의 마커만 고려
                                if (distance <= 20.0) {
                                    // 가장 가까운 5개의 마커를 선택
                                    if (nearMarker.size() < 6) {
                                        nearMarker.add(item);
                                    } else {
                                        for (MapPOIItem near : nearMarker) {
                                            double nearLatitude = near.getMapPoint().getMapPointGeoCoord().latitude;
                                            double nearLongitude = near.getMapPoint().getMapPointGeoCoord().longitude;

                                            // 가장 먼 마커와 비교하여 가장 가까운 5개의 마커를 선택
                                            double nearDistance = calculateDistance(searchLatitude, searchLongitude, nearLatitude, nearLongitude);

                                            if (distance < nearDistance) {
                                                nearMarker.remove(near);
                                                nearMarker.add(item);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        for (MapPOIItem item : nearMarker) {
                            Log.i("##INFO", "가장 가까운 마커: " + item.getItemName());
                        }

                        // 거리에 따라 nearMarker 리스트 정렬
                        double finalSearchLatitude = searchLatitude;
                        double finalSearchLongitude = searchLongitude;
                        Collections.sort(nearMarker, new Comparator<MapPOIItem>() {
                            @Override
                            public int compare(MapPOIItem item1, MapPOIItem item2) {
                                double distance1 = calculateDistance(finalSearchLatitude, finalSearchLongitude, item1.getMapPoint().getMapPointGeoCoord().latitude, item1.getMapPoint().getMapPointGeoCoord().longitude);
                                double distance2 = calculateDistance(finalSearchLatitude, finalSearchLongitude, item2.getMapPoint().getMapPointGeoCoord().latitude, item2.getMapPoint().getMapPointGeoCoord().longitude);
                                return Double.compare(distance1, distance2);
                            }
                        });

                        ArrayList<String> nearMarkerName = new ArrayList<>();
                        for (MapPOIItem item : nearMarker) {
                            nearMarkerName.add(item.getItemName());
                        }

                        stopTracking();
                        mapView.moveCamera(CameraUpdateFactory.newMapPoint(MapPoint.mapPointWithGeoCoord(searchLatitude, searchLongitude), 5));


                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity2.this, android.R.layout.simple_list_item_1, nearMarkerName);
                        listNearMarker.setAdapter(adapter);



                        ArrayList<MarkerInfo> markerInfos = new ArrayList<>();
                        for (MapPOIItem item : nearMarker) {
                            String itemName = item.getItemName();
                            double latitude = item.getMapPoint().getMapPointGeoCoord().latitude;
                            double longitude = item.getMapPoint().getMapPointGeoCoord().longitude;

                            MarkerInfo markerInfo = new MarkerInfo(itemName, latitude, longitude);
                            markerInfos.add(markerInfo);
                        }

                        Button developer_info_btn = (Button) findViewById(R.id.button3);
                        developer_info_btn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            Intent intent = new Intent(getApplicationContext(), MainActivity4.class);
                            intent.putParcelableArrayListExtra("markerInfos", markerInfos);
                            startActivity(intent);
                        }
                    });


                    }
                    return true;
                }
                return false;
            }
        });

    }

    // 두 지점 간의 거리를 계산하는 메서드
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 지구 반지름 (단위: km)
        double radius = 6371;

        // 위도 및 경도를 라디안 단위로 변환
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Haversine 공식을 사용하여 두 지점 간의 거리 계산
        double dlon = lon2Rad - lon1Rad;
        double dlat = lat2Rad - lat1Rad;
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = radius * c;

        return distance;
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
    private void startTracking() {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }
    private void stopTracking() {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }
    //36.782399, 126.455343 //36.81045, 126.370827
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
//            String url = "kakaomap://route?sp=36.782399,126.455343&ep=36.81045,126.3708278&by=FOOT";
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            intent.addCategory(Intent.CATEGORY_BROWSABLE);
//            startActivity(intent);
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}