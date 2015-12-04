package com.javen.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
public final class Res {

    /**  */
    private Res() {

    }

    /** key for id */
    private static final String ID = "id";
    /** key for string */
    private static final String STRING = "string";
    /** key for layout */
    private static final String LAYOUT = "layout";
    /** key for style */
    private static final String STYLE = "style";
    /** key for drawable */
    private static final String DRAWABLE = "drawable";
    /** key for color */
    private static final String COLOR = "color";
    /** key for anim */
    private static final String ANIM = "anim";
    /** key for array */
    private static final String ARRAY = "array";
    /** key for attr */
    private static final String ATTR = "attr";
    /** key for dimen */
    private static final String DIMEN = "dimen";
    /** key for xml */
    private static final String XML = "xml";

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res id
     */
    public static int id(Context context, String name) {
        return getIdentifier(context, ID, name);
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res id
     */
    public static int string(Context context, String name) {
        return getIdentifier(context, STRING, name);
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res id
     */
    public static int layout(Context context, String name) {
        return getIdentifier(context, LAYOUT, name);
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res id
     */
    public static int style(Context context, String name) {
        return getIdentifier(context, STYLE, name);
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res id
     */
    public static int drawable(Context context, String name) {
        return getIdentifier(context, DRAWABLE, name);
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res id
     */
    public static int color(Context context, String name) {
        return getIdentifier(context, COLOR, name);
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res id
     */
    public static int anim(Context context, String name) {
        return getIdentifier(context, ANIM, name);
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res id
     */
    public static int array(Context context, String name) {
        return getIdentifier(context, ARRAY, name);
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res id
     */
    public static int attr(Context context, String name) {
        return getIdentifier(context, ATTR, name);
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res id
     */
    public static int dimen(Context context, String name) {
        return getIdentifier(context, DIMEN, name);
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res id
     */
    public static int xml(Context context, String name) {
        return getIdentifier(context, XML, name);
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res
     */
    public static String getString(Context context, String name) {
        return context.getResources().getString(string(context, name));
    }
    
    public static String getString(String value, Object... obj) {
        try {
 
          
            if (obj != null && obj.length > 0) {
                String flat = null;
                int i = 1;
                int maxLength = obj.length;
                while(value.matches("(\n|.)*%\\d\\$s(\n|.)*") && i <= maxLength) {
                    flat = "%"+ i +"\\$s";
                    Object item = obj[i - 1];
                    value = value.replaceFirst(flat, item.toString());
                    i++;
                }
            }
            return value;
        } catch (Exception localException) {
            
        }
        return "";
    }
    

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res
     */
    public static int getColor(Context context, String name) {
        return context.getResources().getColor(color(context, name));
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res
     */
    public static Drawable getDrawable(Context context, String name) {
        return context.getResources().getDrawable(drawable(context, name));
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res
     */
    public static String[] getStringArray(Context context, String name) {
        return context.getResources().getStringArray(array(context, name));
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res
     */
    public static float getDimension(Context context, String name) {
        return context.getResources().getDimension(dimen(context, name));
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param name
     *            res name
     * @return res
     */
    public static Animation getAnimation(Context context, String name) {
        return AnimationUtils.loadAnimation(context, anim(context, name));
    }

    /**
     * 
     * @param context
     *            {@linkplain Context}
     * @param type
     *            res type
     * @param attrName
     *            res name
     * @return res id
     */
    private static int getIdentifier(Context context, String type, String attrName) {

        if (context == null) {
            throw new NullPointerException("the context is null");
        }

        if (type == null || type.trim().length() == 0) {
            throw new NullPointerException("the type is null or empty");
        }

        if (attrName == null || attrName.trim().length() == 0) {
            throw new NullPointerException("the attrNme is null or empty");
        }

        Resources res = context.getResources();
        return res.getIdentifier(attrName, type, context.getApplicationContext().getPackageName());
    }
}