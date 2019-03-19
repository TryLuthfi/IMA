package indonesia.ima.com.ima;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailArtikel extends AppCompatActivity {
    private String mPostKeyIdArtikel = null;
    private String JSON_STRING;
    TextView nama, deskripsi_artikel, artikel_created_date, judul_artikel, pdf, jpg;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView iv_header;
    private String id_user, id_artikel,namaArtikel, judulArtikel,gambarArtikel, artikelDate, deskripsiArtikel;
    DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);

        mPostKeyIdArtikel = getIntent().getExtras().getString("id_artikel");

        deskripsi_artikel = findViewById(R.id.deskripsi_artikel);
        artikel_created_date = findViewById(R.id.artikel_created_date);
        iv_header = findViewById(R.id.iv_header);
        pdf = findViewById(R.id.pdf);
        jpg = findViewById(R.id.jpg);

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailArtikel.this, "Pdf Tidak Tersedia", Toast.LENGTH_SHORT).show();
            }
        });
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        getJSON();
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
                id_artikel = jo.getString(konfigurasi.id_artikel);
                judulArtikel = jo.getString(konfigurasi.judul_artikel);
                artikelDate = jo.getString(konfigurasi.artikel_created_date);
                deskripsiArtikel = jo.getString(konfigurasi.deskripsi_artikel);
                gambarArtikel = jo.getString(konfigurasi.gambar_artikel);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_artikel, id_artikel);

                list.add(data);

                if (mPostKeyIdArtikel.equals(jo.getString("id_artikel"))) {
                    deskripsi_artikel.setText(deskripsiArtikel);
                    artikel_created_date.setText(artikelDate);
                    collapsingToolbar.setTitle(judulArtikel);
                    Glide.with(DetailArtikel.this).load("http://imaindonesia.000webhostapp.com/acara/" + gambarArtikel).into(iv_header);
                    gambarPreview(gambarArtikel);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void gambarPreview(final String gambarArtikel) {
        iv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(DetailArtikel.this);
                dialog.setCancelable(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.custom_dialog);
                ImageView gambar = dialog.findViewById(R.id.gambar);
                Glide.with((DetailArtikel.this)).load("http://imaindonesia.000webhostapp.com/acara/" + gambarArtikel).into(gambar);
                PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(gambar);
                photoViewAttacher.update();
                dialog.show();
            }
        });

        jpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://imaindonesia.000webhostapp.com/acara/" + gambarArtikel);
                //https://2.bp.blogspot.com/-WXUWh3AXAoY/WEpjoZLy_QI/AAAAAAAACno/p_lSB5RJNdY91bZP3mjNPQHd4hVpVINZACLcB/s640/Cetak%2BKumpulan%2BArtikel%2BKoran.png
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);
            }
        });
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
}
