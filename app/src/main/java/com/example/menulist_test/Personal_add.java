package com.example.menulist_test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Personal_add extends AppCompatActivity {

    Spinner spinner1;
    Spinner spinner2;
    CheckBox whip_box;
    CheckBox drizzle_box;
    String size_data[] = {"Tall","Grande","Venti"};
    String id;
    String price;
    TextView price_text;
    TextView mid_text;
    TextView mname_text;
    TextView size_text;
    String img_url;
    Personal_data p_data;
    ConstraintLayout constraintLayout1;
    ConstraintLayout constraintLayout2;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    String shot;
    String syrub;
    String whip;
    String drizzle;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_add);
        myApplication = (MyApplication)getApplication();


        price_text = (TextView)findViewById(R.id.p_add_price);
        mid_text = (TextView)findViewById(R.id.p_add_id);
        mname_text = (TextView)findViewById(R.id.p_add_name);
        size_text = (TextView)findViewById(R.id.p_add_size);
        whip_box = (CheckBox)findViewById(R.id.whip_checkBox) ;
        drizzle_box = (CheckBox)findViewById(R.id.drizzle_checkBox);

        arrayList = new ArrayList<>();
        arrayList.add("0");
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList);

        spinner1 = (Spinner)findViewById(R.id.shot_spinner);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {int shot_price;
                shot = arrayList.get(i);
                p_data.setShot(shot);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner2 = (Spinner)findViewById(R.id.syrub_spinner);
        spinner2.setAdapter(arrayAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                syrub = arrayList.get(i);
                p_data.setSyrup(syrub);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        constraintLayout1 = (ConstraintLayout)findViewById(R.id.register_layout);
        constraintLayout2 = (ConstraintLayout)findViewById(R.id.personal_op_layout);

        Intent intent = getIntent();
        price = intent.getExtras().getString("price") + "원";
        id = intent.getExtras().getString("id");

        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/myMenu/menus/" + id;
        Personal_addTask networkTask = new Personal_addTask(url,null);
        networkTask.execute();

        price_text.setText(price);
        mid_text.setText(id);

        p_data = new Personal_data("T",myApplication.getAccountId(),id,"0","0","false","false","T","0",price,"","");
    }

    public void whip_checked (View v){
        if (whip_box.isChecked()){
            p_data.setWhipped_cream("true");
        }else{
            p_data.setWhipped_cream("false");
        }
    }

    public void drizzle_checked (View v){
        if (drizzle_box.isChecked()){
            p_data.setDrizzle("true");
        }else{
            p_data.setDrizzle("false");
        }
    }

    public void go_register_layout (View v){
        constraintLayout1.setVisibility(View.VISIBLE);
        constraintLayout2.setVisibility(View.INVISIBLE);
    }

    public void p_add_data_to_db(View v){
        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/myMenu/create";
        ContentValues contentValues = new ContentValues();
        contentValues.put("cup",p_data.getCup());
        contentValues.put("account_id",p_data.getAccount_id());
        contentValues.put("menu_id",p_data.getMenu_id());
        contentValues.put("shot",p_data.getShot());
        contentValues.put("syrup",p_data.getSyrup());
        contentValues.put("whipped_cream",p_data.getWhipped_cream());
        contentValues.put("drizzle",p_data.getDrizzle());
        contentValues.put("size",p_data.getSize());
        Personal_add_toDBTask networkTask = new Personal_add_toDBTask(url,contentValues);
        networkTask.execute();

        Intent intent = new Intent(Personal_add.this, Personal_list.class);
        startActivity(intent);
        finish();
    }

    public void select_size(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(Personal_add.this);
        builder.setTitle("사이즈를 선택해주세요.");
        builder.setSingleChoiceItems(size_data,0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(i == 0){
                    p_data.setSize("T");
                    size_text.setText("Tall");
                }else if(i == 1){
                    p_data.setSize("G");
                    size_text.setText("Grande");
                }else{
                    p_data.setSize("V");
                    size_text.setText("Venti");
                }

            }
        });


        builder.setPositiveButton("확인",null);
        builder.setNegativeButton("취소",null);
        builder.create().show();
    }

    public class Personal_addTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        Personal_addTask(String url, ContentValues values){
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

                /*JSONArray ja = new JSONArray(result);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jsonObject = ja.getJSONObject(i);
                    img_url = jsonObject.getString("image");
                }*/
                JSONObject jsonObject = new JSONObject(result);
                img_url = jsonObject.getString("image");
                mname_text.setText(jsonObject.getString("menu_name"));
                System.out.println(img_url);

                Picasso.with(getBaseContext())
                        .load(img_url)
                        .into((ImageView)findViewById(R.id.p_add_menu));
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    public class Personal_add_toDBTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        Personal_add_toDBTask(String url, ContentValues values){
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

    public void go_back (View v){
        finish();
   }

    public void go_to_personal_op(View v){
        constraintLayout1.setVisibility(View.INVISIBLE);
        constraintLayout2.setVisibility(View.VISIBLE);
    }

    public void close_op (View v){
        constraintLayout1.setVisibility(View.VISIBLE);
        constraintLayout2.setVisibility(View.INVISIBLE);
    }
}
