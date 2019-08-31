package com.example.menulist_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Tumbler_addmoney extends AppCompatActivity {

    int tmoney_charge = 0;
    int tmoney_now = 0;
    int tmoney_after = 0;
    TextView txt_tmoney_after;
    TextView txt_tmoney_now;
    TextView txt_tmoney_name;
    ImageView imageView;
    MyApplication myApplication;
    String tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler_addmoney);
        myApplication = (MyApplication)getApplication();

        Intent intent = getIntent();
        tmoney_now = intent.getExtras().getInt("tumbler_money");
        tid = intent.getExtras().getString("tumbler_id");
        imageView = (ImageView)findViewById(R.id.tumbler_addmoney_image);

        Picasso.with(this)
                .load(intent.getExtras().getString("tumbler_image"))
                .into(imageView);

        txt_tmoney_after = (TextView)findViewById(R.id.tumbler_addmoney_txt_tumbler_money_after);
        txt_tmoney_now = (TextView)findViewById(R.id.tumbler_addmoney_txt_tumbler_money_now);
        txt_tmoney_name = (TextView)findViewById(R.id.tumbler_addmoney_txt_tumbler_name);

        txt_tmoney_now.setText(String.valueOf(tmoney_now)+ "원");
        txt_tmoney_name.setText(intent.getExtras().getString("tumbler_name"));
        txt_tmoney_after.setText(String.valueOf(tmoney_now)+ "원");
    }

    public void go_back (View v){
        finish();
    }

    public void charge_money (View v){
        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/tumbler/charge/" + String.valueOf(tmoney_after);
        ContentValues contentValues = new ContentValues();
        contentValues.put("tumbler_id",tid);

        Tumbler_addmoneyTask tumbler_lostTask = new Tumbler_addmoneyTask(url,contentValues);
        tumbler_lostTask.execute();
        Intent intent = new Intent(Tumbler_addmoney.this, Tumbler_main.class);
        startActivity(intent);
        finish();

    }

    public void click_1won(View v) {
        tmoney_charge = 10000;
        tmoney_after = tmoney_now + tmoney_charge;
        txt_tmoney_after.setText(String.valueOf(tmoney_after) + "원");
    }
    public void click_3won(View v) {
        tmoney_charge = 30000;
        tmoney_after = tmoney_now + tmoney_charge;
        txt_tmoney_after.setText(String.valueOf(tmoney_after) + "원");
    }
    public void click_5won(View v) {
        tmoney_charge = 50000;
        tmoney_after = tmoney_now + tmoney_charge;
        txt_tmoney_after.setText(String.valueOf(tmoney_after) + "원");
    }
    public void click_7won(View v) {
        tmoney_charge = 70000;
        tmoney_after = tmoney_now + tmoney_charge;
        txt_tmoney_after.setText(String.valueOf(tmoney_after) + "원");
    }
    public void click_10won(View v) {
        tmoney_charge = 100000;
        tmoney_after = tmoney_now + tmoney_charge;
        txt_tmoney_after.setText(String.valueOf(tmoney_after) + "원");
    }
    public void click_20won(View v) {
        tmoney_charge = 200000;
        tmoney_after = tmoney_now + tmoney_charge;
        txt_tmoney_after.setText(String.valueOf(tmoney_after) + "원");
    }

    public class Tumbler_addmoneyTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        Tumbler_addmoneyTask(String url, ContentValues values){
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

            if(result.equals("1")){
                Toast.makeText(getApplicationContext(), "금액이 충전되었습니다.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "잘못된 접근입니다.", Toast.LENGTH_LONG).show();
            }

        }
    }
}
