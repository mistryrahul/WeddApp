package com.example.rahul.weddapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 07-01-2016.
 */
public class multiple_img_upload_frgmnt extends StatedFragment {

    Button opngal;
    EditText galname;
    private ArrayList<String> imagesPathList;
    private final int PICK_IMAGE_MULTIPLE =1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.multiple_img_upload_frgmnt, container, false);
        return vw;
    }

    @Override
    public void onResume() {
        super.onResume();


        View parentview = getView();
        opngal = (Button) parentview.findViewById(R.id.opengal);
        galname = (EditText) parentview.findViewById(R.id.galnm);


        opngal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CustomPhotoGalleryActivity.class);
                getActivity().startActivityForResult(intent, PICK_IMAGE_MULTIPLE);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == PICK_IMAGE_MULTIPLE){
                //imagesPathList = new ArrayList<Integer>();

                imagesPathList = data.getStringArrayListExtra("data");


                //String[] imagesPath = data.getStringExtra("data").split("\\|");
//                try{
//                    lnrImages.removeAllViews();
//                }catch (Throwable e){
//                    e.printStackTrace();
//                }

                Toast.makeText(getActivity().getApplicationContext(),"no of pic selected-->"+imagesPathList.size(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity().getApplicationContext(),"path-->"+imagesPathList.get(0),Toast.LENGTH_SHORT).show();


//                for (int i=0;i<imagesPath.length;i++){
////                    imagesPathList.add(imagesPath[i]);
////                    yourbitmap = BitmapFactory.decodeFile(imagesPath[i]);
////                    ImageView imageView = new ImageView(this);
////                    imageView.setImageBitmap(yourbitmap);
////                    imageView.setAdjustViewBounds(true);
////                    lnrImages.addView(imageView);
//                }
            }
        }

    }
}
