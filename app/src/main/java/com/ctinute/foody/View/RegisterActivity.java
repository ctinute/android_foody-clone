package com.ctinute.foody.View;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ctinute.foody.Object.User;
import com.ctinute.foody.R;
import com.ctinute.foody.Webservice.RestClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    private static final int INTENT_CODE_LOGIN = 10;
    private static final int INTENT_CODE_REGISTER = 9;

    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // back
        ImageView back = (ImageView) findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                MainActivity.user = user;
                setResult(INTENT_CODE_LOGIN, intent);
                finish();
            }
        });

        // register
        final EditText name = (EditText) findViewById(R.id.inp_name);
        final EditText email = (EditText) findViewById(R.id.inp_email);
        final EditText password = (EditText) findViewById(R.id.inp_password);
        final EditText repassword = (EditText) findViewById(R.id.inp_repassword);
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().equals(repassword.getText())){
                    Toast.makeText(getBaseContext(),"Mật khẩu không trùng khớp !",Toast.LENGTH_LONG).show();
                }
                else {
                    RequestParams params = new RequestParams();
                    params.put("name", name.getText());
                    params.put("email", email.getText());
                    params.put("password", password.getText());
                    RestClient.get("login", params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(getBaseContext(), "Đã có lỗi xảy ra, vui lòng thử lại sau !", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            if (responseString.equals("false"))
                                Toast.makeText(getBaseContext(), "Đăng kí thất bại !", Toast.LENGTH_LONG).show();
                            else {
                                Toast.makeText(getBaseContext(), "Đăng kí thành công !", Toast.LENGTH_SHORT).show();
                                user = new User();
                                user.setName(String.valueOf(name.getText()));
                                user.setEmail(String.valueOf(email.getText()));
                                user.setPassword(String.valueOf(password.getText()));
                                user.setImage(BitmapFactory.decodeResource(getResources(),R.drawable.ic_user_circle));
                                MainActivity.user = user;
                                Intent intent = new Intent();
                                setResult(INTENT_CODE_REGISTER, intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });

        // dang nhap
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Tính năng chưa hoàn thiện !",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
