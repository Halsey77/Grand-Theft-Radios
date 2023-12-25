package com.halsey.grandtheftradios.RadioObjects;

public class Radio {
    private final String game;
    private final String name;
    private final String url;

    public Radio(String game, String name, String url) {
        this.game = game;
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public String getFileName() {
        return this.game + "-" + this.name + ".mp3";
    }

    public String getUrl() {
        return this.url;
    }
}
