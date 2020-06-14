package com.example.myfitnessappv4.ui.guides;

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

public class GuidesFragment extends Fragment {

    private GuidesViewModel guidesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        guidesViewModel =
                ViewModelProviders.of(this).get(GuidesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        final TextView textView = root.findViewById(R.id.text_info);
        guidesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}