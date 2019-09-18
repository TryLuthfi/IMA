package indonesia.asperindo.com.asperindo.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import indonesia.asperindo.com.asperindo.R;
import indonesia.asperindo.com.asperindo.konfigurasi.konfigurasi;
import indonesia.asperindo.com.asperindo.request.RequestHandler;

public class TentangActivity extends AppCompatActivity {
    private String JSON_STRING;
    private String id_tentang;
    private String logo;
    private String nama;
    private String nama_lengkap;
    private ImageView iv_header;
    private String deskripsi;
    private CollapsingToolbarLayout collapsingToolbar;
    public String mPostKeyIdArtikel = "1";
    private TextView nama_lengkapT, deskripsiT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        deskripsiT = findViewById(R.id.deskripsi);
        nama_lengkapT = findViewById(R.id.nama_lengkap);
        iv_header = findViewById(R.id.iv_header);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);



        getJSON();
    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY4);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                id_tentang = jo.getString(konfigurasi.id_tentang);
                logo = jo.getString(konfigurasi.logo);
                nama = jo.getString(konfigurasi.nama_tentang);
                nama_lengkap = jo.getString(konfigurasi.nama_lengkap);
                deskripsi = jo.getString(konfigurasi.deskripsi);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_tentang, id_tentang);

                list.add(data);

                if (mPostKeyIdArtikel.equals(jo.getString("id_tentang"))) {
                    collapsingToolbar.setTitle(nama);
                    deskripsiT.setText(deskripsi);
                    nama_lengkapT.setText(nama_lengkap);
                    Glide.with(getApplicationContext()).load("http://imaindonesia.000webhostapp.com/tentang/" + logo).into(iv_header);
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_DATAIMA);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
