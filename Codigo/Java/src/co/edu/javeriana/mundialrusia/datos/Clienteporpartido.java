/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.datos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nicolasmiranda
 */
@Entity
@Table(name = "CLIENTEPORPARTIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clienteporpartido.findAll", query = "SELECT c FROM Clienteporpartido c")
    , @NamedQuery(name = "Clienteporpartido.findByIdcliente", query = "SELECT c FROM Clienteporpartido c WHERE c.clienteporpartidoPK.idcliente = :idcliente")
    , @NamedQuery(name = "Clienteporpartido.findByIdpartido", query = "SELECT c FROM Clienteporpartido c WHERE c.clienteporpartidoPK.idpartido = :idpartido")
    , @NamedQuery(name = "Clienteporpartido.findByMetodopago", query = "SELECT c FROM Clienteporpartido c WHERE c.metodopago = :metodopago")})
public class Clienteporpartido implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClienteporpartidoPK clienteporpartidoPK;
    @Basic(optional = false)
    @Column(name = "METODOPAGO")
    private String metodopago;
    @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partido partido;

    public Clienteporpartido() {
    }

    public Clienteporpartido(ClienteporpartidoPK clienteporpartidoPK) {
        this.clienteporpartidoPK = clienteporpartidoPK;
    }

    public Clienteporpartido(ClienteporpartidoPK clienteporpartidoPK, String metodopago) {
        this.clienteporpartidoPK = clienteporpartidoPK;
        this.metodopago = metodopago;
    }

    public Clienteporpartido(short idcliente, short idpartido) {
        this.clienteporpartidoPK = new ClienteporpartidoPK(idcliente, idpartido);
    }

    public ClienteporpartidoPK getClienteporpartidoPK() {
        return clienteporpartidoPK;
    }

    public void setClienteporpartidoPK(ClienteporpartidoPK clienteporpartidoPK) {
        this.clienteporpartidoPK = clienteporpartidoPK;
    }

    public String getMetodopago() {
        return metodopago;
    }

    public void setMetodopago(String metodopago) {
        this.metodopago = metodopago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteporpartidoPK != null ? clienteporpartidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clienteporpartido)) {
            return false;
        }
        Clienteporpartido other = (Clienteporpartido) object;
        if ((this.clienteporpartidoPK == null && other.clienteporpartidoPK != null) || (this.clienteporpartidoPK != null && !this.clienteporpartidoPK.equals(other.clienteporpartidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Clienteporpartido[ clienteporpartidoPK=" + clienteporpartidoPK + " ]";
    }
    
}
