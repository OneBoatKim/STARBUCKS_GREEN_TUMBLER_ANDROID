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

    TextView account_id;
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

        account_id = (TextView) findViewById(R.id.account_id);
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

                account_id.setText(jsonObject.getString("account_id"));
                order_time.setText(jsonObject.getString("order_time"));
                all_price1.setText(jsonObject.getString("price"));
                all_price2.setText(jsonObject.getString("price"));

                JSONArray jsonArray = jsonObject.getJSONArray("orderList");
//                System.out.println(jsonObject);
//                System.out.println(jsonArray);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectOrderList = jsonArray.getJSONObject(i);
//Tumbler_receipt_data(String private_menu_yn, String shot, String syrup, String whipped_cream, String drizzle, String size, String option_sum, String menu_cnt, String menu_price, String menu_name)
                    arr.add(new Tumbler_receipt_data(jsonObjectOrderList.getString("private_menu_yn"),
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
        TextView shot_cnt;
        TextView syrup_cnt;
        TextView whip_cream_chk;
        TextView drizzle_chk;
        TextView p_optionHolder;
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

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (RowDataViewHolder) convertView.getTag();
            }
//            Log.v("syrup_cnt", String.valueOf(viewHolder.shot_cnt.getText()));
//            System.out.println(viewHolder.toString());

            viewHolder.menu_nameHolder.setText(arr.get(position).menu_name);
            viewHolder.menu_cntHolder.setText(arr.get(position).menu_cnt);
            viewHolder.menu_priceHolder.setText(arr.get(position).menu_price);


            String msg = "";
            int personal_chk = 0;

            // 샷이나 시럽같은것을 추가하면 어떤 품목을 얼마나 추가했는지 보여준다.
            if(String.valueOf(arr.get(position).shot).equals("0")){

            }else{
                msg += "샷";
                msg += "\t\t\t\t\t\t\t\t\t\t";
                msg += arr.get(position).shot;
                personal_chk += Integer.parseInt(arr.get(position).shot);
            }

            if(String.valueOf(arr.get(position).syrup).equals("0")){

            }else{
                msg += "\n시럽";
                msg += "\t\t\t\t\t\t\t\t\t\t";
                msg += arr.get(position).syrup;
                personal_chk += Integer.parseInt(arr.get(position).syrup);
            }

            if(String.valueOf(arr.get(position).whipped_cream).equals("false")){

            }else{
                msg += "\n휘핑";
                msg += "\t\t\t\t\t\t\t\t\t\t";
                personal_chk += 1;
            }

            if(String.valueOf(arr.get(position).drizzle).equals("false")){

            }else{
                msg += "\n드리즐";
                msg += "\t\t\t\t\t\t\t\t\t\t";
                personal_chk += 1;
            }

            if(personal_chk != 0) {
                msg += "\n퍼스널 옵션 합계";
                msg += "\t\t\t\t\t\t\t\t\t\t";
                msg += String.valueOf(600 * personal_chk);
            }

            viewHolder.p_optionHolder.setText(msg);

            return convertView;
        }
    }
}
