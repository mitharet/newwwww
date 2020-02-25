package com.mitha.mvicall2222.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chaos.view.PinView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mitha.mvicall2222.MainActivity;
import com.mitha.mvicall2222.MyBounceInterpolator;
import com.mitha.mvicall2222.R;
import com.mitha.mvicall2222.activity.PermissionActivity;

public class VerificationFragment extends BottomSheetDialogFragment {

    public Button lanjut;
    public PinView otp;
    public TextView tv_Warning;

    String getotp = "123456";

    public VerificationFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verification, container);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //        //set to adjust screen height automatically, when soft keyboard appears on screen
        //        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        tv_Warning = rootView.findViewById(R.id.tv_info);
        otp = rootView.findViewById(R.id.pinViewCode);
        lanjut = rootView.findViewById(R.id.buttonNext);
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gettotp = otp.getText().toString();
                if (!TextUtils.isEmpty(gettotp)) {
                    if (!gettotp.equals(getotp)) {
                        otp.setBackgroundResource(R.drawable.kodesalah);
                        @SuppressLint("ResourceType") Animation animation1 =
                                AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                                        R.animator.bounce);

                        // Use bounce interpolator with amplitude 0.2 and frequency 20
                        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                        animation1.setInterpolator(interpolator);

                        otp.startAnimation(animation1);
                        otp.setTextColor(Color.WHITE);
                        tv_Warning.setText("Kode Salah!");
                        tv_Warning.setTextColor(getActivity().getResources().getColor(R.color.kodesalah));
                        tv_Warning.setVisibility(View.VISIBLE);
                    } else {
                        otp.setBackgroundResource(R.drawable.kodebenar);
                        otp.setTextColor(Color.WHITE);
                        tv_Warning.setText("Kode Benar!");
                        tv_Warning.setTextColor(getActivity().getResources().getColor(R.color.kodebenar));
                        startActivity(new Intent(getActivity(), PermissionActivity.class));
                    }
                }
            }
        });

        return rootView;
    }

}