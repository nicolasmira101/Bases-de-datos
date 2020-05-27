/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.datos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nicolasmiranda
 */
@Entity
@Table(name = "CLUB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Club.findAll", query = "SELECT c FROM Club c")
    , @NamedQuery(name = "Club.findByIdclub", query = "SELECT c FROM Club c WHERE c.idclub = :idclub")
    , @NamedQuery(name = "Club.findByNombre", query = "SELECT c FROM Club c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Club.findByEntrenador", query = "SELECT c FROM Club c WHERE c.entrenador = :entrenador")
    , @NamedQuery(name = "Club.findByEstadio", query = "SELECT c FROM Club c WHERE c.estadio = :estadio")
    , @NamedQuery(name = "Club.findByFundacion", query = "SELECT c FROM Club c WHERE c.fundacion = :fundacion")})
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCLUB")
    private Short idclub;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "ENTRENADOR")
    private String entrenador;
    @Basic(optional = false)
    @Column(name = "ESTADIO")
    private String estadio;
    @Basic(optional = false)
    @Column(name = "FUNDACION")
    private short fundacion;
    @JoinTable(name = "CLUBESPORJUGADOR", joinColumns = {
        @JoinColumn(name = "IDCLUB", referencedColumnName = "IDCLUB")}, inverseJoinColumns = {
        @JoinColumn(name = "IDJUGADOR", referencedColumnName = "IDJUGADOR")})
    @ManyToMany
    private List<Jugador> jugadorList;

    public Club() {
    }

    public Club(Short idclub) {
        this.idclub = idclub;
    }

    public Club(Short idclub, String nombre, String entrenador, String estadio, short fundacion) {
        this.idclub = idclub;
        this.nombre = nombre;
        this.entrenador = entrenador;
        this.estadio = estadio;
        this.fundacion = fundacion;
    }

    public Short getIdclub() {
        return idclub;
    }

    public void setIdclub(Short idclub) {
        this.idclub = idclub;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public short getFundacion() {
        return fundacion;
    }

    public void setFundacion(short fundacion) {
        this.fundacion = fundacion;
    }

    @XmlTransient
    public List<Jugador> getJugadorList() {
        return jugadorList;
    }

    public void setJugadorList(List<Jugador> jugadorList) {
        this.jugadorList = jugadorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idclub != null ? idclub.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Club)) {
            return false;
        }
        Club other = (Club) object;
        if ((this.idclub == null && other.idclub != null) || (this.idclub != null && !this.idclub.equals(other.idclub))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Club[ idclub=" + idclub + " ]";
    }
    
}
