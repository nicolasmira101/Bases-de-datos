/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.datos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "TARJETA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarjeta.findAll", query = "SELECT t FROM Tarjeta t")
    , @NamedQuery(name = "Tarjeta.findByIdtarjeta", query = "SELECT t FROM Tarjeta t WHERE t.idtarjeta = :idtarjeta")
    , @NamedQuery(name = "Tarjeta.findByMinuto", query = "SELECT t FROM Tarjeta t WHERE t.minuto = :minuto")
    , @NamedQuery(name = "Tarjeta.findByTipo", query = "SELECT t FROM Tarjeta t WHERE t.tipo = :tipo")})
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTARJETA")
    private Short idtarjeta;
    @Basic(optional = false)
    @Column(name = "MINUTO")
    private short minuto;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @JoinColumn(name = "IDJUGADOR", referencedColumnName = "IDJUGADOR")
    @ManyToOne(optional = false)
    private Jugador idjugador;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO")
    @ManyToOne(optional = false)
    private Partido idpartido;

    public Tarjeta() {
    }

    public Tarjeta(Short idtarjeta) {
        this.idtarjeta = idtarjeta;
    }

    public Tarjeta(Short idtarjeta, short minuto, String tipo) {
        this.idtarjeta = idtarjeta;
        this.minuto = minuto;
        this.tipo = tipo;
    }

    public Short getIdtarjeta() {
        return idtarjeta;
    }

    public void setIdtarjeta(Short idtarjeta) {
        this.idtarjeta = idtarjeta;
    }

    public short getMinuto() {
        return minuto;
    }

    public void setMinuto(short minuto) {
        this.minuto = minuto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Jugador getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(Jugador idjugador) {
        this.idjugador = idjugador;
    }

    public Partido getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(Partido idpartido) {
        this.idpartido = idpartido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtarjeta != null ? idtarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarjeta)) {
            return false;
        }
        Tarjeta other = (Tarjeta) object;
        if ((this.idtarjeta == null && other.idtarjeta != null) || (this.idtarjeta != null && !this.idtarjeta.equals(other.idtarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Tarjeta[ idtarjeta=" + idtarjeta + " ]";
    }
    
}
