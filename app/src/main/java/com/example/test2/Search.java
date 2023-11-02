package com.example.test2;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ScheduledExecutorService;

public class Search extends AppCompatActivity {

    private List<String> list; // 데이터를 넣은 리스트변수
    private ListView listView; // 검색을 보여줄 리스트변수
    private EditText editSearch; // 검색어를 입력할 Input 창
    private SearchAdapter adapter; // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);

// 리스트를 생성한다.
        list = new ArrayList<String>();

// 검색에 사용할 데이터을 미리 저장한다.
        settingList();

// 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

// 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(list, this);

// 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

// input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
// input창에 문자를 입력할때마다 호출된다.
// search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });


    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

// 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

// 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
// 문자 입력을 할때..
        else
        {
// 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
// arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
// 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
// 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList(){
        list.add("국립충주기상과학관");
        list.add("산모랭이 풀내음 농장");
        list.add("다누리센터");
        list.add("청주시립미술관");
        list.add("청주 동보원자연휴양림");
        list.add("문광 저수지 은행나무길");
        list.add("청남대");
        list.add("속리산국립공원");
        list.add("구병산");
        list.add("좌구산천문대");
        list.add("동부창고");
        list.add("문화예술체험촌");
        list.add("솔부엉이 캠핑장(카페, 실내놀이터)");
        list.add("청인약방 박물관");
        list.add("카페 몽도래");
        list.add("해듬카페");
        list.add("어라운드빌리지 카페(캠핑장)");
        list.add("일상화카페(캠핑장)");
        list.add("엽연초 하우스(카페)");
        list.add("광덕산");
        list.add("팔봉산");
        list.add("가야산");
        list.add("황금산");
        list.add("도비산");
        list.add("대둔산");
        list.add("계룡산");
        list.add("천마산");
        list.add("향적산");
        list.add("아미망루(아미산)");
        list.add("진악산");
        list.add("서대산");
        list.add("국사봉");
        list.add("천태산");
        list.add("천방산");
        list.add("칠갑산도립공원");
        list.add("용봉산");
        list.add("오서산");
        list.add("백화산");
        list.add("대천해수욕장");
        list.add("무창포 신비의 바닷길");
        list.add("죽도");
        list.add("외연도");
        list.add("고파도");
        list.add("신두사구");
        list.add("난지섬해수욕장");
        list.add("춘장대해수욕장");
        list.add("간월도관광지");
        list.add("만리포해수욕장");
        list.add("가의도");
        list.add("몽산해변");
        list.add("꽃지해수욕장");
        list.add("대천항");
        list.add("오천항");
        list.add("홍원항");
        list.add("마량포구");
        list.add("장항항");
        list.add("남당항");
        list.add("궁리포구");
        list.add("금강");
        list.add("금학생태공원");
        list.add("정안천생태공원");
        list.add("보령호");
        list.add("신정호관광지");
        list.add("용현계곡");
        list.add("탑정호");
        list.add("숫용추");
        list.add("암용추");
        list.add("적벽강");
        list.add("성치산십이폭포");
        list.add("지천구곡");
        list.add("천장호");
        list.add("역재방죽");
        list.add("예당저수지");
        list.add("창벽수상레저");
        list.add("공주한옥마을");
        list.add("고마나루명승길");
        list.add("계룡산도예촌");
        list.add("마곡사솔바람길");
        list.add("보령 냉풍욕장");
        list.add("아산레일바이크");
        list.add("서산 아라메길");
        list.add("나비아이");
        list.add("강경자전거길");
        list.add("사계솔바람길");
        list.add("도비도해양체험");
        list.add("삼길포항");
        list.add("칠갑산오토캠핑장");
        list.add("개화예술공원");
        list.add("서산한우목장");
        list.add("옥녀봉");
        list.add("왜목마을");
        list.add("서해대교");
        list.add("삽교호방조제");
        list.add("구드래국민관광지");
        list.add("솔향기길");
        list.add("마량리동백나무숲");
        list.add("금강하구둑");
        list.add("신성리갈대밭");
        list.add("천장호출렁다리");
        list.add("삽교평야");
        list.add("안면송림");
        list.add("할미,할아비바위");
        list.add("유관순열사 사적지");
        list.add("망향의동산");
        list.add("공산성");
        list.add("무령왕릉");
        list.add("우금치전적지");
        list.add("현충사");

    }
}
