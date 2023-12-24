package com.halsey.grandtheftradios;

public class Radio {
    private final String name;
    private final String downloadUrl;

    public Radio(String name, String url) {
        this.name = name;
        this.downloadUrl = url;
    }

    public String getName() {
        return this.name;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }
}
