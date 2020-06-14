package com.example.myfitnessappv4.ui.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;

import com.example.myfitnessappv4.R;

public class GoalsFragment extends Fragment {

    private com.example.myfitnessappv4.ui.goals.GoalsViewModel goalsViewModel;
    private TextView appendCurrentWeight;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalsViewModel =
                ViewModelProviders.of(this).get(com.example.myfitnessappv4.ui.goals.GoalsViewModel.class); //doesn't seem to behave anay differently than = new GoalsViewModel
        View root = inflater.inflate(R.layout.fragment_goals, container, false);


        appendCurrentWeight = (TextView) root.findViewById(R.id.enterCurrentBodyfat);


        final TextView addunit = (TextView) root.findViewById(R.id.enterWeightUnit);

        final String suffix = "lbs"; //Change! this so then it looks at whether lb or kg is setup
        addunit.setText(suffix);

        return root;


    }

}