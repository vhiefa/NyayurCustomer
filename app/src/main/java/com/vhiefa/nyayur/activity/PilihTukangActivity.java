package com.vhiefa.nyayur.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vhiefa.nyayur.R;
import com.vhiefa.nyayur.app.AppConfig;
import com.vhiefa.nyayur.helper.JSONParser;
import com.vhiefa.nyayur.helper.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by afifatul on 2016-11-30.
 */

public class PilihTukangActivity extends ActionBarActivity {

    JSONParser jParser = new JSONParser();

    TextView TxtViewId, TxtViewNama, TxtViewNope, TxtViewAlamat;
    Button PilihTSBtn;
    String isi_id_ts;
    String isi_nama_ts;
    String isi_nope_ts;
    String isi_alamat_ts;
    String nope_customer, name, alamat, uid, created_at;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihtukang);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        nope_customer = user.get(SessionManager.KEY_NOPE);
        name = user.get(SessionManager.KEY_NAME) ;
        alamat = user.get(SessionManager.KEY_ALAMAT);
        uid = user.get(SessionManager.KEY_ID);
        created_at = user.get(SessionManager.KEY_TGLBUAT);

        TxtViewId = (TextView) findViewById(R.id.id_tukang);
        TxtViewNama = (TextView) findViewById(R.id.nama_tukang);
        TxtViewNope = (TextView) findViewById(R.id.nope_tukang);
        TxtViewAlamat = (TextView) findViewById(R.id.alamat_tukang);

        PilihTSBtn = (Button) findViewById(R.id.button_pilihtukang);

        //menangkap bundle yang telah di-parsed dari DaftarTukangActivity
        Bundle b = getIntent().getExtras();
        isi_id_ts = b.getString("id_tukang");
        isi_nama_ts= b.getString("nama_tukang");
        isi_nope_ts= b.getString("nope_tukang");
        isi_alamat_ts= b.getString("alamat_tukang");
        //meng-set bundle tersebut
        TxtViewId.setText(isi_id_ts);
        TxtViewNama.setText(isi_nama_ts);
        TxtViewNope.setText(isi_nope_ts);
        TxtViewAlamat.setText(isi_alamat_ts);

        PilihTSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UpdateMhsTask().execute();
            }
        });
    }


    class UpdateMhsTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;
        String errorMsg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PilihTukangActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {

            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("idtukang", isi_id_ts));
            parameter.add(new BasicNameValuePair("no_hp", nope_customer));

            try {
                String url_ = AppConfig.URL_UPDATE_TUKANG;
                JSONObject json = jParser.makeHttpRequest(url_,"POST", parameter);
                boolean error = json.getBoolean("error");

                if (!error)  {
                    return "OK";
                }
                else {
                    errorMsg = json.getString("error_msg");
                    return "FAIL";

                }

            } catch (Exception e) {
                errorMsg =e.getMessage();
                e.printStackTrace();
                return "Exception Caught";
            }

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            pDialog.dismiss();

            if(result.equalsIgnoreCase("OK"))
            {
                session.createLoginSession(name, nope_customer, alamat, isi_id_ts, uid, created_at);
                Intent i = new Intent(PilihTukangActivity.this, BelanjaActivity.class);
                startActivity(i);
                finish();
            }

            else {
                Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_LONG).show();
            }

        }
    }

}
