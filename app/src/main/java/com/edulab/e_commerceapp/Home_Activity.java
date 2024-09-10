package com.edulab.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edulab.e_commerceapp.Class.IOnBackPressed;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Class.userInfo;
import com.edulab.e_commerceapp.Fragment.CommandeFragment;
import com.edulab.e_commerceapp.Fragment.HmizaFragment;
import com.edulab.e_commerceapp.Fragment.HomeFragment;
import com.edulab.e_commerceapp.Fragment.ListCommandeFragment;
import com.edulab.e_commerceapp.Fragment.StoreFragment;
import com.edulab.e_commerceapp.Tools.AppSession;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Home_Activity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private TextView abdrname, nomcomplet, lastconnexion;
    private ImageView imgv;

    private HomeFragment homeFragment = new HomeFragment();
    private CommandeFragment commandeFragment=new CommandeFragment();
    private StoreFragment storeFragment=new StoreFragment();
    private ListCommandeFragment listCommandeFragment=new ListCommandeFragment();
    private HmizaFragment hmizaFragment=new HmizaFragment();
    private DrawerLayout mDrawerLayout;
    private DatabaseReference userInforef;
    List<userInfo> userinfoArray  = new ArrayList<>();
    String codedata;
    public AppSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        session=new AppSession(this);
        Log.d("email", session.getClientID()+" "+"pass"+" "+session.getPassword());
        userInforef= FirebaseDatabase.getInstance().getReference().child("UserInfo");
        getSupportFragmentManager().beginTransaction().replace(R.id.fr_container,homeFragment).commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
       bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.dasshboard){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fr_container,homeFragment).commit();
                    return true;
                }else if(item.getItemId() == R.id.commande){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fr_container,commandeFragment).commit();
                    return true;
                }else if(item.getItemId() == R.id.store){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fr_container,storeFragment).commit();
                    return true;
                }else if(item.getItemId() == R.id.list_commande){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fr_container,listCommandeFragment).commit();
                    return true;
                }else if(item.getItemId() == R.id.hmiza){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fr_container,hmizaFragment).commit();
                    return true;
                }
                return false;
            }
        });

       imgv=findViewById(R.id.image);
       nomcomplet=findViewById(R.id.txt_nom2);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        lastconnexion=findViewById(R.id.derniere_connexion);
        initMenu();
        valueprofil();


    }

    private void valueprofil() {
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null){
                    String Nom=userProfile.nom;
                    String Prenom = userProfile.prenom;


                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    nomcomplet.setText(Prenom+" "+Nom);
                    lastconnexion.setText(currentDate);
                    getalluser(userProfile.email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getalluser(String email) {
        userinfoArray.clear();
        userInforef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    userinfoArray.add(postSnapshot.getValue(userInfo.class));
                }

                if(userinfoArray.size()==0){
                    return;
                }else{
                    for (int i = 0; i < userinfoArray.size(); i++) {
                        if(userinfoArray.get(i).emailuser.equals(email)){
                            codedata=userinfoArray.get(i).photo.replace("data:image/jpeg;base64,","");
                            byte[] code = Base64.decode(codedata, Base64.DEFAULT);
                            Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
                            imgv.setImageBitmap(bitmap);
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initMenu() {
        //--Menu
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void onMaBanqueClicked(View view) {
        mDrawerLayout.openDrawer(GravityCompat.END);

    }

    public void onMesComptesClickedCustomize(View view) {
        startActivity(new Intent(this,NotificationActivity.class));
        finish();
    }

    public void onhomeCustomize(View view){
        startActivity(new Intent(this,Home_Activity.class));
        finish();
    }

    public void onWarningClickedCustomize(View view){
        startActivity(new Intent(this,ClaimActivity.class));
        finish();
    }


    public void onpoketsClickedCustomize(View view){
        startActivity(new Intent(this,PocketsActivity.class));
        finish();
    }

    public void onProfilClickedCustomize(View view){
        startActivity(new Intent(this,ProfilActvity.class));
        finish();
    }

    public void  ongiftClickedCustomize(View view){
        startActivity(new Intent(this,GiftActvity.class));
        finish();
    }




}