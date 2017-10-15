package tw.edu.fcu.recommendedfood.Data;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import tw.edu.fcu.recommendedfood.Activity.LoginActivity;
import tw.edu.fcu.recommendedfood.Activity.MainActivity;

/**
 * Created by yuhong on 2017/10/12.
 */



public class LoginedState implements UserState {
    String account;
    String name;

    @Override
    public void  forward(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void commend(Context context) {

    }

    @Override
    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }
}
