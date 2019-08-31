package com.example.menulist_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tumbler_alarm extends AppCompatActivity {
    ListView listView;
    ArrayList<Alarm_data> a_arr = new ArrayList<>();
    Tumbler_alarm.alarm_adapter adapter;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler_alarm);

        myApplication = (MyApplication)getApplication();

        listView = (ListView)findViewById(R.id.alarm_listview);
        adapter = new alarm_adapter(this);
        listView.setAdapter(adapter);



        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/main/getMyAlarms/"+myApplication.getAccountId();
        //ContentValues value = new ContentValues();

        AlarmTask networkTask = new AlarmTask(url,null);
        networkTask.execute();
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(a_arr.get(i).getA_type().equals("pay")){
                    Intent intent = new Intent(Tumbler_alarm.this, Tumbler_receipt.class);
                    intent.putExtra("order_id",a_arr.get(i).getA_orderid());
                    startActivity(intent);
                }
            }
        });
    }

    public void go_back (View v){
        finish();
    }


    public class AlarmTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        AlarmTask(String url, ContentValues values){
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
                    a_arr.add(new Alarm_data(jsonObject.getString("alarm_type"),jsonObject.getString("msg"),jsonObject.getString("alarm_time"),jsonObject.getString("order_id")));
                    adapter.notifyDataSetChanged();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    public class RowDataViewHolder {
        public TextView contentHolder;
        public TextView timeHolder;
        public ImageView imageHolder;
        public ImageView image2Holder;
    }

    class alarm_adapter extends ArrayAdapter {
        LayoutInflater lnf;

        public alarm_adapter(Activity context) {
            super(context, R.layout.alarm_item, a_arr);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return a_arr.size();
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return a_arr.get(position);
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Tumbler_alarm.RowDataViewHolder viewHolder;
            if (convertView == null) {
                convertView = lnf.inflate(R.layout.alarm_item, parent, false);
                viewHolder = new RowDataViewHolder();
                viewHolder.contentHolder = (TextView) convertView.findViewById(R.id.a_txt_content);
                viewHolder.timeHolder = (TextView) convertView.findViewById(R.id.a_txt_time);
                viewHolder.imageHolder = (ImageView) convertView.findViewById(R.id.a_image_1);
                viewHolder.image2Holder = (ImageView) convertView.findViewById(R.id.a_image_2);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (Tumbler_alarm.RowDataViewHolder) convertView.getTag();
            }


            viewHolder.contentHolder.setText(a_arr.get(position).a_content);
            viewHolder.timeHolder.setText(a_arr.get(position).a_time);

            if(a_arr.get(position).a_type.equals("pay")){
                viewHolder.imageHolder.setImageResource(R.mipmap.pay_image);
                viewHolder.image2Holder.setImageResource(R.mipmap.go_btn);
            }else if(a_arr.get(position).a_type.equals("lost")){
                viewHolder.imageHolder.setImageResource(R.mipmap.lost_img);
                viewHolder.image2Holder.setVisibility(View.INVISIBLE);
            }else{
                viewHolder.imageHolder.setImageResource(R.mipmap.charge_img);
                viewHolder.image2Holder.setVisibility(View.INVISIBLE);
            }


            return convertView;
        }
    }
}
