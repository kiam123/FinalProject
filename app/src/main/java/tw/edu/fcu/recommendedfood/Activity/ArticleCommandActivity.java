package tw.edu.fcu.recommendedfood.Activity;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import tw.edu.fcu.recommendedfood.Data.ArticleBlogData;
import tw.edu.fcu.recommendedfood.Data.ArticleBlogUploadCollectionData;
import tw.edu.fcu.recommendedfood.Data.ArticleBlogUploadData;
import tw.edu.fcu.recommendedfood.Data.LoginContext;
import tw.edu.fcu.recommendedfood.ImgurLib.DocumentHelper;
import tw.edu.fcu.recommendedfood.ImgurLib.ImageResponse;
import tw.edu.fcu.recommendedfood.ImgurLib.ImgurService;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Widget.RichTextEditor;


public class ArticleCommandActivity extends AppCompatActivity {
    private EditText edtTitle;
    private RichTextEditor edtMsg;
    String title;
    Toolbar fragment_toolbar;
    Button command;

    private static final int REQUEST_CODE_PICK_IMAGE = 1023;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1022;
    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private File mCurrentPhotoFile;// 照相机拍照得到的图片

    String comments="";
    int position =0;

    private StorageReference mStorageRef;
    File chosenFile;
    private Uri returnUri;

    boolean isImage = false;
    boolean succuss = false;
    int imgCount = 0;
    ArrayList<Data> stringArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_command);

        initView();
        initData();
    }

    public void initView() {
        edtTitle = (EditText) findViewById(R.id.edt_title);
        edtMsg = (RichTextEditor) findViewById(R.id.edt_msg);
        fragment_toolbar = (Toolbar) findViewById(R.id.fragment_toolbar);
        command = (Button) findViewById(R.id.command);
        command.setOnClickListener(commandOnClickListener);
        setSupportActionBar(fragment_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initData() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    View.OnClickListener commandOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(edtTitle.getText().toString().trim().equals("")){
                Toast.makeText(ArticleCommandActivity.this,"請輸入標題",Toast.LENGTH_SHORT).show();
                return;
            }

            stringArrayList = new ArrayList<>();

            Log.v("edit_text", "" + edtMsg.buildEditData().size());
            for (int i = 0; i < edtMsg.buildEditData().size(); i++) {
                if (edtMsg.checkImageExist(i)) {
                    stringArrayList.add(new Data("",edtMsg.buildEditData().get(i).imagePath));
                    Log.v("edit_text", "11 " + stringArrayList.get(stringArrayList.size()-1).getImagePath());
                } else {
                    Log.v("edit_text", "yyyy   " );
                    stringArrayList.add(new Data("<p>" +edtMsg.buildEditData().get(i).inputStr+"</p><br>",""));
                    Log.v("edit_text", "yyyy   " + stringArrayList.get(stringArrayList.size()-1).getInputStr());
                }
            }

            for (int i = 0; i < stringArrayList.size(); i++) {
                if(!stringArrayList.get(i).getImagePath().equals("")){
                    isImage = true;
                    uploadImg(stringArrayList.get(i).getImagePath(), i);
                }
            }

            if(!isImage){
                for (int i = 0; i < stringArrayList.size(); i++) {
                    comments = comments + stringArrayList.get(i).getInputStr();
                    Log.v("edit_text", "" + comments);
                }
                uploadArticle();
                finish();
            }else {

            }
            Log.v("edit_text", "" + succuss);
            //TODO 還沒完成短語的字串切割
        }
    };

    public class Data {
        public String inputStr = "";
        public String imagePath = "";

        public Data(String inputStr, String imagePath) {
            this.inputStr = inputStr;
            this.imagePath = imagePath;
        }

        public String getInputStr() {
            return inputStr;
        }

        public String getImagePath() {
            return imagePath;
        }
    }

    private void uploadImg(String path, final int i) {
        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = mStorageRef.child(file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.v("edit_text123",stringArrayList.get(i).imagePath);

                stringArrayList.get(i).imagePath = "<br><a href=\"" + downloadUrl.toString() +"\">"+downloadUrl.toString()+"</a><br>";
                stringArrayList.get(i).imagePath += "<img src='" + downloadUrl.toString() + "'/><br>";
                imgCount++;

                Log.v("edit_text",edtMsg.imgCount+" "+imgCount);
                if(edtMsg.imgCount == imgCount){
                    setSuccuss(true);
                }
            }
        });
    }

    public void setSuccuss(boolean succuss) {
        this.succuss = succuss;

        if (succuss == true) {
            for(int i = 0;i < stringArrayList.size();i++){
                if(!stringArrayList.get(i).getImagePath().equals("")){
                    comments += stringArrayList.get(i).imagePath;
                } else {
                    comments += stringArrayList.get(i).inputStr;
                }
            }
            uploadArticle();
        }
        finish();
    }


    public void uploadArticle(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("article_title_table");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Calendar c = Calendar.getInstance();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                Date myDate = new Date();
                int thisYear = myDate.getYear() + 1900;//thisYear = 2003
                int thisMonth = myDate.getMonth() + 1;//thisMonth = 5
                int thisDate = myDate.getDate();//thisDate = 30
                final String articleCount = (dataSnapshot.getChildrenCount()+1)+"";
                final ArticleBlogUploadData articleBlogUploadData = new ArticleBlogUploadData(
                        LoginContext.getLoginContext().getAccount(),
                        comments,
                        (dataSnapshot.getChildrenCount()+1)+"",
                        "0",
                        thisYear + "/" + thisMonth + "/" + thisDate,
                        edtTitle.getText().toString(),
                        getIntent().getStringExtra("Type"),
                        dateFormat.format(myDate)
                );

                final ArticleBlogUploadCollectionData articleBlogUploadCollectionData = new ArticleBlogUploadCollectionData(
                        LoginContext.getLoginContext().getAccount(),
                        articleCount,
                        "0",
                        thisYear + "/" + thisMonth + "/" + thisDate,
                        dateFormat.format(myDate),
                        edtTitle.getText().toString(),
                        getIntent().getStringExtra("Type")
                );
                myRef.child(articleCount).setValue(articleBlogUploadData, new DatabaseReference.CompletionListener() {
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if(error == null){
                            Toast.makeText(ArticleCommandActivity.this, "發送成功", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(ArticleCommandActivity.this, "發送失敗", Toast.LENGTH_LONG).show();
                        }
                        Log.v("aaasdasdas123",error+"");
                    }
                });
                final DatabaseReference myRef2 = database.getReference().child("account_collection_table").child(LoginContext.getLoginContext().getAccount());
                myRef2.child(articleCount).setValue(articleBlogUploadCollectionData, new DatabaseReference.CompletionListener() {
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if(error == null){
                            Toast.makeText(ArticleCommandActivity.this, "發送成功", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(ArticleCommandActivity.this, "發送失敗", Toast.LENGTH_LONG).show();
                        }
                        Log.v("aaasdasdas123",error+"");
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void OnGalleryClick(View view) {
        if (view.getId() == R.id.img_editor_gallery) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");// 相片类型
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        } else if (view.getId() == R.id.img_camera) {
            openCamera();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            returnUri = data.getData();
            insertBitmap(getRealFilePath(returnUri));

            Log.v("filePath", "" + returnUri);
            String filePath;
            filePath = DocumentHelper.getPath(this, returnUri);
            Log.v("filePath", filePath);
            chosenFile = new File(filePath);
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            insertBitmap(mCurrentPhotoFile.getAbsolutePath());
        }
    }

    private void insertBitmap(String imagePath) {
        edtMsg.insertImage(imagePath);
    }

    protected void openCamera() {
        try {
            // Launch camera to take photo for selected contact
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
        } catch (ActivityNotFoundException e) {
        }
    }

    /**
     * 用当前时间给取得的图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date) + ".jpg";
    }

    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    public String getRealFilePath(final Uri uri) {
        if (null == uri) {
            return null;
        }

        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuBack) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}