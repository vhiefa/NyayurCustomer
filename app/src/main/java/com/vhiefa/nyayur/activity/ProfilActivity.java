package com.vhiefa.nyayur.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vhiefa.nyayur.R;
import com.vhiefa.nyayur.helper.SessionManager;

import java.util.HashMap;

public class ProfilActivity extends ActionBarActivity {

    String nope, name, alamat, tukang;
    TextView TxtViewNama, TxtViewNope, TxtViewAlamat, TxtViewTukang;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        nope = user.get(SessionManager.KEY_NOPE);
        name = user.get(SessionManager.KEY_NAME) ;
        alamat = user.get(SessionManager.KEY_ALAMAT);
        tukang = user.get(SessionManager.KEY_TKGSYR);

        TxtViewNama = (TextView) findViewById(R.id.nama_cust);
        TxtViewNope = (TextView) findViewById(R.id.nope_cust);
        TxtViewAlamat = (TextView) findViewById(R.id.alamat_cust);
        TxtViewTukang = (TextView) findViewById(R.id.tukang_cust);

        TxtViewNama.setText(name);
        TxtViewAlamat.setText(alamat);
        TxtViewNope.setText(nope);
        TxtViewTukang.setText(tukang);

        Button btnLogout = (Button) findViewById(R.id.logoutBtn);
        Button btnUbahProfil = (Button) findViewById(R.id.ubahProfilBtn);
        Button btnUbahTkgSayur = (Button) findViewById(R.id.ubahTukangSayur);
        Button btnHistoryBelanja = (Button) findViewById(R.id.daftarBelanjaBtn);

        /**
         * Logout button click event
         * */
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
                finish();
            }
        });

        btnUbahTkgSayur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfilActivity.this, DaftarTukangActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnUbahProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfilActivity.this, UbahProfilActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
