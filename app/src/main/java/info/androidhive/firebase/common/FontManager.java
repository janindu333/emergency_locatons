package info.androidhive.firebase.common;

import android.content.Context;
import android.graphics.Typeface;

public class FontManager {
    public enum Style {
        ExtraLight,
        Light,
        Regular,
        Medium,
        SemiBold,
        Bold
    }

    public static Typeface getTypeface(Context context, Style style) {
        if(style == Style.ExtraLight) {
            return Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-ExtraLight.ttf");
        } else if(style == Style.Light) {
            return Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Light.ttf");
        } else if(style == Style.Medium) {
            return Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Medium.ttf");
        } else if(style == Style.SemiBold) {
            return Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-SemiBold.ttf");
        } else if(style == Style.Bold) {
            return Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Bold.ttf");
        } else {
            return Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Regular.ttf");
        }
    }
}
