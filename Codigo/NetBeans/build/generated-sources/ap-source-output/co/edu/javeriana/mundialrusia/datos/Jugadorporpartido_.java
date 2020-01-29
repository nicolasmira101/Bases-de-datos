package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Jugador;
import co.edu.javeriana.mundialrusia.datos.JugadorporpartidoPK;
import co.edu.javeriana.mundialrusia.datos.Partido;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Jugadorporpartido.class)
public class Jugadorporpartido_ { 

    public static volatile SingularAttribute<Jugadorporpartido, String> posicion;
    public static volatile SingularAttribute<Jugadorporpartido, String> tipo;
    public static volatile SingularAttribute<Jugadorporpartido, Jugador> jugador;
    public static volatile SingularAttribute<Jugadorporpartido, JugadorporpartidoPK> jugadorporpartidoPK;
    public static volatile SingularAttribute<Jugadorporpartido, Partido> partido;

}