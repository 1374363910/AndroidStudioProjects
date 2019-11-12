package cn.edu.hdu.movierankapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    static List<User> users = new ArrayList<User>();

    private EditText loginUsernameEditText;
    private EditText loginPasswordEditText;
    private Button loginButton;
    private Button gotoSignupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        users.add(user);

        loginUsernameEditText = findViewById(R.id.edit_login_username);
        loginPasswordEditText = findViewById(R.id.edit_login_password);
        loginButton = findViewById(R.id.btn_login);
        gotoSignupButton = findViewById(R.id.btn_goto_signup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUsernameEditText.getText().toString();
                String password = loginPasswordEditText.getText().toString();
                for (User user : users) {
                    if (user.getUsername().equals(username)&& user.getPassword().equals(password)){
                        Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, RankActivity.class);
                        startActivity(intent);
                        return;
                    }
                }
                Toast.makeText(LoginActivity.this,"不存在该用户或密码错误",Toast.LENGTH_SHORT).show();
            }
        });

        gotoSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if ((resultCode == RESULT_OK)) {
                    String username = data.getStringExtra("username");
                    loginUsernameEditText.setText(username);
                }
                break;
            default:
                break;
        }
    }
}
