package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Jugador;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Pais.class)
public class Pais_ { 

    public static volatile SingularAttribute<Pais, Integer> numerohabitantes;
    public static volatile ListAttribute<Pais, Jugador> jugadorList;
    public static volatile SingularAttribute<Pais, Short> idpais;
    public static volatile SingularAttribute<Pais, String> nombre;

}