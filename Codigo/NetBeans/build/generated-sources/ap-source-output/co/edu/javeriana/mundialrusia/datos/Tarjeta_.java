package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Jugador;
import co.edu.javeriana.mundialrusia.datos.Partido;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Tarjeta.class)
public class Tarjeta_ { 

    public static volatile SingularAttribute<Tarjeta, String> tipo;
    public static volatile SingularAttribute<Tarjeta, Short> minuto;
    public static volatile SingularAttribute<Tarjeta, Partido> idpartido;
    public static volatile SingularAttribute<Tarjeta, Short> idtarjeta;
    public static volatile SingularAttribute<Tarjeta, Jugador> idjugador;

}