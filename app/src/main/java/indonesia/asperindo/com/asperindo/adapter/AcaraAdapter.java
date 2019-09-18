package indonesia.asperindo.com.asperindo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

import indonesia.asperindo.com.asperindo.json.Acara;
import indonesia.asperindo.com.asperindo.activity.DetailEvent;
import indonesia.asperindo.com.asperindo.R;

public class AcaraAdapter extends RecyclerView.Adapter<AcaraAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Acara> productList;

    public AcaraAdapter(Context mCtx, List<Acara> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.acara_preview, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Acara product = productList.get(position);
        Glide.with(Objects.requireNonNull(mCtx)).load("http://imaindonesia.000webhostapp.com/acara/" + product.getGambar_acara()).into(holder.gambar);
        holder.judul_acara.setText(product.getJudul_acara());
        holder.jam_acara.setText(product.getJam_acara());
        holder.tanggal_acara.setText(product.getTanggal_acara());
        holder.alamat_acara.setText(product.getAlamat_acara());
        holder.alamat_lengkap_acara.setText(product.getAlamat_lengkap_acara());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, DetailEvent.class);
                intent.putExtra("id_acara", product.getId_acara());
                mCtx.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView judul_acara, jam_acara, tanggal_acara, alamat_acara, alamat_lengkap_acara;
        LinearLayout user;
        CardView da;
        View view;
        ImageView gambar;


        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            judul_acara = view.findViewById(R.id.judul_acara);
            jam_acara = view.findViewById(R.id.jam_acara);
            tanggal_acara = view.findViewById(R.id.tanggal_acara);
            alamat_acara = view.findViewById(R.id.alamat_acara);
            alamat_lengkap_acara = view.findViewById(R.id.alamat_lengkap_acara);
            gambar = view.findViewById(R.id.gambar);
        }
    }
}