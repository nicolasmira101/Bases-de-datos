/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.datos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author nicolasmiranda
 */
@Embeddable
public class SillaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSILLA")
    private short idsilla;
    @Basic(optional = false)
    @Column(name = "FILA")
    private String fila;

    public SillaPK() {
    }

    public SillaPK(short idsilla, String fila) {
        this.idsilla = idsilla;
        this.fila = fila;
    }

    public short getIdsilla() {
        return idsilla;
    }

    public void setIdsilla(short idsilla) {
        this.idsilla = idsilla;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idsilla;
        hash += (fila != null ? fila.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SillaPK)) {
            return false;
        }
        SillaPK other = (SillaPK) object;
        if (this.idsilla != other.idsilla) {
            return false;
        }
        if ((this.fila == null && other.fila != null) || (this.fila != null && !this.fila.equals(other.fila))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.SillaPK[ idsilla=" + idsilla + ", fila=" + fila + " ]";
    }
    
}
