package com.edulab.e_commerceapp.Entreprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

import com.edulab.e_commerceapp.ClaimActivity;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Class.userInfo;
import com.edulab.e_commerceapp.Entreprise.Fragment.AddHmizaFragment;
import com.edulab.e_commerceapp.Entreprise.Fragment.AddProductFragment;
import com.edulab.e_commerceapp.Entreprise.Fragment.ChoixaddFragment;
import com.edulab.e_commerceapp.Entreprise.Fragment.HomeEntrepriseFragment;
import com.edulab.e_commerceapp.Entreprise.Fragment.ListHmizateFragment;
import com.edulab.e_commerceapp.Entreprise.Fragment.ListLivreurFragment;
import com.edulab.e_commerceapp.Entreprise.Fragment.ListProductFragment;
import com.edulab.e_commerceapp.Fragment.HomeFragment;
import com.edulab.e_commerceapp.GiftActvity;
import com.edulab.e_commerceapp.Home_Activity;
import com.edulab.e_commerceapp.NotificationActivity;
import com.edulab.e_commerceapp.PocketsActivity;
import com.edulab.e_commerceapp.ProfilActvity;
import com.edulab.e_commerceapp.R;
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

public class HomeEntrepriseActivity extends AppCompatActivity {
    private HomeEntrepriseFragment homeFragment = new HomeEntrepriseFragment();
    private AddProductFragment addProductFragment=new AddProductFragment();
    private AddHmizaFragment addHmizaFragment=new AddHmizaFragment();
    private ListHmizateFragment listHmizateFragment = new ListHmizateFragment();
    private ListProductFragment listProductFragment=new ListProductFragment();
    private ChoixaddFragment choixaddFragment=new ChoixaddFragment();
    private ListLivreurFragment listLivreurFragment=new ListLivreurFragment();

    private DrawerLayout mDrawerLayout;
    private DatabaseReference userInforef;
    List<userInfo> userinfoArray  = new ArrayList<>();
    String codedata;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private TextView abdrname, nomcomplet, lastconnexion;
    private ImageView imgv;
    public AppSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_entreprise);
        session=new AppSession(this);
        Log.d("email", session.getClientID()+" "+"pass"+" "+session.getPassword());
        userInforef= FirebaseDatabase.getInstance().getReference().child("UserInfo");
        getSupportFragmentManager().beginTransaction().replace(R.id.entr_container,homeFragment).commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.dasshboard){
                    getSupportFragmentManager().beginTransaction().replace(R.id.entr_container,homeFragment).commit();
                    return true;
                }else if(item.getItemId() == R.id.livreurs){
                    getSupportFragmentManager().beginTransaction().replace(R.id.entr_container,listLivreurFragment).commit();
                    return true;
                }else if(item.getItemId() == R.id.addprod){
                    getSupportFragmentManager().beginTransaction().replace(R.id.entr_container,choixaddFragment).commit();
                    return true;
                }else if(item.getItemId() == R.id.hmizate){
                    getSupportFragmentManager().beginTransaction().replace(R.id.entr_container,listHmizateFragment).commit();
                    return true;
                }else if(item.getItemId() == R.id.product){
                    getSupportFragmentManager().beginTransaction().replace(R.id.entr_container,listProductFragment).commit();
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
        startActivity(new Intent(this, NotificationActivity.class));
        finish();
    }

    public void onhomeCustomize(View view){
        startActivity(new Intent(this, HomeEntrepriseActivity.class));
        finish();
    }

    public void onWarningClickedCustomize(View view){
        startActivity(new Intent(this, ClaimEntrepriseActivity.class));
        finish();
    }


    public void onpoketsClickedCustomize(View view){
        startActivity(new Intent(this, PocketsEntrepriseActivity.class));
        finish();
    }

    public void onProfilClickedCustomize(View view){
        startActivity(new Intent(this, ProfilEntrepriseActivity.class));
        finish();
    }

    public void onStatsClickedCustomize(View v){
        startActivity(new Intent(this, StatsEntrepriseActivity.class));
        finish();
    }

}