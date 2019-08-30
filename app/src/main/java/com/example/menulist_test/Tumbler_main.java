package com.example.menulist_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class Tumbler_main extends AppCompatActivity {

    TextView tname;
    TextView tmoney;
    ImageView imageView;
    String img_url;
    String tid;
    int tnow_money;
    Switch aSwitch;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler_main);
        myApplication = (MyApplication)getApplication();

        tname = (TextView)findViewById(R.id.tumble_main_txt_tname);
        tmoney = (TextView)findViewById(R.id.tumbler_main_txt_tmoney);
        imageView = (ImageView)findViewById(R.id.tumbler_main_image);
        aSwitch = (Switch)findViewById(R.id.tumbler_main_switch);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/tumbler/payYn/"+tid;
                //ContentValues value = new ContentValues();

                Tumbler_onoffTask networkTask = new Tumbler_onoffTask(url,null);
                networkTask.execute(); // ONOFF 바꾸기

                url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/tumbler/"+myApplication.getAccountId();
                Tumbler_mainTask networkTask1 = new Tumbler_mainTask(url,null);
                networkTask1.execute(); // 텀블러 상태 리프레쉬

                System.out.println(tid);

            }
        });

        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/tumbler/"+myApplication.getAccountId();
        //ContentValues value = new ContentValues();

        Tumbler_mainTask networkTask = new Tumbler_mainTask(url,null);
        networkTask.execute();
    }

    public void go_back(View v){
        finish();
    }

    public void go_to_addmoney(View v){
        Intent intent = new Intent(Tumbler_main.this,Tumbler_addmoney.class);
        intent.putExtra("tumbler_name",tname.getText());
        intent.putExtra("tumbler_money",tnow_money);
        intent.putExtra("tumbler_image",img_url);
        intent.putExtra("tumbler_id",tid);
        startActivity(intent);
    }

    public void go_to_lost(View v){
        Intent intent = new Intent(Tumbler_main.this,Tumbler_lost.class);
        intent.putExtra("tumbler_name",tname.getText());
        intent.putExtra("tumbler_money",tnow_money);
        intent.putExtra("tumbler_image",img_url);
        intent.putExtra("tumbler_id",tid);
        startActivity(intent);
    }

    public void go_to_grennseed_main(View v){
        Intent intent = new Intent(Tumbler_main.this,Tumbler_greenseed_main.class);
        intent.putExtra("tumbler_name",tname.getText());
        intent.putExtra("tumbler_money",tnow_money);
        intent.putExtra("tumbler_image",img_url);
        intent.putExtra("tumbler_id",tid);
        startActivity(intent);
    }
    public void go_to_alarm(View v){
        Intent intent = new Intent(Tumbler_main.this,Tumbler_alarm.class);
        startActivity(intent);
    }

    public void go_to_nfc(View v){
        Intent intent = new Intent(Tumbler_main.this,Tumbler_NFC.class);
        startActivity(intent);
    }

    public void go_to_personal (View v){
        Intent intent = new Intent(Tumbler_main.this,Personal_list.class);
        startActivity(intent);
    }

    public void go_to_new (View v){
        Intent intent = new Intent(Tumbler_main.this,Tumbler_newTumbler.class);
        startActivity(intent);
    }


    public class Tumbler_mainTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        Tumbler_mainTask(String url, ContentValues values){
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

                JSONArray ja = new JSONArray(result);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jsonObject = ja.getJSONObject(i);
                    tmoney.setText(jsonObject.getString("tumbler_Money")+"원");
                    tname.setText(jsonObject.getString("tumbler_name"));
                    if(jsonObject.getString("pay_yn").equals("true")){
                        aSwitch.setText("결제 기능 ON 상태");
                        aSwitch.setChecked(true);
                    }else{
                        aSwitch.setText("결제 기능 OFF 상태");
                        aSwitch.setChecked(false);
                    }

                    tid = jsonObject.getString("tumbler_id");
                    img_url = jsonObject.getString("image");
                    tnow_money = Integer.parseInt(jsonObject.getString("tumbler_Money"));
                    Picasso.with(getBaseContext())
                            .load(jsonObject.getString("image"))
                            .into(imageView);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    public class Tumbler_onoffTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        Tumbler_onoffTask(String url, ContentValues values){
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
