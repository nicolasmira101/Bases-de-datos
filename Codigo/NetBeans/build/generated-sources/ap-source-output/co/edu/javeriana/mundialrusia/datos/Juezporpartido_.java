package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Juez;
import co.edu.javeriana.mundialrusia.datos.JuezporpartidoPK;
import co.edu.javeriana.mundialrusia.datos.Partido;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Juezporpartido.class)
public class Juezporpartido_ { 

    public static volatile SingularAttribute<Juezporpartido, Juez> juez;
    public static volatile SingularAttribute<Juezporpartido, Partido> partido;
    public static volatile SingularAttribute<Juezporpartido, JuezporpartidoPK> juezporpartidoPK;
    public static volatile SingularAttribute<Juezporpartido, String> rol;

}