package com.vhiefa.nyayur.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vhiefa.nyayur.R;
import com.vhiefa.nyayur.app.TukangSayur;

import java.util.ArrayList;

/**
 * Created by Afifatul on 2016-12-03.
 */

public class TukangSayurAdapter extends BaseAdapter {
    private Activity activity;

    private ArrayList<TukangSayur> data_ts=new ArrayList<TukangSayur>();

    private static LayoutInflater inflater = null;

    public TukangSayurAdapter(Activity a, ArrayList<TukangSayur> d) {
        activity = a; data_ts = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return data_ts.size();
    }
    public Object getItem(int position) {
        return data_ts.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_item_tukang, null);
        TextView id_ = (TextView) vi.findViewById(R.id.id_tukang);
        TextView nama_ = (TextView) vi.findViewById(R.id.nama_tukang);
        TextView nope_ = (TextView) vi.findViewById(R.id.nope_tukang);
        TextView alamat_ = (TextView) vi.findViewById(R.id.alamat_tukang);

        TukangSayur daftar_ts = data_ts.get(position);
        id_.setText(daftar_ts.getTSId());
        nama_.setText(daftar_ts.getTSName());
        nope_.setText(daftar_ts.getTSNope());
        alamat_.setText(daftar_ts.getTSAddres());

        return vi;
    }
}

