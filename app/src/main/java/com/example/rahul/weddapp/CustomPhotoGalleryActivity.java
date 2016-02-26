package com.example.rahul.weddapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class CustomPhotoGalleryActivity extends AppCompatActivity {

    // private GridView grdImages;
    private RecyclerView recvu;
    private ImageAdapter imgadtpr;
    private Button btnSelect;
    private ArrayList<Integer> selectedImg = new ArrayList<Integer>();
    // private ImageAdapter imageAdapter;
    private String[] arrPath;
    private boolean[] thumbnailsselection;
    private int ids[];
    private int count;


    /**
     * Overrides methods
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_gallery);
        // grdImages= (GridView) findViewById(R.id.grdImages);

        recvu = (RecyclerView)findViewById(R.id.my_recycler_view);

        btnSelect= (Button) findViewById(R.id.btnSelect);

        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media._ID;
        @SuppressWarnings("deprecation")
        Cursor imagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
        this.count = imagecursor.getCount();
        this.arrPath = new String[this.count];
        ids = new int[count];
        this.thumbnailsselection = new boolean[this.count];
        for (int i = 0; i < this.count; i++) {
            imagecursor.moveToPosition(i);
            ids[i] = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            arrPath[i] = imagecursor.getString(dataColumnIndex);

            //  Log.d("TST","PATH-->"+imagecursor.getString(dataColumnIndex));
        }

        imgadtpr = new ImageAdapter(getApplicationContext());
        recvu.setAdapter(imgadtpr);
        recvu.setLayoutManager(new GridLayoutManager(CustomPhotoGalleryActivity.this, 3));
        // imageAdapter = new ImageAdapter();
        // grdImages.setAdapter((ListAdapter) imageAdapter);
        imagecursor.close();


        btnSelect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if(selectedImg.size()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please selct Atleast One Image",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ArrayList<String> img_path = new ArrayList<String>();

                    for(int x : selectedImg)
                    {
                        img_path.add(arrPath[x]);
                    }

                    Intent i = new Intent();
                    i.putExtra("data", img_path);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();

    }


    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.VwHlder>
    {
        private LayoutInflater inflater;
        //        private String[] data;
        private Context ctx;

        public ImageAdapter(Context context)
        {
            ctx =context;
            inflater = LayoutInflater.from(context);
            //Log.d("TST","In Constructure....");
        }



        @Override
        public VwHlder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.custom_gallery_item,parent,false);
            VwHlder holder = new VwHlder(view);

            return holder;

        }

        @Override
        public void onBindViewHolder(VwHlder holder, int position) {

            holder.imgThumb.setBackground(null);
            holder.flg=0;

            if(selectedImg!=null)
            {
                int indx=0;
                for(int x : selectedImg)
                {
                    if(x==position)
                    {
                        holder.imgThumb.setBackgroundResource(R.drawable.border);
                        holder.flg=1;
                        break;
                    }
                    indx++;
                }

            }



            File img = new File(arrPath[position]);
            if(img != null)
            {

                Picasso.with(ctx)
                        .load(img)
                        .resize(200,150)
                        .centerCrop()
                        .into(holder.imgThumb);
            }
            //String x = arrPath[position];
            //  ImageLoader.getInstance().displayImage(Uri.parse("file://"+img.toString()).toString(),holder.imgThumb);

        }

        @Override
        public int getItemCount() {
            return arrPath.length;
        }

        class VwHlder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            ImageView imgThumb;
            CheckBox chkImage;
            int flg=0;

            public VwHlder(View itemView)
            {
                super(itemView);
                itemView.setOnClickListener(this);
                imgThumb = (ImageView) itemView.findViewById(R.id.imgThumb);

                //Log.d("TST","IN View Holder Set View");
            }

            @Override
            public void onClick(View v)
            {

                //Toast.makeText(getApplicationContext(),"On CLick Working",Toast.LENGTH_SHORT).show();
                View parent = v.getRootView();
                ImageView imv = (ImageView) v.findViewById(R.id.imgThumb);

                if(this.flg==0)
                {

                    imv.setBackgroundResource(R.drawable.border);
                    this.flg=1;
                    // Log.d("TST", "Position-->" + getAdapterPosition() + "Selected....");
                    selectedImg.add(getAdapterPosition());
                }
                else
                {
                    imv.setBackground(null);
                    this.flg=0;
                    // Log.d("TST","Position-->"+getAdapterPosition()+"Unselected......");

                    int indx=0;
                    for(int x : selectedImg)
                    {
                        if(x==getAdapterPosition())
                        {
                            selectedImg.remove(indx);
                            break;
                        }
                        indx++;
                    }
                }




            }
        }
    }





}