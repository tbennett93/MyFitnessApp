package com.example.myfitnessappv4.ui.goals;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;

import com.example.myfitnessappv4.MainActivity;
import com.example.myfitnessappv4.R;

import org.w3c.dom.Text;

public class GoalsFragment extends Fragment {

//    private com.example.myfitnessappv4.ui.goals.GoalsViewModel goalsViewModel;
    private LinearLayout clickableCurrentWeight;


    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.myfitnessappv4.sharedprefsfile";




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_goals, container, false);

        TextView userCurrentWeight = (TextView) root.findViewById(R.id.enterCurrentWeight);

//        //TODO remove this as we don't want to set it to 180 mandatorily
        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        int weight = mPreferences.getInt("CURRENT_WEIGHT_KEY",0);
        String weightUnit = mPreferences.getString("CURRENT_WEIGHT_UNIT","");

        userCurrentWeight.setText(String.valueOf(weight) + weightUnit);




        //Set the currentweight row to be clickable and when clicked opens a window to enter an input
        clickableCurrentWeight = (LinearLayout) root.findViewById(R.id.currentWeightLayout);
        clickableCurrentWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_enter_weight, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                popupWindow.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                dimBehind(popupWindow);


            }


        });


        //Hides the title of the app
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        return root;


    }


    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.6f;
        wm.updateViewLayout(container, p);
    }


}


//TODO understand gravity and how to lay views out in a parent screen (i.e. showatLocation above, how do the x and y talk with the gravity)
