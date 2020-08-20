package com.zf.yichat.dto;

import com.zf.yichat.model.SysMenu;

import java.io.Serializable;

public class MenuIndexDto implements Serializable {
    private SysMenu menu;
    private String pName;//

    public SysMenu getMenu() {
        return menu;
    }

    public void setMenu(SysMenu menu) {
        this.menu = menu;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
