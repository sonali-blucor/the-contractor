package com.blucor.tcthecontractor.helper;

import android.content.Context;
import android.graphics.Typeface;

import com.blucor.tcthecontractor.R;

import java.lang.reflect.Field;

public class FontsOverride {


    public static void setDefaultFont(Context context){
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                context.getResources().getString(R.string.font_name));
        replaceFont("DEFAULT", regular);
        replaceFont("MONOSPACE", regular);
        replaceFont("SERIF", regular);
        replaceFont("SANS_SERIF", regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

   /* private static final int BOLD = 1;
    private static final int BOLD_ITALIC = 2;
    private static final int ITALIC = 3;
    private static final int LIGHT = 4;
    private static final int CONDENSED = 5;
    private static final int THIN = 6;
    private static final int MEDIUM = 7;
    private static final int REGULAR = 8;

    private Context context;

    public FontsOverride(Context context) {
        this.context = context;
    }

    public void loadFonts() {
        Map<String, Typeface> fontsMap = new HashMap<>();
        fontsMap.put("sans-serif", getTypeface(context.getResources().getString(R.string.font_name), REGULAR));
        fontsMap.put("sans-serif-bold", getTypeface(context.getResources().getString(R.string.font_name), BOLD));
        fontsMap.put("sans-serif-italic", getTypeface(context.getResources().getString(R.string.font_name), ITALIC));
        fontsMap.put("sans-serif-light", getTypeface(context.getResources().getString(R.string.font_name), LIGHT));
        fontsMap.put("sans-serif-condensed", getTypeface(context.getResources().getString(R.string.font_name), CONDENSED));
        fontsMap.put("sans-serif-thin", getTypeface(context.getResources().getString(R.string.font_name), THIN));
        fontsMap.put("sans-serif-medium", getTypeface(context.getResources().getString(R.string.font_name), MEDIUM));
        overrideFonts(fontsMap);
    }

    private void overrideFonts(Map<String, Typeface> typefaces) {
        if (Build.VERSION.SDK_INT == 21) {
            try {
                final Field field = Typeface.class.getDeclaredField("sSystemFontMap");
                field.setAccessible(true);
                Map<String, Typeface> oldFonts = (Map<String, Typeface>) field.get(null);
                if (oldFonts != null) {
                    oldFonts.putAll(typefaces);
                } else {
                    oldFonts = typefaces;
                }
                field.set(null, oldFonts);
                field.setAccessible(false);
            } catch (Exception e) {
                Log.e("TypefaceUtil", "Cannot set custom fonts");
            }
        } else {
            try {
                for (Map.Entry<String, Typeface> entry : typefaces.entrySet()) {
                    final Field staticField = Typeface.class.getDeclaredField(entry.getKey());
                    staticField.setAccessible(true);
                    staticField.set(null, entry.getValue());
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Typeface getTypeface(String fontFileName, int fontType) {
        final Typeface tf = Typeface.createFromAsset(context.getAssets(), "" + fontFileName);
        return Typeface.create(tf, fontType);
    }
    */

}
