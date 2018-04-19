package com.tochi.RobotJUEGO;

import java.io.Serializable;

/**
 * Created by 813743 on 03/12/2017.
 */

public class DataDescriptorComandoRobot implements Serializable {
    private String id;
    private String name;
    private String secuencia;
    private String comando;
    private String tiempo;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataDescriptorComandoRobot(String id, String name, String secuencia, String comando, String tiempo, String status) {
        super();
        this.id = id;
        this.name = name;
        this.secuencia = secuencia;
        this.comando = comando;
        this.tiempo = tiempo;
        this.status = status;
    }
    // get and set methods

}

