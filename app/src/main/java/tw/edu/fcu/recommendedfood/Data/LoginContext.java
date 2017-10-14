package tw.edu.fcu.recommendedfood.Data;

import android.content.Context;

/**
 * Created by yuhong on 2017/10/13.
 */

public class LoginContext {
    UserState mState = new LogoutState();
    static LoginContext sLoginContext = new LoginContext();

    private  LoginContext(){
    }
    public static LoginContext getLoginContext(){
        return  sLoginContext;
    }
    public void setState(UserState aState){
        this.mState = aState;
    }
    public void forward(Context context){
        mState.forward(context);
    }

}
