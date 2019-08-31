package com.example.menulist_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainPage extends AppCompatActivity {

    TextView txt_nickname;
    TextView txt_starcnt;
    TextView txt_tumbler_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication myApplication = (MyApplication)getApplication();
        myApplication.setServerUrl("52.68.20.62"); //10.149.179.91:8088 희재 서버 // 52.68.20.62 실제 서버
        myApplication.setAccountId("sanghyeon");

        try {
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.d("IDService","device token : "+token);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main_page);

        txt_nickname = (TextView)findViewById(R.id.main_txt_nickname);
        txt_starcnt = (TextView)findViewById(R.id.main_txt_star_cnt);
        txt_tumbler_money = (TextView)findViewById(R.id.main_txt_tumbler_money);

        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/main/accounts/" + myApplication.getAccountId();
        MainPageTask networkTask = new MainPageTask(url,null);
        networkTask.execute();
    }

    public void go_to_personal(View v){
        Intent intent = new Intent(MainPage.this, Personal_list.class);
        startActivity(intent);
    }

    public void go_to_tumbler(View v){
        Intent intent = new Intent(MainPage.this, Tumbler_main.class);
        startActivity(intent);
    }


    public class MainPageTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        MainPageTask(String url, ContentValues values){
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
            try{
                JSONObject jsonObject = new JSONObject(result);
                String account = jsonObject.getString("account");
                JSONObject jsonaccount = new JSONObject(account);
                txt_nickname.setText(jsonaccount.getString("nickname"));
                txt_starcnt.setText(jsonaccount.getString("star_cnt"));
                String tumbler = jsonObject.getString("tumbler");
                JSONObject jsontumbler = new JSONObject(tumbler);
                txt_tumbler_money.setText(jsontumbler.getString("tumbler_Money")+"원");

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
