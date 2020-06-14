package com.example.myfitnessappv4.ui.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;

import com.example.myfitnessappv4.R;

public class GoalsFragment extends Fragment {

    private com.example.myfitnessappv4.ui.goals.GoalsViewModel goalsViewModel;
    private LinearLayout clickableCurrentWeight;
    private TextView enterCurrentWeight;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalsViewModel =
                ViewModelProviders.of(this).get(com.example.myfitnessappv4.ui.goals.GoalsViewModel.class); //doesn't seem to behave anay differently than = new GoalsViewModel
        View root = inflater.inflate(R.layout.fragment_goals, container, false);


        //Enter current weight
        enterCurrentWeight = (TextView) root.findViewById(R.id.enterCurrentWeight);
        clickableCurrentWeight = (LinearLayout) root.findViewById(R.id.currentWeightLayout);
        clickableCurrentWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO have this popup with a data entry screen (create new xml layout file, horiontal linear layout)
                enterCurrentWeight.setText("testing");

            }
        });


        //Hides the title of the app
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        return root;


    }

}