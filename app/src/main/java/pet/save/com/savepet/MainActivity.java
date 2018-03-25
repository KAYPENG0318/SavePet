package pet.save.com.savepet;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    //storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference("uploadsimage");
    StorageReference riversRef;
    //處理圖片
    Uri FileUri;
    String suri;
    ImageView imagepic;
    TextView txtpetname;
    TextView txtdate;
    String putPetname;
    String putPetdate;

    String petpetUri;

    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtpetname = (TextView)findViewById(R.id.petName);
        txtdate = (TextView)findViewById(R.id.date);
        putPetname = txtpetname.getText().toString();
        putPetdate = txtdate.getText().toString();



    }

    public void button2(View v)
    {

        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference riversRef = storageRef.child("images/rivers.jpg");

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(MainActivity.this,"已執行上傳圖片",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
//        petpetUri=getIntent().getStringExtra("picUri");
//
//        suri=petpetUri;
//
//        //字串轉回 Uri
//        FileUri =Uri.parse(petpetUri);
//        //本地端 抓照片位置
//        ContentResolver cr = this.getContentResolver();
//
//        try {
//            bitmap = BitmapFactory.decodeStream(cr.openInputStream(FileUri));//由抽象資料接口轉換圖檔路徑為Bitmap
//            imagepic = (ImageView) findViewById(R.id.petImage);//取得圖片控制項ImageView
//            imagepic.setImageBitmap(bitmap);// 將Bitmap設定到ImageView
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
    //上傳firebase
    public void buttonSave(View v)
    {
        //取得 the storage  reference
        riversRef = storageRef.child(System.currentTimeMillis()+"."+suri);
        //add file to reference
        riversRef.putFile(FileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String loadurl=taskSnapshot.getDownloadUrl().toString();

                String key=myRef.child("notes").push().getKey();
                PetData user = new PetData (putPetname,putPetdate,loadurl);
                //這行是跟據路徑  送到FIREBASE 上
                myRef.child("notes").child(key).setValue(user);

                Toast.makeText(MainActivity.this,"已執行",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });






    }
}
