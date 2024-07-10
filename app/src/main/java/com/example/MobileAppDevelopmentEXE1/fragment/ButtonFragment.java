package com.example.MobileAppDevelopmentEXE1.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.MobileAppDevelopmentEXE1.R;
import com.example.MobileAppDevelopmentEXE1.MainActivity;
import com.example.MobileAppDevelopmentEXE1.model.StartActivity;
import com.google.android.material.button.MaterialButton;

public class ButtonFragment extends Fragment {

    private MaterialButton gameOver_BTN_ExitGame;
    private MaterialButton gameOver_BTN_StartAgain;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_button_fragment, container, false);
        findViews(v);
        initViews();
        return v;
    }

    private void findViews(View v) {
        gameOver_BTN_ExitGame = v.findViewById(R.id.gameOver_BTN_ExitGame);
        gameOver_BTN_StartAgain = v.findViewById(R.id.gameOver_BTN_StartAgain);
    }

    private void initViews() {
        gameOver_BTN_ExitGame.setOnClickListener(v -> changeActivity(1));
        gameOver_BTN_StartAgain.setOnClickListener(v -> changeActivity(0));
    }

    private void changeActivity(int choice) {
        if(choice == 1){
            closeApp();
        }else{
            Intent intent = new Intent(getActivity() , StartActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0, 0);
        }
    }

    private void closeApp() {
        if (getActivity() != null) {
            getActivity().finishAffinity();
 }
}


}