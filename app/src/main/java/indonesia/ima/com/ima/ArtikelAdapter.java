package indonesia.ima.com.ima;

import android.annotation.SuppressLint;
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

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Artikel> productList;

    public ArtikelAdapter(Context mCtx, List<Artikel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artikel_preview, parent, false);

        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Artikel product = productList.get(position);
        holder.nama.setText(product.getNama());
        holder.deskripsi_artikel.setText(""+product.getDeskripsi_artikel());
        holder.artikel_created_date.setText(product.getArtikel_created_date());
        holder.judul_artikel.setText(product.getJudul_artikel());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (mCtx, DetailArtikel.class);
                intent.putExtra("id_artikel", product.getId_artikel());
                mCtx.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {


        LinearLayout user;
        TextView nama, deskripsi_artikel, artikel_created_date, judul_artikel;
        CardView da;
        View view;


        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nama = view.findViewById(R.id.nama);
            deskripsi_artikel = view.findViewById(R.id.deskripsi_artikel);
            artikel_created_date = view.findViewById(R.id.artikel_created_date);
            judul_artikel = view.findViewById(R.id.judul_artikel);

        }
    }
}
