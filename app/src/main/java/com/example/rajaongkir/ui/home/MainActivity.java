package com.example.rajaongkir.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rajaongkir.R;
import com.example.rajaongkir.data.cost.DataCost;
import com.example.rajaongkir.data.cost.DataType;
import com.example.rajaongkir.data.cost.ResponseCost;
import com.example.rajaongkir.ui.search.SearchCityActivity;
import com.ndroid.CoolEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    Unbinder unbinder;
    private static final int REQUEST_SOURCE = 1;
    private static final int REQUEST_DESTINATION = 2;

    private String source_id = "";
    private String destination_id = "";

    private MainPresenter presenter;
    private MainAdapter adapter;

    private List<DataType> data = new ArrayList<>();
    private List<String> courier = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
        presenter = new MainPresenter(this);

        adapter = new MainAdapter(this, data, courier);
        rvMain.setAdapter(adapter);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onLoadingCost(boolean loadng, int progress) {
        if(loadng){
            llMain.setVisibility(View.VISIBLE);
            rvMain.setVisibility(View.GONE);
            pbMain.setProgress(progress);
        }else{
            pbMain.setProgress(progress);
            llMain.setVisibility(View.GONE);
            rvMain.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResultCost(List<DataType> data, List<String> courier) {
        this.data.addAll(data);
        this.courier.addAll(courier);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorCost() {
        rvMain.setVisibility(View.GONE);
        llMain.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getOrigin() {
        return source_id;
    }

    @Override
    public String getDestination() {
        return destination_id;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SOURCE && resultCode == RESULT_OK){
            etSource.setText(data.getStringExtra("city"));
            source_id = data.getStringExtra("city_id");
        }else if(requestCode == REQUEST_DESTINATION && resultCode == RESULT_OK){
            etDestination.setText(data.getStringExtra("city"));
            destination_id = data.getStringExtra("city_id");
        }
    }

    @OnClick(R.id.etSource) void source(){
        Intent intent = new Intent(getApplicationContext(), SearchCityActivity.class);
        intent.putExtra("requestCode", REQUEST_SOURCE);
        startActivityForResult(intent, REQUEST_SOURCE);
    }

    @OnClick(R.id.etDestination) void destination(){
        Intent intent = new Intent(getApplicationContext(), SearchCityActivity.class);
        intent.putExtra("requestCode", REQUEST_DESTINATION);
        startActivityForResult(intent, REQUEST_DESTINATION);
    }

    @OnClick(R.id.btnSubmit) void onSubmit(){
        data.clear();
        courier.clear();
        presenter.setupENV(getOrigin(), getDestination(), 1000);
    }

    @BindView(R.id.etSource)
    CoolEditText etSource;
    @BindView(R.id.etDestination)
    CoolEditText etDestination;
    @BindView(R.id.llMain)
    LinearLayout llMain;
    @BindView(R.id.pbMain)
    ProgressBar pbMain;
    @BindView(R.id.rvMain)
    RecyclerView rvMain;
}
