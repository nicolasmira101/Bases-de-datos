/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.datos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nicolasmiranda
 */
@Entity
@Table(name = "JUGADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j")
    , @NamedQuery(name = "Jugador.findByIdjugador", query = "SELECT j FROM Jugador j WHERE j.idjugador = :idjugador")
    , @NamedQuery(name = "Jugador.findByNombre", query = "SELECT j FROM Jugador j WHERE j.nombre = :nombre")
    , @NamedQuery(name = "Jugador.findByApellido", query = "SELECT j FROM Jugador j WHERE j.apellido = :apellido")
    , @NamedQuery(name = "Jugador.findByFechanacimiento", query = "SELECT j FROM Jugador j WHERE j.fechanacimiento = :fechanacimiento")
    , @NamedQuery(name = "Jugador.findByEstatura", query = "SELECT j FROM Jugador j WHERE j.estatura = :estatura")
    , @NamedQuery(name = "Jugador.findByPeso", query = "SELECT j FROM Jugador j WHERE j.peso = :peso")
    , @NamedQuery(name = "Jugador.findByNumero", query = "SELECT j FROM Jugador j WHERE j.numero = :numero")})
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDJUGADOR")
    private Long idjugador;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "FECHANACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechanacimiento;
    @Basic(optional = false)
    @Column(name = "ESTATURA")
    private short estatura;
    @Basic(optional = false)
    @Column(name = "PESO")
    private short peso;
    @Basic(optional = false)
    @Column(name = "NUMERO")
    private short numero;
    @Lob
    @Column(name = "FOTO")
    private byte[] foto;
    @ManyToMany(mappedBy = "jugadorList")
    private List<Club> clubList;
    @JoinColumn(name = "IDEQUIPO", referencedColumnName = "IDEQUIPO")
    @ManyToOne(optional = false)
    private Equipo idequipo;
    @JoinColumn(name = "IDPAIS", referencedColumnName = "IDPAIS")
    @ManyToOne(optional = false)
    private Pais idpais;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idjugador")
    private List<Gol> golList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idjugador")
    private List<Tarjeta> tarjetaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jugador")
    private List<Jugadorporpartido> jugadorporpartidoList;

    public Jugador() {
    }

    public Jugador(Long idjugador) {
        this.idjugador = idjugador;
    }

    public Jugador(Long idjugador, String nombre, String apellido, Date fechanacimiento, short estatura, short peso, short numero) {
        this.idjugador = idjugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechanacimiento = fechanacimiento;
        this.estatura = estatura;
        this.peso = peso;
        this.numero = numero;
    }

    public Long getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(Long idjugador) {
        this.idjugador = idjugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public short getEstatura() {
        return estatura;
    }

    public void setEstatura(short estatura) {
        this.estatura = estatura;
    }

    public short getPeso() {
        return peso;
    }

    public void setPeso(short peso) {
        this.peso = peso;
    }

    public short getNumero() {
        return numero;
    }

    public void setNumero(short numero) {
        this.numero = numero;
    }

    public Serializable getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @XmlTransient
    public List<Club> getClubList() {
        return clubList;
    }

    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }

    public Equipo getIdequipo() {
        return idequipo;
    }

    public void setIdequipo(Equipo idequipo) {
        this.idequipo = idequipo;
    }

    public Pais getIdpais() {
        return idpais;
    }

    public void setIdpais(Pais idpais) {
        this.idpais = idpais;
    }

    @XmlTransient
    public List<Gol> getGolList() {
        return golList;
    }

    public void setGolList(List<Gol> golList) {
        this.golList = golList;
    }

    @XmlTransient
    public List<Tarjeta> getTarjetaList() {
        return tarjetaList;
    }

    public void setTarjetaList(List<Tarjeta> tarjetaList) {
        this.tarjetaList = tarjetaList;
    }

    @XmlTransient
    public List<Jugadorporpartido> getJugadorporpartidoList() {
        return jugadorporpartidoList;
    }

    public void setJugadorporpartidoList(List<Jugadorporpartido> jugadorporpartidoList) {
        this.jugadorporpartidoList = jugadorporpartidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idjugador != null ? idjugador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.idjugador == null && other.idjugador != null) || (this.idjugador != null && !this.idjugador.equals(other.idjugador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Jugador[ idjugador=" + idjugador + " ]";
    }
    
}
