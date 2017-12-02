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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import tw.edu.fcu.recommendedfood.Data.ArticleBlogData;
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
    private String arr[];
    String title;
    String msg = "";
    Toolbar fragment_toolbar;

    private static final int REQUEST_CODE_PICK_IMAGE = 1023;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1022;
    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private File mCurrentPhotoFile;// 照相机拍照得到的图片

//    List<String> comments;
    String comments="";
    int position =0;

    private StorageReference mStorageRef;
    File chosenFile;
    private Uri returnUri;
    Call<ImageResponse> call;

    boolean isImage = false;
    boolean succuss = false;

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
        setSupportActionBar(fragment_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initData() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void btnDoneCommand(View view) {

        if(edtTitle.getText().toString().trim().equals("")){
            return;
        }
        String shortSentences = "";
//        comments = new ArrayList<>();
        //TODO 可能要把檔案

//        final NotificationHelper notificationHelper = new NotificationHelper(this.getApplicationContext());
//        notificationHelper.createUploadingNotification();

        ImgurService imgurService = ImgurService.retrofit.create(ImgurService.class);

        for (int i = 0; i < edtMsg.buildEditData().size(); i++) {
            if (edtMsg.checkImageExist(i)) {
                isImage = true;
                //TODO 可能要用File來存下uri
                returnUri = Uri.parse(edtMsg.buildEditData().get(i).imagePath);
//                chosenFile = new File(returnUri+"");
                uploadImg(edtMsg.buildEditData().get(i).imagePath);
                Log.v("edit_text", "" + edtMsg.buildEditData().get(i).imagePath);

//                edtMsg.dataList.get(i).imagePath = "<img src=\"http://i.imgur.com/RyFb0yU.jpg\"/>";//http://i.imgur.com/RyFb0yU.jpg
////                Log.v("edt123",edtMsg.dataList.get(i).imagePath);
//                comments.add("<img src=\"http://i.imgur.com/RyFb0yU.jpg\"/><br>");


//                call = imgurService.postImage("", "", "", "",
//                        MultipartBody.Part.createFormData(
//                                "image",
//                                chosenFile.getName(),
//                                RequestBody.create(MediaType.parse("image/*"), chosenFile)
//                        ));
//
//                final int temp = i;
//                call.enqueue(new Callback<ImageResponse>() {
//                    @Override
//                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
//                        if (response == null) {
//                            notificationHelper.createFailedUploadNotification();
//                            return;
//                        }
//                        if (response.isSuccessful()) {
//                            succuss = true;
//                            Toast.makeText(ArticleCommandActivity.this, "http://imgur.com/" + response.body().data.id, Toast.LENGTH_SHORT)
//                                    .show();
//                            Log.v("Picture", "http://imgur.com/" + response.body().data.id);
////                              下面有錯
//                            edtMsg.dataList.get(temp).imagePath = <img src="http://imgur.com/" + response.body().data.id"/>;
//                            notificationHelper.createUploadedNotification(response.body());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ImageResponse> call, Throwable t) {
//                        Toast.makeText(ArticleCommandActivity.this, "An unknown error has occured.", Toast.LENGTH_SHORT)
//                                .show();
//                        notificationHelper.createFailedUploadNotification();
//                        t.printStackTrace();
//                    }
//                });
            } else {
                comments += "<p>" + edtMsg.buildEditData().get(i).inputStr + "</p><br>";
//                comments.add("<p>" + edtMsg.buildEditData().get(i).inputStr + "</p><br>");
//                edtMsg.dataList.get(i).inputStr = "<p>" +edtMsg.buildEditData().get(i).inputStr+"</p><br>";
//                Log.v("edt123",edtMsg.dataList.get(i).inputStr);
            }
        }
//        for (int i = 0; i < comments.size(); i++) {
////            RichTextEditor.EditData editData = edtMsg.buildEditData().get(i);
////            Log.v("edit_text", "" + editData.toString());
//            Log.v("edit_text", "" + comments.get(i));
//        }
        if(!isImage){
            uploadArticle();
            finish();
        }else {

        }


        Log.v("edit_text", "" + succuss);
        //TODO 還沒完成短語的字串切割

    }

    private void uploadImg(String path) {
        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = mStorageRef.child(file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                setSuccuss(false);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                comments += "<br><a href=\"" + downloadUrl.toString() + "\"<br>";
                comments += "<img src=" + downloadUrl.toString() + "/><br>";
                Log.v("edit_text",downloadUrl.toString());
//                comments.add("<img src=\"" + downloadUrl.toString() + "\"/><br>");
                setSuccuss(true);
            }
        });
    }

    public void setSuccuss(boolean succuss) {
        this.succuss = succuss;

        if (succuss == true) {
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
                Date myDate = new Date();
                int thisYear = myDate.getYear() + 1900;//thisYear = 2003
                int thisMonth = myDate.getMonth() + 1;//thisMonth = 5
                int thisDate = myDate.getDate();//thisDate = 30

                final ArticleBlogUploadData articleBlogUploadData = new ArticleBlogUploadData(
                        LoginContext.getLoginContext().getAccount(),
                        comments,
                        (dataSnapshot.getChildrenCount()+1)+"",
                        "0",
                        thisYear + "/" + thisMonth + "/" + thisDate,
                        edtTitle.getText().toString(),
                        getIntent().getStringExtra("Type")
                );
                myRef.child((dataSnapshot.getChildrenCount()+1)+"").setValue(articleBlogUploadData, new DatabaseReference.CompletionListener() {
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

    public void imgBackAtivity(View view) {
        finish();
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
