package com.example.milkiminz.raddilo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Milki Minz on 2/27/2017.
 */

public class myorder extends ArrayAdapter<String> {

        Activity context;
        String[] name,email,phone,add,qty,ppr,gls,pls,mel,oth;

        TextView textViewName,textViewEmail,textViewPhone,textViewAdd,textViewQty,textViewPpr,textViewMel,textViewGls,textViewPls,textViewOth;

public myorder(Activity context, String[] name, String[] email, String[] phone, String[] add,String[] qty,String[] ppr,String[] gls,String[] mel,String[] oth,String[] pls) {
        super(context, R.layout.myorder, name);
        this.context = context;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.add = add;
        this.qty=qty;
        this.ppr=ppr;
        this.mel=mel;
        this.gls=gls;
        this.pls=pls;
        this.oth=oth;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.myorder,null,true);

        textViewName = (TextView)view.findViewById(R.id.shopname);
        textViewEmail = (TextView)view.findViewById(R.id.shopemail);
        textViewPhone = (TextView)view.findViewById(R.id.shopphone);
        textViewAdd = (TextView)view.findViewById(R.id.shopadd);
        textViewQty=(TextView) view.findViewById(R.id.custitem);
        textViewPpr=(TextView)view.findViewById(R.id.paper);
        textViewMel=(TextView)view.findViewById(R.id.metal);
        textViewGls=(TextView)view.findViewById(R.id.glass);
        textViewPls=(TextView)view.findViewById(R.id.plastic);
        textViewOth=(TextView)view.findViewById(R.id.others);

        textViewName.setText(name[position]);
        textViewEmail.setText(email[position]);
        textViewPhone.setText(phone[position]);
        textViewAdd.setText(add[position]);
        textViewQty.setText(qty[position]);
        textViewPpr.setText(ppr[position]);
        textViewMel.setText(mel[position]);
        textViewGls.setText(gls[position]);
        textViewPls.setText(pls[position]);
        textViewOth.setText(oth[position]);


        return view;
        }
        }
