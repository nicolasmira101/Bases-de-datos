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
public class JugadorporpartidoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDPARTIDO")
    private short idpartido;
    @Basic(optional = false)
    @Column(name = "IDJUGADOR")
    private long idjugador;

    public JugadorporpartidoPK() {
    }

    public JugadorporpartidoPK(short idpartido, long idjugador) {
        this.idpartido = idpartido;
        this.idjugador = idjugador;
    }

    public short getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(short idpartido) {
        this.idpartido = idpartido;
    }

    public long getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(long idjugador) {
        this.idjugador = idjugador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idpartido;
        hash += (int) idjugador;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JugadorporpartidoPK)) {
            return false;
        }
        JugadorporpartidoPK other = (JugadorporpartidoPK) object;
        if (this.idpartido != other.idpartido) {
            return false;
        }
        if (this.idjugador != other.idjugador) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.JugadorporpartidoPK[ idpartido=" + idpartido + ", idjugador=" + idjugador + " ]";
    }
    
}
