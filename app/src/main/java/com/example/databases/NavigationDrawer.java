package com.example.databases;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.Session;
import com.google.android.material.navigation.NavigationView;

public class NavigationDrawer extends AppCompatActivity {

    ResponseLogin userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        getSupportActionBar().hide();

        final DrawerLayout drawerLayout =  findViewById(R.id.drawerLayout);
        ImageView menuImageView =  findViewById(R.id.imageMenu);
        userLogin =  Session.obtenerSessionUsuario(getApplicationContext());

        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView =  findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        final NavController navController  = Navigation.findNavController(this , R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView , navController);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.userName);
        TextView navMailUser = (TextView) headerView.findViewById(R.id.userMail);
        navUsername.setText(userLogin.getNombre());
        navMailUser.setText(userLogin.getCorreo());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if (id==R.id.menuCerrarSesion){
                    Session.cerrarSessionUsuario(getApplicationContext());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //This is for maintaining the behavior of the Navigation view
                    NavigationUI.onNavDestinationSelected(item,navController);
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                return false;

            }
        });

    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void onBackPressed() {

    }
}
