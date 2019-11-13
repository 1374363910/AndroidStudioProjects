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
import java.nio.Buffer;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

//    private String myCer = "-----BEGIN CERTIFICATE-----\n" +
//            "MIIG9zCCBd+gAwIBAgIRAJGoxTDnpKfPu9tpFAwWiZ8wDQYJKoZIhvcNAQELBQAw\n" +
//            "gZAxCzAJBgNVBAYTAkdCMRswGQYDVQQIExJHcmVhdGVyIE1hbmNoZXN0ZXIxEDAO\n" +
//            "BgNVBAcTB1NhbGZvcmQxGjAYBgNVBAoTEUNPTU9ETyBDQSBMaW1pdGVkMTYwNAYD\n" +
//            "VQQDEy1DT01PRE8gUlNBIERvbWFpbiBWYWxpZGF0aW9uIFNlY3VyZSBTZXJ2ZXIg\n" +
//            "Q0EwHhcNMTgxMTAxMDAwMDAwWhcNMjAxMDMxMjM1OTU5WjBeMSEwHwYDVQQLExhE\n" +
//            "b21haW4gQ29udHJvbCBWYWxpZGF0ZWQxITAfBgNVBAsTGFBvc2l0aXZlU1NMIE11\n" +
//            "bHRpLURvbWFpbjEWMBQGA1UEAxMNaGV3ZWF0aGVyLmNvbTCCASIwDQYJKoZIhvcN\n" +
//            "AQEBBQADggEPADCCAQoCggEBANsOiIwzCLjZQPapbQxGUvtMQRUNT1/hc6FvAr0E\n" +
//            "u1NArntJQ+7X90tCoAbH6sC4yBEOwYgBQMZepbyuFAA/8hbRhW1R2LA7T4zTvMTM\n" +
//            "F25fIsJ25kOcbXuwnmnby89f8UsxPrWWuCJYEJ9ptk6ZJV89WPgHWGtCmCcqR/O1\n" +
//            "jtSYhULdzR90F0OIeQKTBdeCb2nYJaAdQ8BOoOrOfkI/YPa3TICwmP8Hozr9+5zZ\n" +
//            "wTIrGFUS62XsvX5FSIh7Qu/t6tfFuwauBbAftaSw11Lq6xhaZFcJb9l60Z+HXZ2I\n" +
//            "7XRmuYA+nF/9A6XfXrITjJ2RSWzMOOTin5q4AXDNhL+ledsCAwEAAaOCA3swggN3\n" +
//            "MB8GA1UdIwQYMBaAFJCvajqUWgvYkOoSVnPfQ7Q6KNrnMB0GA1UdDgQWBBSKr7b+\n" +
//            "8aEadsVoGuecdwfeAW/hzjAOBgNVHQ8BAf8EBAMCBaAwDAYDVR0TAQH/BAIwADAd\n" +
//            "BgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwTwYDVR0gBEgwRjA6BgsrBgEE\n" +
//            "AbIxAQICBzArMCkGCCsGAQUFBwIBFh1odHRwczovL3NlY3VyZS5jb21vZG8uY29t\n" +
//            "L0NQUzAIBgZngQwBAgEwVAYDVR0fBE0wSzBJoEegRYZDaHR0cDovL2NybC5jb21v\n" +
//            "ZG9jYS5jb20vQ09NT0RPUlNBRG9tYWluVmFsaWRhdGlvblNlY3VyZVNlcnZlckNB\n" +
//            "LmNybDCBhQYIKwYBBQUHAQEEeTB3ME8GCCsGAQUFBzAChkNodHRwOi8vY3J0LmNv\n" +
//            "bW9kb2NhLmNvbS9DT01PRE9SU0FEb21haW5WYWxpZGF0aW9uU2VjdXJlU2VydmVy\n" +
//            "Q0EuY3J0MCQGCCsGAQUFBzABhhhodHRwOi8vb2NzcC5jb21vZG9jYS5jb20wSgYD\n" +
//            "VR0RBEMwQYINaGV3ZWF0aGVyLmNvbYIOKi5oZXdlYXRoZXIuY26CDyouaGV3ZWF0\n" +
//            "aGVyLmNvbYIPKi5oZXdlYXRoZXIubmV0MIIBewYKKwYBBAHWeQIEAgSCAWsEggFn\n" +
//            "AWUAdQDuS723dc5guuFCaR+r4Z5mow9+X7By2IMAxHuJeqj9ywAAAWbP7LvnAAAE\n" +
//            "AwBGMEQCIDuw19H1pj+2YM0EWsGtS5eGTjRrFPHQQBfxy7o9d/RVAiApmQgycCoH\n" +
//            "lIgd7sB3wj9Qm+kmDG3LW8XdCgQdFy7IUwB1AF6nc/nfVsDntTZIfdBJ4DJ6kZoM\n" +
//            "hKESEoQYdZaBcUVYAAABZs/svBkAAAQDAEYwRAIgKzbuwnf44mhQmhs3dg4qDXTx\n" +
//            "11TOt+pTSVXE9+Hm67MCIFh77glvKgdxnjG1UAD3qAmr4oYmM16Dz4/ba/9GFjpW\n" +
//            "AHUA8JWkWfIA0YJAEC0vk4iOrUv+HUfjmeHQNKawqKqOsnMAAAFmz+y8MgAABAMA\n" +
//            "RjBEAiBOMK5DUkrC9QEwogsEilPRSzcc3e74RRdMAh1nF6m0hgIgFNbNO8jEw0Op\n" +
//            "LqfS6wPQQhGD2tARP6xAVIk7ewUp+cIwDQYJKoZIhvcNAQELBQADggEBAHLa78Ho\n" +
//            "xYFy38v5SA9tfDIeKHsnFeJe9bLl5VGMx+v6A4PUwSiGch25YZJaJRD85M0mIZlb\n" +
//            "aTrXHwdxA5N0xJwYzQMMD3cxfxGJjUMNMzFJxDZqFdWH53lnQJGPL3ah3DM/JhFF\n" +
//            "oC0C0njJEVnEh2DN8fYEznXPuJHfN4iIswILrxTvYLM/cwiddgqJxPiLmpEkE6fJ\n" +
//            "3H7W9Rao/+qmG32nhZUpkKqEnNYcj7XlmJsatz+CCPrucRQqnmIcAiBzqD8veq15\n" +
//            "AYNhZUAS4+yrdWzOmKfh7V1VrQ1tMUm21lkWkvDDOLV+slPEAS9StagT48BRSny1\n" +
//            "g9eq+11B3uxr6so=\n" +
//            "-----END CERTIFICATE-----";

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
//                    System.out.println(cityName);
                    Weather weather = new Weather();

                    URL urlWeatherNow = new URL("https://free-api.heweather.net/s6/weather/now?location=" + cityName + "&key=56c7111027ab4242874a3a2ff434328e");
//                    System.out.println(urlWeatherNow.toString());
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
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());

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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class MyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            // TODO Auto-generated method stub
            return true;
        }

    }

    private class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
            // TODO Auto-generated method stub
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

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
