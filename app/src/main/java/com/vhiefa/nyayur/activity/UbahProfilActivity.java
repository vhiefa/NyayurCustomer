package com.vhiefa.nyayur.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UbahProfilActivity extends ActionBarActivity {

    String uid, nope, name, alamat;
    String sId,  sNama, sNope, sAlamat, sCreateAt, sTukangSayur;
    SessionManager session;
    EditText EdtTxtnama;
    EditText EdtTxtnope;
    EditText EdtTxtalamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        uid = user.get(SessionManager.KEY_ID);
        nope = user.get(SessionManager.KEY_NOPE);
        name = user.get(SessionManager.KEY_NAME) ;
        alamat = user.get(SessionManager.KEY_ALAMAT);
        sCreateAt = user.get(SessionManager.KEY_TGLBUAT);
        sTukangSayur = user.get(SessionManager.KEY_TKGSYR);


        EdtTxtnama = (EditText) findViewById(R.id.EdtTxtNama);
        EdtTxtnope = (EditText) findViewById(R.id.EdtTxtNope);
        EdtTxtalamat=(EditText) findViewById(R.id.EdtTxtAlamat);

        EdtTxtnama.setText(name);
        EdtTxtnope.setText(nope);
        EdtTxtalamat.setText(alamat);

        Button btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sId = uid;
                sNama = EdtTxtnama.getText().toString();
                sNope = EdtTxtnope.getText().toString();
                sAlamat = EdtTxtalamat.getText().toString();
                if ( sNama.isEmpty() || sAlamat.isEmpty() || sNope.isEmpty())
                    Toast.makeText(getApplicationContext(),
                            "Harap lengkapi semua kolom", Toast.LENGTH_LONG).show();
                else {

                    UpdateProfil m= (UpdateProfil) new UpdateProfil().execute();
                }

            }
        });
    }

    class UpdateProfil extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;
        JSONParser jParser = new JSONParser();
        String errorMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UbahProfilActivity.this);
            pDialog.setMessage("Loading..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            String returnResult = UpdateProfil();
            return returnResult;

        }

        public String UpdateProfil(){

            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("id_user", sId));
            parameter.add(new BasicNameValuePair("nama", sNama));
            parameter.add(new BasicNameValuePair("nope", sNope));
            parameter.add(new BasicNameValuePair("alamat", sAlamat));

            try {
                String url_ = AppConfig.URL_UPDATE_PROFIL;
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
                session.createLoginSession(sNama, sNope, sAlamat, sTukangSayur, sId, sCreateAt);
                Intent i = new Intent(UbahProfilActivity.this, ProfilActivity.class);
                startActivity(i);
                finish();
            }

            else {
                Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
