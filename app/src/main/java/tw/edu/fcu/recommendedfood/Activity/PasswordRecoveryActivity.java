package tw.edu.fcu.recommendedfood.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import tw.edu.fcu.recommendedfood.R;

public class PasswordRecoveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);


    }

    public void btnSend(View view){
        Toast.makeText(this,"系統還未開放",Toast.LENGTH_SHORT).show();
    }
}
