package com.example.menulist_test;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Personal_menu extends AppCompatActivity {

    MyAdapter adapter;
    MyAdapter2 adapter2;
    MyAdapter3 adapter3;
    MyAdapter4 adapter4;
    MyAdapter5 adapter5;
    ArrayList<Menu_data> arr1 = new ArrayList<>();
    ArrayList<Menu_data> arr2 = new ArrayList<>();
    ArrayList<Menu_data> arr3 = new ArrayList<>();
    ArrayList<Menu_data> arr4 = new ArrayList<>();
    ArrayList<Menu_data> arr5 = new ArrayList<>();


    ListView mainLv;
    ListView mainLv2;
    ListView mainLv3;
    ListView mainLv4;
    ListView mainLv5;
    MyApplication myApplication;

    public void go_back(View v){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_menu);
        myApplication = (MyApplication)getApplication();

        mainLv = (ListView) findViewById(R.id.list_view_es);
        mainLv2 = (ListView) findViewById(R.id.list_view_fr);
        mainLv3 = (ListView) findViewById(R.id.list_view_bl);
        mainLv4 = (ListView) findViewById(R.id.list_view_fz);
        mainLv5 = (ListView) findViewById(R.id.list_view_tea);

        adapter = new MyAdapter(this);
        adapter2 = new MyAdapter2(this);
        adapter3 = new MyAdapter3(this);
        adapter4 = new MyAdapter4(this);
        adapter5 = new MyAdapter5(this);

        mainLv.setAdapter(adapter);
        mainLv2.setAdapter(adapter2);
        mainLv3.setAdapter(adapter3);
        mainLv4.setAdapter(adapter4);
        mainLv5.setAdapter(adapter5);

        init();

        mainLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Personal_menu.this, Personal_add.class);

                String price = arr1.get(i).getPrice();
                int imgsrc = arr1.get(i).getResId();
                intent.putExtra("price", price);
                intent.putExtra("imgsrc",imgsrc);
                String id = arr1.get(i).getMenuId();
                intent.putExtra("id",id);

                startActivity(intent);
            }
        });

        mainLv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Personal_menu.this, Personal_add.class);

                String price = arr2.get(i).getPrice();
                int imgsrc = arr2.get(i).getResId();
                intent.putExtra("price", price);
                intent.putExtra("imgsrc",imgsrc);
                String id = arr2.get(i).getMenuId();
                intent.putExtra("id",id);

                startActivity(intent);
            }
        });

        mainLv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Personal_menu.this, Personal_add.class);

                String price = arr3.get(i).getPrice();
                int imgsrc = arr3.get(i).getResId();
                intent.putExtra("price", price);
                intent.putExtra("imgsrc",imgsrc);
                String id = arr3.get(i).getMenuId();
                intent.putExtra("id",id);

                startActivity(intent);
            }
        });

        mainLv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Personal_menu.this, Personal_add.class);

                String price = arr4.get(i).getPrice();
                int imgsrc = arr4.get(i).getResId();
                intent.putExtra("price", price);
                intent.putExtra("imgsrc",imgsrc);
                String id = arr4.get(i).getMenuId();
                intent.putExtra("id",id);

                startActivity(intent);
            }
        });

        mainLv5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Personal_menu.this, Personal_add.class);

                String price = arr5.get(i).getPrice();
                int imgsrc = arr5.get(i).getResId();
                intent.putExtra("price", price);
                intent.putExtra("imgsrc",imgsrc);
                String id = arr5.get(i).getMenuId();
                intent.putExtra("id",id);

                startActivity(intent);
            }
        });

    }

    public void call_list1(View v){
        mainLv.setVisibility(View.VISIBLE);
        mainLv2.setVisibility(View.INVISIBLE);
        mainLv3.setVisibility(View.INVISIBLE);
        mainLv4.setVisibility(View.INVISIBLE);
        mainLv5.setVisibility(View.INVISIBLE);
    }

    public void call_list2(View v){
        mainLv.setVisibility(View.INVISIBLE);
        mainLv2.setVisibility(View.VISIBLE);
        mainLv3.setVisibility(View.INVISIBLE);
        mainLv4.setVisibility(View.INVISIBLE);
        mainLv5.setVisibility(View.INVISIBLE);
    }

    public void call_list3(View v){
        mainLv.setVisibility(View.INVISIBLE);
        mainLv2.setVisibility(View.INVISIBLE);
        mainLv3.setVisibility(View.VISIBLE);
        mainLv4.setVisibility(View.INVISIBLE);
        mainLv5.setVisibility(View.INVISIBLE);
    }

    public void call_list4(View v){
        mainLv.setVisibility(View.INVISIBLE);
        mainLv2.setVisibility(View.INVISIBLE);
        mainLv3.setVisibility(View.INVISIBLE);
        mainLv4.setVisibility(View.VISIBLE);
        mainLv5.setVisibility(View.INVISIBLE);
    }

    public void call_list5(View v){
        mainLv.setVisibility(View.INVISIBLE);
        mainLv2.setVisibility(View.INVISIBLE);
        mainLv3.setVisibility(View.INVISIBLE);
        mainLv4.setVisibility(View.INVISIBLE);
        mainLv5.setVisibility(View.VISIBLE);
    }

    private void init(){
        arr1.add(new Menu_data("4100", R.mipmap.americano_es,"1"));
        arr1.add(new Menu_data("5100",R.mipmap.caffe_moca_es,"3"));
        arr1.add(new Menu_data("3600",R.mipmap.espresso_es,"2"));
        arr1.add(new Menu_data("5600", R.mipmap.dolce_latte_es,"6"));
        arr1.add(new Menu_data("4600", R.mipmap.caffe_latte_es,"4"));
        arr1.add(new Menu_data("4600", R.mipmap.cappuccino_es,"5"));

        arr2.add(new Menu_data("6300",R.mipmap.almond_mocha_fr,"8"));
        arr2.add(new Menu_data("5100",R.mipmap.espresso_fr,"9"));
        arr2.add(new Menu_data("5600", R.mipmap.mocha_fr,"7"));
        arr2.add(new Menu_data("4800", R.mipmap.vanilla_cream_fr,"11"));

        arr3.add(new Menu_data("6100",R.mipmap.decaf_affogato_bl,"12"));
        arr3.add(new Menu_data("6300", R.mipmap.mango_banana_bl,"16"));
        arr3.add(new Menu_data("7200",R.mipmap.jeju_walnut_bl,"14"));
        arr3.add(new Menu_data("6100", R.mipmap.strawberry_yogurt_bl,"15"));
        arr3.add(new Menu_data("5000", R.mipmap.mango_passion_bl,"13"));

        arr4.add(new Menu_data("5400",R.mipmap.black_tea_lemon_fz,"17"));
        arr4.add(new Menu_data("5900",R.mipmap.cool_lime_fz,"18"));
        arr4.add(new Menu_data("6300", R.mipmap.pink_grapefruit_fz,"19"));
        arr4.add(new Menu_data("5400", R.mipmap.passion_tango_fz,"20"));

        arr5.add(new Menu_data("5600",R.mipmap.byuldabang_tea,"22"));
        arr5.add(new Menu_data("7500",R.mipmap.grandma_apple_tea,"24"));
        arr5.add(new Menu_data("5600", R.mipmap.lime_passion_tea,"25"));
        arr5.add(new Menu_data("6100", R.mipmap.matcha_lemon_tea,"21"));
        arr5.add(new Menu_data("6100", R.mipmap.pinkberry_youth_tea,"23"));

        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
        adapter4.notifyDataSetChanged();
        adapter5.notifyDataSetChanged();
    }

    public class RowDataViewHolder {
        public ImageView humanIvHoler;
    }

    class MyAdapter extends ArrayAdapter {
                LayoutInflater lnf;

                public MyAdapter(Activity context) {
                    super(context, R.layout.menu_item, arr1);
                    lnf = (LayoutInflater) context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                }

                @Override
                public int getCount() {
// TODO Auto-generated method stub
                    return arr1.size();
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return arr1.get(position);
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            RowDataViewHolder viewHolder;
            if (convertView == null) {
                convertView = lnf.inflate(R.layout.menu_item, parent, false);
                viewHolder = new RowDataViewHolder();

                viewHolder.humanIvHoler = (ImageView) convertView.findViewById(R.id.menu_image);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RowDataViewHolder) convertView.getTag();
            }


            viewHolder.humanIvHoler.setImageResource(arr1.get(position).resId);

            return convertView;
        }
    }


    class MyAdapter2 extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter2(Activity context) {
            super(context, R.layout.menu_item, arr2);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return arr2.size();
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return arr2.get(position);
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            RowDataViewHolder viewHolder;
            if (convertView == null) {
                convertView = lnf.inflate(R.layout.menu_item, parent, false);
                viewHolder = new RowDataViewHolder();

                viewHolder.humanIvHoler = (ImageView) convertView.findViewById(R.id.menu_image);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RowDataViewHolder) convertView.getTag();
            }


            viewHolder.humanIvHoler.setImageResource(arr2.get(position).resId);

            return convertView;
        }
    }

    class MyAdapter3 extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter3(Activity context) {
            super(context, R.layout.menu_item, arr3);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return arr3.size();
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return arr3.get(position);
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            RowDataViewHolder viewHolder;
            if (convertView == null) {
                convertView = lnf.inflate(R.layout.menu_item, parent, false);
                viewHolder = new RowDataViewHolder();

                viewHolder.humanIvHoler = (ImageView) convertView.findViewById(R.id.menu_image);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RowDataViewHolder) convertView.getTag();
            }


            viewHolder.humanIvHoler.setImageResource(arr3.get(position).resId);

            return convertView;
        }
    }

    class MyAdapter4 extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter4(Activity context) {
            super(context, R.layout.menu_item, arr4);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return arr4.size();
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return arr4.get(position);
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            RowDataViewHolder viewHolder;
            if (convertView == null) {
                convertView = lnf.inflate(R.layout.menu_item, parent, false);
                viewHolder = new RowDataViewHolder();

                viewHolder.humanIvHoler = (ImageView) convertView.findViewById(R.id.menu_image);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RowDataViewHolder) convertView.getTag();
            }


            viewHolder.humanIvHoler.setImageResource(arr4.get(position).resId);

            return convertView;
        }
    }

    class MyAdapter5 extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter5(Activity context) {
            super(context, R.layout.menu_item, arr2);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return arr5.size();
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return arr5.get(position);
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            RowDataViewHolder viewHolder;
            if (convertView == null) {
                convertView = lnf.inflate(R.layout.menu_item, parent, false);
                viewHolder = new RowDataViewHolder();

                viewHolder.humanIvHoler = (ImageView) convertView.findViewById(R.id.menu_image);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RowDataViewHolder) convertView.getTag();
            }


            viewHolder.humanIvHoler.setImageResource(arr5.get(position).resId);

            return convertView;
        }
    }
}


