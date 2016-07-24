package com.dikshay.mobilecomputing.assignments.ifthisthenthat.Utils;

import android.view.WindowManager;

/**
 * Created by Dikshay on 4/13/2016.
 */
public class Utilities {
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

}
