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
public class ClienteporpartidoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDCLIENTE")
    private short idcliente;
    @Basic(optional = false)
    @Column(name = "IDPARTIDO")
    private short idpartido;

    public ClienteporpartidoPK() {
    }

    public ClienteporpartidoPK(short idcliente, short idpartido) {
        this.idcliente = idcliente;
        this.idpartido = idpartido;
    }

    public short getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(short idcliente) {
        this.idcliente = idcliente;
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
        hash += (int) idcliente;
        hash += (int) idpartido;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteporpartidoPK)) {
            return false;
        }
        ClienteporpartidoPK other = (ClienteporpartidoPK) object;
        if (this.idcliente != other.idcliente) {
            return false;
        }
        if (this.idpartido != other.idpartido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.ClienteporpartidoPK[ idcliente=" + idcliente + ", idpartido=" + idpartido + " ]";
    }
    
}
