package tw.edu.fcu.recommendedfood.Data;


import android.content.Context;
import android.widget.Toast;

/**
 * Created by yuhong on 2017/10/12.
 */



public class LoginedState implements UserState {

    @Override
    public void  forward(Context context){
        Toast.makeText(context,"轉發微薄", Toast.LENGTH_LONG).show();
    }

}
