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
public class SillaporestadioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSILLA")
    private short idsilla;
    @Basic(optional = false)
    @Column(name = "FILA")
    private String fila;
    @Basic(optional = false)
    @Column(name = "IDESTADIO")
    private short idestadio;

    public SillaporestadioPK() {
    }

    public SillaporestadioPK(short idsilla, String fila, short idestadio) {
        this.idsilla = idsilla;
        this.fila = fila;
        this.idestadio = idestadio;
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

    public short getIdestadio() {
        return idestadio;
    }

    public void setIdestadio(short idestadio) {
        this.idestadio = idestadio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idsilla;
        hash += (fila != null ? fila.hashCode() : 0);
        hash += (int) idestadio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SillaporestadioPK)) {
            return false;
        }
        SillaporestadioPK other = (SillaporestadioPK) object;
        if (this.idsilla != other.idsilla) {
            return false;
        }
        if ((this.fila == null && other.fila != null) || (this.fila != null && !this.fila.equals(other.fila))) {
            return false;
        }
        if (this.idestadio != other.idestadio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.SillaporestadioPK[ idsilla=" + idsilla + ", fila=" + fila + ", idestadio=" + idestadio + " ]";
    }
    
}
