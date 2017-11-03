package tw.edu.fcu.recommendedfood.Data;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by kiam on 2017/10/18.
 */

public interface OnLocationChangeListener extends Serializable {
    public void getLocation(LatLng latLng);
}
