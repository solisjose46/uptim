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
        // getActivity below to share viewModel with base activity
        viewModel = new ViewModelProvider(getActivity()).get(AnnouncementViewModel.class); // share view model with "BaseActivity" hence the getActivity()
        binding.setViewModel(viewModel); // remember to bind the layout to the viewmodel
        binding.textViewAnnouncement.setText(announcement);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        viewModel.setSeen();

    }

    public static AnnouncementFragment newInstance(String announcement){
        AnnouncementFragment announcementFragment = new AnnouncementFragment();
        announcementFragment.announcement = announcement;
        return announcementFragment;
    }



    //    private final String TAG = AnnouncementFragment.class.getSimpleName();
//
//    AnnouncementViewModel viewModel;
//    FragmentAnnouncementBinding binding;
//
//    public AnnouncementFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // data binding set here
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_announcement, container, false);
//        // getActivity below to share viewModel with base activity
//        viewModel = new ViewModelProvider(getActivity()).get(AnnouncementViewModel.class); // share view model with "BaseActivity" hence the getActivity()
//        binding.setViewModel(viewModel); // remember to bind the layout to the viewmodel
//
//        viewModel.getBetteruptimeLiveData().observe(getViewLifecycleOwner(), new Observer<Betteruptime>() {
//            // betteruptime is null because of api call is async so must wait for update, handled here
//            @Override
//            public void onChanged(Betteruptime betteruptime) {
//                if(betteruptime != null) {
//                    System.out.println(TAG + "observe announcement: " + betteruptime.getAnnouncement());
//                    binding.textViewAnnouncement.setText(betteruptime.getAnnouncement());
//                }
//                else{
//                    System.out.println(TAG + "observe announcement: is null ...");
//                    binding.textViewAnnouncement.setText("fetching announcement...");
//                }
//            }
//        });
//
//        View view = binding.getRoot();
//        return view;
//    }
//
//    // TODO: for dialog fragment, on dismiss call: setSeen();
//
//    public void setText(String text){
//        binding.textViewAnnouncement.setText(text);
//    }
}