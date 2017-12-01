package tw.edu.fcu.recommendedfood.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import tw.edu.fcu.recommendedfood.Encryption.MD5;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;

public class RegisterActivity extends AppCompatActivity {
    EditText edtAccount;
    EditText edtPassword;
    EditText edtRepeatPassword;
    EditText edtName;
    EditText edtEmail;
    EditText edtPhone;
    EditText edtAddress;
    TextView txtErrorAccount;
    TextView txtErrorPassword;
    TextView txtErrorRepeatPassword;
    TextView txtErrorName;
    TextView txtErrorEmail;
    TextView txtErrorPhone;
    TextView txtErrorAddress;
    Button btnSubmit;
    AccountData lock = new AccountData();
    String content = "";
    boolean sex = true;
    HashMap<String, String> params = new HashMap<String, String>();
    MD5 md5 = new MD5();
    ConnectivityManager mConnectivityManager;
    NetworkInfo mNetworkInfo;
    HttpCall httpCallPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        setTextChangedListener();
        initNetwork();
    }

    public void initView() {
        edtAccount = (EditText) findViewById(R.id.edt_account);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtRepeatPassword = (EditText) findViewById(R.id.edt_repeat_password);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        txtErrorAccount = (TextView) findViewById(R.id.txt_error_account);
        txtErrorPassword = (TextView) findViewById(R.id.txt_error_password);
        txtErrorRepeatPassword = (TextView) findViewById(R.id.txt_error_repeat_password);
        txtErrorName = (TextView) findViewById(R.id.txt_error_name);
        txtErrorEmail = (TextView) findViewById(R.id.txt_error_email);
        txtErrorPhone = (TextView) findViewById(R.id.txt_error_phone);
        txtErrorAddress = (TextView) findViewById(R.id.txt_error_address);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    public void initNetwork() {
        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
    }

    public void setTextChangedListener() {
        edtAccount.addTextChangedListener(new NewTextWatcher(edtAccount, RegisterActivity.this));
        edtPassword.addTextChangedListener(new NewTextWatcher(edtPassword, RegisterActivity.this));
        edtRepeatPassword.addTextChangedListener(new NewTextWatcher(edtRepeatPassword, RegisterActivity.this));
        edtName.addTextChangedListener(new NewTextWatcher(edtName, RegisterActivity.this));
        edtEmail.addTextChangedListener(new NewTextWatcher(edtEmail, RegisterActivity.this));
        edtPhone.addTextChangedListener(new NewTextWatcher(edtPhone, RegisterActivity.this));
        edtAddress.addTextChangedListener(new NewTextWatcher(edtAddress, RegisterActivity.this));
    }

    public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_man:
                sex = true;
                break;
            case R.id.radio_woman:
                sex = false;
                break;
        }
    }

    public void submitButon(View view) {
        if (mNetworkInfo == null) {
            Toast.makeText(RegisterActivity.this, "沒有網絡", Toast.LENGTH_LONG).show();
            return;
        }

        Log.v("countSubmitButon", lock.getCountState() + "");
        if (lock.getCountState() < 7) {
            Toast.makeText(RegisterActivity.this, "資料必須填齊", Toast.LENGTH_LONG).show();
        } else {
//            params.put("Account", edtAccount.getText().toString());
//            params.put("Password", md5.MD5SixteenBit(edtPassword.getText().toString()));
//            params.put("Name", edtName.getText().toString());
//            params.put("Sex", sex == true ? "男" : "女");
//            params.put("Email", edtEmail.getText().toString());
//            params.put("Phone", edtPhone.getText().toString());
//            params.put("Address", edtAddress.getText().toString());
//
//            httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/RegisterAccount.php");
//            httpCallPost.setParams(params);
//            new HttpRequest() {
//                @Override
//                public void onResponse(String response) {
//                    super.onResponse(response);
//                    lock.result = response;
//                }
//            }.execute(httpCallPost);
//
//            Log.v("result", lock.result);
//            if (lock.result.contains("ok")) {
//                Toast.makeText(RegisterActivity.this, "註冊成功", Toast.LENGTH_LONG).show();
//            } else if (lock.result.contains("no")) {
//                txtErrorAccount.setText("此帳號已經有人使用");
//                txtErrorAccount.setVisibility(View.VISIBLE);
//                Toast.makeText(RegisterActivity.this, "註冊失敗，此帳號已被使用", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(RegisterActivity.this, "系統有問題", Toast.LENGTH_LONG).show();
//            }
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//            database.child("account_table").push().setValue(edtAccount.getText().toString());
            database.child("account_table").child(edtAccount.getText().toString()).child("account_id").setValue(edtAccount.getText().toString());
            database.child("account_table").child(edtAccount.getText().toString()).child("address").setValue(edtAddress.getText().toString());
            database.child("account_table").child(edtAccount.getText().toString()).child("email").setValue(edtEmail.getText().toString());
            database.child("account_table").child(edtAccount.getText().toString()).child("name").setValue(edtName.getText().toString());
            database.child("account_table").child(edtAccount.getText().toString()).child("password").setValue(md5.MD5SixteenBit(edtPassword.getText().toString()));
            database.child("account_table").child(edtAccount.getText().toString()).child("phone").setValue(edtPhone.getText().toString());
            database.child("account_table").child(edtAccount.getText().toString()).child("sex").setValue(sex == true ? "男" : "女");
        }

//        params.clear();


    }

    class AccountData {
        int count[] = new int[7];
        int total = 0;
        int temp = 0;
        String result = "";

        public AccountData(){}

        public int getCountState() {
            for (int i : count) {
                total += i;
            }
            temp = total;
            total = 0;
            return temp;
        }
    }

    public class NewTextWatcher implements TextWatcher {
        EditText et;
        Context context;

        public NewTextWatcher(EditText editText, Context context) {
            this.et = editText;
            this.context = context;
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            Log.d("TAG", "afterTextChanged--------------->");
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            Log.d("TAG", "beforeTextChanged--------------->");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            Log.d("TAG", "onTextChanged--------------->");
            if (s.length() != 0) {
                content = et.getText().toString();
                switch (et.getId()) {
                    case R.id.edt_account: {
                        final String rule = "^[a-zA-Z]\\w{3,17}$";//以字母开头，长度在4~18之间，只能包含字符、数字和下划线
                        if (!content.matches(rule)) {
                            txtErrorAccount.setText("以字母开头，长度在4~18之间，只能包含字符、数字和下划线");
                            txtErrorAccount.setVisibility(View.VISIBLE);
                            Log.v("text", "" + et.getText().toString());
                        } else {
                            et.setOnFocusChangeListener(edtAccountOnFocusChangeListener);
                            txtErrorAccount.setVisibility(View.GONE);
                        }
                        break;
                    }
                    case R.id.edt_password: {
                        if (content.length() < 4) {
                            txtErrorPassword.setText("密碼必須大於4位");
                            txtErrorPassword.setVisibility(View.VISIBLE);
                            lock.count[1] = 0;
                        } else {
                            txtErrorPassword.setVisibility(View.GONE);
                            lock.count[1] = 1;
                        }
                        edtRepeatPassword.setText("");
                        lock.count[2] = 0;
                        break;
                    }
                    case R.id.edt_repeat_password: {
                        if (edtPassword.getText().toString().equals(content)) {
                            txtErrorRepeatPassword.setVisibility(View.GONE);
                            lock.count[2] = 1;
                        } else {
                            txtErrorRepeatPassword.setText("密碼不一致");
                            txtErrorRepeatPassword.setVisibility(View.VISIBLE);
                            lock.count[2] = 0;
                        }
                        break;
                    }
                    case R.id.edt_name: {
                        if (content.length() < 3) {
                            txtErrorName.setText("名字太短");
                            txtErrorName.setVisibility(View.VISIBLE);
                            lock.count[3] = 0;
                        } else {
                            txtErrorName.setVisibility(View.GONE);
                            lock.count[3] = 1;
                        }
                        break;
                    }
                    case R.id.edt_email: {
                        final String rule = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
                        if (!content.matches(rule)) {
                            txtErrorEmail.setText("格式有錯");
                            txtErrorEmail.setVisibility(View.VISIBLE);
                            lock.count[4] = 0;
                        } else {
                            txtErrorEmail.setVisibility(View.GONE);
                            lock.count[4] = 1;
                        }
                        break;
                    }
                    case R.id.edt_phone: {
                        if (!content.matches("^-?\\d+$")) {
                            txtErrorPhone.setText("號碼有錯");
                            txtErrorPhone.setVisibility(View.VISIBLE);
                            lock.count[5] = 0;
                        } else {
                            txtErrorPhone.setVisibility(View.GONE);
                            lock.count[5] = 1;
                        }
                        break;
                    }
                    case R.id.edt_address: {
                        if (content.length() < 5) {
                            txtErrorAddress.setText("地址太短了");
                            txtErrorAddress.setVisibility(View.VISIBLE);
                            lock.count[6] = 0;
                        } else {
                            txtErrorAddress.setVisibility(View.GONE);
                            lock.count[6] = 1;
                        }
                        break;
                    }
                }
            }
        }
    }

    private View.OnFocusChangeListener edtAccountOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            //當focus離開後
            if (!hasFocus) {
                if (content.length() < 4) {
                    txtErrorAccount.setText("賬號必須大於4");
                    txtErrorAccount.setVisibility(View.VISIBLE);
                    return;
                } else {
                    Log.v("content123",content);
                    txtErrorAccount.setVisibility(View.GONE);
                    txtErrorAccount.setText("驗證中...");
                    txtErrorAccount.setVisibility(View.VISIBLE);
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        params.put("check_account", content);
//                        httpCallPost.setUrl("http://140.134.26.31/postoffice_pj/CheckAccount.php");
//                        httpCallPost.setParams(params);
//                        new HttpRequest() {
//                            @Override
//                            public void onResponse(String response) {
//                                super.onResponse(response);
//                                Log.v("response", response);
//                                if (response.contains("ok")) {
//                                    txtErrorAccount.setVisibility(View.GONE);
//                                    lock.count[0] = 1;
//                                    Log.v("count", lock.count[0] + "");
//                                } else {
//                                    lock.count[0] = 0;
//                                    txtErrorAccount.setText("此帳號已經有人使用");
//                                }
//                                params.clear();
//                            }
//                        }.execute(httpCallPost);
//                    }
//                }.start();

                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    myRef.child("account_table").child(content).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.v("content123",dataSnapshot.getValue()+"");
                            if (dataSnapshot.getValue() != null) {
                                lock.count[0] = 0;
                                txtErrorAccount.setText("此帳號已經有人使用");
                            } else {
                                txtErrorAccount.setVisibility(View.GONE);
                                lock.count[0] = 1;
                                Log.v("count", lock.count[0] + "");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        }
    };
}
