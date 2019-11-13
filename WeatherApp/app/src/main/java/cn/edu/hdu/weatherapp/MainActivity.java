package cn.edu.hdu.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView currentCityTextView;
    TextView nowWeatherTextView;
    TextView nowTempTextView;
    TextView todayWeatherTextView;
    TextView todayTempLowTextView;
    TextView todayTempHighTextView;
    TextView tomorrowWeatherTextView;
    TextView tomorrowTempLowTextView;
    TextView tomorrowTempHighTextView;
    TextView afterTomorrowWeatherTextView;
    TextView afterTomorrowTempLowTextView;
    TextView afterTomorrowTempHighTextView;
    TextView comfTextView;
    TextView sunriseTextView;
    TextView sunsetTextView;
    TextView popTextView;
    TextView humTextView;
    TextView windDirTextView;
    TextView windScTextView;
    TextView windSpdTextView;
    TextView qltyTextView;
    TextView aqiTextView;
    TextView pm25TextView;
    private Button changeCityButton;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentCityTextView = findViewById(R.id.currentCityTextView);
        nowWeatherTextView = findViewById(R.id.nowWeatherTextView);
        nowTempTextView = findViewById(R.id.nowTempTextView);
        todayWeatherTextView = findViewById(R.id.todayWeatherTextView);
        todayTempLowTextView = findViewById(R.id.todayTempLowTextView);
        todayTempHighTextView = findViewById(R.id.todayTempHighTextView);
        tomorrowWeatherTextView = findViewById(R.id.tomorrowWeatherTextView);
        tomorrowTempLowTextView = findViewById(R.id.tomorrowTempLowTextView);
        tomorrowTempHighTextView = findViewById(R.id.tomorrowTempHighTextView);
        afterTomorrowWeatherTextView = findViewById(R.id.afterTomorrowWeatherTextView);
        afterTomorrowTempLowTextView = findViewById(R.id.afterTomorrowTempLowTextView);
        afterTomorrowTempHighTextView = findViewById(R.id.afterTomorrowTempHighTextView);
        comfTextView = findViewById(R.id.comfTextView);
        sunriseTextView = findViewById(R.id.sunriseTextView);
        sunsetTextView = findViewById(R.id.sunsetTextView);
        popTextView = findViewById(R.id.popTextView);
        humTextView = findViewById(R.id.humTextView);
        windDirTextView = findViewById(R.id.windDirTextView);
        windScTextView = findViewById(R.id.windScTextView);
        windSpdTextView = findViewById(R.id.windSpdTextView);
        qltyTextView = findViewById(R.id.qltyTextView);
        aqiTextView = findViewById(R.id.aqiTextView);
        pm25TextView = findViewById(R.id.pm25TextView);

        changeCityButton = findViewById(R.id.changeCityButton);

        sharedPreferences = getSharedPreferences("defaultCity", Context.MODE_PRIVATE);

        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityChangeActivity.class);
                startActivity(intent);
                return;
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    String cityName = sharedPreferences.getString("cityName", "hangzhou");
                    System.out.println(cityName);
                    Weather weather = new Weather();

                    URL urlWeatherNow = new URL("https://free-api.heweather.net/s6/weather/now?location=" + cityName + "&key=56c7111027ab4242874a3a2ff434328e");
                    System.out.println(urlWeatherNow.toString());
                    String dataWeatherNow = getWeatherData(urlWeatherNow);
                    if (dataWeatherNow != null) {
                        weather = parseWeatherNow(dataWeatherNow, weather);
                    }
                    URL urlWeatherForecast = new URL("https://free-api.heweather.net/s6/weather/forecast?location=" + cityName + "&key=56c7111027ab4242874a3a2ff434328e");
                    String dataWeatherForecast =
                            getWeatherData(urlWeatherForecast);
                    if (dataWeatherForecast != null) {
                        weather = parseWeatherForecast(dataWeatherForecast,
                                weather);
                    }
                    URL urlWeatherLifestyle = new URL("https://free-api.heweather.net/s6/weather/lifestyle?location=" + cityName + "&key=56c7111027ab4242874a3a2ff434328e");
                    String dataWeatherLifestyle =
                            getWeatherData(urlWeatherLifestyle);
                    if (dataWeatherLifestyle != null) {
                        weather = parseWeatherLifestyle(dataWeatherLifestyle,
                                weather);
                    }
                    URL urlAirNow = new URL("https://free-api.heweather.net/s6/air/now?location=" + cityName + "&key=56c7111027ab4242874a3a2ff434328e");
                    String dataAirNow = getWeatherData(urlAirNow);
                    if (dataAirNow != null) {
                        weather = parseAirNow(dataAirNow, weather);
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = weather;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

    private String getWeatherData(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream input = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String strRead = null;
            StringBuilder sbf = new StringBuilder();
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append(System.lineSeparator());
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析实况天气数据
     *
     * @param jsonWeatherNow
     * @param weather
     * @return
     */
    private Weather parseWeatherNow(String jsonWeatherNow, Weather weather) {
        try {
            JSONArray root = new JSONObject(jsonWeatherNow).getJSONArray("HeWeather6");
            JSONObject basicObj = root.getJSONObject(0).getJSONObject("basic");
            JSONObject nowObj = root.getJSONObject(0).getJSONObject("now");

            weather.setCurrentCity(basicObj.getString("location"));
            weather.setNowWeather(nowObj.getString("cond_txt"));
            weather.setNowTemp(nowObj.getString("tmp"));
            weather.setHum(nowObj.getString("hum") + "%");
            weather.setWindDir(nowObj.getString("wind_dir"));
            weather.setWindSc(nowObj.getString("wind_sc") + "级");
            weather.setWindSpd(nowObj.getString("wind_spd") + "kmph");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }

    /**
     * 解析天气预报数据
     *
     * @param jsonWeatherForecast
     * @param weather
     * @return
     */
    private Weather parseWeatherForecast(String jsonWeatherForecast, Weather weather) {
        try {
            JSONArray root = new JSONObject(jsonWeatherForecast).getJSONArray("HeWeather6");
            JSONArray forecastObj = root.getJSONObject(0).getJSONArray("daily_forecast");

            weather.setTodayWeather(forecastObj.getJSONObject(0).getString("cond_txt_d"));
            weather.setTodayTempLow(forecastObj.getJSONObject(0).getString("tmp_min"));
            weather.setTodayTempHigh(forecastObj.getJSONObject(0).getString("tmp_max"));
            weather.setTomorrowWeather(forecastObj.getJSONObject(1).getString("cond_txt_d"));
            weather.setTomorrowTempLow(forecastObj.getJSONObject(1).getString("tmp_min"));
            weather.setTomorrowTempHigh(forecastObj.getJSONObject(1).getString("tmp_max"));
            weather.setAfterTomorrowWeather(forecastObj.getJSONObject(2).getString("cond_txt_d"));
            weather.setAfterTomorrowTempLow(forecastObj.getJSONObject(2).getString("tmp_min"));
            weather.setAfterTomorrowTempHigh(forecastObj.getJSONObject(2).getString("tmp_max"));
            weather.setSunrise(forecastObj.getJSONObject(0).getString("sr"));
            weather.setSunset(forecastObj.getJSONObject(0).getString("ss"));
            weather.setPop(forecastObj.getJSONObject(0).getString("pop") + "%");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }

    /**
     * 解析生活指数数据
     *
     * @param jsonWeatherLifestyle
     * @param weather
     * @return
     */
    private Weather parseWeatherLifestyle(String jsonWeatherLifestyle, Weather weather) {
        try {
            JSONArray root = new JSONObject(jsonWeatherLifestyle).getJSONArray("HeWeather6");
            JSONArray lifestyleObj = root.getJSONObject(0).getJSONArray("lifestyle");

            weather.setComf(lifestyleObj.getJSONObject(0).getString("txt"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }

    /**
     * 解析空气质量数据
     *
     * @param jsonAirNow
     * @param weather
     * @return
     */
    private Weather parseAirNow(String jsonAirNow, Weather weather) {
        try {
            JSONArray root = new JSONObject(jsonAirNow).getJSONArray("HeWeather6");
            JSONObject airObject = root.getJSONObject(0).getJSONObject("air_now_city");

            weather.setQlty(airObject.getString("qlty"));
            weather.setAqi(airObject.getString("aqi"));
            weather.setPm25(airObject.getString("pm25") + "ug/m³");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Weather weather = (Weather) msg.obj;
                    currentCityTextView.setText(weather.getCurrentCity());
                    nowWeatherTextView.setText(weather.getNowWeather());
                    nowTempTextView.setText(weather.getNowTemp());
                    todayWeatherTextView.setText(weather.getTodayWeather());
                    todayTempLowTextView.setText(weather.getTodayTempLow());
                    todayTempHighTextView.setText(weather.getTodayTempHigh());
                    tomorrowWeatherTextView.
                            setText(weather.getTomorrowWeather());
                    tomorrowTempLowTextView.
                            setText(weather.getTomorrowTempLow());
                    tomorrowTempHighTextView.
                            setText(weather.getTomorrowTempHigh());
                    afterTomorrowWeatherTextView.
                            setText(weather.getAfterTomorrowWeather());
                    afterTomorrowTempLowTextView.
                            setText(weather.getAfterTomorrowTempLow());
                    afterTomorrowTempHighTextView.setText(weather.getAfterTomorrowTempHigh());
                    comfTextView.setText(weather.getComf());
                    sunriseTextView.setText(weather.getSunrise());
                    sunsetTextView.setText(weather.getSunset());
                    popTextView.setText(weather.getPop());
                    humTextView.setText(weather.getHum());
                    windDirTextView.setText(weather.getWindDir());
                    windScTextView.setText(weather.getWindSc());
                    windSpdTextView.setText(weather.getWindSpd());
                    qltyTextView.setText(weather.getQlty());
                    aqiTextView.setText(weather.getAqi());
                    pm25TextView.setText(weather.getPm25());
                    break;
                default:
                    break;
            }
        }
    };
}
