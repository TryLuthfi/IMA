package indonesia.ima.com.ima;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DetailEvent extends AppCompatActivity {
    private TextView info, deskripsi, deskripsi_acara, galeri, jam_acara, tanggal_acara, alamat_acara
            , alamat_lengkap_acara, judul_acara;
    private String mPostKeyIDACARA = null;
    private static final String URL_PRODUCTS = "http://imaindonesia.000webhostapp.com/galerypreview.php";
    private String JSON_STRING;
    private String id_acara, jamAcara, tanggalAcara, alamatAcara, Alamatlengkap_acara, deskripsiAcara
            , judulAcara, gambarAcara;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView iv_header, btnGalery, btnUpload, gambar;
    List<Galery> productList;
    RecyclerView recyclerView;
    private static final int NUM_COLUMNS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        info = (TextView) findViewById(R.id.info);
        deskripsi = (TextView) findViewById(R.id.deskripsi);
        galeri = (TextView) findViewById(R.id.galeri);
        jam_acara = (TextView) findViewById(R.id.jam_acara);
        tanggal_acara = (TextView) findViewById(R.id.tanggal_acara);
        deskripsi_acara = (TextView) findViewById(R.id.deskripsi_acara);
        alamat_acara = (TextView) findViewById(R.id.alamat_acara);
        alamat_lengkap_acara = (TextView) findViewById(R.id.alamat_lengkap_acara);
        mPostKeyIDACARA = getIntent().getExtras().getString("id_acara");
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        iv_header = findViewById(R.id.iv_header);


        Typeface customfont = Typeface.createFromAsset(getAssets(),"font/Product Sans Regular.ttf");
        info.setTypeface(customfont);
        deskripsi.setTypeface(customfont);
        galeri.setTypeface(customfont);

        productList = new ArrayList<>();


        recyclerView = findViewById(R.id.recycler_view);
        GaleryAdapter staggeredRecyclerViewAdapter =
                new GaleryAdapter(DetailEvent.this, productList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);

        loadProducts();

        getJSON();
    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                id_acara = jo.getString(konfigurasi.id_acara);
                jamAcara = jo.getString(konfigurasi.jamAcara);
                tanggalAcara = jo.getString(konfigurasi.tanggalAcara);
                alamatAcara = jo.getString(konfigurasi.alamatAcara);
                judulAcara = jo.getString(konfigurasi.judulAcara);
                gambarAcara = jo.getString(konfigurasi.gambarAcara);
                Alamatlengkap_acara = jo.getString(konfigurasi.Alamatlengkap_acara);
                deskripsiAcara = jo.getString(konfigurasi.deskripsiAcara);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_acara, id_acara);

                list.add(data);

                if (mPostKeyIDACARA.equals(jo.getString("id_acara"))) {
                    jam_acara.setText("" + jamAcara);
                    tanggal_acara.setText("" + tanggalAcara);
                    deskripsi_acara.setText("" + deskripsiAcara);
                    alamat_acara.setText(""+alamatAcara);
                    alamat_lengkap_acara.setText(""+Alamatlengkap_acara);
                    collapsingToolbar.setTitle(judulAcara);
                    Glide.with(DetailEvent.this).load("http://imaindonesia.000webhostapp.com/acara/" + gambarAcara).into(iv_header);
                    iv_header.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(DetailEvent.this, "clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_DATAACARA);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                if (mPostKeyIDACARA.equals(product.getString("id_acara"))) {
                                    productList.add(new Galery(
                                            product.getString("id_galery"),
                                            product.getString("id_acara"),
                                            product.getString("id_user"),
                                            product.getString("gambar_galery"),
                                            product.getString("judul_acara")
                                    ));
                                }
                            }

                            GaleryAdapter adapter = new GaleryAdapter(DetailEvent.this, productList);

                            if (adapter != null){
                                recyclerView.setAdapter(adapter);

                            }else {
                                Toast.makeText(DetailEvent.this, "null", Toast.LENGTH_SHORT).show();
                            }

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
        Volley.newRequestQueue(DetailEvent.this).add(stringRequest);
    }
}
