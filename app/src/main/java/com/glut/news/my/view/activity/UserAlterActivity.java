package com.glut.news.my.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.glut.news.AppApplication;
import com.glut.news.R;
import com.glut.news.common.model.entity.UserModel;
import com.glut.news.common.utils.Base64CoderUtil;
import com.glut.news.common.utils.SetUtil;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.ToastUtil;
import com.glut.news.login.view.fragment.LoginActivity;
import com.glut.news.my.presenter.impl.UserAlterActivityPresenterImpl;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by yy on 2018/2/12.
 */

public class UserAlterActivity extends AppCompatActivity implements OnClickListener,IUserAlterActivityView,DatePickerDialog.OnDateSetListener {
    private Toolbar t;
    private RelativeLayout btn_logo;
    private RelativeLayout btn_name;
    private RelativeLayout btn_desc;
    private RelativeLayout btn_sex;
    private RelativeLayout btn_birth;
    private RelativeLayout btn_distric;
    private Button btn_logout;

    private TextView mUserName;
    private ImageView mUserLogo;
    private TextView mUserDesc;
    private TextView mUserSex;
    private TextView mUserBirth;

    private TextView mUserDistrc;


    private  EditText newName;
    private EditText  newDec;

    private String newNameString;
    private String newSexString;
    private String newLogoString;
    private String newDescString;
    private String newDistrcString;
    private String newBirthString;

    private  View  popupView;
    private PopupWindow popupWindow;
    private File file;
    private Uri ImgUri;


    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;

    //调用照相机返回图片文件
    private File tempFile;


    private UserAlterActivityPresenterImpl u=new UserAlterActivityPresenterImpl(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_alter);
        SetUtil.getInstance().setStatusColor(getResources().getColor(R.color.side_1),getWindow());
        AppApplication.getInstance().addActivity(this);
        initView();

        //获取资料默认信息
       intiUserInfo();

    }

    private void initView() {


        btn_logo= (RelativeLayout) findViewById(R.id.btn_logo);
        btn_name= (RelativeLayout) findViewById(R.id.btn_name);
        btn_desc= (RelativeLayout) findViewById(R.id.btn_desc);

        btn_sex= (RelativeLayout) findViewById(R.id.btn_sex);
        btn_distric= (RelativeLayout) findViewById(R.id.btn_distic);
        btn_birth= (RelativeLayout) findViewById(R.id.btn_birth);

        btn_logout=findViewById(R.id.btn_logOut);
        mUserDesc= (TextView) findViewById(R.id.user_desc);

        mUserName= (TextView) findViewById(R.id.user_name);
        mUserLogo= (ImageView) findViewById(R.id.user_logo);
        mUserSex= (TextView) findViewById(R.id.user_sex);
        mUserBirth= (TextView) findViewById(R.id.user_birth);
        mUserDistrc= (TextView) findViewById(R.id.user_destric);

        btn_logo.setOnClickListener(this);
        btn_name.setOnClickListener(this);
        btn_desc.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        btn_sex.setOnClickListener(this);
        btn_birth.setOnClickListener(this);
        btn_distric.setOnClickListener(this);
        t= (Toolbar) findViewById(R.id.toolbar);
        t.setTitle("");
        t.setTitleTextColor(Color.WHITE);
        setSupportActionBar(t);
        //动态改变Toolbar返回按钮颜色：改为白色
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);

        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        ActionBar a=getSupportActionBar();
        if (a!=null){

          a.setDisplayHomeAsUpEnabled(true);
            //a.setHomeAsUpIndicator(R.drawable.back);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
    private void getCityInfo() {
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));


        CityPicker.getInstance()
                .setFragmentManager(getSupportFragmentManager())    //此方法必须调用
                .enableAnimation(true)    //启用动画效果

                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        Toast.makeText(UserAlterActivity.this, data.getName(), Toast.LENGTH_SHORT).show();
                        newDistrcString= data.getName();
                        u.alterUserDistrc(data.getName());

                        CityPicker.getInstance()
                                .locateComplete(null, LocateState.SUCCESS);
                    }

                    @Override
                    public void onLocate() {

                       /* //开始定位，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                //定位完成之后更新数据
                                CityPicker.getInstance()
                                        .locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);

                                Toast.makeText(getActivity(),mWeatherId2 , Toast.LENGTH_SHORT).show();

                                titleCity.setText(mWeatherId2);
                            }


                        }, 2000);*/
                    }
                })
                .show();
    }
    public void intiUserInfo() {
        Glide.with(this).load(Base64CoderUtil.decodeLines(SpUtil.getUserFromSp("UserLogo"))).apply(
                RequestOptions.circleCropTransform().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)).into(mUserLogo);
        mUserName.setText(SpUtil.getUserFromSp("UserName"));
        mUserDesc.setText(SpUtil.getUserFromSp("UserDesc"));
        mUserSex.setText(SpUtil.getUserFromSp("UserSex"));
        mUserBirth.setText(SpUtil.getUserFromSp("UserBirth"));
        mUserDistrc.setText(SpUtil.getUserFromSp("UserDistric"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logo:
                uploadHeadImage();

                break;
            case R.id.btn_name:
                new MaterialDialog.Builder(this)
                        .title(R.string.input_newName)
                        .content(R.string.input_content_name)
                        .widgetColorRes(R.color.input_dialog)
                        .widgetColor(getResources().getColor(R.color.input_dialog))
                        .contentColorRes(R.color.input_dialog)
                        .dividerColorRes(R.color.input_dialog)
                        .negativeColorRes(R.color.input_dialog)
                        .titleColor(getResources().getColor(R.color.input_dialog))
                        .positiveColorRes(R.color.input_dialog)
                        .backgroundColorRes(R.color.dialog_bg)
                        .negativeText("取消")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                ToastUtil.showSuccess("已取消该操作",3000,UserAlterActivity.this);
                            }
                        })
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                ToastUtil.showOnLoading("修改是需要时间的",UserAlterActivity.this);

                                newNameString=input.toString();
                                u.alterUserName(input.toString());//修改用户名

                            }
                        }).show();

                break;
            case R.id.btn_desc:
             new MaterialDialog.Builder(this)
                    .title(R.string.input_desc)
                    .content(R.string.input_content_desc)
                    .widgetColorRes(R.color.input_dialog)
                    .widgetColor(getResources().getColor(R.color.input_dialog))
                    .contentColorRes(R.color.input_dialog)
                    .dividerColorRes(R.color.input_dialog)
                    .negativeColorRes(R.color.input_dialog)
                    .titleColor(getResources().getColor(R.color.input_dialog))
                    .positiveColorRes(R.color.input_dialog)
                    .backgroundColorRes(R.color.dialog_bg)
                    .negativeText("取消")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                            ToastUtil.showSuccess("已取消该操作",3000,UserAlterActivity.this);
                        }
                    })
                    .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            ToastUtil.showOnLoading("修改是需要时间的",UserAlterActivity.this);

                            newDescString=input.toString();
                          u.alterUserDesc(input.toString());//修改desc

                        }
                    }).show();
                break;

            case R.id.btn_sex:
                break;
            case R.id.btn_birth:

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        UserAlterActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.btn_distic:
                getCityInfo();
                break;

            case R.id.btn_logOut:
                u.logOut();

                break;




        }

    }



    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(UserAlterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(UserAlterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(UserAlterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(UserAlterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    getPicFromAlbm();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_PICK);
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

       /* crop 	String 	发送裁剪信号
        aspectX 	int 	X方向上的比例
        aspectY 	int 	Y方向上的比例
        outputX 	int 	裁剪区的宽
        outputY 	int 	裁剪区的高
        scale 	boolean 	是否保留比例
        return-data 	boolean 	是否将数据保留在Bitmap中返回
        data 	Parcelable 	相应的Bitmap数据
        circleCrop 	String 	圆形裁剪区域？
        MediaStore.EXTRA_OUTPUT ("output") 	URI 将URI指向相应的file:///...*/
        //cropImageUri=Uri.fromFile(CropPhoto);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("circleCrop",true);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }
    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(UserAlterActivity.this, "com.glut.news", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(UserAlterActivity.this, "com.glut.news", tempFile);
                        cropPhoto(contentUri);
                    } else {

                        cropPhoto(Uri.fromFile(tempFile));
                    }
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropPhoto(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                Bundle bundle = intent.getExtras();

                if (bundle != null) {
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    Bitmap image = bundle.getParcelable("data");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bytes=baos.toByteArray();
                  String  tp = new String(Base64CoderUtil.encodeLines(bytes));

                    //设置到ImageView上
                    newLogoString=tp;
                    Drawable drawable = new BitmapDrawable(image);
                    /*Glide.with(this).load(newLogoString).apply(
                            RequestOptions.circleCropTransform().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)).into(mUserLogo);*/
                    String d=saveImage("UserLogo",image);
                    File file=new File(d);
                    File imageFile = new File(Environment.getExternalStorageDirectory().getPath(),"123.png");
                    RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
                    RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    u.alterUserLogo(SpUtil.getUserFromSp("UserId"),imageRequestBody);
                    //u.alterUserLogoByBase64(tp);
                    //也可以进行一些保存、压缩等操作后上传
//                    String path = saveImage("crop", image);
                    /**
                     * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上
                     * 传到服务器，QQ头像上传采用的方法跟这个类似
                     */

            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] b = stream.toByteArray();
            // 将图片流以字符串形式存储下来

            tp = new String(Base64Coder.encodeLines(b));
            这个地方大家可以写下给服务器上传图片的实现，直接把tp直接上传就可以了，
            服务器处理的方法是服务器那边的事了，吼吼

            如果下载到的服务器的数据还是以Base64Coder的形式的话，可以用以下方式转换
            为我们可以用的图片类型就OK啦...吼吼
            Bitmap dBitmap = BitmapFactory.decodeFile(tp);
            Drawable drawable = new BitmapDrawable(dBitmap);
            */
                    //ib.setBackgroundDrawable(drawable);
                    //iv.setBackgroundDrawable(drawable);
                }
                break;
        }
    }

    /*保存bitmap到本地*/
    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    @Override
    public void alterSuccess(UserModel userModel) {
        ToastUtil.showSuccess("修改成功",3000,UserAlterActivity.this);


        if (newLogoString!=null){

            SpUtil.saveUserToSp("UserLogo",newLogoString);

            Glide.with(this).load(Base64CoderUtil.decodeLines(SpUtil.getUserFromSp("UserLogo"))).apply(
                    RequestOptions.circleCropTransform().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)).into(mUserLogo);

        }
        if (newSexString!=null){
            SpUtil.saveUserToSp("UserSex",newSexString);
            mUserSex.setText(newSexString);
        }
        if (newNameString!=null){
            SpUtil.saveUserToSp("UserName",newNameString);
            mUserName.setText(newNameString);
        }

        if (newSexString!=null){
            SpUtil.saveUserToSp("UserSex",newSexString);
            mUserSex.setText(newSexString);
        }

        if (newBirthString!=null){
            SpUtil.saveUserToSp("UserBirth",newBirthString);
            mUserBirth.setText(newBirthString);
        }

        if (newDescString!=null){
            SpUtil.saveUserToSp("UserDesc",newDescString);
            mUserDesc.setText(newDescString);
        }

        if (newDistrcString!=null){
            SpUtil.saveUserToSp("UserDistric",newDistrcString);
            mUserDistrc.setText(newDistrcString);
        }
    }

    @Override
    public void alterFail() {
        ToastUtil.showError("修改失败",3000,UserAlterActivity.this);

    }

    @Override
    public void logOutSuccess() {
        ToastUtil.showSuccess("登出成功",3000,UserAlterActivity.this);
        //清除客户端上用户数据
        SpUtil.saveUserToSp("UserId","null");
        SpUtil.saveUserToSp("UserName","null");
        SpUtil.saveUserToSp("UserSex","null");

        SpUtil.saveUserToSp("UserLogo","null");
        SpUtil.saveUserToSp("UserDistric","null");

        SpUtil.saveUserToSp("UserDesc","null");


        Intent intent=new Intent(UserAlterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void logOutFail() {
        ToastUtil.showError("登出失败",3000,UserAlterActivity.this);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =year+"年"+(monthOfYear+1)+"月"+ dayOfMonth+"日";
        newBirthString=date;

        u.alterUserBirthday(date);
        Toast.makeText(UserAlterActivity.this,date,Toast.LENGTH_LONG).show();
    }
}
