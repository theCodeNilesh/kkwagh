package com.kkwagh.kkw_poly;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import static com.kkwagh.kkw_poly.URLenvActivity.sendOTP_api;

public class SendOTPActivity extends AppCompatActivity {
    ImageView top_circle, circle_top;
    EditText phone_no;
    String PhoneNoHolder;
    Button send_otp;
    String finalResult;
    Boolean CheckEditText;
    String HttpURL = sendOTP_api;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParser httpParse = new HttpParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        send_otp = findViewById(R.id.send_otp);
        phone_no = findViewById(R.id.phone_no);

        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    OTPFunction(PhoneNoHolder);
                } else {
                    Toast.makeText(SendOTPActivity.this, "Please fill all form fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot() {
        PhoneNoHolder = phone_no.getText().toString();

        CheckEditText = !TextUtils.isEmpty(PhoneNoHolder);
    }

    public void OTPFunction(final String mobile_no) {
        class OTPFunctionClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                Toast.makeText(SendOTPActivity.this, "OTP sent Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SendOTPActivity.this, VerifyOTPActivity.class);
                intent.putExtra("phone_no", PhoneNoHolder);
                intent.putExtra("otp", httpResponseMsg);
                startActivity(intent);
                finish();
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("mobile_no", params[0]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }
        }
        OTPFunctionClass otpFunctionClass = new OTPFunctionClass();
        otpFunctionClass.execute(mobile_no);
    }
}
