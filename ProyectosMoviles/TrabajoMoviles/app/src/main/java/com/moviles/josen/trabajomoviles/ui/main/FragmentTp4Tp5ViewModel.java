package com.moviles.josen.trabajomoviles.ui.main;

import android.arch.lifecycle.ViewModel;

public class FragmentTp4Tp5ViewModel extends ViewModel {

    private final byte zero = 0;
    private final String base = "Iteración: ";

    private String serviceText;
    private String intentServiceText;
    private int lastNumberFromService;
    private boolean boundServiceConnected;

    public FragmentTp4Tp5ViewModel() {
        serviceText = base + zero;
        intentServiceText = base + zero;
        lastNumberFromService = -1;
        boundServiceConnected = false;
    }

    public String getServiceText() { return serviceText; }

    public  String getIntentServiceText() { return intentServiceText; }

    public void setServiceText(String service) {

        this.serviceText = base + service;
    }

    public void setIntentServiceText(String intentService) {
        this.intentServiceText = base + intentService;
    }

    public int getLastNumberFromService() {
        return lastNumberFromService;
    }

    public void setLastNumberFromService(int lastNumberFromService) {
        this.lastNumberFromService = lastNumberFromService;
    }

    public boolean isBoundServiceConnected() {
        return boundServiceConnected;
    }

    public void setBoundServiceConnected(boolean boundServiceConnected) {
        this.boundServiceConnected = boundServiceConnected;
    }
}
