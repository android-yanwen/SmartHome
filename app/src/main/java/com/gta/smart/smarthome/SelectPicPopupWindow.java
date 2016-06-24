package com.gta.smart.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/6/20.
 */
public class SelectPicPopupWindow extends Activity implements View.OnClickListener{
    private Button take_photo_btn;
    private static final int GET_USER_HEAD_PORTRAIT_CODE = 1;
    private Button select_photo_album_btn;
    private Button cancel_btn;
    private static String path = "/sdcard/myHead/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_window_layout);
        take_photo_btn = (Button) findViewById(R.id.take_photo_btn);
        take_photo_btn.setOnClickListener(this);
        select_photo_album_btn = (Button) findViewById(R.id.select_photo_album_btn);
        select_photo_album_btn.setOnClickListener(this);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return /*super.onTouchEvent(event)*/true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_photo_btn:
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.png")));
                startActivityForResult(intent1, 2);
                break;
            case R.id.select_photo_album_btn:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);
                break;
            case R.id.cancel_btn:
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File file = new File(Environment.getExternalStorageDirectory(), "head.png");
                    cropPhoto(Uri.fromFile(file));
                }
                break;
            case 3:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    Bitmap head = bundle.getParcelable("data");
                    if (head != null) {
                        setPictureToView(head);
                        Intent intent = new Intent();
                        UserHeadPortait userHeadPortait = new UserHeadPortait();
                        userHeadPortait.setBitmap(head);
                        Bundle bundle1 = new Bundle();
                        bundle1.putParcelable("UserHeadPortrait", userHeadPortait);
                        intent.putExtras(bundle1);
                        this.setResult(RESULT_OK, intent);
                        this.finish();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPictureToView(Bitmap bitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdir();
        String fileName = path + "head.png";
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Drawable getUserHeadPortrait() {
        Drawable drawable = null;
        Bitmap bt = BitmapFactory.decodeFile(path + "head.png");
        if (bt != null) {
            drawable = new BitmapDrawable(bt);
        }
        return drawable;
    }
}
