package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Categoria;
import co.edu.javeriana.mundialrusia.datos.Partido;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Estadio.class)
public class Estadio_ { 

    public static volatile SingularAttribute<Estadio, String> sede;
    public static volatile ListAttribute<Estadio, Partido> partidoList;
    public static volatile SingularAttribute<Estadio, String> nombre;
    public static volatile SingularAttribute<Estadio, Short> idestadio;
    public static volatile ListAttribute<Estadio, Categoria> categoriaList;
    public static volatile SingularAttribute<Estadio, Integer> capacidad;

}