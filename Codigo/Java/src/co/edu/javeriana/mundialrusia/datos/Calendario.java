/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.datos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nicolasmiranda
 */
@Entity
@Table(name = "CALENDARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calendario.findAll", query = "SELECT c FROM Calendario c")
    , @NamedQuery(name = "Calendario.findByIdcalendario", query = "SELECT c FROM Calendario c WHERE c.idcalendario = :idcalendario")
    , @NamedQuery(name = "Calendario.findByFase", query = "SELECT c FROM Calendario c WHERE c.fase = :fase")})
public class Calendario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCALENDARIO")
    private Short idcalendario;
    @Basic(optional = false)
    @Column(name = "FASE")
    private String fase;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcalendario")
    private List<Partido> partidoList;

    public Calendario() {
    }

    public Calendario(Short idcalendario) {
        this.idcalendario = idcalendario;
    }

    public Calendario(Short idcalendario, String fase) {
        this.idcalendario = idcalendario;
        this.fase = fase;
    }

    public Short getIdcalendario() {
        return idcalendario;
    }

    public void setIdcalendario(Short idcalendario) {
        this.idcalendario = idcalendario;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    @XmlTransient
    public List<Partido> getPartidoList() {
        return partidoList;
    }

    public void setPartidoList(List<Partido> partidoList) {
        this.partidoList = partidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcalendario != null ? idcalendario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calendario)) {
            return false;
        }
        Calendario other = (Calendario) object;
        if ((this.idcalendario == null && other.idcalendario != null) || (this.idcalendario != null && !this.idcalendario.equals(other.idcalendario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Calendario[ idcalendario=" + idcalendario + " ]";
    }
    
}
