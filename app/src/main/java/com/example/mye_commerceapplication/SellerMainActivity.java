package com.example.mye_commerceapplication;

import android.os.Bundle;

import com.example.mye_commerceapplication.Prevalent.Prevalent;
import com.example.mye_commerceapplication.ui.gallery.AddProductsFragment;
import com.example.mye_commerceapplication.ui.gallery.AddProductsViewModel;
import com.example.mye_commerceapplication.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class SellerMainActivity extends AppCompatActivity implements SellerActivityInterface {

    private AppBarConfiguration mAppBarConfiguration;
    private String phoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        Bundle extras = getIntent().getExtras();
        //phoneNumber=extras.getString("phoneSeller");
        phoneNumber=Prevalent.currentOnlineUser.getPhone();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each

        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send, R.id.nav_add,R.id.nav_mod)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Fragment fragment    = new AddProductsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.layout.fragment_add_product,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                 */
                NavController navController = Navigation.findNavController(SellerMainActivity.this,R.id.nav_host_fragment);
                navController.navigate(R.id.nav_add);



            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seller_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    public void switchFragment(int idFrag){
        NavController navController = Navigation.findNavController(SellerMainActivity.this,R.id.nav_host_fragment);
        Bundle bundle = new Bundle();
        bundle.putString("phoneSeller", phoneNumber);
        if(idFrag==R.id.nav_slideshow){

            navController.navigate(R.id.action_nav_home_to_nav_slideshow2,bundle);
        }
        navController.navigate(idFrag);
    }

    @Override
    public String getPhoneSeller(){
        return phoneNumber;
    }



}


