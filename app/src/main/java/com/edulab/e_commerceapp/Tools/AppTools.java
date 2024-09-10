package com.edulab.e_commerceapp.Tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppTools {

   /**
    * Check if user have connection
    *
    * @param context
    * @return
    */
   public static boolean isOnline(Context context) {
      boolean haveConnectedWifi = false;
      boolean haveConnectedMobile = false;
      ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo[] netInfo = cm.getAllNetworkInfo();
      for (NetworkInfo ni : netInfo) {
         if (ni.getTypeName().equalsIgnoreCase("WIFI"))
            if (ni.isConnected())
               haveConnectedWifi = true;
         if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
            if (ni.isConnected())
               haveConnectedMobile = true;
      }
      return haveConnectedWifi || haveConnectedMobile;
   }
}
