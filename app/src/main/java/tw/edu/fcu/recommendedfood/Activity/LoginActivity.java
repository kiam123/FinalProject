package tw.edu.fcu.recommendedfood.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import tw.edu.fcu.recommendedfood.Data.LoginContext;
import tw.edu.fcu.recommendedfood.Data.LoginedState;
import tw.edu.fcu.recommendedfood.Data.LogoutState;
import tw.edu.fcu.recommendedfood.R;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditId, mEditPassword;
    private CheckBox mCBoxPasswordRemember;

    private SharedPreferences settings;
    private static final String data = "RegisterPage1";
    private static final String idField = "ID";
    private static final String secretCodeField = "CODE";
    private static final String checkboxField = "checkbox";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView() {
        mEditId = (EditText) findViewById(R.id.edtID);
        mEditPassword = (EditText) findViewById(R.id.edtSecret_code);
        mCBoxPasswordRemember = (CheckBox) findViewById(R.id.checkbox);
    }

    public void loginButton(View view) {
        String id = mEditId.getText().toString();
        String password = mEditPassword.getText().toString();
        if(checkAccount(id, password)){
            setLoginState();
        }
        saveData();
    }

    public void registerButton(View view) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private boolean checkAccount(String id, String password){

        if(!id.equals("admin") && !password.equals("123123")){
            return false;
        }

        return true;
    }

    private void readData() {
        settings = getSharedPreferences(data, 0);
        mEditId.setText(settings.getString(idField, ""));
        mEditPassword.setText(settings.getString(secretCodeField, ""));
        mCBoxPasswordRemember.setChecked(settings.getBoolean(checkboxField, false));

        if(mCBoxPasswordRemember.isEnabled()){
            if(checkAccount(mEditId.getText().toString(), mEditPassword.getText().toString())){
                setLoginState();
            }
        }
    }

    public void setLoginState(){
        LoginContext.getLoginContext().setState(new LoginedState());
        LoginContext.getLoginContext().forward(this);
        LoginContext.getLoginContext().setAccount(mEditId.getText().toString());
        LoginContext.getLoginContext().setName("admin");
    }

    private void saveData() {
        settings = getSharedPreferences(data, 0);
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
