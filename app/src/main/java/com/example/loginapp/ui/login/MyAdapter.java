package com.example.loginapp.ui.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loginapp.R;

import java.util.List;
/**
 *
 * Esta clase contiene atributos y metodos de MyAdapter
 * @author Rim
 * @version 12
 * @since 02/04/2022
 *
 */
public class MyAdapter extends BaseAdapter {
    Button  Registrar;
    Context context;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_login, parent, false);
       // ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);
        //textView.setText(cartas.get(position).getName());

       // imageView.setImageResource(cartas.get(position).getFoto());

        return  view;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
