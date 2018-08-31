package com.ellixar.app.customer.sembe.customerapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.ellixar.app.customer.sembe.customerapp.AppController.TAG;


public class ListViewBaseAdapter extends BaseAdapter {

    Context context;

    ArrayList<Beanclass>bean;

    SharedPreferences sharedpreferences;
    public static final String MyCartPREFERENCES = "MyCartPrefs" ;

    Typeface fonts1,fonts2;
    public int number;
    private int no_of_items_in_cart;
    String[] setarray = new String[0];

    public int total;

    int[] totalArray;

    private String totalValueOfCart = "0";

    Set<String> set;

    ViewHolder viewHolder;

    private int[] IMAGE = {R.drawable.grooming7,R.drawable.grooming7,R.drawable.grooming7,R.drawable.grooming7};


    private ArrayList<Beanclass> Beanclass;
    private ListViewBaseAdapter baseAdapter;

    public ListViewBaseAdapter(Context context, ArrayList<Beanclass> bean) {


        this.context = context;
        this.bean = bean;
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

        fonts1 =  Typeface.createFromAsset(context.getAssets(),
                "fonts/MavenPro-Regular.ttf");

//        fonts2 = Typeface.createFromAsset(context.getAssets(),
//                "fonts/Lato-Regular.ttf");

        viewHolder = null;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_adapter_for_cart,null);

            viewHolder = new ViewHolder();

            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.description = (TextView)convertView.findViewById(R.id.description);
            viewHolder.selling_price = (TextView)convertView.findViewById(R.id.selling_price);
            viewHolder.sellat_price = (TextView)convertView.findViewById(R.id.sellat);
            viewHolder.min = (ImageView)convertView.findViewById(R.id.min);
            viewHolder.text = (TextView)convertView.findViewById(R.id.text);
            viewHolder.plus = (ImageView)convertView.findViewById(R.id.plus);


            viewHolder.title.setTypeface(fonts1);
            viewHolder.description.setTypeface(fonts1);
            viewHolder.text.setTypeface(fonts1);
            viewHolder.selling_price.setTypeface(fonts1);
            viewHolder.sellat_price.setTypeface(fonts1);

            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }


        sharedpreferences = this.getActivity().getSharedPreferences(MyCartPREFERENCES, Context.MODE_PRIVATE);

        Map<String, ?> allPrefs = sharedpreferences.getAll(); //carts sharedPreference(s)
        set = allPrefs.keySet();
        for (String s : set) {
            Log.d(TAG, s + "<" + allPrefs.get(s).getClass().getSimpleName() + "> =  "
                    + allPrefs.get(s).toString());
        }

        //items in cart
        no_of_items_in_cart = set.size();

        //to help compute total
        total = 0;
        totalArray = new int[no_of_items_in_cart];

        String[] arrayOfItemInCart = new String[no_of_items_in_cart];


        final Beanclass bean = (Beanclass)getItem(position);

        viewHolder.image.setImageResource(bean.getImage1());
        viewHolder.title.setText(bean.getTitle1());
        viewHolder.description.setText(bean.getDescription1());
        viewHolder.text.setText(bean.getQuantityToSell1());
        viewHolder.selling_price.setText(bean.getSellingprice1());
        viewHolder.sellat_price.setText(bean.getSellatprice1());

        // remove while setting text ie quantity to a large value
        //viewHolder.text.setText(""+number);


        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = Integer.parseInt(String.valueOf(finalViewHolder.text.getText()));

                final String prod_name_plus = bean.getTitle1();
                final String prod_id_plus = bean.getDescription1();
                if (number > 1){
                    number = Integer.parseInt(minus(prod_id_plus, prod_name_plus));
                    finalViewHolder.text.setText(""+number);
                }
                else {
                    if (number == 1) {
                        Toast.makeText(getActivity(), "Sorry you cannot sell zero(0) quantity of an item.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int max = Integer.parseInt(bean.getQuantity1());
                number = Integer.parseInt(String.valueOf(finalViewHolder.text.getText()));

                final String prod_name = bean.getTitle1();
                final String prod_id = bean.getDescription1();

                if (number < max) {
                    number = Integer.parseInt(plus(prod_id, prod_name));
                    finalViewHolder1.text.setText("" + number);

                }
                else {
                    if (number == max) {
                        Toast.makeText(getActivity(), "Maximum quantity available in stock reached.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return convertView;
    }

    public Context getActivity() {
        return context;
    }

    public String minus(String product_id_for_clicked, String product_name_for_clicked){

        final String serialized = sharedpreferences.getString(""+product_id_for_clicked+"", ""+product_name_for_clicked+"");

        String updated_product_quantity_for_clicked = null;

        ArrayList cartArray1 = new ArrayList<String>(Arrays.asList(TextUtils.split(serialized, ",")));
        String CurrentString = (String) cartArray1.get(0); // first element
        final String[] no_of_text_separated = CurrentString.split("\\|");

        //number of properties per item in cart
        int no_of_properties_of_each_item = no_of_text_separated.length;

        String prod_id_for_total = null;
        String prod_name_for_total = null;
        String product_selling_price_for_total = null;
        String product_quantity_for_total = null;
        String product_sellat_for_total = null;
        String product_quantity_to_sell_for_total = null;

        String[] separated_for_total;
        for (int j = 0; j < no_of_properties_of_each_item; j++) {
            separated_for_total = CurrentString.split("\\|");

            prod_id_for_total = separated_for_total[0].trim();
            prod_name_for_total = separated_for_total[1].trim();
            product_selling_price_for_total = separated_for_total[2].trim();
            product_quantity_for_total = separated_for_total[3].trim();
            product_sellat_for_total = separated_for_total[4].trim();
            product_quantity_to_sell_for_total = separated_for_total[5].trim();
        }

        updated_product_quantity_for_clicked = String.valueOf(Integer.parseInt(product_quantity_to_sell_for_total)-1);


        final List<String> cartArray = new ArrayList<String>();

        //add to cart: add to shared preferences
        cartArray.add(""+prod_id_for_total+" | "+prod_name_for_total+" | "+product_selling_price_for_total+" | "+product_quantity_for_total+" | "+product_sellat_for_total+" | "+updated_product_quantity_for_clicked+"");

        SharedPreferences.Editor   editor = sharedpreferences.edit();
        editor.putString(String.valueOf(product_id_for_clicked), TextUtils.join(",", cartArray));
        editor.apply();

        //Toast.makeText(getActivity(), "Product "+ prod_name_for_total + " quantity reduced successfully.", Toast.LENGTH_SHORT).show();

        CalculateTotal();

        return updated_product_quantity_for_clicked;
    }


    public String plus(String product_id_for_clicked, String product_name_for_clicked){

        final String serialized = sharedpreferences.getString(""+product_id_for_clicked+"", ""+product_name_for_clicked+"");

        String updated_product_quantity_for_clicked = null;

        ArrayList cartArray1 = new ArrayList<String>(Arrays.asList(TextUtils.split(serialized, ",")));
        String CurrentString = (String) cartArray1.get(0); // first element
        final String[] no_of_text_separated = CurrentString.split("\\|");

        //number of properties per item in cart
        int no_of_properties_of_each_item = no_of_text_separated.length;

        String prod_id_for_total = null;
        String prod_name_for_total = null;
        String product_selling_price_for_total = null;
        String product_quantity_for_total = null;
        String product_sellat_for_total = null;
        String product_quantity_to_sell_for_total = null;

        String[] separated_for_total;
        for (int j = 0; j < no_of_properties_of_each_item; j++) {
            separated_for_total = CurrentString.split("\\|");

            prod_id_for_total = separated_for_total[0].trim();
            prod_name_for_total = separated_for_total[1].trim();
            product_selling_price_for_total = separated_for_total[2].trim();
            product_quantity_for_total = separated_for_total[3].trim();
            product_sellat_for_total = separated_for_total[4].trim();
            product_quantity_to_sell_for_total = separated_for_total[5].trim();
        }
        updated_product_quantity_for_clicked = String.valueOf(Integer.parseInt(product_quantity_to_sell_for_total)+1);

        final List<String> cartArray = new ArrayList<String>();

        //add to cart: add to shared preferences
        cartArray.add(""+prod_id_for_total+" | "+prod_name_for_total+" | "+product_selling_price_for_total+" | "+product_quantity_for_total+" | "+product_sellat_for_total+" | "+updated_product_quantity_for_clicked+"");

        SharedPreferences.Editor   editor = sharedpreferences.edit();
        editor.putString(String.valueOf(product_id_for_clicked), TextUtils.join(",", cartArray));
        editor.apply();


        //Toast.makeText(getActivity(), "Product "+ prod_name_for_total + " quantity added successfully.", Toast.LENGTH_SHORT).show();

        CalculateTotal();

        return updated_product_quantity_for_clicked;
    }

    public void CalculateTotal(){
        //reset total to zero
        total = 0;

        //Initialize a new bean class to repopulate the listview on add or reduce
        Beanclass = new ArrayList<Beanclass>();

        //Initialize base adapter to populate list view on data change
        baseAdapter = new ListViewBaseAdapter(getActivity(), Beanclass) {
        };

        //Initialize listview od the activity
        ListView listview = (ListView) ((Activity)context).findViewById(R.id.listview);
        listview.setAdapter(baseAdapter);

        if(no_of_items_in_cart != 0){
            //create string array setarray
            setarray = set.toArray(new String[no_of_items_in_cart]);


            String serialized = sharedpreferences.getString("" + setarray[0] + "", "" + setarray[0] + "");
            ArrayList cartArray1 = new ArrayList<String>(Arrays.asList(TextUtils.split(serialized, ",")));
            String CurrentString = (String) cartArray1.get(0); // first element
            final String[] no_of_text_separated = CurrentString.split("\\|");

            //number of properties per item in cart
            int no_of_properties_of_each_item = no_of_text_separated.length;

            for (int i = 0; i < no_of_items_in_cart; i++) {
                serialized = sharedpreferences.getString("" + setarray[i] + "", "" + setarray[i] + "");
                cartArray1 = new ArrayList<String>(Arrays.asList(TextUtils.split(serialized, ",")));
                CurrentString = (String) cartArray1.get(0);

                String[] prod_id_to_calculate_total = new String[0];
                String[] prod_name_to_calculate_total = new String[0];
                String[] product_selling_price_to_calculate_total = new String[0];
                String[] product_quantity_to_calculate_total = new String[0];
                String[] product_sellat_to_calculate_total = new String[0];
                String[] product_quantity_to_sell_to_calculate_total = new String[0];
                String product_status_to_calculate_total = String.valueOf(1);

                String[] separated;
                for (int j = 0; j < no_of_properties_of_each_item; j++) {
                    separated = CurrentString.split("\\|");

                    prod_id_to_calculate_total = new String[no_of_items_in_cart];
                    prod_name_to_calculate_total = new String[no_of_items_in_cart];
                    product_selling_price_to_calculate_total = new String[no_of_items_in_cart];
                    product_quantity_to_calculate_total = new String[no_of_items_in_cart];
                    product_sellat_to_calculate_total = new String[no_of_items_in_cart];
                    product_quantity_to_sell_to_calculate_total = new String[no_of_items_in_cart];

                    prod_id_to_calculate_total[i] = separated[0].trim();
                    prod_name_to_calculate_total[i] = separated[1].trim();
                    product_selling_price_to_calculate_total[i] = separated[2].trim();
                    product_quantity_to_calculate_total[i] = separated[3].trim();
                    product_sellat_to_calculate_total[i] = separated[4].trim();
                    product_quantity_to_sell_to_calculate_total[i] = separated[5].trim();

                    if (Float.valueOf(product_quantity_to_calculate_total[i]) == 0){
                        product_status_to_calculate_total = "0";
                    }else {
                        product_status_to_calculate_total = "1";
                    }

                }
                //Removing white spaces
                //product_selling_price[i] = product_selling_price[i].replaceAll("\\s","");
                int sellAtByQuantityToSellAt = (int) (Float.valueOf(product_sellat_to_calculate_total[i])*Float.valueOf(product_quantity_to_sell_to_calculate_total[i]));
                total = total + sellAtByQuantityToSellAt;
                totalArray[i] = total;

                //Add kes to selling price
                String sellat_price_display_to_calculate_total = "SELL AT: KES " + product_sellat_to_calculate_total[i] + ".00";


                //Add kes to selling price
                String selling_price_display_to_calculate_total = "SP: KES " + product_selling_price_to_calculate_total[i] + ".00";

                //Actual cart item populating
                Beanclass bean = new Beanclass(IMAGE[0], prod_name_to_calculate_total[i], prod_id_to_calculate_total[i], selling_price_display_to_calculate_total, sellat_price_display_to_calculate_total, product_quantity_to_calculate_total[i], product_quantity_to_sell_to_calculate_total[i], product_status_to_calculate_total);
                Beanclass.add(bean);

            }
            totalValueOfCart = String.valueOf(totalArray[totalArray.length-1]);

            String total_price_display = "KES " + totalValueOfCart + ".00";

            TextView txtView = (TextView) ((Activity)context).findViewById(R.id.total_cart_value);
            txtView.setText(total_price_display);

            //notify baseadapter of listview that cart has been emptied
            baseAdapter.notifyDataSetChanged();
        }

    }

    private class ViewHolder{
        ImageView image;
        TextView title;
        TextView description;
        TextView selling_price;
        TextView sellat_price;
        ImageView min;
        TextView text;
        ImageView plus;
        

    }
}




