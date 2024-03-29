package cohl.photodemo;

import java.io.ByteArrayOutputStream;  
import java.io.File;  
import android.app.Activity;  
import android.content.Intent;  
import android.graphics.Bitmap;  
import android.net.Uri;  
import android.os.Bundle;  
import android.os.Environment;  
import android.provider.MediaStore;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.ImageView;  
  
public class testActivity extends Activity {  
  
    public static final int NONE = 0;  
    public static final int PHOTOHRAPH = 1;// 拍照  
    public static final int PHOTOZOOM = 2; // 缩放  
    public static final int PHOTORESOULT = 3;// 结果  
  
    public static final String IMAGE_UNSPECIFIED = "image/*";  
    ImageView imageView = null;  
    Button button0 = null;  
    Button button1 = null;  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
        imageView = (ImageView) findViewById(R.id.imageID);  
        button0 = (Button) findViewById(R.id.btn_01);  
        button1 = (Button) findViewById(R.id.btn_02);  
  
        button0.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                Intent intent = new Intent(Intent.ACTION_PICK, null);  
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);  
                startActivityForResult(intent, PHOTOZOOM);  
            }  
        });  
  
        button1.setOnClickListener(new OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));  
                startActivityForResult(intent, PHOTOHRAPH);  
            }  
        });  
    }  
  
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (resultCode == NONE)  
            return;  
        // 拍照  
        if (requestCode == PHOTOHRAPH) {  
            //设置文件保存路径这里放在跟目录下  
            File picture = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");  
            startPhotoZoom(Uri.fromFile(picture));  
        }  
          
        if (data == null)  
            return;  
          
        // 读取相册缩放图片  
        if (requestCode == PHOTOZOOM) {  
            startPhotoZoom(data.getData());  
        }  
        // 处理结果  
        if (requestCode == PHOTORESOULT) {  
            Bundle extras = data.getExtras();  
            if (extras != null) {  
                Bitmap photo = extras.getParcelable("data");  
                ByteArrayOutputStream stream = new ByteArrayOutputStream();  
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 - 100)压缩文件  
                imageView.setImageBitmap(photo);  
            }  
  
        }  
  
        super.onActivityResult(requestCode, resultCode, data);  
    }  
  
    public void startPhotoZoom(Uri uri) {  
        Intent intent = new Intent("com.android.camera.action.CROP");  
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);  
        intent.putExtra("crop", "true");  
        // aspectX aspectY 是宽高的比例  
        intent.putExtra("aspectX", 1);  
        intent.putExtra("aspectY", 1);  
        // outputX outputY 是裁剪图片宽高  
        intent.putExtra("outputX", 64);  
        intent.putExtra("outputY", 64);  
        intent.putExtra("return-data", true);  
        startActivityForResult(intent, PHOTORESOULT);  
    }  
}