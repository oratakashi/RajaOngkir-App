package com.example.rajaongkir.ui.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rajaongkir.R;
import com.example.rajaongkir.data.city.DataCity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.ViewHolder> {

    Context context;
    List<DataCity> data;

    public SearchCityAdapter(Context context, List<DataCity> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_city,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCity.setText(data.get(position).getType()+" "+data.get(position).getCityName());
        holder.tvProvince.setText("Provinsi "+data.get(position).getProvince());
        holder.llCity.setOnClickListener(v -> {
            switch (((Activity) context).getIntent().getExtras().getInt("requestCode")){
                case 1:
                    Intent source = new Intent();
                    source.putExtra("city", holder.tvCity.getText().toString());
                    source.putExtra("city_id", data.get(position).getCityId());
                    ((Activity) context).setResult(Activity.RESULT_OK, source);
                    break;
                case 2:
                    Intent destination = new Intent();
                    destination.putExtra("city", holder.tvCity.getText().toString());
                    destination.putExtra("city_id", data.get(position).getCityId());
                    ((Activity) context).setResult(Activity.RESULT_OK, destination);
                    break;
            }
            ((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvCity)
        TextView tvCity;
        @BindView(R.id.tvProvince)
        TextView tvProvince;
        @BindView(R.id.llCity)
        LinearLayout llCity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
