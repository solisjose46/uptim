package com.example.uptime.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.uptime.R;
import com.example.uptime.databinding.FragmentAnnouncementBinding;

/**
 * This fragment is for in app notifications for betteruptime announcements
 * 11/20/21 Jose Salazar
 * **/

public class AnnouncementFragment extends DialogFragment {
    public static String TAG = AnnouncementFragment.class.getSimpleName();
    private String announcement;

    AnnouncementViewModel viewModel;
    FragmentAnnouncementBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // data binding set here
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_announcement, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(AnnouncementViewModel.class); // share view model with "BaseActivity" hence the getActivity()
        binding.setViewModel(viewModel); // remember to bind the layout to the viewmodel
        binding.getViewModel().announcementTimer.cancel(); // Turn off timer since we already have an announcement to display
        binding.textViewAnnouncement.setText(announcement); // Insert the announcement into text field of the dialog fragment
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        // Whenever user clicks anywhere on the screen outside of the dialog fragment, it will call this method
        super.onDismiss(dialog);
        // sets this announcement as seen in table and starts the timer
        viewModel.setSeen();

    }

    public static AnnouncementFragment newInstance(String announcement){
        // This is to give the activity this fragment is created in a reference to this dialog fragment
        AnnouncementFragment announcementFragment = new AnnouncementFragment();
        announcementFragment.announcement = announcement;
        return announcementFragment;
    }
}