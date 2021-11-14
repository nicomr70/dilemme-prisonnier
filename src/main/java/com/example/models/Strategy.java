package com.example.models;

public interface Strategy {
    byte execute(int turnCount, Player player, Player otherPlayer);
}
