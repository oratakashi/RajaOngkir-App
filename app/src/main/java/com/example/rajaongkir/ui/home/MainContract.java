package com.example.rajaongkir.ui.home;

import com.example.rajaongkir.data.cost.DataCost;
import com.example.rajaongkir.data.cost.DataType;
import com.example.rajaongkir.data.cost.ResponseCost;

import java.util.List;

public interface MainContract {
    interface Presenter{
        boolean getJNE();
        boolean getTIKI();
        boolean getPOS();
        void setupENV(String origin, String destination, int weight);
    }

    interface View{
        void onLoadingCost(boolean loadng, int progress);
        void onResultCost(List<DataType> data, List<String> courier);
        void onErrorCost();

        void showMessage(String msg);
        String getOrigin();
        String getDestination();
    }
}
