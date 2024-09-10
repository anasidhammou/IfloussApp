
package com.edulab.e_commerceapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.edulab.e_commerceapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PrincipalAdminActivity extends AppCompatActivity {

    private HomeAdminFragment homeAdminFragment=new HomeAdminFragment();
    private listcommandeFragment listcommandeFragment=new listcommandeFragment();
    private ListeLivreurFragment listeLivreurFragment=new ListeLivreurFragment();
    private listuserFragment listuserFragments=new listuserFragment();
    private listenteFragment listenteFragments=new listenteFragment();
    private ListclaimFragments listclaimFragments=new ListclaimFragments();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_admin);
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_container,listcommandeFragment).commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.list_commande){
                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_container,listcommandeFragment).commit();
                    return true;
                }else if(item.getItemId() == R.id.livreurs){
                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_container,listeLivreurFragment).commit();
                    return true;
                }else if(item.getItemId() == R.id.entreprise){
                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_container,listenteFragments).commit();
                    return true;
                }else if(item.getItemId() == R.id.user){
                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_container,listuserFragments).commit();
                    return true;
                }else if(item.getItemId() == R.id.claim){
                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_container,listclaimFragments).commit();
                    return true;
                }
                return false;
            }
        });

    }
}