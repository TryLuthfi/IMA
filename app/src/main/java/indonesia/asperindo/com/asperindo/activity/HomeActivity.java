package indonesia.asperindo.com.asperindo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

//import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import indonesia.asperindo.com.asperindo.R;
import indonesia.asperindo.com.asperindo.adapter.SlidingImageAdapter;
import indonesia.asperindo.com.asperindo.json.Artikel;
import indonesia.asperindo.com.asperindo.request.ImageModel;

public class HomeActivity extends AppCompatActivity {
    Context context;
    private static final String URL_PRODUCTS = "http://imaindonesia.000webhostapp.com/artikelpreview.php";
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final int NUM_COLUMNS = 1;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    List<Artikel> productList;
    RecyclerView recyclerView;
    private ProgressBar loading;

    private int[] myImageList = new int[]{R.drawable.anu1, R.drawable.anu2,
            R.drawable.anu3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();

        init();
    }

    private ArrayList<ImageModel> populateList(){

        ArrayList<ImageModel> list = new ArrayList<>();

        for(int i = 2; i < myImageList.length; i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }

    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImageAdapter(HomeActivity.this,imageModelArrayList));

//        CirclePageIndicator indicator = (CirclePageIndicator)
//                findViewById(R.id.indicator);

//        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
//        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                currentPage = position;
//
//            }
//
//            @Override
//            public void onPageScrolled(int pos, float arg1, int arg2) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int pos) {
//
//            }
//        });

    }

    public void acara(View view) {
        Intent intent = new Intent(HomeActivity.this, EventActivity.class);
        startActivity(intent);
    }

    public void artikel(View view) {
        Intent intent = new Intent(HomeActivity.this, ArtikelActivity.class);
        startActivity(intent);
    }

    public void search(View view){
        Intent intent = new Intent (HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void chat(View view){
        Intent intent = new Intent (HomeActivity.this, ChatActivity.class);
        startActivity(intent);
    }

    public void profilku(View view) {
        Intent intent = new Intent (HomeActivity.this, ProfileUser.class);
        startActivity(intent);
    }


    public void tentang(View view) {
        Intent intent = new Intent(HomeActivity.this, TentangActivity.class);
        startActivity(intent);
    }
}
