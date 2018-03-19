package com.glut.news.my.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Type;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glut.news.R;
import com.glut.news.my.view.fragment.UserAlterFragment;

import java.io.File;




/**
 * Created by yy on 2018/2/12.
 */

public class UserAlterActivity extends AppCompatActivity implements OnClickListener {
    private Toolbar t;
    private RelativeLayout btn_logo;
    private RelativeLayout btn_name;
    private RelativeLayout btn_desc;
    private TextView mUserName;
    private ImageView mUserLogo;
    private TextView mUserDesc;
    private  EditText newName;
    private EditText  newDec;

    private  View  popupView;
    private PopupWindow popupWindow;
    private File file;
    private Uri ImgUri;
    private Type type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_alter);

        //获取资料默认信息
        getUserInfo();
        initView();
    }

    private void initView() {


        btn_logo= (RelativeLayout) findViewById(R.id.btn_logo);
        btn_name= (RelativeLayout) findViewById(R.id.btn_name);
        btn_desc= (RelativeLayout) findViewById(R.id.btn_desc);
        mUserName= (TextView) findViewById(R.id.user_name);
        mUserLogo= (ImageView) findViewById(R.id.user_logo);
        mUserDesc= (TextView) findViewById(R.id.user_desc);

        btn_logo.setOnClickListener(this);
        btn_name.setOnClickListener(this);
        btn_desc.setOnClickListener(this);
        Glide.with(this).load(R.drawable.logo).apply(
                RequestOptions.circleCropTransform()).into(mUserLogo);
        t= (Toolbar) findViewById(R.id.toolbar);
        t.setTitle("");
        t.setTitleTextColor(Color.WHITE);
        setSupportActionBar(t);
        ActionBar a=getSupportActionBar();
        if (a!=null){

          a.setDisplayHomeAsUpEnabled(true);
            a.setHomeAsUpIndicator(R.drawable.back);

        }
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction  ft=fm.beginTransaction();
        UserAlterFragment uaf=new UserAlterFragment();
        ft.replace(R.id.other_info,uaf);
        ft.commit();
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

    public void getUserInfo() {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("用户昵称",sp.getString("username","空"));
        Log.d("用户性别",sp.getString("sex","空"));
        Log.d("用户生日",sp.getString("birthder","空"));
        Log.d("用户介绍",sp.getString("desc","空"));

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

        editor.putString("name", "林计钦");

        editor.putInt("age", 24);

        editor.commit();//提交修改

       /* String name = sharedPreferences.getString("name", "");

        int age = sharedPreferences.getInt("age", 1);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logo:

                backgroundToDark();

                //准备PopupWindow的布局View
                popupView = LayoutInflater.from(this).inflate(R.layout.popup, null);
                //初始化一个PopupWindow，width和height都是WRAP_CONTENT
             popupWindow = new PopupWindow( 500,500);
                //设置PopupWindow的视图内容
                popupWindow.setContentView(popupView);
                // 点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popupWindow.setOutsideTouchable(true);
                // 设置PopupWindow动画
                popupWindow.setAnimationStyle(R.style.AnimDown);
                // 设置是否允许PopupWindow的范围超过屏幕范围
                popupWindow.setClippingEnabled(true);
                // 设置PopupWindow消失监听
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override public void onDismiss() {
                       backgroundToLight();
                    } });

                View vv=LayoutInflater.from(this).inflate(R.layout.activity_user_alter,null);
                // PopupWindow在targetView下方弹出
                //popupWindow.showAsDropDown(vv);
                popupWindow.showAtLocation(vv, Gravity.CENTER, 0, 0);

                TextView btn_pic=popupView.findViewById(R.id.pop_btn_pic);
                TextView btn_photo=popupView.findViewById(R.id.pop_btn_photo);

                 TextView btn_cancel= popupView.findViewById(R.id.pop_btn_cancel);
                btn_pic.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent picture = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(picture, 2);
                        popupWindow.dismiss();
                    }
                });

                btn_photo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                      file = new File(Environment.getExternalStorageDirectory(),
                                System.currentTimeMillis() + ".jpg");
                        ImgUri = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImgUri);
                        startActivityForResult(intent, 1);
                        /*type = Type.CAMERA;
                        if (listener != null) {
                            listener.getType(type);
                            listener.getImgUri(ImgUri, file);
                        }*/

                        //dismiss();

                    }
                });
                btn_cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.btn_name:

                backgroundToDark();
                //准备PopupWindow的布局View
                popupView = LayoutInflater.from(this).inflate(R.layout.popup_name, null);
                //初始化一个PopupWindow，width和height都是WRAP_CONTENT
                popupWindow = new PopupWindow( 500,500);
                //设置PopupWindow的视图内容
                popupWindow.setContentView(popupView);
                // 点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popupWindow.setOutsideTouchable(true);
                // 设置PopupWindow动画
                popupWindow.setAnimationStyle(R.style.AnimDown);
                // 设置是否允许PopupWindow的范围超过屏幕范围
                popupWindow.setClippingEnabled(true);
                // 设置PopupWindow消失监听
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override public void onDismiss() {
                        backgroundToLight();
                    } });

                vv=LayoutInflater.from(this).inflate(R.layout.activity_user_alter,null);
                // PopupWindow在targetView下方弹出
                //popupWindow.showAsDropDown(vv);
                popupWindow.showAtLocation(vv, Gravity.CENTER, 0, 0);

               newName=popupView.findViewById(R.id.new_name);
                TextView btn_cancel2=popupView.findViewById(R.id.btn_cancel);
                TextView btn_confirm=popupView.findViewById(R.id.btn_confirm);
                btn_cancel2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                btn_confirm.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName2=newName.getText().toString();
                        Log.d("newName",newName2);
                        popupWindow.dismiss();
                    }
                });

                break;
            case R.id.btn_desc:

                backgroundToDark();
                //准备PopupWindow的布局View
             popupView = LayoutInflater.from(this).inflate(R.layout.popup_desc, null);
                //初始化一个PopupWindow，width和height都是WRAP_CONTENT
                popupWindow = new PopupWindow( 500,500);
                //设置PopupWindow的视图内容
                popupWindow.setContentView(popupView);
                // 点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popupWindow.setOutsideTouchable(true);
                // 设置PopupWindow动画
                popupWindow.setAnimationStyle(R.style.AnimDown);
                // 设置是否允许PopupWindow的范围超过屏幕范围
                popupWindow.setClippingEnabled(true);
                // 设置PopupWindow消失监听
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override public void onDismiss() {
                        backgroundToLight();
                    } });

                vv=LayoutInflater.from(this).inflate(R.layout.activity_user_alter,null);
                // PopupWindow在targetView下方弹出
                //popupWindow.showAsDropDown(vv);
                popupWindow.showAtLocation(vv, Gravity.CENTER, 0, 0);

               newDec=popupView.findViewById(R.id.new_desc);
                 btn_cancel2=popupView.findViewById(R.id.btn_cancel);
                 btn_confirm=popupView.findViewById(R.id.btn_confirm);
                btn_cancel2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                btn_confirm.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newDec2=newDec.getText().toString();
                        Log.d("newDec",newDec2);
                        popupWindow.dismiss();
                    }
                });
                break;

        }

    }
    // 设置背景颜色变暗
    public void backgroundToDark(){


        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
    }
    // 设置背景颜色恢复正常
    public void backgroundToLight(){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && null != data) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String picturePath = c.getString(columnIndex);
                c.close();

                Bitmap bmp = BitmapFactory.decodeFile(picturePath);

                Glide.with(this).load(picturePath).apply(
                        RequestOptions.circleCropTransform()).into(mUserLogo);
                // 获取图片并显示
                // zqRoundOvalImageView.setImageBitmap(bmp);
                //  saveBitmapFile(UtilImags.compressScale(bmp), UtilImags.SHOWFILEURL(SettingActivity.this) + "/stscname.jpg");
                // staffFileupload(new File(UtilImags.SHOWFILEURL(SettingActivity.this) + "/stscname.jpg"));
            } catch (Exception e) {
                //  showToastShort("上传失败");
            }
        }
    }
}
