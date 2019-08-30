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

import org.json.JSONArray;
import org.json.JSONObject;

public class Tumbler_lost extends AppCompatActivity {

    TextView tname;
    TextView tmoney;
    ImageView imageView;
    int tmoney_now = 0;
    String tid;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler_lost);
        myApplication = (MyApplication)getApplication();

        Intent intent = getIntent();
        tid = intent.getExtras().getString("tumbler_id");
        tname = (TextView)findViewById(R.id.tumbler_lost_tname);
        tmoney = (TextView)findViewById(R.id.tumbler_lost_tmoney);
        imageView = (ImageView)findViewById(R.id.tumbler_lost_img);

        tmoney_now = intent.getExtras().getInt("tumbler_money");

        Picasso.with(this)
                .load(intent.getExtras().getString("tumbler_image"))
                .into(imageView);

        tmoney.setText(String.valueOf(tmoney_now)+ "원");
        tname.setText(intent.getExtras().getString("tumbler_name"));
    }

    public void go_back (View v){
        finish();
    }

    public void tumbler_lost_DB (View v){
        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/tumbler/lost";
        ContentValues contentValues = new ContentValues();
        contentValues.put("tumblerId",tid);

        Tumbler_lostTask tumbler_lostTask = new Tumbler_lostTask(url,contentValues);
        tumbler_lostTask.execute();
        finish();
    }

    public class Tumbler_lostTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        Tumbler_lostTask(String url, ContentValues values){
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

        }
    }
}
