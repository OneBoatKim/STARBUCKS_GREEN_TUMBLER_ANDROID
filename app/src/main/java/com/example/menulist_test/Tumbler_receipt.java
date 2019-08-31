package com.example.menulist_test;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;



public class Tumbler_receipt extends AppCompatActivity {

    TextView nickname;
    TextView order_time;
    TextView all_price1;
    TextView all_price2;
    TextView store_name;

    ListView receiptLv;
    ArrayList<Tumbler_receipt_data> arr = new ArrayList<>();
    tumbler_receipt_adapter adapter;

    MyApplication myApplication;

    public void go_back(View v) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler_receipt);
        myApplication = (MyApplication)getApplication();

        Intent intent = getIntent();

        nickname = (TextView) findViewById(R.id.nickname);
        order_time = (TextView) findViewById(R.id.order_time);
        all_price1 = (TextView) findViewById(R.id.all_price1);
        all_price2 = (TextView) findViewById(R.id.all_price2);
        store_name = (TextView) findViewById(R.id.store_name);

        receiptLv = (ListView) findViewById(R.id.menuView);
        adapter = new tumbler_receipt_adapter(this);
        receiptLv.setAdapter(adapter);

        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/orderDetails/getOrder/"+intent.getExtras().getString("order_id");

        ReceiptTask networkTask = new ReceiptTask(url, null);
        networkTask.execute();
        adapter.notifyDataSetChanged();
    }


    public class ReceiptTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        ReceiptTask(String url, ContentValues values) {
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

//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(result);

                nickname.setText(jsonObject.getString("nickname"));
                order_time.setText(jsonObject.getString("order_time"));
                all_price1.setText(jsonObject.getString("price"));
                all_price2.setText(jsonObject.getString("price"));

                JSONArray jsonArray = jsonObject.getJSONArray("orderList");
//                System.out.println(jsonObject);
//                System.out.println(jsonArray);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectOrderList = jsonArray.getJSONObject(i);
//Tumbler_receipt_data(String nickname, String private_menu_yn, String shot, String syrup, String whipped_cream, String drizzle, String size, String option_sum, String menu_cnt, String menu_price, String menu_name)
                    arr.add(new Tumbler_receipt_data(
                            jsonObjectOrderList.getString("private_menu_yn"),
                            jsonObjectOrderList.getString("shot"),
                            jsonObjectOrderList.getString("syrup"),
                            jsonObjectOrderList.getString("whipped_cream"),
                            jsonObjectOrderList.getString("drizzle"),
                            jsonObjectOrderList.getString("size"),
                            jsonObjectOrderList.getString("option_sum"),
                            jsonObjectOrderList.getString("menu_cnt"),
                            jsonObjectOrderList.getString("price"),
                            jsonObjectOrderList.getString("menu_name")
                    ));
//                    System.out.println(jsonObjectOrderList);
//                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public class RowDataViewHolder {

        TextView menu_nameHolder;
        TextView menu_priceHolder;
        TextView menu_cntHolder;
        TextView p_optionHolder;
        TextView p_option_cntHolder;
        TextView p_option_sumHolder;
        TextView p_option_sum_cntHolder;
    }

    class tumbler_receipt_adapter extends ArrayAdapter {

        LayoutInflater lnf;

        public tumbler_receipt_adapter(Activity context) {
            super(context, R.layout.receipt_menu_listview, arr);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            return arr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            RowDataViewHolder viewHolder;

            if (convertView == null) {
                convertView = lnf.inflate(R.layout.receipt_menu_listview, parent, false);
                viewHolder = new RowDataViewHolder();

                viewHolder.menu_nameHolder = (TextView) convertView.findViewById(R.id.menu_name);
                viewHolder.menu_cntHolder = (TextView) convertView.findViewById(R.id.menu_cnt);
                viewHolder.menu_priceHolder = (TextView) convertView.findViewById(R.id.menu_price);

                viewHolder.p_optionHolder = (TextView) convertView.findViewById(R.id.personal_option);
                viewHolder.p_option_cntHolder = (TextView) convertView.findViewById(R.id.personal_option_cnt);
                viewHolder.p_option_sumHolder = (TextView) convertView.findViewById(R.id.personal_option_sum);
                viewHolder.p_option_sum_cntHolder = (TextView) convertView.findViewById(R.id.personal_option_sum_cnt);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (RowDataViewHolder) convertView.getTag();
            }
//            Log.v("syrup_cnt", String.valueOf(viewHolder.shot_cnt.getText()));
//            System.out.println(viewHolder.toString());

            // 우선 viewholder에 값을 넣을 수 있도록 arr에서 데이터 가져옴
            viewHolder.menu_nameHolder.setText(arr.get(position).menu_name);
            viewHolder.menu_cntHolder.setText(arr.get(position).menu_cnt);
            viewHolder.menu_priceHolder.setText(arr.get(position).menu_price);


            String msg = "";
            String msg_cnt = "";
            String msg_sum = "";
            String msg_sum_cnt = "";
            int personal_chk = 0;


            if(String.valueOf(arr.get(position).shot).equals("0")){

            }else{
                msg += "ㄴ샷";
                msg_cnt += arr.get(position).shot;
                personal_chk += Integer.parseInt(arr.get(position).shot);
            }

            if(String.valueOf(arr.get(position).syrup).equals("0")){

            }else{
                if (personal_chk == 0) {
                    msg += "ㄴ시럽";
                    msg_cnt += arr.get(position).syrup;
                } else {
                    msg += "\nㄴ시럽";
                    msg_cnt += "\n" + arr.get(position).syrup;
                }
                personal_chk += Integer.parseInt(arr.get(position).syrup);
            }

            if(String.valueOf(arr.get(position).whipped_cream).equals("false")){

            }else{
                if (personal_chk == 0) {
                    msg += "ㄴ휘핑";
                    msg_cnt += arr.get(position).whipped_cream;
                } else {
                    msg += "\nㄴ휘핑";
                    msg_cnt += "\n" + arr.get(position).whipped_cream;
                }
                personal_chk += 1;
            }

            if(String.valueOf(arr.get(position).drizzle).equals("false")){

            }else{
                if (personal_chk == 0) {
                    msg += "ㄴ드리즐";
                    msg_cnt += arr.get(position).drizzle;
                } else {
                    msg += "\nㄴ드리즐";
                    msg_cnt += "\n" + arr.get(position).drizzle;
                }
                personal_chk += 1;
            }

            if(personal_chk != 0) {
                msg_sum += "ㄴ퍼스널 옵션 합계";
                msg_sum_cnt += String.valueOf(600 * personal_chk);

                viewHolder.p_optionHolder.setText(msg);
                viewHolder.p_option_cntHolder.setText(msg_cnt);
                viewHolder.p_option_sumHolder.setText(msg_sum);
                viewHolder.p_option_sum_cntHolder.setText(msg_sum_cnt);

            } else {
                viewHolder.p_optionHolder.setVisibility(View.GONE);
                viewHolder.p_option_cntHolder.setVisibility(View.GONE);
                viewHolder.p_option_sumHolder.setVisibility((View.GONE));
                viewHolder.p_option_sum_cntHolder.setVisibility((View.GONE));
            }

            return convertView;
        }
    }
}
