package com.example.menulist_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Tumbler_greenseed_main extends AppCompatActivity {

    int greenseed;
    TextView nickname;
    TextView seedgrade;
    TextView content;
    ImageView imageView;
    MyApplication myApplication;
    String tname;
    String tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler_greenseed_main);
        myApplication = (MyApplication)getApplication();

        nickname = (TextView)findViewById(R.id.tumbler_greenseed_txt_nickname); // 닉네임 대신에 텀블러 이름으로 함
        seedgrade = (TextView)findViewById(R.id.tumbler_greenseed_txt_seedgrade);
        content = (TextView)findViewById(R.id.tumbler_greenseed_txt_content);
        imageView = (ImageView)findViewById(R.id.tumbler_greenseed_tree);

        Intent intent = getIntent();
        tid = intent.getExtras().getString("tumbler_id");
        tname = intent.getExtras().getString("tumbler_name");

        nickname.setText(tname);

        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/tumbler/greenSeed/" + tid; // 텀블러 아이디 인텐트로 훔쳐와야댐 그때 유저 닉네임이나 텀블러 이름 가져오면 더 좋을듯
        /*ContentValues contentValues = new ContentValues();
        contentValues.put("tumblerId","1");*/

        Tumbler_grennseedTask tumbler_grennseedTask = new Tumbler_grennseedTask(url,null);
        tumbler_grennseedTask.execute();
    }

    public void go_back(View v){
        finish();
    }

    public void go_2(View v){
        Intent intent = new Intent(Tumbler_greenseed_main.this,Tumbler_greenseed_2.class);
        startActivity(intent);
    }


    public class Tumbler_grennseedTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        Tumbler_grennseedTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        }

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            greenseed = Integer.parseInt(result);

            if(greenseed < 20){
                seedgrade.setText("씨앗 단계");
                content.setText("현재 씨앗은 "+ greenseed+"개 이고, 몇개 더 모으면 새싹 단계가 됩니다.");
            }else if(greenseed >=20 && greenseed < 30){
                seedgrade.setText("새싹 단계");
                content.setText("현재 씨앗은 "+ greenseed+"개 이고, 몇개 더 모으면 묘목 단계가 됩니다.");

            }else if(greenseed >=30 && greenseed < 50){
                seedgrade.setText("묘목 단계");
                content.setText("현재 씨앗은 "+ greenseed+"개 이고, 몇개 더 모으면 나무 단계가 됩니다.");

            }else if(greenseed >=50 && greenseed < 150){
                seedgrade.setText("나무 단계");
                content.setText("현재 씨앗은 "+ greenseed+"개 이고, 몇개 더 모으면 숲 단계가 됩니다.");
                imageView.setImageResource(R.mipmap.grade_tree);

            }else{
                seedgrade.setText("숲 단계");
                content.setText("현재 씨앗은 "+ greenseed+"개 입니다. 환경보호에 동참해주셔서 감사합니다.");
                imageView.setImageResource(R.mipmap.grade_forest);
            }

        }
    }

}
