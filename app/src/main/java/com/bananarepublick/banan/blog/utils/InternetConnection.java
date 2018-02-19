package com.bananarepublick.banan.blog.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

/**
 * Created by Banan on 17.02.2018.
 */

public class InternetConnection {


    public static boolean checkConnection(@NonNull Context context){

        return ((ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() !=null;

    }
}
