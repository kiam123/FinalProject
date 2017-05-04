package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by yan on 2017/4/6.
 */import android.graphics.Color;

public enum WaitingEatColors {

    LIGHTGREY("#D3D3D3"), BLUE("#33B5E5"), PURPLE("#AA66CC"),
    GREEN("#99CC00"), ORANGE("#FFBB33"), RED("#FF4444");

    private String code;

    private WaitingEatColors(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public int parseColor() {
        return Color.parseColor(code);
    }

}