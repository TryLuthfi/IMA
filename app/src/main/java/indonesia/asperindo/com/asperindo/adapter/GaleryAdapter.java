package indonesia.asperindo.com.asperindo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Objects;

import indonesia.asperindo.com.asperindo.json.Galery;
import indonesia.asperindo.com.asperindo.R;

public class GaleryAdapter extends RecyclerView.Adapter<GaleryAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Galery> productList;

    public GaleryAdapter(Context mCtx, List<Galery> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_galery, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Galery product = productList.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(Objects.requireNonNull(mCtx)).load("https://imaindonesia.000webhostapp.com/galery/" + product.getGambar_galery()).apply(requestOptions).into(holder.gambar_galery);


    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView gambar_galery;


        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            gambar_galery = view.findViewById(R.id.gambar_galery);

        }
    }
}