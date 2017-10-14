package tw.edu.fcu.recommendedfood.Data;

import android.content.Context;
import android.content.Intent;

import tw.edu.fcu.recommendedfood.Activity.MainActivity;

/**
 * Created by yuhong on 2017/10/12.
 */

public class LogoutState implements UserState {

    @Override
    public void  forward(Context context){
        gotoLoginActivity(context);
    }

    private void gotoLoginActivity(Context context){
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
    }
}
