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
@Table(name = "JUGADORPORPARTIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jugadorporpartido.findAll", query = "SELECT j FROM Jugadorporpartido j")
    , @NamedQuery(name = "Jugadorporpartido.findByIdpartido", query = "SELECT j FROM Jugadorporpartido j WHERE j.jugadorporpartidoPK.idpartido = :idpartido")
    , @NamedQuery(name = "Jugadorporpartido.findByIdjugador", query = "SELECT j FROM Jugadorporpartido j WHERE j.jugadorporpartidoPK.idjugador = :idjugador")
    , @NamedQuery(name = "Jugadorporpartido.findByPosicion", query = "SELECT j FROM Jugadorporpartido j WHERE j.posicion = :posicion")
    , @NamedQuery(name = "Jugadorporpartido.findByTipo", query = "SELECT j FROM Jugadorporpartido j WHERE j.tipo = :tipo")})
public class Jugadorporpartido implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JugadorporpartidoPK jugadorporpartidoPK;
    @Basic(optional = false)
    @Column(name = "POSICION")
    private String posicion;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @JoinColumn(name = "IDJUGADOR", referencedColumnName = "IDJUGADOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Jugador jugador;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partido partido;

    public Jugadorporpartido() {
    }

    public Jugadorporpartido(JugadorporpartidoPK jugadorporpartidoPK) {
        this.jugadorporpartidoPK = jugadorporpartidoPK;
    }

    public Jugadorporpartido(JugadorporpartidoPK jugadorporpartidoPK, String posicion, String tipo) {
        this.jugadorporpartidoPK = jugadorporpartidoPK;
        this.posicion = posicion;
        this.tipo = tipo;
    }

    public Jugadorporpartido(short idpartido, long idjugador) {
        this.jugadorporpartidoPK = new JugadorporpartidoPK(idpartido, idjugador);
    }

    public JugadorporpartidoPK getJugadorporpartidoPK() {
        return jugadorporpartidoPK;
    }

    public void setJugadorporpartidoPK(JugadorporpartidoPK jugadorporpartidoPK) {
        this.jugadorporpartidoPK = jugadorporpartidoPK;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
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
        hash += (jugadorporpartidoPK != null ? jugadorporpartidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jugadorporpartido)) {
            return false;
        }
        Jugadorporpartido other = (Jugadorporpartido) object;
        if ((this.jugadorporpartidoPK == null && other.jugadorporpartidoPK != null) || (this.jugadorporpartidoPK != null && !this.jugadorporpartidoPK.equals(other.jugadorporpartidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Jugadorporpartido[ jugadorporpartidoPK=" + jugadorporpartidoPK + " ]";
    }
    
}
