package com.mitha.mvicall2222.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private PopupWindow mPopupWindow;
    private boolean mIsPopupVisible;
    ProgressDialog progressDialog;

    public static String[] permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE};


    public void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setKeyboard(View mParentLayout){
        mParentLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();

            mParentLayout.getWindowVisibleDisplayFrame(r);

            int heightDiff = mParentLayout.getRootView().getHeight() - (r.bottom - r.top);
            if (heightDiff > 100) {
                if (mIsPopupVisible) {
                    keepKeyboard();
                    mIsPopupVisible = false;
                    mPopupWindow.dismiss();
                }
            }

        });

        checkKeyboardIsOpen(mParentLayout, mParentLayout);
    }

    public void keepKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void checkKeyboardIsOpen(final View contentView, View parentView) {

        parentView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {

            Rect r = new Rect();
            contentView.getWindowVisibleDisplayFrame(r);

        });
    }

    protected void showSettingsDialog() {

        if (!BaseActivity.this.isFinishing()) {
            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Test")
                    .setMessage("Test 2")
                    .setPositiveButton("Ya", null) //Set to null. We override the onclick
                    .setCancelable(false).create();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(final DialogInterface dialog) {
                    Button buttonPositive = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    buttonPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            openSettings();
                        }
                    });

                }
            });
            dialog.show();
        }

    }

    protected void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void checkPermissionAllow() {
        Dexter.withActivity(this).withPermissions(permissions).withListener(new MultiplePermissionsListener() {

            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    Log.i("allow all permission", "All permissions are granted");
                } else if (!report.getDeniedPermissionResponses().isEmpty()) {
                    showSettingsDialog();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Log.i("error persion", String.valueOf(error));
            }
        }).onSameThread().check();
    }

    public boolean checkValidation(EditText etPhoneNo) {
        boolean ret = true;
        if (!hasText(etPhoneNo)) ret = false;
        return ret;
    }

    public void showSnackBar(String argument){
        try{
            Snackbar.make(findViewById(android.R.id.content), argument, Snackbar.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0) {
            showSnackBar("Cannot be empty");
            return false;
        }

        return true;
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showLoading(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismissLoading()
    {
        progressDialog.dismiss();
    }
}
