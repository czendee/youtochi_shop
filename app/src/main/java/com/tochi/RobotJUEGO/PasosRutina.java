package com.tochi.RobotJUEGO;

import java.io.Serializable;

/**
 * Created by 813743 on 15/10/2016.
 */

public class PasosRutina implements Serializable {

    private String secuencia;
    private String tiempoEsperaEnSegundos;
    private String comando;

    public PasosRutina(String secuencia, String tiempoEsperaEnSegundos, String comando) {
        super();
        this.secuencia = secuencia;
        this.tiempoEsperaEnSegundos = tiempoEsperaEnSegundos;
        this.comando = comando;
    }
    // get and set methods

    public String getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }

    public String getTiempoEsperaEnSegundos() {
        return tiempoEsperaEnSegundos;
    }

    public void setTiempoEsperaEnSegundos(String tiempoEsperaEnSegundos) {
        this.tiempoEsperaEnSegundos = tiempoEsperaEnSegundos;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }
}