package indonesia.asperindo.com.asperindo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import indonesia.asperindo.com.asperindo.activity.ProfileActivity;
import indonesia.asperindo.com.asperindo.R;
import indonesia.asperindo.com.asperindo.json.User;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private static final String TAG = "ProductAdapter";

    private Activity mCtx;
    private Context context;
    private List<User> contactList;



    public ProductAdapter(Activity mCtx, List<User> contactList) {
        this.mCtx = mCtx;
        this.contactList = contactList;

    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.user_row_item, parent, false);
        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final User contact = contactList.get(position);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        holder.name.setText(contact.getNama());
        holder.phone.setText(contact.getNo_telp());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, ProfileActivity.class);
                intent.putExtra("id_user", contact.getId_user());
                mCtx.startActivity(intent);
            }
        });

        Glide.with(mCtx)
                .load("http://imaindonesia.000webhostapp.com/userimage/" + contact.getFoto_profile())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        View view;
        public TextView name, phone;
        public ImageView thumbnail;

        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }


}
