package com.latitude.seventimer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.latitude.seventimer.R;
import com.latitude.seventimer.model.Address;
import com.latitude.seventimer.util.AddressAdapter;
import com.latitude.seventimer.util.ClearEditText;
import com.latitude.seventimer.util.HttpCallbackListener;
import com.latitude.seventimer.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by yechy on 2015/9/7.
 */
public class ChooseAreaActivity extends Activity {
    private Button findButton;
    private ClearEditText inputAddress;
    private ListView listView;

    private static List<Address> addressList = new ArrayList<Address>();
    private LocationManager locationManager;
    private String provider;
    private static AddressAdapter adapter;
    private MyHandler handler;

    public static final int UPDATE_CURRENTLOCATION = 0;
    public static final int UPDATE_INPUTLOCATION = 1;
    private int addressNum = 1;
    private int i = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ye", "debug 2");
        setContentView(R.layout.choose_area);
        Log.d("ye", "debug 3");
        handler = new MyHandler(this);
        listView = (ListView) findViewById(R.id.address_listview);
        adapter = new AddressAdapter(this, R.layout.address_item, addressList);

        listView.setAdapter(adapter);
        Log.d("ye", "debug 4");
        initAddressList();
        Log.d("ye", "debug 5");
        this.registerForContextMenu(listView);
        //listView点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Address address = addressList.get(position);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selected_address", address);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                //销毁当前活动
                finish();
            }
        });
        //listView长按弹出删除按钮
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                AdapterView.AdapterContextMenuInfo adapterMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
                if (adapterMenuInfo.position > 0) {
                    menu.add(0, 1, Menu.NONE, "删除");
                    //	listView.setSelection(adapterMenuInfo.position);
                    //	listView.setSelector(R.drawable.item_background_pressed);
                }
            }
        });



        //输入地址查询
        inputAddress = (ClearEditText) findViewById(R.id.input_address);
        findButton = (Button) findViewById(R.id.find);
        findButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String content = inputAddress.getText().toString();
                switch (v.getId()) {
                    case R.id.find:
                        if (content != null & content != "") {
                            addressNum = addressNum + 1;
                            StringBuilder url = new StringBuilder();
                            url.append("http://maps.googleapis.com/maps/api/geocode/json?address=");
                            url.append(content);
                            url.append("&sensor=false");
                            HttpUtil.sendHttpRequest(url.toString(), new HttpCallbackListener() {

                                @Override
                                public void onFinish(String response, float latitude, float longitude) {
                                    // TODO 自动生成的方法存根

                                }

                                @Override
                                public void onFinish(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray resultArray = jsonObject.getJSONArray("results");
                                        if (resultArray.length() > 0) {
                                            JSONObject subObject = resultArray.getJSONObject(0);
                                            String address_name = subObject.getString("formatted_address");
                                            StringTokenizer tokenizer = new StringTokenizer(address_name);
                                            address_name = tokenizer.nextToken();
                                            JSONObject geometryObject = subObject.getJSONObject("geometry");
                                            JSONObject locationObject = geometryObject.getJSONObject("location");
                                            float latitude = (float) locationObject.getDouble("lat");
                                            float longitude = (float) locationObject.getDouble("lng");
                                            Address inputLocation = new Address(address_name, R.drawable.weather1,
                                                    latitude, longitude);
                                            Message message = new Message();
                                            message.what = UPDATE_INPUTLOCATION;
                                            message.obj = inputLocation;
                                            handler.sendMessage(message);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onError(Exception e) {
                                    // TODO 自动生成的方法存根

                                }
                            });
                        }
                        break;
                    default:
                        break;
                }


            }
        });

    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case 1:
                int position = (int) menuInfo.position;
                if (position > 0 ) {
                    addressList.remove(position);
                    addressNum = addressNum - 1;
                    adapter.notifyDataSetChanged();
                }
                break;
        }
        return false;
    }


    private void initAddressList() {
        //清空数据
        addressList.clear();
        i = 2;
        addressNum = 1;


        Address firstAddress = new Address();
        addressList.add(firstAddress);

        Log.d("ye","addressList size1 = "+addressList.size() );
        Log.d("ye", "addressNum1 is "+addressNum);
        //获取当前位置
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider= LocationManager.NETWORK_PROVIDER;
        } else {
            //当没有可用的位置提供器时，弹出Toast
            Toast.makeText(this, R.string.tips, Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        //对当前位置经纬度解析
        StringBuilder url = new StringBuilder();
        url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
        url.append(location.getLatitude()).append(",");
        url.append(location.getLongitude());
        url.append("&sensor=false");

        HttpUtil.sendHttpRequest(url.toString(), (float) location.getLatitude(),
                (float) location.getLongitude(), new HttpCallbackListener() {

                    @Override
                    public void onFinish(String response, float latitude, float longitude) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray resultArray = jsonObject.getJSONArray("results");
                            if (resultArray.length() > 0) {
                                JSONObject subObject = resultArray.getJSONObject(0);
                                String address_name = subObject.getString("formatted_address");
                                StringTokenizer tokenizer = new StringTokenizer(address_name);
                                address_name = tokenizer.nextToken();
                                Address myLocation = new Address(address_name, R.drawable.weather1,
                                        latitude, longitude);
                                Message message = new Message();
                                message.what = UPDATE_CURRENTLOCATION;
                                message.obj = myLocation;

                                handler.sendMessage(message);
                            }
                        } catch (JSONException e) {
                            // TODO 自动生成的 catch 块
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        // TODO 自动生成的方法存根
                    }
                    @Override
                    public void onFinish(String response) {
                        // TODO 自动生成的方法存根

                    }
                });

        //读取预存的地址记录
        SharedPreferences pref;
        int TAG = 1;
        do {	Log.d("ye","i = " + i);
            String addressFile = "inputlocation" + i;
            pref = getSharedPreferences(addressFile, MODE_PRIVATE);
            if (pref.getString("address", null) != null) {
                addressList.add(createAddress(pref));
                adapter.notifyDataSetChanged();
                i = i + 1;

                addressNum = addressNum + 1;
                //清除预存的地址文件
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
            } else {
                TAG = 0;
            }

        } while (TAG == 1);
    }

    static class MyHandler extends Handler {
        private WeakReference<ChooseAreaActivity> mOuter;
        public MyHandler(ChooseAreaActivity activity) {
            mOuter = new WeakReference<ChooseAreaActivity>(activity);
        }

        public void handleMessage(Message msg) {
            ChooseAreaActivity outer = mOuter.get();
            if (outer != null) {
                switch (msg.what) {
                    case UPDATE_CURRENTLOCATION:
                        Address myLocation = (Address) msg.obj;
                        //将addressList第一项替换为mylocation
                        addressList.set(0, myLocation);
                        adapter.notifyDataSetChanged();
                        break;
                    case UPDATE_INPUTLOCATION:
                        Address inputLocation = (Address) msg.obj;
                        addressList.add(inputLocation);
                        adapter.notifyDataSetChanged();
                    default:
                        break;
                }
            }
        }
    }

    private Address createAddress(SharedPreferences pref) {
        Address address = new Address(pref.getString("address", null), pref.getInt("imageId", 0),
                pref.getFloat("latitude", 0), pref.getFloat("longitute", 0));
        return address;
    }

    public void onStart() {
        super.onStart();
    }

    public void  onPause() {
        super.onPause();
        //保存输入的地址
        Log.d("ye", "addressNum is " + addressNum);
        Log.d("ye","addressList size is " + addressList.size());
        if (addressNum >= 2) {
            for (int j = 2; j <= addressNum; j++) {
                String inputLocationFile = "inputlocation" + j;
                SharedPreferences.Editor editor = getSharedPreferences(inputLocationFile ,
                        MODE_PRIVATE).edit();
                editor.putString("address", addressList.get(j-1).getAddress());
                editor.putInt("imageId", addressList.get(j-1).getImageId());
                editor.putFloat("latitude", addressList.get(j-1).getLatitude());
                editor.putFloat("longitude", addressList.get(j-1).getLongitude());
                editor.commit();
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.unregisterForContextMenu(listView);

    }
}
