package indonesia.asperindo.com.asperindo.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import indonesia.asperindo.com.asperindo.R;
import indonesia.asperindo.com.asperindo.adapter.ProductAdapter;
import indonesia.asperindo.com.asperindo.json.User;
import indonesia.asperindo.com.asperindo.konfigurasi.konfigurasi;
import indonesia.asperindo.com.asperindo.request.RequestHandler;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView gambar;
    private String JSON_STRING;
    private String id_tentang;
    private String logo;
    private String nama;
    private String nama_lengkap;
    private String deskripsi;
    public String mPostKeyIdArtikel = "1";
    private static final String URL = "http://imaindonesia.000webhostapp.com/userpreview2.php";
    Spinner spinner;
    String ChapterPelanggan;
    String EmailPelanggan;
    String NamaPelanggan;
    String IdPelanggan;

    List<User> contactList;
    RecyclerView recyclerView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        gambar = findViewById(R.id.gambar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User");

        spinner = (Spinner) findViewById(R.id.spiner);

        getKota();

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        contactList = new ArrayList<>();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.StringWithTag pelanggan = (MainActivity.StringWithTag) parent.getItemAtPosition(position);
                IdPelanggan = (String) pelanggan.id;
                NamaPelanggan = (String) pelanggan.Nama;
                EmailPelanggan = (String) pelanggan.Email;
                ChapterPelanggan = (String) pelanggan.Chapter;

                Toast.makeText(MainActivity.this, ChapterPelanggan, Toast.LENGTH_SHORT).show();

                loadProduct();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getJSON();

    }

    List<MainActivity.StringWithTag> pelangganName = new ArrayList<MainActivity.StringWithTag>();

    private void getKota() {
        pelangganName.add(new StringWithTag(null, null, null, "-- Umum --"));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_KONTAK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray arrSupplier = obj.getJSONArray("datakota");
                            for (int i = 0; i < arrSupplier.length(); i++) {
                                JSONObject supplierJson = arrSupplier.getJSONObject(i);
                                String id_user = supplierJson.getString("id_user");
                                String nama = supplierJson.getString("nama");
                                String email_pribadi = supplierJson.getString("email_pribadi");
                                String chapter = supplierJson.getString("chapter");
                                pelangganName.add(new StringWithTag(id_user, nama, email_pribadi, chapter));
                            }

                            ArrayAdapter<MainActivity.StringWithTag> adapterSpinnerPelanggan = new ArrayAdapter<MainActivity.StringWithTag>(
                                    MainActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, pelangganName);
                            spinner.setAdapter(adapterSpinnerPelanggan);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: " + e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadProduct(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            contactList.clear();
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {


                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                if (ChapterPelanggan.equals("-- Umum --")) {
                                    Toast.makeText(MainActivity.this, "Pilih Chapter", Toast.LENGTH_SHORT).show();
                                } else if (ChapterPelanggan.equals(product.getString("chapter"))){
                                    contactList.add(new User(
                                            product.getString("id_user"),
                                            product.getString("nama"),
                                            product.getString("username"),
                                            product.getString("password"),
                                            product.getString("no_telp"),
                                            product.getString("email_pribadi"),
                                            product.getString("foto_profile"),
                                            product.getString("bidang_usaha"),
                                            product.getString("nama_usaha"),
                                            product.getString("chapter")
                                    ));

                                }
                            }

                            ProductAdapter adapter = new ProductAdapter(MainActivity.this, contactList);

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
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
                    Glide.with(getApplicationContext()).load("http://imaindonesia.000webhostapp.com/tentang/" + logo).into(gambar);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void whiteNotificationBar(View view) {
    }

    private static class StringWithTag {
        public Object id;
        public String Nama;
        public String Email;
        public String Chapter;

        public StringWithTag(Object id, String Nama, String Email, String Chapter) {
            this.id = id;
            this.Nama = Nama;
            this.Email = Email;
            this.Chapter = Chapter;
        }

        @Override
        public String toString() {
            return Chapter;
        }
    }

    public void back(View v) {
        finish();
    }
}
