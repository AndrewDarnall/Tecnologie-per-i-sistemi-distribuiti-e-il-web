package com.netsix.server;

import java.util.HashMap;

public class ShowList {
    protected HashMap<String, Integer> shows;

    public ShowList() {
        shows = new HashMap<>();
    }

    public ShowList add(String name, Integer episodes) {
        shows.put(name, episodes);
        return this;
    }

    public boolean isAvailable(String name, Integer episode) {
        return episode > 0 && shows.containsKey(name) && shows.get(name) >= episode;
    }
}