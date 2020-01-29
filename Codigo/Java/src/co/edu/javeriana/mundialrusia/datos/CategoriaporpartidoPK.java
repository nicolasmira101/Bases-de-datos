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
public class CategoriaporpartidoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDCATEGORIA")
    private short idcategoria;
    @Basic(optional = false)
    @Column(name = "IDPARTIDO")
    private short idpartido;

    public CategoriaporpartidoPK() {
    }

    public CategoriaporpartidoPK(short idcategoria, short idpartido) {
        this.idcategoria = idcategoria;
        this.idpartido = idpartido;
    }

    public short getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(short idcategoria) {
        this.idcategoria = idcategoria;
    }

    public short getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(short idpartido) {
        this.idpartido = idpartido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcategoria;
        hash += (int) idpartido;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaporpartidoPK)) {
            return false;
        }
        CategoriaporpartidoPK other = (CategoriaporpartidoPK) object;
        if (this.idcategoria != other.idcategoria) {
            return false;
        }
        if (this.idpartido != other.idpartido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.CategoriaporpartidoPK[ idcategoria=" + idcategoria + ", idpartido=" + idpartido + " ]";
    }
    
}
