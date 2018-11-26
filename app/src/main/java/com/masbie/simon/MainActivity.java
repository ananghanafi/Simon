package com.masbie.simon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private String gg;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            System.out.println("id " + id);
            switch (id) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    //    fragmentTransaction.replace(R.id.frameLayout, new HomeFragment()).commit();
                    System.out.println("idhome " + id);
                    break;
                case R.id.navigation_dashboard:
                    fragment = new SimonFragment();
                    //    fragmentTransaction.replace(R.id.frameLayout, new PengaturanFragment()).commit();
                    System.out.println("idprofile " + id);
                    break;
                case R.id.navigation_notifications:
                    fragment = new EncryptFragment();
                    //       fragmentTransaction.replace(R.id.frameLayout, new InfoAplikasiFragment()).commit();
                    System.out.println("idInfo " + id);
                    break;
                case R.id.navigation_dashboard2:
                    fragment = new DecryptFragment();
                    //       fragmentTransaction.replace(R.id.frameLayout, new InfoAplikasiFragment()).commit();
                    System.out.println("idInfo " + id);
                    break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frameLayout, fragment).commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        System.out.println("Tes ");
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        gg = getIntent().getStringExtra("ambilNilai");
        if (gg != null) {
            fragmentTransaction.replace(R.id.frameLayout, new DecryptFragment()).commit();
        } else {

            fragmentTransaction.replace(R.id.frameLayout, new HomeFragment()).commit();
        }


    }
}
