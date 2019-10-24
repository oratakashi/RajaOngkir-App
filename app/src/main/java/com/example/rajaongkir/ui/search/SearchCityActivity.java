package com.example.rajaongkir.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rajaongkir.R;
import com.example.rajaongkir.data.city.DataCity;
import com.example.rajaongkir.data.city.ResponseCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchCityActivity extends AppCompatActivity implements SearchCityContract.View{

    Unbinder unbinder;
    List<DataCity> dataSumber = new ArrayList<>();
    List<DataCity> dataList = new ArrayList<>();
    List<DataCity> dataSearch = new ArrayList<>();

    SearchCityAdapter adapter;
    SearchCityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        getSupportActionBar().hide();

        unbinder = ButterKnife.bind(this);
        adapter = new SearchCityAdapter(this, dataList);
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        rvCity.setAdapter(adapter);

        presenter = new SearchCityPresenter(this);

        srCity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getCity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCity();
    }

    @Override
    public void onLoadingSearch(boolean loading) {
        if(loading){
            srCity.setRefreshing(true);
        }else {
            srCity.setRefreshing(false);
        }
    }

    @Override
    public void onResultSearch(ResponseCity response) {
        dataSumber.clear();
        dataList.clear();
        dataSumber.clear();
        if(response.getRajaongkir().getResults().size()!=0){
            dataSumber.addAll(response.getRajaongkir().getResults());
            dataList.addAll(response.getRajaongkir().getResults());

            adapter.notifyDataSetChanged();
            presenter.instantSearch(etSearch, dataSumber);
        }
    }

    @Override
    public void onErrorSearch() {
        srCity.setRefreshing(false);
    }

    @Override
    public void onResultInstantSearch(List<DataCity> data) {
        if(data.size() != 0){
            dataSearch.clear();
            dataList.clear();
            dataSearch.addAll(data);
            dataList.addAll(data);

            adapter.notifyDataSetChanged();
        }else{
            dataList.clear();
            dataList.addAll(dataSumber);

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ivBarBack) void onBack(){
        finish();
    }

    @BindView(R.id.etBarSearch)
    EditText etSearch;
    @BindView(R.id.srCity)
    SwipeRefreshLayout srCity;
    @BindView(R.id.rvCity)
    RecyclerView rvCity;
}
