package com.example.menulist_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Tumbler_alarm_receipt extends AppCompatActivity {

    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler_alarm_receipt);

        myApplication = (MyApplication)getApplication();
        Intent intent = getIntent();

        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/orderDetails/getOrder/"+intent.getExtras().getString("order_id");

        //ContentValues value = new ContentValues();
        Alarm_receiptTask networkTask = new Alarm_receiptTask(url,null);
        networkTask.execute();

    }

    public class Alarm_receiptTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        Alarm_receiptTask(String url, ContentValues values){
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

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

        }
    }
}
