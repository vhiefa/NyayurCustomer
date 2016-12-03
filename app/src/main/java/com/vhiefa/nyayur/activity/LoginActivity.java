package com.vhiefa.nyayur.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vhiefa.nyayur.helper.JSONParser;
import com.vhiefa.nyayur.R;
import com.vhiefa.nyayur.app.AppConfig;
import com.vhiefa.nyayur.helper.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LoginActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputNoHp;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputNoHp = (EditText) findViewById(R.id.nohp);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            HashMap<String, String> user = session.getUserDetails();
            String tukangsayur = user.get(SessionManager.KEY_TKGSYR);
            if (tukangsayur == "null") {
                Intent intent = new Intent(LoginActivity.this,DaftarTukangActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this,BelanjaActivity.class);
                intent.putExtra("tukangsayur", tukangsayur);
                startActivity(intent);
                finish();
            }
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String nohp = inputNoHp.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!nohp.isEmpty() && !password.isEmpty()) {
                    // login user
                 //   checkLogin(nohp, password);
                    checkLogin m= (checkLogin) new checkLogin().execute(nohp, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     * */



    class checkLogin extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;
        JSONParser jParser = new JSONParser();
        String errorMsg;

        String uid, name, nohp,tukangsayur, alamat ,created_at ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            String nohp_ = sText[0];
            String password_ = sText[1];
            String returnResult = getUserAccount(nohp_, password_);
            return returnResult;

        }

        public String getUserAccount(String nohp_, String password_)
        {

            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("no_hp", nohp_));
            parameter.add(new BasicNameValuePair("password", password_));

            try {
                String url_ = AppConfig.URL_LOGIN;

                JSONObject jObj = jParser.makeHttpRequest(url_, "POST", parameter);
                boolean error = jObj.getBoolean("error");


                if (!error)  {
                  //  session.setLogin(true);

                    // Now store the user in SQLite
                    uid = jObj.getString("uid");

                    JSONObject user = jObj.getJSONObject("user");
                    name = user.getString("name");
                    nohp = user.getString("no_hp");
                    tukangsayur = user.getString("tukangsayur");
                    alamat = user.getString("alamat");
                    created_at = user.getString("created_at");

                    // Inserting row in users table
                   // db.addUser(name, nohp, alamat, tukangsayur, uid, created_at);

                    return "OK";

                }
                else {
                    //Tidak Ada Record Data (SUCCESS = 0)
                    //EMAIL ATAU PASSWORD SALAH
                    errorMsg = jObj.getString("error_msg");
                    return "error";
                }

            } catch (Exception e) {
                errorMsg = e.getMessage();
                e.printStackTrace();
                return "Exception Caught";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();


            if (result.equalsIgnoreCase("OK")) {

                // Creating user login session and putting user data in preference
                session.createLoginSession(name, nohp, alamat, tukangsayur, uid, created_at);

                if (tukangsayur == "null") {
                    Intent intent = new Intent(LoginActivity.this,
                            DaftarTukangActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(LoginActivity.this,BelanjaActivity.class);
                    intent.putExtra("tukangsayur", tukangsayur);
                    startActivity(intent);
                    finish();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_LONG).show();
            }
        }

    }

}