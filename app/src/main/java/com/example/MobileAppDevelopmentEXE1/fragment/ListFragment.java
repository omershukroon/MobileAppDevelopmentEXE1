package com.example.MobileAppDevelopmentEXE1.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MobileAppDevelopmentEXE1.R;
import com.example.MobileAppDevelopmentEXE1.Utilities.SharePreferencesManager;
import com.example.MobileAppDevelopmentEXE1.adapter.PlayerAdapter;
import com.example.MobileAppDevelopmentEXE1.model.Player;

import java.util.List;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlayerAdapter playerAdapter;
    private List<Player> playerList;
    private OnPlayerSelectedListener callback;

    public interface OnPlayerSelectedListener {
        void onPlayerSelected(Player player);
    }

    public void setOnPlayerSelectedListener(OnPlayerSelectedListener callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_list_fragment, container, false);

        recyclerView = v.findViewById(R.id.listFragment_LST_players);

        SharePreferencesManager preferencesManager = SharePreferencesManager.getInstance();
        playerList = preferencesManager.loadPlayerList();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        playerAdapter = new PlayerAdapter(playerList, player -> {
            if (callback != null) {
                callback.onPlayerSelected(player);
            }
        });

        recyclerView.setAdapter(playerAdapter);

        return v;
}
}