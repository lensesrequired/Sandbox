package edu.coe.asmarek.sandbox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class GetPhoto extends AppCompatActivity implements View.OnClickListener {
    Button gallery;
    Button camera;
    ImageView photo;
    LinearLayout photoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i;

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_theme):
                i = new Intent("edu.coe.asmarek.sandbox.MainActivity");
                startActivity(i);
                break;
            case (R.id.action_customBackground):
                i = new Intent("edu.coe.asmarek.sandbox.GetPhoto");
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        gallery = (Button) findViewById(R.id.btnGallery);
        camera = (Button) findViewById(R.id.btnCamera);
        photo = (ImageView) findViewById(R.id.imgPhoto);
        photoLayout = (LinearLayout) findViewById(R.id.llPhotos);

        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnGallery:
                i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                startActivityForResult(pickIntent, 0);
                break;
            case R.id.btnCamera:
                i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photoBitmap;
        try {
            if (requestCode == 0) {
                if (gallery.getText().toString().equals("Another! (Gallery)")) {
                    ImageView imageView = new ImageView(this);
                    try {
                        InputStream is = getContentResolver().openInputStream(data.getData());
                        photoBitmap = BitmapFactory.decodeStream(is);
                        imageView.setImageBitmap(photoBitmap);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    photoLayout.addView(imageView);

                } else {
                    try {
                        InputStream is = getContentResolver().openInputStream(data.getData());
                        photoBitmap = BitmapFactory.decodeStream(is);
                        photo.setImageBitmap(photoBitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                photoBitmap = (Bitmap) data.getExtras().get("data");
                if (camera.getText().toString().equals("Another! (Camera)")) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageBitmap(photoBitmap);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(
                            0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                    photoLayout.addView(imageView);
                } else {
                    photo.setImageBitmap(photoBitmap);
                }
            }

            gallery.setText("Another! (Gallery)");
            camera.setText("Another! (Camera)");
        } catch (Exception e) {

        }
    }
}
