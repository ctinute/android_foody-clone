package com.ctinute.foody.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ctinute.foody.Object.User;
import com.ctinute.foody.R;
import com.ctinute.foody.Webservice.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private static final int INTENT_CODE_LOGIN = 10;

    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // back
        ImageButton back = (ImageButton) findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                MainActivity.user = user;
                setResult(INTENT_CODE_LOGIN, intent);
                finish();
            }
        });

        // login
        final EditText email = (EditText) findViewById(R.id.inp_email);
        final EditText password = (EditText) findViewById(R.id.inp_password);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put("email",email.getText());
                params.put("password",password.getText());
                RestClient.get("login", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        if (response.length()==0)
                            Toast.makeText(getBaseContext(),"Đăng nhập thất bại !",Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(getBaseContext(),"Đăng nhập thành công !",Toast.LENGTH_SHORT).show();
                            try {
                                user = new User(response.getJSONObject(0),getBaseContext());
                                MainActivity.user = user;
                                Intent intent = new Intent();
                                setResult(INTENT_CODE_LOGIN, intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getBaseContext(),"Đã có lỗi xảy ra, vui lòng thử lại sau !",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // quen mat khau
        TextView forgot = (TextView) findViewById(R.id.forgot);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Tính năng chưa hoàn thiện !",Toast.LENGTH_SHORT).show();
            }
        });

        // dang ki
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Tính năng chưa hoàn thiện !",Toast.LENGTH_SHORT).show();
                /*
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                */
            }
        });
    }

}
