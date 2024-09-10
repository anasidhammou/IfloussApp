package com.edulab.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.edulab.e_commerceapp.Tools.AppTools;
import com.edulab.e_commerceapp.Tools.Dialogs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecoverPasswordActivity extends AppCompatActivity {

    private EditText edtmail;
    private String valuemail;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        edtmail=findViewById(R.id.edt_email);
        auth=FirebaseAuth.getInstance();
    }

    public void forget(View view) {
        if (AppTools.isOnline(this) == false) {
            Dialogs.alertDialog(this, "يرجى الاتصال باتصال بالإنترنت ، أو أن تكون أقرب عبر wifii أو اتصالك المحمول");
            return;
        }else{
            valuemail=edtmail.getText().toString();
            if(valuemail.isEmpty()){
                Toast.makeText(getApplicationContext(),"المرجو إدخال البريد الإلكتروني",Toast.LENGTH_LONG).show();
                return;
            }else
                if(!Patterns.EMAIL_ADDRESS.matcher(valuemail).matches()){
                    Toast.makeText(getApplicationContext(),"المرجو إدخال بريد الإلكتروني صحيح",Toast.LENGTH_LONG).show();
                    return;
                }else{

               auth.sendPasswordResetEmail(valuemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(getApplicationContext(),"يرجى التحقق من بريدك الإلكتروني لإعادة تعيين كلمة المرور الخاصة بك",Toast.LENGTH_LONG).show();
                           startActivity(new Intent(RecoverPasswordActivity.this,MainActivity.class));
                           finish();
                       }else{
                           Toast.makeText(getApplicationContext(),"حدث شيء ما ، يرجى المحاولة مرة أخرى",Toast.LENGTH_LONG).show();
                           startActivity(new Intent(RecoverPasswordActivity.this,MainActivity.class));
                           finish();
                       }
                   }
               });

                }
        }
    }
}