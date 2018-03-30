package com.glut.news.my.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Type;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.bumptech.glide.request.RequestOptions;
import com.glut.news.AppApplication;
import com.glut.news.R;
import com.glut.news.common.utils.SetUtil;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.ToastUtil;
import com.glut.news.login.view.fragment.LoginActivity;
import com.glut.news.my.presenter.impl.UserAlterActivityPresenterImpl;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.Calendar;


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
    private Type type;

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
       /* FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction  ft=fm.beginTransaction();
        UserAlterFragment uaf=new UserAlterFragment();
        ft.replace(R.id.other_info,uaf);
        ft.commit();*/
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

    public void intiUserInfo() {
        Glide.with(this).load(R.drawable.logo).apply(
                RequestOptions.circleCropTransform()).into(mUserLogo);
        mUserName.setText(SpUtil.getUserFromSp("UserName"));
        mUserDesc.setText(SpUtil.getUserFromSp("UserDesc"));
        mUserSex.setText(SpUtil.getUserFromSp("UserSex"));
        mUserBirth.setText(SpUtil.getUserFromSp("UserBitrh"));
        mUserDistrc.setText(SpUtil.getUserFromSp("UserDistric"));

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
                ToastUtil.showSuccess("修改成功",3000,UserAlterActivity.this);

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
                break;

            case R.id.btn_logOut:
                u.logOut();

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

    @Override
    public void alterSuccess() {
        ToastUtil.showSuccess("修改成功",3000,UserAlterActivity.this);


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
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Toast.makeText(UserAlterActivity.this,date,Toast.LENGTH_LONG).show();
    }
}
