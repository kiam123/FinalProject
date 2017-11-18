package tw.edu.fcu.recommendedfood.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import tw.edu.fcu.recommendedfood.Data.ArticleBlogData;
import tw.edu.fcu.recommendedfood.Data.LoginContext;
import tw.edu.fcu.recommendedfood.Data.LoginedState;
import tw.edu.fcu.recommendedfood.Data.LogoutState;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditId, mEditPassword;
    private CheckBox mCBoxPasswordRemember;

    private SharedPreferences settings;
    public static final String data = "RegisterPage1";
    private static final String idField = "ID";
    private static final String secretCodeField = "CODE";
    public static final String checkboxField = "checkbox";
    String accountId = "";
    String name = "";


    HttpCall httpCallPost;
    HashMap<String, String> params = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView() {
        mEditId = (EditText) findViewById(R.id.edt_account);
        mEditPassword = (EditText) findViewById(R.id.edt_password);
        mCBoxPasswordRemember = (CheckBox) findViewById(R.id.chk_record_password);
    }

    public void loginButton(View view) {
        String account = mEditId.getText().toString();
        String password = mEditPassword.getText().toString();
        if (account.trim().equals("") || password.trim().equals("")) {
            Toast.makeText(LoginActivity.this, "帳密不能為空", Toast.LENGTH_LONG).show();
            return;
        }
        checkAccount(account, password);
    }

    private void checkAccount(String account, String password) {
        params.put("Account", account);
        params.put("Password", password);
        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
        httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/account_MySQL.php");
        httpCallPost.setParams(params);

        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                final String result = response;
                Log.v("response", response);
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    String state = jsonArray.getJSONObject(0).getString("state");
                    accountId = jsonArray.getJSONObject(0).getString("account_id");
                    name = jsonArray.getJSONObject(0).getString("name");

                    if (state.equals("ok")) {
                        setLoginState();
                        saveData();
                    } else {
                        Toast.makeText(LoginActivity.this, "帳號或密碼輸入有誤！！請重新輸入正確帳密", Toast.LENGTH_LONG).show();
                    }
                    params.clear();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpCallPost);
        setLoginState();
    }

    public void registerButton(View view) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void rememberPassword(View view) {
        saveData();
    }

    public void passwordRecovery(View view) {
        Intent intent = new Intent();
        intent.setClass(this, PasswordRecoveryActivity.class);
        startActivity(intent);
    }

    private void readData() {
        settings = getSharedPreferences(data, MODE_PRIVATE);

        mEditId.setText(settings.getString(idField, "admin"));
        mEditPassword.setText(settings.getString(secretCodeField, "admin"));
        mCBoxPasswordRemember.setChecked(settings.getBoolean(checkboxField, false));
        if (mCBoxPasswordRemember.isChecked()) {
            checkAccount(mEditId.getText().toString(), mEditPassword.getText().toString());
        }
    }

    public void setLoginState() {
        LoginContext.getLoginContext().setState(new LoginedState());
        LoginContext.getLoginContext().forward(this);
        LoginContext.getLoginContext().setAccount(accountId);
        LoginContext.getLoginContext().setName(name);
    }

    private void saveData() {
        settings = getSharedPreferences(data, MODE_PRIVATE);
        settings.edit().putString(idField, mEditId.getText().toString())
                .putString(secretCodeField, mEditPassword.getText().toString())
                .putBoolean(checkboxField, mCBoxPasswordRemember.isChecked())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        readData();
    }
}
