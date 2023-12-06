package com.harsh.s.notes.holder;

import android.content.Context;
import android.util.TypedValue;

public class ThemeUtil {

    ThemeUtil _instance;
    
    public static final int white = 0xFFFFFFFF;
    public static final int black = 0xFF000000;
    public static final int black1 = 0xFF2F2F2F;
    public static final int black2 = 0xFF212121;
    public static final int grey = 0xFFE0E0E0;
    public static final int greyLight = 0x33E0E0E0;
    public static final int materialWhite = 0xFFF2F5FB;
    
    public static int bgColor = white;
    public static int h1Color = black1;
    public static int h2Color = black2;
    public static int rippleColor = grey;
    public static int borderColor = grey;
    public static int imgColor = grey;
    
    public ThemeUtil(){
        new ThemeUtil(ThemeMode.SYS_DEFAULT);
    }
    
    public ThemeUtil(ThemeMode tm){
        if(tm.equals(ThemeMode.DARK)){
            themeDark();
        }
        else if(tm.equals(ThemeMode.LIGHT)){
            themeLight();
        }
        else{
            themeDef();
        }
    }

    public ThemeUtil get_instance() {
        return (_instance != null) ? _instance : new ThemeUtil();
    }

    private void themeDark(){
        
    }
    
    private void themeLight(){
        
    }
    
    private void themeDef(){
        
    }

    public static float getDip(Context _context, int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, _context.getResources().getDisplayMetrics());
    }
    
    
    
    enum ThemeMode {
        DARK,
        LIGHT,
        SYS_DEFAULT;
    }
    
}
