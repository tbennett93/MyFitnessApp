package com.example.myfitnessappv4.ui.planner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myfitnessappv4.R;

public class PlannerFragment extends Fragment {




    private com.example.myfitnessappv4.ui.planner.PlannerViewModel plannerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        plannerViewModel =
                ViewModelProviders.of(this).get(com.example.myfitnessappv4.ui.planner.PlannerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_planner, container, false);
        final TextView textView = root.findViewById(R.id.text_planner);
        plannerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}