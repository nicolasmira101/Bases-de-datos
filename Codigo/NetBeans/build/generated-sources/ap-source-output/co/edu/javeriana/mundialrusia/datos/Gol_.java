package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Jugador;
import co.edu.javeriana.mundialrusia.datos.Partido;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Gol.class)
public class Gol_ { 

    public static volatile SingularAttribute<Gol, String> tipo;
    public static volatile SingularAttribute<Gol, Short> minuto;
    public static volatile SingularAttribute<Gol, String> tiempo;
    public static volatile SingularAttribute<Gol, Partido> idpartido;
    public static volatile SingularAttribute<Gol, String> usovar;
    public static volatile SingularAttribute<Gol, Jugador> idjugador;
    public static volatile SingularAttribute<Gol, Short> idgol;

}