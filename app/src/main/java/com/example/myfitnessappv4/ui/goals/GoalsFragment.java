package com.example.myfitnessappv4.ui.goals;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class GoalsFragment extends Fragment {


    //Universal
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.myfitnessappv4.sharedprefsfile";
    private SharedPreferences.Editor preferencesEditor;
    private View root;

    //Current weight
    private TextView popupUnit;
    private EditText popupEnterWeight;
    private TextView userCurrentWeight;
    private LinearLayout clickableCurrentWeight;
    private PopupWindow popupWindow;

    //Current BF
    private EditText popupEnterBodyfat;
    private TextView userCurrentBodyfat;
    private LinearLayout clickableCurrentBodyfat;


    //Start Date
    private EditText displayStartDateField;
    private LinearLayout clickableStartDate;
    private Calendar myCalendar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Hides the title of the app
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        //Create a View object of the fragment for use in the app

        root = inflater.inflate(R.layout.fragment_goals, container, false);


        //establish objects fpr sharedpreferences queries
        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();


        //**********************************************************************************
        //Current Weight
        //**********************************************************************************

        //Current weight View with functionality
        //When the row is clicked, a popup view will appear prompting the user to enter their weight.
        //When entered, this closes the popup, updates the saved data and forces a refresh in the main layout
        userCurrentWeight = (TextView) root.findViewById(R.id.enterCurrentWeight);

        //set weight text to that held in the preferences
        userCurrentWeight.setText(String.valueOf( mPreferences.getInt("CURRENT_WEIGHT_KEY",0)) +  mPreferences.getString("CURRENT_WEIGHT_UNIT",""));




        //Set the currentweight row to be clickable and when clicked opens a window to enter an input
        clickableCurrentWeight = (LinearLayout) root.findViewById(R.id.currentWeightLayout);
        clickableCurrentWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_enter_weight, null);
                popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

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


        //**********************************************************************************
        //Current Bodyfat
        //**********************************************************************************

        //Current bodyfat View with functionality
        //When the row is clicked, a popup view will appear prompting the user to enter their bodyfat.
        //When entered, this closes the popup, updates the saved data and forces a refresh in the main layout
        userCurrentBodyfat = (TextView) root.findViewById(R.id.enterCurrentBodyfat);

        //set bodyfat text to that held in the preferences
        userCurrentBodyfat.setText(String.valueOf( mPreferences.getInt("CURRENT_BODYFAT",0)) +  "%");


        //Set the currentBFLayout row to be clickable and when clicked opens a window to enter an input
        clickableCurrentBodyfat = (LinearLayout) root.findViewById(R.id.currentBFLayout);





        //**********************************************************************************
        //**********************************************************************************



        //**********************************************************************************
        //Start Date
        //**********************************************************************************

        //Current start date View with functionality
        //When the row is clicked, a popup view will appear prompting the user to enter the start date
        displayStartDateField = (EditText) root.findViewById(R.id.enterStartDate);

        //TODO set this to the stored value of  preferencesEditor.putString("START_DATE%",); and set a test one up at initialisation
//        userCurrentBodyfat.setText(String.valueOf( mPreferences.getInt("CURRENT_BODYFAT",0)) +  "%");

        //Set the currentBFLayout row to be clickable and when clicked opens a window to enter an input
        clickableStartDate = (LinearLayout) root.findViewById(R.id.startDate);

        myCalendar = Calendar.getInstance();


        displayStartDateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }


            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

            };

        });



        //**********************************************************************************
        //**********************************************************************************







        return root;


    }


    private void updateLabel() {

        //TODO check time formatting and if it can be translated to the users accepted format
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        displayStartDateField.setText(sdf.format(myCalendar.getTime()));

        //figure out how to incorporate the below into the above
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        LocalDate dateOfBirth = LocalDate.of(1991, Month.OCTOBER, 13);
        String formattedDob = dateOfBirth.format(dateFormatter);
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

