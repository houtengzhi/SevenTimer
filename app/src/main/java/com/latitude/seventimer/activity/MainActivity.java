package com.latitude.seventimer.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.latitude.seventimer.R;
import com.latitude.seventimer.fragment.OtherFragment;
import com.latitude.seventimer.fragment.WeatherFragment;
import com.latitude.seventimer.model.Address;
import com.latitude.seventimer.model.AstroWeather;
import com.latitude.seventimer.model.AstroWeatherCluster;
import com.latitude.seventimer.util.FragAdapter;
import com.latitude.seventimer.util.HttpCallbackListener;
import com.latitude.seventimer.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by yechy on 2015/9/7.
 */
public class MainActivity extends FragmentActivity {

    private Button selectAddress;
    private TextView showAddress;
    private Address selectedAddress;
    private TextView showUpdateTime;
    private ImageView pageDot1, pageDot2;
    private static ArrayList<AstroWeather> astroWeatheList1 = new ArrayList<AstroWeather>();
    private static WeatherFragment weatherFragment;
    private OtherFragment otherFragment;
    private LocationManager locationManager;
    private String provider;

    public final static int UPDATE_CURRENTLOCATION = 0;
    public final static int UPDATE_ASTROWEATHER = 1;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInsatnceState) {
        super.onCreate(savedInsatnceState);
        setContentView(R.layout.activity_main);

        selectAddress =(Button) findViewById(R.id.select_address);
        showAddress =(TextView) findViewById(R.id.show_address);
        showUpdateTime = (TextView) findViewById(R.id.show_updatetime);

        List<Fragment> fragments = new ArrayList<Fragment>();
        weatherFragment = new WeatherFragment();
        otherFragment = new OtherFragment();
        fragments.add(weatherFragment);
        fragments.add(otherFragment);
        FragAdapter fragAdapter = new FragAdapter(getSupportFragmentManager(), fragments);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(fragAdapter);

        initPageDot();
        viewPager.setCurrentItem(0);
        currentIndex = 0;
        pageDot1.setImageResource(R.drawable.dot_selected);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Animation animation = null;
                switch (position) {
                    case 0:
                        pageDot1.setImageResource(R.drawable.dot_selected);
                        pageDot2.setImageResource(R.drawable.dot);
                        if (position == currentIndex - 1) {
                            animation = new TranslateAnimation(position+1, position, 0, 0);
                        }
                        break;
                    case 1:
                        pageDot1.setImageResource(R.drawable.dot);
                        pageDot2.setImageResource(R.drawable.dot_selected);
                        if (position == currentIndex + 1) {
                            animation = new TranslateAnimation(position-1, position,0,0);
                        }
                        break;
                    default:
                        break;
                }
                currentIndex = position;
                animation.setFillAfter(true);
                animation.setDuration(300);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initDatas();
        //进入选择地址界面
        selectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseAreaActivity.class);
                startActivityForResult(intent, 1);
            }
        });


    }

    private void initDatas() {
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

                    }

                    @Override
                    public void onFinish(String response) {
                        // TODO 自动生成的方法存根
                    }
                });

        //查询所选地址的AstroWeather并显示
        queryAstroWeather((float) location.getLatitude(), (float) location.getLongitude());
    }

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_CURRENTLOCATION:
                    selectedAddress = (Address) msg.obj;
                    String addressName = (String) selectedAddress.getAddress();
                    showAddress.setText(addressName);
                    break;
                case UPDATE_ASTROWEATHER:
                    AstroWeatherCluster astroWeatherCluster = (AstroWeatherCluster) msg.obj;
                    String updateTime = (String) astroWeatherCluster.getUpdateTime();
                    showUpdateTime.setText("预报生成时间： " + updateTime);
                    //int timeZone = (int) astroWeatherCluster.getTimeZone();
                    weatherFragment.refreshShow(astroWeatherCluster.getList(), updateTime);
                default:
                    break;
            }
        }
    };

    private void queryAstroWeather(float latitude, float longitude) {

        StringBuilder awUrl = new StringBuilder();
        awUrl.append("http://ftp.astron.ac.cn/v4/bin/astro.php?lon=");
        awUrl.append(longitude + "&lat=");
        awUrl.append(latitude + "&ac=0&lang=zh-CN&unit=metric&tzshift=0&output=json");
        HttpUtil.sendHttpRequestNormal(awUrl.toString(), latitude, longitude, new HttpCallbackListener() {

            @Override
            public void onFinish(String response, float latitude, float longitude) {

                ArrayList<AstroWeather> astroWeatheList2 = new ArrayList<AstroWeather>();
                //由longitude算所在时区
                int timeZone;
                if (longitude % 15 < 7.5) {
                    timeZone = (int) longitude / 15;
                } else {
                    timeZone = ((int) longitude / 15) + 1;
                }
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String product = jsonObject1.getString("product");
                    String dateStr = jsonObject1.getString("init");
                    String format = "yyyyMMddHH";
                    DateFormat showDateFormat = new SimpleDateFormat("dd日", Locale.UK);
                    DateFormat showUpdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.UK);
                    Date originDate = new SimpleDateFormat(format,Locale.UK).parse(dateStr);
                    Calendar calendar = Calendar.getInstance(Locale.UK);
                    calendar.setTime(originDate);
                    calendar.add(Calendar.HOUR_OF_DAY, timeZone);
                    String updateTime = showUpdateFormat.format(calendar.getTime());

                    int astro_cloud[] = {R.drawable.astro_cloud_1, R.drawable.astro_cloud_2, R.drawable.astro_cloud_3,
                            R.drawable.astro_cloud_4, R.drawable.astro_cloud_5, R.drawable.astro_cloud_6,
                            R.drawable.astro_cloud_7, R.drawable.astro_cloud_8, R.drawable.astro_cloud_9};
                    int astro_seeing[] = {R.drawable.astro_seeing_1, R.drawable.astro_seeing_2,
                            R.drawable.astro_seeing_3, R.drawable.astro_seeing_4, R.drawable.astro_seeing_5,
                            R.drawable.astro_seeing_6, R.drawable.astro_seeing_7, R.drawable.astro_seeing_8};
                    int astro_trans[] = {R.drawable.astro_transparency_1, R.drawable.astro_transparency_2,
                            R.drawable.astro_transparency_3, R.drawable.astro_transparency_4,
                            R.drawable.astro_transparency_5, R.drawable.astro_transparency_6,
                            R.drawable.astro_transparency_7, R.drawable.astro_transparency_8};
                    int astro_unstable[] = {R.drawable.astro_unstable_1, R.drawable.astro_unstable_2,
                            R.drawable.astro_unstable_3};
                    int astro_rh[] = {R.drawable.astro_rh_1, R.drawable.astro_rh_2, R.drawable.astro_rh_3};
                    int astro_rainsnow[] = {R.drawable.astro_rainsnow_rain, R.drawable.astro_rainsnow_snow};
                    int astro_wind[] = {R.drawable.astro_wind_1, R.drawable.astro_wind_2, R.drawable.astro_wind_3};

                    JSONArray jsonArray2 = jsonObject1.getJSONArray("dataseries");
                    String[] date = new String[jsonArray2.length()];
                    String[] dateRef = new String[jsonArray2.length()];
                    int[] isShowDivider = new int[jsonArray2.length()];
                    int[] hour = new int[jsonArray2.length()];
                    int[] astroCloudId = new int[jsonArray2.length()];
                    int[] astroSeeingId = new int[jsonArray2.length()];
                    int[] astroTransparencyId = new int[jsonArray2.length()];
                    int[] temp = new int[jsonArray2.length()];
                    int[] astroRhId = new int[jsonArray2.length()];
                    int[] astroRainsnowId = new int[jsonArray2.length()];
                    int[] astroUnstableId = new int[jsonArray2.length()];
                    int[] astroWindId = new int[jsonArray2.length()];

                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                        int timepoint = jsonObject2.getInt("timepoint");
                        int cloudcover = jsonObject2.getInt("cloudcover");
                        int seeing = jsonObject2.getInt("seeing");
                        int trans = jsonObject2.getInt("transparency");
                        int lifted_index = jsonObject2.getInt("lifted_index");
                        int rh2m = jsonObject2.getInt("rh2m");
                        int temp2m = jsonObject2.getInt("temp2m");
                        String prec_type = jsonObject2.getString("prec_type");
                        JSONObject jsonObject3 = jsonObject2.getJSONObject("wind10m");

                        String direction = jsonObject3.getString("direction");
                        int speed = jsonObject3.getInt("speed");

                        calendar.add(Calendar.HOUR_OF_DAY, timepoint);
                        date[i] = showDateFormat.format(calendar.getTime());
                        dateRef[i] = showDateFormat.format(calendar.getTime());
                        hour[i] = calendar.get(Calendar.HOUR_OF_DAY);
                        calendar.add(Calendar.HOUR_OF_DAY, -timepoint);
                        astroCloudId[i] = astro_cloud[cloudcover-1];
                        astroSeeingId[i] = astro_seeing[seeing-1];
                        astroTransparencyId[i] = astro_trans[trans-1];
                        temp[i] = temp2m;
                        //判断相对湿度
                        if (rh2m == 12 || rh2m == 13) {
                            astroRhId[i] = astro_rh[0];
                        } else if (rh2m == 14) {
                            astroRhId[i] = astro_rh[1];
                        } else if (rh2m == 15 || rh2m == 16) {
                            astroRhId[i] = astro_rh[2];
                        } else {
                            astroRhId[i] = R.drawable.astro_null;
                        }
                        //判断降水类型
                        if (prec_type == "rain") {
                            astroRainsnowId[i] = astro_rainsnow[0];
                        } else if (prec_type == "snow") {
                            astroRainsnowId[i] = astro_rainsnow[1];
                        } else {
                            astroRainsnowId[i] = R.drawable.astro_null;
                        }
                        //判断抬升指数
                        if (lifted_index == -1) {
                            astroUnstableId[i] = astro_unstable[0];
                        } else if (lifted_index == -4) {
                            astroUnstableId[i] = astro_unstable[1];
                        } else if (lifted_index <= -5) {
                            astroUnstableId[i] = astro_unstable[2];
                        } else {
                            astroUnstableId[i] = R.drawable.astro_null;
                        }
                        //判断大风预警
                        if (speed == 5) {
                            astroWindId[i] = astro_wind[0];
                        } else if (speed == 6) {
                            astroWindId[i] =astro_wind[1];
                        } else if (speed > 6) {
                            astroWindId[i] =astro_wind[2];
                        } else {
                            astroWindId[i] = R.drawable.astro_null;
                        }
                        isShowDivider[i] = AstroWeather.NOSHOWDIVIDER;
                    }
                    for(int i = 1; i < jsonArray2.length(); i++) {
                        if (dateRef[i].equals(dateRef[i-1])) {
                            date[i] = " ";
                        } else {
                            isShowDivider[i-1] = AstroWeather.SHOWDIVIDER;
                        }
                    }
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        AstroWeather astroWeather = new AstroWeather(date[i], hour[i], astroCloudId[i],
                                astroSeeingId[i], astroTransparencyId[i], temp[i], astroRhId[i], astroRainsnowId[i],
                                astroUnstableId[i], astroWindId[i], isShowDivider[i]);
                        astroWeatheList2.add(astroWeather);
                    }

                    AstroWeatherCluster astroWeatherCluster = new AstroWeatherCluster(astroWeatheList2, timeZone,
                            updateTime);
                    Message message = new Message();
                    message.what = UPDATE_ASTROWEATHER;
                    message.obj = astroWeatherCluster;
                    handler.sendMessage(message);

                } catch (JSONException e ) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish(String response) {

            }

            @Override
            public void onError(Exception e) {
                // TODO 自动生成的方法存根

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    selectedAddress = (Address) data.getSerializableExtra("selected_address");
                    float latitude = selectedAddress.getLatitude();
                    float longitude = selectedAddress.getLongitude();
                    String addressName = selectedAddress.getAddress();
                    showAddress.setText(addressName);
                    queryAstroWeather(latitude, longitude);
                }
                break;
            default:
                break;
        }
    }


    public Address getSelectedAddress() {
        return selectedAddress;
    }

    private void initPageDot() {
        pageDot1 = (ImageView) findViewById(R.id.page_dot1);
        pageDot2 = (ImageView) findViewById(R.id.page_dot2);
    }
}
