package com.example.myfitnessappv4.ui.goals;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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

import static android.content.Context.INPUT_METHOD_SERVICE;

public class GoalsFragment extends Fragment {

//    private com.example.myfitnessappv4.ui.goals.GoalsViewModel goalsViewModel;
    private LinearLayout clickableCurrentWeight;


    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.myfitnessappv4.sharedprefsfile";
    private SharedPreferences.Editor preferencesEditor;
    private TextView popupUnit;
    private  EditText popupEnterWeight;
    private TextView userCurrentWeight;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_goals, container, false);

        userCurrentWeight = (TextView) root.findViewById(R.id.enterCurrentWeight);


        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();

        int weight = mPreferences.getInt("CURRENT_WEIGHT_KEY",0);
        String weightUnit = mPreferences.getString("CURRENT_WEIGHT_UNIT","");

        userCurrentWeight.setText(String.valueOf( mPreferences.getInt("CURRENT_WEIGHT_KEY",0)) +  mPreferences.getString("CURRENT_WEIGHT_UNIT",""));




        //Set the currentweight row to be clickable and when clicked opens a window to enter an input
        clickableCurrentWeight = (LinearLayout) root.findViewById(R.id.currentWeightLayout);
        clickableCurrentWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_enter_weight, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

                //set weight text in popup
                popupEnterWeight = (EditText) popupView.findViewById(R.id.popupEnterWeight);
                popupEnterWeight.setText(String.valueOf( mPreferences.getInt("CURRENT_WEIGHT_KEY",0)) );

                //set weight unit
                popupUnit = (TextView) popupView.findViewById(R.id.popup_unit);
                popupUnit.setText(String.valueOf( mPreferences.getString("CURRENT_WEIGHT_UNIT","")));

                //open popup
                popupWindow.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                dimBehind(popupWindow);

                //Clear text from enter text field if clicked
                popupEnterWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        popupEnterWeight.setText("");


                        popupEnterWeight.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_ENTER) {


                                    InputMethodManager manager = (InputMethodManager) getContext()
                                            .getSystemService(INPUT_METHOD_SERVICE);
                                    if (manager != null)
                                        manager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                    return true; //this is required to stop sending key event to parent


                                }
                                return false;
                            }
                        });


                    }
                });

                //okay button to finalise info and close
                Button buttonOkayEnterWeight = (Button) popupView.findViewById(R.id.buttonOkayEnterWeight);

                buttonOkayEnterWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(popupEnterWeight.getText().toString().matches("")){

                        }else
                        {
                            preferencesEditor.putInt("CURRENT_WEIGHT_KEY", Integer.parseInt(popupEnterWeight.getText().toString()));
                            preferencesEditor.apply();
                            preferencesEditor.commit();
                            userCurrentWeight.setText(String.valueOf( mPreferences.getInt("CURRENT_WEIGHT_KEY",0)) +  mPreferences.getString("CURRENT_WEIGHT_UNIT",""));
                            popupWindow.dismiss();
                        }


                    }
                });

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

