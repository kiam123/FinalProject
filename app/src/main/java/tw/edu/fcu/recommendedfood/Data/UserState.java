package tw.edu.fcu.recommendedfood.Data;

import android.content.Context;

/**
 * Created by yuhong on 2017/10/12.
 */

public interface UserState {
    public void forward(Context context);
    public void commend(Context context);
    public void setAccount(String account);
    public void setName(String name);
    public String getAccount();
    public String getName();
}
