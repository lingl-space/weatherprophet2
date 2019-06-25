package com.example.weatherprophet20;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.util.Log;

import com.example.weatherprophet20.db.City;
import com.example.weatherprophet20.db.County;
import com.example.weatherprophet20.db.Province;
import com.example.weatherprophet20.util.HttpUtil;
import com.example.weatherprophet20.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by asus on 2019/6/24.
 */

public class Location_choice extends Fragment {

    private static final String TAG = "Location_choice";            ////////

    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;

    private TextView title;

    private Button back;

    private ListView listView;

    private ArrayAdapter<String> arrayAdapter;

    private List<String> dataList = new ArrayList<>();

    private List<Province> provincesList ;

    private List<City> citiesList ;

    private List<County> countiesList ;

    private Province province_selected;

    private City city_selected;

    private County county_selected;

    private int level;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_choice, container, false);
        title = (TextView) view.findViewById(R.id.title);
        back = (Button) view.findViewById(R.id.back);
        listView = (ListView) view.findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(arrayAdapter);
        return view;
    }


    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(level == LEVEL_PROVINCE) {
                    province_selected = provincesList.get(position);
                    queryCities();
                }
                else if(level == LEVEL_CITY) {
                    city_selected = citiesList.get(position);
                    queryCounties();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level == LEVEL_COUNTY){
                    queryCities();
                }
                else if (level == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    private void queryProvinces(){
        title.setText("中华人民共和国");
        back.setVisibility(View.GONE);                  //设置不可见
        provincesList = DataSupport.findAll(Province.class);
        if(provincesList.size() > 0) {
            dataList.clear();
            for (Province province : provincesList){
                dataList.add(province.getProvinceName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            level = LEVEL_PROVINCE;
        }
        else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }

    private void queryCities() {
        title.setText(province_selected.getProvinceName());
        back.setVisibility(View.VISIBLE);
        citiesList = DataSupport.where("provinceid = ?", String.valueOf(province_selected.getProvinceId())).find(City.class);
        if (citiesList.size() > 0) {
            dataList.clear();
            for (City city : citiesList) {
                dataList.add(city.getCityName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            level = LEVEL_CITY;
        } else {
            int provinceCode = province_selected.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }
    }

    private void queryCounties() {
        title.setText(city_selected.getCityName());
        back.setVisibility(View.VISIBLE);
        countiesList = DataSupport.where("cityid = ?", String.valueOf(city_selected.getCityId())).find(County.class);
        if (countiesList.size() > 0) {
            dataList.clear();
            for (County county : countiesList) {
                dataList.add(county.getCountyName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            level = LEVEL_COUNTY;
        } else {
            int provinceCode = province_selected.getProvinceCode();
            int cityCode = city_selected.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }

    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = Utility.handleCityResponse(responseText, province_selected.getProvinceId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountyResponse(responseText, city_selected.getCityId());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // 通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

}
