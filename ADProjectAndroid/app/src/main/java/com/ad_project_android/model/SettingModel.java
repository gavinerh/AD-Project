package com.ad_project_android.model;

public class SettingModel {
    private String iconFilename;
    private String name;

    public SettingModel(String iconFilename, String name) {
        this.iconFilename = iconFilename;
        this.name = name;
    }

    public SettingModel() {
    }

    public String getIconFilename() {
        return iconFilename;
    }

    public void setIconFilename(String iconFilename) {
        this.iconFilename = iconFilename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
