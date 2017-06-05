package com.example.tomcat.photoalbumcamera;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = MainActivity.class.getSimpleName();
    private final int REQUEST_CAMERA = 1;
    private final int CAMERA = 10;
    private final int PHOTO = 20;

    ImageView   imgView;
    TextView    txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate()...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initControl();
    }

    @Override
    protected void onResume()
    {
        Log.d(TAG, "onResume()...");
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d(TAG, "onActivityResult()...");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        Log.d(TAG, "onRequestPermissionsResult()...");

        switch (requestCode)
        {
            case REQUEST_CAMERA:
            {
                if ((grantResults.length > 0) &&
                        (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {
                    Toast.makeText(getApplicationContext(), "Permission granted",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Permission denied",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void imgOnClick(View view)
    {
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
        adBuilder.setMessage("照片選取")
          .setPositiveButton("相 機", new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                ContentValues   values = new ContentValues();
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Environment.getDataDirectory().getAbsolutePath());
                startActivityForResult(intent, CAMERA);
            }
        }).setNegativeButton("相 簿", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PHOTO);

            }
        });
        AlertDialog logout_dialog = adBuilder.create();
        logout_dialog.show();
    }


    private void initView()
    {
        Log.d(TAG, "initView()...");

        imgView = (ImageView) findViewById(R.id.imageView);
        txtView = (TextView) findViewById(R.id.textView);
    }

    private void initControl()
    {
        Log.d(TAG, "initControl()...");

        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                this.REQUEST_CAMERA);
    }

}
