package cn.edu.hdu.movierankapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    private EditText signupUsernameEditText;
    private EditText signupPasswordEditText;
    private EditText signupRepasswordEditText;
    private Button singupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupUsernameEditText = findViewById(R.id.edit_signup_username);
        signupPasswordEditText = findViewById(R.id.edit_signup_password);
        signupRepasswordEditText = findViewById(R.id.edit_signup_repassword);
        singupButton = findViewById(R.id.btn_signup);

        singupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = signupUsernameEditText.getText().toString();
                String password = signupPasswordEditText.getText().toString();
                String repassword = signupRepasswordEditText.getText().toString();

                for (User user:LoginActivity.users) {
                    if (user.getUsername().equals(username)) {
                        Toast.makeText(SignupActivity.this,"已存在该用户",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!username.matches("[0-9a-zA-Z_]{1,10}")) {
                    Toast.makeText(SignupActivity.this,"用户名最多10个字符，只能包括字母，下划线，数字",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.matches("[a-zA-Z0-9]{1,16}")) {
                    Toast.makeText(SignupActivity.this,"密码必须由字母和数字构成，且不能超过16位",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(repassword)) {
                    Toast.makeText(SignupActivity.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                LoginActivity.users.add(user);
                Intent intent = new Intent();
                intent.putExtra("username", signupUsernameEditText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                Toast.makeText(SignupActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
