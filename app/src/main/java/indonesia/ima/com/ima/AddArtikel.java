package indonesia.ima.com.ima;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddArtikel extends AppCompatActivity {
    private TextView nama;
    private EditText judul_artikel, deskripsi_artikel;
    private String JSON_STRING, id_user, namaa, id_artikel, judul_artikell, deskripsi_artikell;
    private ImageView foto_profile;
    private Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artikel);

        nama = findViewById(R.id.nama);
        foto_profile = findViewById(R.id.foto_profile);
        judul_artikel = findViewById(R.id.judul_artikel);
        deskripsi_artikel = findViewById(R.id.deskripsi_artikel);
        upload = findViewById(R.id.upload);

        getJSON();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uppload();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY2);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                id_user = jo.getString(konfigurasi.id_user);
                namaa = jo.getString(konfigurasi.nama);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_artikel, id_artikel);

                list.add(data);

                String id_user = getIdUser();
                if (id_user.equals(jo.getString("id_user"))) {
                    nama.setText(""+namaa);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showData();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_DATAARTIKEL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void uppload() {
        judul_artikell = judul_artikel.getText().toString().trim();
        deskripsi_artikell = deskripsi_artikel.getText().toString().trim();
        class Upload extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddArtikel.this, "Sedang Diproses...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AddArtikel.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();

                String id_user = getIdUser();

                params.put(konfigurasi.id_user, id_user);
                params.put(konfigurasi.judul_artikel, judul_artikell);
                params.put(konfigurasi.deskripsi_artikel, deskripsi_artikell);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_GET_UPLOAD_ARTIKEL, params);
                return res;
            }
        }

        Upload ae = new Upload();
        ae.execute();
    }

    private String getIdUser(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "null");
        return id_user;
    }
}
