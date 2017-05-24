package com.ctinute.foody.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ctinute.foody.R;

public class AccountActivity extends AppCompatActivity {

    RelativeLayout login;
    RelativeLayout logout;

    private static final int INTENT_CODE_LOGIN = 10;
    private static final int INTENT_CODE_REGISTER = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // event click dang nhap -> goi activity dang nhap
        login = (RelativeLayout) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivityForResult(intent,INTENT_CODE_LOGIN);
            }
        });

        // event click dang nhap -> dang xuat
        logout = (RelativeLayout) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.user = null;
                Toast.makeText(getBaseContext(),"Bạn đã đăng xuất !", Toast.LENGTH_SHORT).show();
                checkLogin();
            }
        });
    }

    // xu li ket qua tra ve cua activity con
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_CODE_LOGIN){
            // request code tu AccountActivity -> LoginActivity
            /*
            if (data.get){
                changeCity(data.getIntExtra("selectedCityId",1), data.getStringExtra("selectedCityName"));
            }
            */
        }
    }

    // xet trang thai dang nhap
    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }

    private void checkLogin(){
        login = (RelativeLayout) findViewById(R.id.login);
        logout = (RelativeLayout) findViewById(R.id.logout);
        TextView text =  (TextView) login.findViewById(R.id.login_text);
        ImageView imageView = (ImageView) login.findViewById((R.id.login_ioon));
        if (MainActivity.user == null){
            logout.setVisibility(View.GONE);
            String  s = "Đăng nhập";
            text.setText(s);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.tn_ic_not_login_profile));
        }
        else{
            logout.setVisibility(View.VISIBLE);
            text.setText(MainActivity.user.getName());
            imageView.setImageBitmap(MainActivity.user.getImage());
        }
    }
}
