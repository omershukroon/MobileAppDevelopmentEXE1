package com.example.MobileAppDevelopmentEXE1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MobileAppDevelopmentEXE1.R;
import com.example.MobileAppDevelopmentEXE1.model.Player;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private List<Player> playerList;
    private OnPlayerClickListener onPlayerClickListener;

    public interface OnPlayerClickListener {
        void onPlayerClick(Player player);
    }

    public PlayerAdapter(List<Player> playerList, OnPlayerClickListener onPlayerClickListener) {
        this.playerList = playerList;
        this.onPlayerClickListener = onPlayerClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.playerName.setText(player.getName());
        holder.playerScore.setText(String.valueOf(player.getScore()));
        holder.playerIcon.setImageResource(player.getCharacterIcon());
        holder.itemView.setOnClickListener(v -> onPlayerClickListener.onPlayerClick(player));
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playe_score_item, parent, false);
        return new PlayerViewHolder(view);
    }


    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        TextView playerScore;
        ShapeableImageView playerIcon;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.player_name);
            playerScore = itemView.findViewById(R.id.player_score);
            playerIcon = itemView.findViewById(R.id.player_icon);
        }
    }
    @Override
    public int getItemCount() {
        return playerList.size();
    }
}