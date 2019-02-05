package com.moviles.josen.trabajomoviles.ui.main;

import android.arch.lifecycle.ViewModel;

public class FragmentTp2ViewModel extends ViewModel {

    private String result;

    private String contact;

    public  FragmentTp2ViewModel(){
        result = "Resultado";
        contact = "Contacto";
    }

    public String getResult() {
        return result;
    }

    public String getContact() {
        return contact;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
