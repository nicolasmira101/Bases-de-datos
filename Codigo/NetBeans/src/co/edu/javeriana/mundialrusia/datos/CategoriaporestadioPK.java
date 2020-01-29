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
public class CategoriaporestadioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDCATEGORIA")
    private short idcategoria;
    @Basic(optional = false)
    @Column(name = "IDESTADIO")
    private short idestadio;

    public CategoriaporestadioPK() {
    }

    public CategoriaporestadioPK(short idcategoria, short idestadio) {
        this.idcategoria = idcategoria;
        this.idestadio = idestadio;
    }

    public short getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(short idcategoria) {
        this.idcategoria = idcategoria;
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
        hash += (int) idcategoria;
        hash += (int) idestadio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaporestadioPK)) {
            return false;
        }
        CategoriaporestadioPK other = (CategoriaporestadioPK) object;
        if (this.idcategoria != other.idcategoria) {
            return false;
        }
        if (this.idestadio != other.idestadio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.CategoriaporestadioPK[ idcategoria=" + idcategoria + ", idestadio=" + idestadio + " ]";
    }
    
}
