package com.example.myfitnessappv4.ui.goals;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;

import com.example.myfitnessappv4.MainActivity;
import com.example.myfitnessappv4.R;

public class GoalsFragment extends Fragment {

//    private com.example.myfitnessappv4.ui.goals.GoalsViewModel goalsViewModel;
    private LinearLayout clickableCurrentWeight;
    private TextView enterCurrentWeight;
    Point p;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        goalsViewModel =
//                ViewModelProviders.of(this).get(com.example.myfitnessappv4.ui.goals.GoalsViewModel.class); //doesn't seem to behave anay differently than = new GoalsViewModel

        final View root = inflater.inflate(R.layout.fragment_goals, container, false);




        //Enter current weight
        enterCurrentWeight = (TextView) root.findViewById(R.id.enterCurrentWeight);
        clickableCurrentWeight = (LinearLayout) root.findViewById(R.id.currentWeightLayout);



        clickableCurrentWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO have this popup with a data entry screen (create new xml layout file, horiontal linear layout)



                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_enter_weight, null);


//                LinearLayout popupEnterWeightLayout = root.findViewById(R.id.popupEnterWeightLayout);

                enterCurrentWeight.setText(popupView.getWidth() + "and" + popupView.getHeight() );

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


//TODO understand this
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

