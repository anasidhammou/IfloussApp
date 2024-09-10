package com.edulab.e_commerceapp.Tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

public class AppSession {

   public static final String KEY_ACTIVATED_ACCOUNT = "key_account_activated";
   public static final String KEY_CLIENT_ID = null;
   public static final String KEY_PASSWORD = null;
   private static final String KEY_LANGUAGE = "app_language";
   private static final String KEY_DARK_MODE = "app_mode";


   private final SharedPreferences sharedPreferences;

   public AppSession(Context context) {
      this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
   }

   //==============================================================================================
   //==============================================================================================

   public void setClientID(String value) {
      String clientId = Base64.encodeToString(value.getBytes(), Base64.DEFAULT);
      sharedPreferences.edit().putString(KEY_CLIENT_ID, clientId).apply();
   }


   public String getClientID() {
      String clientId = sharedPreferences.getString(KEY_CLIENT_ID, "");
      return new String(Base64.decode(clientId, Base64.DEFAULT));
   }

   //==============================================================================================
   //==============================================================================================

   public void setPassword(String values) {
      String password = Base64.encodeToString(values.getBytes(), Base64.DEFAULT);
      sharedPreferences.edit().putString(KEY_PASSWORD, password).apply();
   }


   public String getPassword() {
      String password = sharedPreferences.getString(KEY_PASSWORD, "");
      return new String(Base64.decode(password, Base64.DEFAULT));
   }

}
