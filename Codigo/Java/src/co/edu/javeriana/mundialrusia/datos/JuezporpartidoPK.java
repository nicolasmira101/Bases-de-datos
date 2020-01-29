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
public class JuezporpartidoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDPARTIDO")
    private short idpartido;
    @Basic(optional = false)
    @Column(name = "IDJUEZ")
    private short idjuez;

    public JuezporpartidoPK() {
    }

    public JuezporpartidoPK(short idpartido, short idjuez) {
        this.idpartido = idpartido;
        this.idjuez = idjuez;
    }

    public short getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(short idpartido) {
        this.idpartido = idpartido;
    }

    public short getIdjuez() {
        return idjuez;
    }

    public void setIdjuez(short idjuez) {
        this.idjuez = idjuez;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idpartido;
        hash += (int) idjuez;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JuezporpartidoPK)) {
            return false;
        }
        JuezporpartidoPK other = (JuezporpartidoPK) object;
        if (this.idpartido != other.idpartido) {
            return false;
        }
        if (this.idjuez != other.idjuez) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.JuezporpartidoPK[ idpartido=" + idpartido + ", idjuez=" + idjuez + " ]";
    }
    
}
