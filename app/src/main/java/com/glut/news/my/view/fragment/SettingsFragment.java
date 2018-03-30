package com.glut.news.my.view.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.glut.news.R;
import com.glut.news.common.utils.CacheDataManager;
import com.glut.news.my.view.activity.OtherSettingActivity;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.MITLicense;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;


/**
 * Created by yy on 2018/2/11.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    private OtherSettingActivity context;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.app_preferences);
        context = (OtherSettingActivity) getActivity();

        setCacheData();//设置缓存数据
        findPreference("clearCache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                CacheDataManager.clearAllCache(getActivity());
                Snackbar.make(getView(), R.string.clear_cache_successfully, Snackbar.LENGTH_SHORT).show();
                setCacheData();
                return false;
            }
        });

        try {
            String version = "当前版本 " + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            findPreference("version").setSummary(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

       /* findPreference("changelog").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.preference.Preference preference) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.changelog_url))));
                return false;
            }
        });*/

        findPreference("licenses").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                createLicenseDialog();
                return false;
            }
        });
        findPreference("sourceCode").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.source_code_url))));
                return false;
            }
        });

        findPreference("copyRight").setOnPreferenceClickListener(new  Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
           /*     new AlertDialog.Builder(context)
                        .setTitle(R.string.copyright)
                        .setMessage(R.string.copyright_content)
                        .setCancelable(true)
                        .show();
                return false;*/
                return false;
            }
        });
    }
    private void createLicenseDialog() {
        Notices notices = new Notices();
        notices.addNotice(new Notice("OkHttp", "https://github.com/square/okhttp", "Copyright 2016 Square, Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Gson", "https://github.com/google/gson", "Copyright 2008 Google Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Glide", "https://github.com/bumptech/glide", "Sam Judd - @sjudd on GitHub, @samajudd on Twitter", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("PersistentCookieJar", "https://github.com/franmontiel/PersistentCookieJar", "Copyright 2016 Francisco José Montiel Navarro", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("jsoup", "https://jsoup.org", "Copyright © 2009 - 2016 Jonathan Hedley (jonathan@hedley.net)", new MITLicense()));

        new LicensesDialog.Builder(context)
                .setNotices(notices)
                .setIncludeOwnLicense(true)
                .build()
                .show();
    }

    private void setCacheData() {
        try {
            findPreference("clearCache").setSummary(CacheDataManager.getTotalCacheSize(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
