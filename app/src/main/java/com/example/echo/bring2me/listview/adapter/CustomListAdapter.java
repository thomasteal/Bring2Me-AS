package com.example.echo.bring2me.listview.adapter;

/**
 * Created by thomas on 16/09/16.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.echo.bring2me.AppController;
import com.example.echo.bring2me.R;
import com.example.echo.bring2me.listview.model.Viagem;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
        private Activity activity;
        private LayoutInflater inflater;
        private List<Viagem> viagemItems;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        public CustomListAdapter(Activity activity, List<Viagem> viagemItems) {
                this.activity = activity;
                this.viagemItems = viagemItems;
        }

        @Override
        public int getCount() {
                return viagemItems.size();
        }

        @Override
        public Object getItem(int location) {
                return viagemItems.get(location);
        }

        @Override
        public long getItemId(int position) {
                return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

                if (inflater == null)
                        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (convertView == null)
                        convertView = inflater.inflate(R.layout.list_row, null);

                if (imageLoader == null)
                        imageLoader = AppController.getInstance().getImageLoader();

                NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
                TextView origemTV = (TextView) convertView.findViewById(R.id.mostraorigem);
                TextView destinoTV = (TextView) convertView.findViewById(R.id.mostradestino);
                TextView avaliacaoViajanteTV = (TextView) convertView.findViewById(R.id.avaliacaoViajante);
                TextView precoBaseTV = (TextView) convertView.findViewById(R.id.precoBase);

                // getting movie data for the row
                Viagem v = viagemItems.get(position);

                if(v != null) {
                        // thumbnail image
                        thumbNail.setImageUrl(v.getThumbnailUrl(), imageLoader);

                        // origem
                        origemTV.setText("Origem: " + v.getOrigem());

                        // destino
                        destinoTV.setText("Destino: " + v.getDestino());

                        // avaliacaoViajante
                        avaliacaoViajanteTV.setText("Avaliação do viajante: " + v.getAvaliacaoViajante());

                        // preço base
                        precoBaseTV.setText("R$" + v.getPrecoBase() + ",00");
                }
                return convertView;
        }

}