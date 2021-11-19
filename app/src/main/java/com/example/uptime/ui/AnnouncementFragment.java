package com.example.uptime.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uptime.R;
import com.example.uptime.databinding.FragmentAnnouncementBinding;
import com.example.uptime.pojo.Betteruptime;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AnnouncementFragment extends Fragment {
    private final String TAG = AnnouncementFragment.class.getSimpleName();

    // @Inject
    AnnouncementViewModel viewModel;
    FragmentAnnouncementBinding binding;

    public AnnouncementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // data binding set here
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_announcement, container, false);
        // viewModel inject via Hilt! set to binding TODO: fix this and delete below
        viewModel = new ViewModelProvider(this).get(AnnouncementViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getBetteruptimeLiveData().observe(getViewLifecycleOwner(), new Observer<Betteruptime>() {
            @Override
            public void onChanged(Betteruptime betteruptime) {
                System.out.println(TAG + "observe announcement: " + betteruptime.getData().getAttributes().getAnnouncement());
            }
        });

        View view = binding.getRoot();
        return view;
    }
}