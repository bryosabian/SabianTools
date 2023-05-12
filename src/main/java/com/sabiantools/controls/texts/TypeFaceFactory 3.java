package com.sabiantools.controls.texts;

import android.content.Context;
import android.graphics.Typeface;

import java.util.LinkedHashMap;

public class TypeFaceFactory {

    private static LinkedHashMap<String, Typeface> typeList;

    private static void init() {
        if (typeList == null) {
            typeList = new LinkedHashMap<>();
        }
    }

    public static Typeface getTypeFace(Context context, String ID) {
        init();
        Typeface face = typeList.get(ID);
        if (face != null) {
            return face;
        }
        face = Typeface.createFromAsset(context.getAssets(), ID);
        typeList.put(ID, face);
        return face;
    }
}
