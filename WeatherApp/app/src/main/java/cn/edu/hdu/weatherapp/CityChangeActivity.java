package cn.edu.hdu.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CityChangeActivity extends AppCompatActivity {
    private EditText editCity;
    private Button checkButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_change);

        editCity = findViewById(R.id.edit_city);
        checkButton = findViewById(R.id.check_Button);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("defaultCity",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String cityName = editCity.getText().toString();
                editor.clear();
                editor.putString("cityName", cityName);
                editor.commit();
                Intent intent = new Intent(CityChangeActivity.this, MainActivity.class);
                startActivity(intent);
                return;
            }
        });
    }
}
