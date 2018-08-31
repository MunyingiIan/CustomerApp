package com.ellixar.app.customer.sembe.customerapp.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ellixar.app.customer.sembe.customerapp.R;

import java.util.ArrayList;


public class GridviewAdapter extends BaseAdapter {

    Context context;

    ArrayList<Beanclass> bean;
//    Typeface fonts1, fonts2;


    public GridviewAdapter(Context context, ArrayList<Beanclass> bean) {
        this.bean = bean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        fonts1 = Typeface.createFromAsset(context.getAssets(),
//                "fonts/Lato-Light.ttf");
//
//        fonts2 = Typeface.createFromAsset(context.getAssets(),
//                "fonts/Lato-Regular.ttf");

        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.gridview_adapter_for_product_detail, null);

            viewHolder = new ViewHolder();

            viewHolder.image1 = (ImageView) convertView.findViewById(R.id.image1);
            viewHolder.product_name1 = (TextView) convertView.findViewById(R.id.product_name1);
            viewHolder.packaging_size1 = (TextView) convertView.findViewById(R.id.packaging_size1);
//            viewHolder.sellingprice1 = (TextView) convertView.findViewById(R.id.sellingprice1);
            viewHolder.buying_price1 = (TextView) convertView.findViewById(R.id.buyingPrice1);

//
//            viewHolder.product_name1.setTypeface(fonts2);
//            viewHolder.packaging_size1.setTypeface(fonts1);
//            viewHolder.sellingprice1.setTypeface(fonts2);

            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        Beanclass bean = (Beanclass) getItem(position);

        viewHolder.image1.setImageResource(bean.getImage1());
        viewHolder.product_name1.setText(bean.getProductName1());
        viewHolder.packaging_size1.setText(bean.getProductPacketSize1());
//        viewHolder.sellingprice1.setText(bean.getSellingprice1());
        viewHolder.buying_price1.setText(bean.getProductBuyingPrice1());

        return convertView;
    }

    private class ViewHolder {
        ImageView image1;
        TextView product_name1;
        TextView packaging_size1;
//        TextView sellingprice1;
        TextView buying_price1;

    }
}







//package ws.wolfsoft.get_detail;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//import java.util.ArrayList;
//
///**
// * Created by Rp on 3/30/2016.
// */
//public class GridviewAdapter extends BaseAdapter {
//
//    Context context;
//    ArrayList<Beanclass> beans;
//
//    public GridviewAdapter(Context context,ArrayList<Beanclass> beans) {
//        this.beans = beans;
//        this.context = context;
//    }
//
//
//
//
//    @Override
//    public int getCount() {
//        return beans.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return beans.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        ViewHolder viewHolder = null;
//        if (convertView == null){
//
//            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//
//            viewHolder = new ViewHolder();
//
//            convertView = layoutInflater.inflate(R.layout.gridview_adapter_for_product_detail,null);
//
//
//            viewHolder.image= (ImageView)convertView.findViewById(R.id.image);
//
//
//
//
//            convertView.setTag(viewHolder);
//
//
//        }else {
//
//            viewHolder = (ViewHolder)convertView.getTag();
//
//        }
//
//
//        Beanclass beans = (Beanclass)getItem(position);
//
//        viewHolder.image.setImageResource(beans.getImage());
//        return convertView;
//    }
//
//    private class ViewHolder{
//        ImageView image;
//
//
//    }
//
//}
