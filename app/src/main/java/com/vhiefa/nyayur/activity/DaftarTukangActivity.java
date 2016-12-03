package com.vhiefa.nyayur.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vhiefa.nyayur.R;
import com.vhiefa.nyayur.app.AppConfig;
import com.vhiefa.nyayur.app.TukangSayur;
import com.vhiefa.nyayur.helper.JSONParser;
import com.vhiefa.nyayur.helper.TukangSayurAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afifatul on 2016-11-30.
 */

public class DaftarTukangActivity extends ActionBarActivity {

    ListView list;

    JSONParser jParser = new JSONParser();
    ArrayList<TukangSayur> daftar_tukangsayur = new ArrayList<TukangSayur>();
    JSONArray daftarTukangSayur = null;
    // JSON Node names, ini harus sesuai yang di API
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_TS = "tukangsayur";
    public static final String TAG_ID_TS = "unique_id";
    public static final String TAG_NAMA_TS = "name";
    public static final String TAG_NOPE_TS = "no_hp";
    public static final String TAG_ALAMAT_TS = "alamat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftartukang);

        list = (ListView) findViewById(R.id.list_tukangsayur);

        //jalankan ReadTukangSayurTask
        ReadTukangSayurTask m= (ReadTukangSayurTask) new ReadTukangSayurTask().execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int urutan, long id) {

                String idTs = ((TextView) view.findViewById(R.id.id_tukang)).getText().toString();
                String namaTs = ((TextView) view.findViewById(R.id.nama_tukang)).getText().toString();
                String nopeTs = ((TextView) view.findViewById(R.id.nope_tukang)).getText().toString();
                String alamatTs = ((TextView) view.findViewById(R.id.alamat_tukang)).getText().toString();

                //varible string tadi kita simpan dalam suatu Bundle, dan kita parse bundle tersebut bersama intent menuju kelas UpdateDeleteActivity
                Intent i = new Intent(DaftarTukangActivity.this, PilihTukangActivity.class);
                Bundle b = new Bundle();
                b.putString("id_tukang", idTs);
                b.putString("nama_tukang", namaTs);
                b.putString("nope_tukang", nopeTs);
                b.putString("alamat_tukang", alamatTs);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    class ReadTukangSayurTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;
        String errorMsg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DaftarTukangActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            String returnResult = getTukangSayurList(); //memanggil method getTukangSayurList()
            return returnResult;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();

            if (result.equalsIgnoreCase("OK")) {

                list.setAdapter(new TukangSayurAdapter(DaftarTukangActivity.this,daftar_tukangsayur)); //Adapter menampilkan data mahasiswa ke dalam listView
            }
            else{
                Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_LONG).show();
            }

        }


        //method untuk memperoleh daftar mahasiswa dari JSON
        public String getTukangSayurList()
        {
            TukangSayur tempTukang = new TukangSayur();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            try {

                String url_ = AppConfig.URL_LIHAT_TUKANG;

                JSONObject json = jParser.makeHttpRequest(url_,"POST", parameter);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) { //Ada record Data (SUCCESS = 1)
                    //Getting Array of daftar_tukangsayur
                    daftarTukangSayur = json.getJSONArray(TAG_TS);
                    // looping through All daftar_tukangsayur
                    for (int i = 0; i < daftarTukangSayur.length() ; i++){
                        JSONObject c = daftarTukangSayur.getJSONObject(i);
                        tempTukang = new TukangSayur();
                        tempTukang.setTSId(c.getString(TAG_ID_TS));
                        tempTukang.setTSName(c.getString(TAG_NAMA_TS));
                        tempTukang.setTSNope(c.getString(TAG_NOPE_TS));
                        tempTukang.setTSAddress(c.getString(TAG_ALAMAT_TS));
                        daftar_tukangsayur.add(tempTukang);
                    }
                    return "OK";
                }
                else {
                    errorMsg = json.getString("message");
                    return "no results";
                }

            } catch (Exception e) {
                e.printStackTrace();
                errorMsg = e.getMessage();
                return "Exception Caught";
            }
        }

    }
}