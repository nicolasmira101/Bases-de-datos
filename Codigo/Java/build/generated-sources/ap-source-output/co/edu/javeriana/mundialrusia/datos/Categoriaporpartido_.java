package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Categoria;
import co.edu.javeriana.mundialrusia.datos.CategoriaporpartidoPK;
import co.edu.javeriana.mundialrusia.datos.Partido;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Categoriaporpartido.class)
public class Categoriaporpartido_ { 

    public static volatile SingularAttribute<Categoriaporpartido, CategoriaporpartidoPK> categoriaporpartidoPK;
    public static volatile SingularAttribute<Categoriaporpartido, Short> precio;
    public static volatile SingularAttribute<Categoriaporpartido, Categoria> categoria;
    public static volatile SingularAttribute<Categoriaporpartido, Partido> partido;

}