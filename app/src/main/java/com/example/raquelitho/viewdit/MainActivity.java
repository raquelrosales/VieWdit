package com.example.raquelitho.viewdit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView mSetImage;
    private Button mOptionButton;
    private RelativeLayout mRlView;

    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSetImage = (ImageView) findViewById(R.id.set_picture);
        mOptionButton = (Button) findViewById(R.id.show_options_button);
        mRlView = (RelativeLayout) findViewById(R.id.rl_view);

        if (mayRequestStoragePermission())
            mOptionButton.setEnabled(true);
        else
            mOptionButton.setEnabled(false);


        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

    }
    private boolean mayRequestStoragePermission(){

        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.M){
            return true;
        }
        return true;
    }

    private void showOptions(){
        final CharSequence[] option ={"Elegir de galeria","Cancela"};
        final AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Elige una opcion");
        builder.setItems(option,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                if(option[which]=="Elegir de galeria"){
                    Intent intent= new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Selecciona la imagen"),SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode== RESULT_OK){
            switch(requestCode){
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    mSetImage.setImageURI(path);
            }
        }
    }
}
