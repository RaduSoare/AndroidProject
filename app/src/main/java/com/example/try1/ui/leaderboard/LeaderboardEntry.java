package com.example.try1.ui.leaderboard;

public class LeaderboardEntry implements Comparable<LeaderboardEntry> {

    private String userName;
    private int locationsVisited;

    public LeaderboardEntry(String userName, int locationsVisited) {
        this.userName = userName;
        this.locationsVisited = locationsVisited;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLocationsVisited() {
        return locationsVisited;
    }

    public void setLocationsVisited(int locationsVisited) {
        this.locationsVisited = locationsVisited;
    }

    @Override
    public int compareTo(LeaderboardEntry leaderboardEntry) {
        return leaderboardEntry.getLocationsVisited() - this.getLocationsVisited();
    }
}
