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
@Table(name = "GOL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gol.findAll", query = "SELECT g FROM Gol g")
    , @NamedQuery(name = "Gol.findByIdgol", query = "SELECT g FROM Gol g WHERE g.idgol = :idgol")
    , @NamedQuery(name = "Gol.findByMinuto", query = "SELECT g FROM Gol g WHERE g.minuto = :minuto")
    , @NamedQuery(name = "Gol.findByTiempo", query = "SELECT g FROM Gol g WHERE g.tiempo = :tiempo")
    , @NamedQuery(name = "Gol.findByTipo", query = "SELECT g FROM Gol g WHERE g.tipo = :tipo")
    , @NamedQuery(name = "Gol.findByUsovar", query = "SELECT g FROM Gol g WHERE g.usovar = :usovar")})
public class Gol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDGOL")
    private Short idgol;
    @Basic(optional = false)
    @Column(name = "MINUTO")
    private short minuto;
    @Basic(optional = false)
    @Column(name = "TIEMPO")
    private String tiempo;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "USOVAR")
    private String usovar;
    @JoinColumn(name = "IDJUGADOR", referencedColumnName = "IDJUGADOR")
    @ManyToOne(optional = false)
    private Jugador idjugador;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO")
    @ManyToOne(optional = false)
    private Partido idpartido;

    public Gol() {
    }

    public Gol(Short idgol) {
        this.idgol = idgol;
    }

    public Gol(Short idgol, short minuto, String tiempo, String tipo, String usovar) {
        this.idgol = idgol;
        this.minuto = minuto;
        this.tiempo = tiempo;
        this.tipo = tipo;
        this.usovar = usovar;
    }

    public Short getIdgol() {
        return idgol;
    }

    public void setIdgol(Short idgol) {
        this.idgol = idgol;
    }

    public short getMinuto() {
        return minuto;
    }

    public void setMinuto(short minuto) {
        this.minuto = minuto;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsovar() {
        return usovar;
    }

    public void setUsovar(String usovar) {
        this.usovar = usovar;
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
        hash += (idgol != null ? idgol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gol)) {
            return false;
        }
        Gol other = (Gol) object;
        if ((this.idgol == null && other.idgol != null) || (this.idgol != null && !this.idgol.equals(other.idgol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Gol[ idgol=" + idgol + " ]";
    }
    
}
