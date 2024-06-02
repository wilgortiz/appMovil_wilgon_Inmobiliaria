package com.wilgon.appinmobiliariawilgon.modelo;

public class CambiarClaveView {
    public String ClaveActual;
    public String ClaveNueva;

    public CambiarClaveView(String claveActual, String claveNueva) {
        ClaveActual = claveActual;
        ClaveNueva = claveNueva;
    }


    public String getClaveActual() {
        return ClaveActual;
    }

    public void setClaveActual(String claveActual) {
        ClaveActual = claveActual;
    }

    public String getClaveNueva() {
        return ClaveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        ClaveNueva = claveNueva;
    }
}
