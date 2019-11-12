package cn.edu.hdu.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    String city = URLEncoder.encode("杭州", "utf-8");
                    URL url = new URL("https://free-api.heweather.com/v5/weather?city="+city+"&key=f0420d78bd784bc7920d29e41bf1a239");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(input));
                    String strRead = null;
                    StringBuilder sbf = new StringBuilder();
                    while ((strRead = reader.readLine()) != null) {
                        sbf.append(strRead);
                        sbf.append(System.lineSeparator());
                    }
                    reader.close();
                    Weather weather = parseWeatherResult(sbf.toString());
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

    private Weather parseWeatherResult(String jsonWeatherResult) {
        Weather weather = new Weather();
        try {
            JSONArray root = new JSONObject(jsonWeatherResult).getJSONArray("HeWeather5");
            JSONObject basicObj = root.getJSONObject(0).getJSONObject("basic");
            JSONObject aqiObj = root.getJSONObject(0).getJSONObject("aqi");
            JSONObject nowObj = root.getJSONObject(0).getJSONObject("now");
            JSONArray forecastObj = root.getJSONObject(0).getJSONArray("daily_forecast");
            JSONObject suggestionObj = root.getJSONObject(0).getJSONObject("suggestion");

            weather.setCurrentCity(basicObj.getString("city"));
            weather.setNowWeather(nowObj.getJSONObject("cond").getString("txt"));
            weather.setNowTemp(nowObj.getString("tmp"));
            weather.setTodayWeather(forecastObj.getJSONObject(0).getJSONObject("cond").getString("txt_d"));
            weather.setTodayTempLow(forecastObj.getJSONObject(0).getJSONObject("tmp").getString("min"));
            weather.setTodayTempHigh(forecastObj.getJSONObject(0).getJSONObject("tmp").getString("max"));
            weather.setTomorrowWeather(forecastObj.getJSONObject(1).getJSONObject("cond").getString("txt_d"));
            weather.setTomorrowTempLow(forecastObj.getJSONObject(1).getJSONObject("tmp").getString("min"));
            weather.setTomorrowTempHigh(forecastObj.getJSONObject(1).getJSONObject("tmp").getString("max"));
            weather.setAfterTomorrowWeather(forecastObj.getJSONObject(2).getJSONObject("cond").getString("txt_d"));
            weather.setAfterTomorrowTempLow(forecastObj.getJSONObject(2).getJSONObject("tmp").getString("min"));
            weather.setAfterTomorrowTempHigh(forecastObj.getJSONObject(2).getJSONObject("tmp").getString("max"));
            weather.setComf(suggestionObj.getJSONObject("comf").getString("txt"));
            weather.setSunrise(forecastObj.getJSONObject(0).getJSONObject("astro").getString("sr"));
            weather.setSunset(forecastObj.getJSONObject(0).getJSONObject("astro").getString("ss"));
            weather.setPop(forecastObj.getJSONObject(0).getString("pop") + "%");
            weather.setHum(nowObj.getString("hum") + "%");
            weather.setWindDir(nowObj.getJSONObject("wind").getString("dir"));
            weather.setWindSc(nowObj.getJSONObject("wind").getString("sc") + "级");
            weather.setWindSpd(nowObj.getJSONObject("wind").getString("spd") + "kmph");
            weather.setQlty(aqiObj.getJSONObject("city").getString("qlty"));
            weather.setAqi(aqiObj.getJSONObject("city").getString("aqi"));
            weather.setPm25(aqiObj.getJSONObject("city").getString("pm25") + "ug/m³");
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
