package com.edulab.e_commerceapp.Tools;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.edulab.e_commerceapp.Fragment.CommandeFragment;
import com.edulab.e_commerceapp.R;

public class Dialogs {




   public static void alertDialog(final Context context, final String title, final String message){
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder
              //.setTitle(title)
              .setMessage(message)
              .setPositiveButton(context.getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                 }
              })
              .show();
   }


   public static void alertDialog(final Context context,final String message){
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder
              .setMessage(message)
              .setPositiveButton(context.getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                 }
              })
              .show();
   }



   public static void alertDialogWithTitle(final Context context, final String title, final String message){
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder
              .setTitle(title)
              .setMessage(message)
              .setPositiveButton(context.getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                 }
              })
              .show();
   }




   //==============================================================================================
   //                                  OTP
   //==============================================================================================


   public enum OTP_ALERT_MESSAGE
   {
      EMPTY,INCORRECT;
   }




}
