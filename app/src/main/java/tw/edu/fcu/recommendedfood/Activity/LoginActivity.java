package tw.edu.fcu.recommendedfood.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import tw.edu.fcu.recommendedfood.R;

public class LoginActivity extends AppCompatActivity {

    private EditText mEdtID, mEdtSecret_code;
    private Button mBtnRegister,mBtnSignIn;
    private CheckBox mCbCodeRemembered;

    private SharedPreferences settings;
    private boolean acceptable=false;
    private static final String data = "RegisterPage1";
    private static final String idField = "ID";
    private static final String secretCodeField = "CODE";
    private static final String checkboxField = "checkbox";



    public void initView(){
        mBtnRegister = (Button)findViewById(R.id.register);
        mBtnSignIn = (Button)findViewById(R.id.signIn);
        mEdtID=(EditText)findViewById(R.id.edtID);
        mEdtSecret_code = (EditText) findViewById(R.id.edtSecret_code);
        mCbCodeRemembered = (CheckBox)findViewById(R.id.checkbox);
    }
    private void readData(){
        settings = getSharedPreferences(data,0);
        mEdtID.setText(settings.getString(idField, ""));
        mEdtSecret_code.setText(settings.getString(secretCodeField, ""));
        mCbCodeRemembered.setChecked(settings.getBoolean(checkboxField,false));
    }
    private void saveData() {
        settings = getSharedPreferences(data,0);
        settings.edit().putString(idField,mEdtID.getText().toString())
                .putString(secretCodeField,mEdtSecret_code.getText().toString())
                .putBoolean(checkboxField,mCbCodeRemembered.isChecked())
                .commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        readData();
        mBtnSignIn.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){


                acceptable=true;
                /*假設帳密輸入正確(詳細規則內容之後可再更改)
                ....
                ..

                */
                if(acceptable && mCbCodeRemembered.isChecked()){//登入成功且已勾選記住密碼
                        saveData();
                    //....登入系統的程式碼
                }else if(acceptable && !mCbCodeRemembered.isChecked()){//登入成功且未勾選記住密碼



                        //....登入系統的程式碼
                        mEdtID.setText("");
                        mEdtSecret_code.setText("");
                        mCbCodeRemembered.setChecked(false);
                        saveData();
                    }else {                                    //登入失敗
                       mEdtID.setText("");
                       mEdtSecret_code.setText("");
                       mCbCodeRemembered.setChecked(false);
                       saveData();
                        Toast toast = Toast.makeText(LoginActivity.this,
                                "登入失敗", Toast.LENGTH_LONG);
                        //顯示Toast
                        toast.show();
                    }
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化Intent物件
                Intent intent = new Intent();
                //從MainActivity 到Main2Activity
                intent.setClass(LoginActivity.this , RegisterActivity.class);
                //開啟Activity
                startActivity(intent);
            }
        });
    }


}
