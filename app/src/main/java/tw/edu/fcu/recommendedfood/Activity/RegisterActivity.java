package tw.edu.fcu.recommendedfood.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;

public class RegisterActivity extends AppCompatActivity {
    private SharedPreferences settings;
    private static final String data = "DATA";
    private static final String accountField = "ACCOUNT";
    private static final String codeField = "CODE";
    private static final String code2Field = "CODE2";
    private static final String nameField = "NAME";
    private static final String emailField = "EMAIL";
    private static final String addressField = "ADDRESS";
    private static final String phoneField = "PHONE";
    private static final String sexField = "SEX";//

    private EditText mEdtRegAccount,mEdtRegScode, mEdtRegScodeAgain,mERegName,
            mERegEmail,mERegAddress,mERegPhone;
    private TextView mTxtWAccount, mTxtWCode, mTxtWCodeAgain, mTxtWName
            , mTxtWEmail, mTxtWAddress, mTxtWPhone;
    private int sex ;//0是為確認，1是男生，2是女生
    private RadioButton mMan,mWoman;
    private Button mRegOK;
    private HttpCall httpCall;
    private String strSex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        clearWarningMessages();

        httpCall = new HttpCall();
        httpCall.setMethodtype(HttpCall.GET);

        mRegOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(sex==1)strSex=new String("男");
                else if (sex==2)strSex=new String("女");
                else if (sex==0)strSex=new String("");
                httpCall.setUrl("http://127.0.0.1/Recommended_food/member_insert.php?query_string=1"
                        +"&id="+mEdtRegAccount.getText().toString()
                        +"&password="+mEdtRegScode.getText().toString()
                        +"&name="+mERegName.getText().toString()
                        +"&sex=男"
                        +"&email="+mERegEmail.getText().toString()
                        +"&phone="+mERegPhone.getText().toString()
                        +"&address="+mERegAddress.getText().toString());
                clearInput();
                clearWarningMessages();
            }
        });

    }








    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_man:
                if (checked)
                sex=1;
                    break;
            case R.id.radio_woman:
                if (checked)
                sex=2;
                    break;
        }
    }

    public void initView(){
        mEdtRegAccount=(EditText)findViewById(R.id.ERegAccount);
        mEdtRegAccount.addTextChangedListener(new ClassOfTextWatcher(mEdtRegAccount));
        mEdtRegScode=(EditText)findViewById(R.id.ERegScode);
        mEdtRegScode.addTextChangedListener(new ClassOfTextWatcher(mEdtRegScode));
        mEdtRegScodeAgain =(EditText)findViewById(R.id.ERegScodeAgain);
        mEdtRegScodeAgain.addTextChangedListener(new ClassOfTextWatcher(mEdtRegScodeAgain));
        mERegName=(EditText)findViewById(R.id.ERegName);
        mERegName.addTextChangedListener(new ClassOfTextWatcher(mERegName));
        mERegEmail=(EditText)findViewById(R.id.ERegEmail);
        mERegEmail.addTextChangedListener(new ClassOfTextWatcher(mERegEmail));
        mERegAddress=(EditText)findViewById(R.id.ERegAddress);
        mERegAddress.addTextChangedListener(new ClassOfTextWatcher(mERegAddress));
        mERegPhone=(EditText)findViewById(R.id.ERegPhone);
        mERegPhone.addTextChangedListener(new ClassOfTextWatcher(mERegPhone));

        mMan=(RadioButton)findViewById(R.id.radio_man) ;
        mWoman=(RadioButton)findViewById(R.id.radio_woman);

        mTxtWAccount =(TextView)findViewById(R.id.TwarningAccount);
        mTxtWCode =(TextView)findViewById(R.id.TwarningCode);
        mTxtWCodeAgain =(TextView)findViewById(R.id.TwarningCodeAgain);
        mTxtWName =(TextView)findViewById(R.id.TwarningName);

        mTxtWEmail =(TextView)findViewById(R.id.TwarningEmail);
        mTxtWAddress =(TextView)findViewById(R.id.TwarningAddress);
        mTxtWPhone =(TextView)findViewById(R.id.TwarningPhone);

        mRegOK=(Button)findViewById(R.id.RegOK);
    }
    private class ClassOfTextWatcher implements TextWatcher {

        private TextView view;

        public ClassOfTextWatcher(View view) {

            if (view instanceof TextView)
                this.view = (TextView) view;
            else
                throw new ClassCastException(
                        "view must be an instance Of TextView");
        }

        @Override
        public void afterTextChanged(Editable s) {
            String string;
            String warning;
            warning="";


            switch (view.getId()) {
                case R.id.ERegAccount:
                    if(s.length()<6)warning+="帳號長度須大於6個字元";
                    mTxtWAccount.setText(warning);
                    break;
                case R.id.ERegScode:
                    if(s.length()<6)warning+="密碼長度須大於6個字元";
                    mTxtWCode.setText(warning);
                    mEdtRegScodeAgain.setText("");
                    mTxtWCodeAgain.setText("");
                    break;
                case R.id.ERegScodeAgain:
                    if(s.length()<6)warning+="密碼長度須大於6個字元";
                    if(mEdtRegScode.getText().toString().compareTo(mEdtRegScodeAgain.getText().toString())!=0)
                        warning+="、與輸入密碼不符";
                    mTxtWCodeAgain.setText(warning);
                    break;
                case R.id.ERegName:
                    //if(s.length()>20)warning+="姓名字元不可超過20";
                    mTxtWName.setText(warning);
                    break;

                case R.id.ERegEmail:
                    string = mERegEmail.getText().toString();
                    if( !string.matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$"))
                        warning+="格式錯誤";
                    mTxtWEmail.setText(warning);
                    break;
                case R.id.ERegAddress:


                    mTxtWAddress.setText(warning);
                    break;
                case R.id.ERegPhone:
                    if(s.length()<9)warning+="長度不足";
                    string= mERegPhone.getText().toString();
                    if(!string.matches("^-?\\d+$"))warning+="、勿輸入非法字元";
                    mTxtWPhone.setText(warning);
                    break;
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

    }
    private void clearWarningMessages(){
        mTxtWAccount.setText("");
        mTxtWAddress.setText("");
        mTxtWCode.setText("");
        mTxtWCodeAgain.setText("");
        mTxtWEmail.setText("");
        mTxtWName.setText("");
        mTxtWPhone.setText("");
    }
    private void clearInput(){
        mEdtRegAccount.setText("");
        mEdtRegScode.setText("");
        mEdtRegScodeAgain.setText("");
        mERegName.setText("");
        mERegEmail.setText("");
        mERegPhone.setText("");
        mERegAddress.setText("");
        sex=0;
        mMan.setChecked(false);
        mWoman.setChecked(false);
    }
}
