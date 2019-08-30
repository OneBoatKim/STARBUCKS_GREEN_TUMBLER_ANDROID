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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;



public class Personal_list extends AppCompatActivity {

    ListView listView;
    ArrayList<Personal_data> p_arr = new ArrayList<>();
    personal_adapter adapter;
    MyApplication myApplication;

    public void go_plus_pmenu(View v){
        Intent intent = new Intent(Personal_list.this, Personal_menu.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_list);
        myApplication = (MyApplication)getApplication();

        listView = (ListView)findViewById(R.id.person_list);
        adapter = new personal_adapter(this);
        listView.setAdapter(adapter);

        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/myMenu/getMyMenus/"+myApplication.getAccountId();
        //ContentValues value = new ContentValues();

        Personal_listTask networkTask = new Personal_listTask(url,null);
        networkTask.execute();
        adapter.notifyDataSetChanged();


    }


    public class Personal_listTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        Personal_listTask(String url, ContentValues values){
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
                    p_arr.add(new Personal_data(jsonObject.getString("cup"),jsonObject.getString("account_id"),jsonObject.getString("menu_id"),jsonObject.getString("shot"),jsonObject.getString("syrup"),jsonObject.getString("whipped_cream"),jsonObject.getString("drizzle"),jsonObject.getString("size"),jsonObject.getString("option_sum"),jsonObject.getString("price"),jsonObject.getString("menu_name"),jsonObject.getString("image")));
                    adapter.notifyDataSetChanged();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void go_back (View v){
        finish();
    }


    public class RowDataViewHolder {
        public TextView cupHolder;
        //public TextView account_idHolder;
        //public TextView menu_idHolder;
        public TextView shotHolder;
        //public TextView syrupHolder;
        //public TextView whipped_creamHolder;
        //public TextView drizzleHolder;
        //public TextView sizeHolder;
        //public TextView option_sumHolder;
        public TextView priceHolder;
        public TextView menu_nameHolder;
        public ImageView imageHolder;
    }

    class personal_adapter extends ArrayAdapter {
        LayoutInflater lnf;

        public personal_adapter(Activity context) {
            super(context, R.layout.personal_item, p_arr);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return p_arr.size();
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return p_arr.get(position);
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            RowDataViewHolder viewHolder;
            if (convertView == null) {
                convertView = lnf.inflate(R.layout.personal_item, parent, false);
                viewHolder = new RowDataViewHolder();
                viewHolder.cupHolder = (TextView) convertView.findViewById(R.id.p_cup);
                //viewHolder.account_idHolder = (TextView) convertView.findViewById(R.id.p_account_id);
                //viewHolder.menu_idHolder = (TextView) convertView.findViewById(R.id.p_menu_id);
                viewHolder.shotHolder = (TextView) convertView.findViewById(R.id.p_shot);
                //viewHolder.syrupHolder = (TextView) convertView.findViewById(R.id.p_syrup);
                //viewHolder.whipped_creamHolder = (TextView) convertView.findViewById(R.id.p_whipped_cream);
                //viewHolder.drizzleHolder = (TextView) convertView.findViewById(R.id.p_drizzle);
                //viewHolder.sizeHolder = (TextView) convertView.findViewById(R.id.p_size);
                //viewHolder.option_sumHolder = (TextView) convertView.findViewById(R.id.p_option_sum);
                viewHolder.priceHolder = (TextView) convertView.findViewById(R.id.p_price);
                viewHolder.menu_nameHolder = (TextView) convertView.findViewById(R.id.p_menu_name);
                viewHolder.imageHolder = (ImageView) convertView.findViewById(R.id.p_image);



                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RowDataViewHolder) convertView.getTag();
            }
            int total_price;
            String total_price_str;
            String size_cup = "텀블러 / ";
            String personal_op = "";
            if(p_arr.get(position).size.equals("T")){
                size_cup += "Tall";
            }else if (p_arr.get(position).size.equals("G")){
                size_cup += "Grande";
            }else{
                size_cup += "Venti";
            }

            total_price = Integer.parseInt(p_arr.get(position).price);
            total_price_str = String.valueOf(total_price) + "원";
            personal_op = "샷 " + p_arr.get(position).shot + ", 시럽 " + p_arr.get(position).syrup;

            if(p_arr.get(position).whipped_cream == "false"){
                personal_op += ", 휘핑 x";
            }else{
                personal_op += ", 휘핑 o";
            }

            if(p_arr.get(position).drizzle == "false"){
                personal_op += ", 드리즐 x";
            }else{
                personal_op += ", 드리즐 o";
            }

            viewHolder.cupHolder.setText(size_cup);
            viewHolder.shotHolder.setText(personal_op);
            viewHolder.priceHolder.setText(total_price_str);
            viewHolder.menu_nameHolder.setText(p_arr.get(position).menu_name);

            Picasso.with(getContext())
                    .load(p_arr.get(position).image)
                    .into((viewHolder.imageHolder));
            return convertView;
        }
    }
}
