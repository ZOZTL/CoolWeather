package com.coolweather.android;

import android.app.Fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.ProcessDialogUtil;
import com.coolweather.android.util.Utility;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zhouyang on 2019/4/24.
 */

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;//省等级为0

    public static final int LEVEL_CITY = 1;//市等级为1

    public static final int LEVEL_COUNTY = 2;//县等级为2

   // private ProgressDialog  progressDialog;
    //private ProgressBar progressBar;//进度条

    private TextView titleText;//标题text

    private Button backButton;//返回按钮

    private ListView listView;//内容列表

    private ArrayAdapter<String> adapter;//listview适配器

    private List<String> dataList = new ArrayList<String>();//数据list

    /**
     * 省列表
     */
    private List<Province> provinceList;

    /**
     * 市列表
     */
    private List<City> cityList;

    /**
     * 县列表
     */
    private List<County> countyList;

    private Province  selectProvince;//选中的省

    private City selectCity;//选中的市

    public  int currentLevel;//当前选中的级别

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.choose_area,container,false);
        titleText = (TextView)view.findViewById(R.id.title_text);
        backButton = (Button)view.findViewById(R.id.back_button);
        listView = (ListView)view.findViewById(R.id.list_view);

        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //点击listview事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("query","currentLevel is : "+String.valueOf(currentLevel));
                if(currentLevel == LEVEL_PROVINCE)
                {
                    selectProvince = provinceList.get(position);
                    queryCities();
                }
                else if(currentLevel == LEVEL_CITY)
                {
                    selectCity = cityList.get(position);
                    queryCounties();

                }

                Log.d("query","query success");
            }
        });

        //返回键点击事件
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel == LEVEL_CITY)
                {
                    queryProvinces();
                }
                else if(currentLevel == LEVEL_COUNTY)
                {
                    queryCities();
                }
            }
        });

        //首先做一次省查询
        queryProvinces();
    }


    /**
     * 查询省数据，优先级：数据库>>服务器
     */
    private void queryProvinces()
    {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);

        //查询数据库
        provinceList = LitePal.findAll(Province.class);

        if(provinceList.size()>0)
        {
            dataList.clear();
            for(Province province : provinceList)
            {
                dataList.add(province.getProvinveName());

            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }
        else
        {
            String address = "http://guolin.tech/api/china";
            //查询服务器
            queryFromServer(address,"province");
        }
    }

    /**
     * 查询市数据，优先级：数据库>>服务器
     */
    private void queryCities()
    {
        titleText.setText(selectProvince.getProvinveName());
        backButton.setVisibility(View.VISIBLE);

        cityList = LitePal.where("provinceId = ?",String.valueOf(selectProvince.getId())).find(City.class);

        if(cityList.size()>0)
        {
            dataList.clear();

            for(City city:cityList)
            {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }
        else
        {
            int provinceCode = selectProvince.getProvinceCode();

            String address = "http://guolin.tech/api/china/" + provinceCode;
            //查询服务器
            queryFromServer(address,"city");
        }
    }

    /**
     * 查询选中城市内县数据，优先级 数据库>>服务器
     */
    private void queryCounties()
    {
        titleText.setText(selectCity.getCityName());
        backButton.setVisibility(View.VISIBLE);

        countyList = LitePal.where("cityId = ?", String.valueOf(selectCity.getId())).find(County.class);

        if(countyList.size()>0)
        {
            Log.d("query","countyList is not null");
            dataList.clear();

            for (County county:countyList)
            {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();

            listView.setSelection(0);

            currentLevel = LEVEL_COUNTY;
        }
        else
        {
            Log.d("query","countyList is  null");
            int provinceCode = selectProvince.getProvinceCode();
            int cityCode = selectCity.getCityCode();

            String address = "http://guolin.tech/api/china/" + provinceCode +"/"+ cityCode;
            //查询服务器
            queryFromServer(address,"county");
        }
    }

    /**
     * 根据地址与类型，查询服务器数据
     */
    private  void queryFromServer(String address,final String type)
    {
        ProcessDialogUtil.openProgressDialog(getContext(),null);
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //返回主线程处理ui
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProcessDialogUtil.dismiss();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;

                if(type.equals("province"))
                {
                    result = Utility.handleProvinceResponse(responseText);
                }
                else if (type.equals("city"))
                {
                    result = Utility.handleCityResponse(responseText,selectProvince.getId());
                }
                else if(type.equals("county"))
                {
                    result = Utility.handleCountyResponse(responseText,selectCity.getId());
                }

                if (result)
                {
                    //在主线程进行数据库读取操作
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ProcessDialogUtil.dismiss();
                            if(type.equals("province"))
                            {
                                queryProvinces();
                            }
                            else if (type.equals("city"))
                            {
                                queryCities();
                            }
                            else if (type.equals("county"))
                            {
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 处理系统返回键事件
     */
    public void handleBack()
    {
        currentLevel = LEVEL_PROVINCE;
        Log.d("query","handleBack is used");
        Log.d("query","currentLevel :"+ LEVEL_PROVINCE);
    }

}
