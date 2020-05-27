DROP TABLE calendario CASCADE CONSTRAINTS;

DROP TABLE categoria CASCADE CONSTRAINTS;

DROP TABLE categoriaporestadio CASCADE CONSTRAINTS;

DROP TABLE categoriaporpartido CASCADE CONSTRAINTS;

DROP TABLE cliente CASCADE CONSTRAINTS;

DROP TABLE clienteporpartido CASCADE CONSTRAINTS;

DROP TABLE club CASCADE CONSTRAINTS;

DROP TABLE clubesporjugador CASCADE CONSTRAINTS;

DROP TABLE directortecnico CASCADE CONSTRAINTS;

DROP TABLE equipo CASCADE CONSTRAINTS;

DROP TABLE estadio CASCADE CONSTRAINTS;

DROP TABLE gol CASCADE CONSTRAINTS;

DROP TABLE juez CASCADE CONSTRAINTS;

DROP TABLE juezporpartido CASCADE CONSTRAINTS;

DROP TABLE jugador CASCADE CONSTRAINTS;

DROP TABLE jugadorporpartido CASCADE CONSTRAINTS;

DROP TABLE pais CASCADE CONSTRAINTS;

DROP TABLE partido CASCADE CONSTRAINTS;

DROP TABLE sillaporestadio CASCADE CONSTRAINTS;

DROP TABLE silla CASCADE CONSTRAINTS;

DROP TABLE tarjeta CASCADE CONSTRAINTS;

CREATE TABLE calendario (
    idcalendario   NUMBER(2) NOT NULL,
    fase           VARCHAR2(20) NOT NULL
);

ALTER TABLE calendario ADD CONSTRAINT calendario_pk PRIMARY KEY ( idcalendario );

CREATE TABLE categoria (
    idcategoria   NUMBER(1) NOT NULL
);

ALTER TABLE categoria ADD CONSTRAINT categoria_pk PRIMARY KEY ( idcategoria );

CREATE TABLE categoriaporestadio (
    idcategoria   NUMBER(1) NOT NULL,
    idestadio     NUMBER(3) NOT NULL,
    ejemplo       VARCHAR2(2)
);

ALTER TABLE categoriaporestadio ADD CONSTRAINT categoriaporestadio_pk PRIMARY KEY ( idcategoria,
                                                                                    idestadio );

CREATE TABLE categoriaporpartido (
    idcategoria   NUMBER(1) NOT NULL,
    idpartido     NUMBER(2) NOT NULL,
    precio        NUMBER(4) NOT NULL
);

ALTER TABLE categoriaporpartido ADD CONSTRAINT categoriaporpartido_pk PRIMARY KEY ( idcategoria,
                                                                                    idpartido );

CREATE TABLE cliente (
    idcliente   NUMBER(3) NOT NULL,
    nombre      VARCHAR2(20) NOT NULL,
    apellido    VARCHAR2(20) NOT NULL,
    pasaporte   NUMBER(4) NOT NULL,
    correo      VARCHAR2(30) NOT NULL,
    direccion   VARCHAR2(30) NOT NULL,
    saldo       NUMBER(4) NOT NULL
);

ALTER TABLE cliente ADD CONSTRAINT cliente_pk PRIMARY KEY ( idcliente );

CREATE TABLE clienteporpartido (
    idcliente    NUMBER(3) NOT NULL,
    idpartido    NUMBER(2) NOT NULL,
    metodopago   VARCHAR2(20) NOT NULL
);

ALTER TABLE clienteporpartido ADD CONSTRAINT clienteporpartido_pk PRIMARY KEY ( idcliente,
                                                                                idpartido );

CREATE TABLE club (
    idclub       NUMBER(3) NOT NULL,
    nombre       VARCHAR2(20) NOT NULL,
    entrenador   VARCHAR2(30) NOT NULL,
    estadio      VARCHAR2(30) NOT NULL,
    fundacion    NUMBER(4) NOT NULL
);

ALTER TABLE club ADD CONSTRAINT club_pk PRIMARY KEY ( idclub );

CREATE TABLE clubesporjugador (
    idjugador   NUMBER(10) NOT NULL,
    idclub      NUMBER(3) NOT NULL
);

ALTER TABLE clubesporjugador ADD CONSTRAINT clubesporjugador_pk PRIMARY KEY ( idjugador,
                                                                              idclub );

CREATE TABLE directortecnico (
    iddirectortecnico   NUMBER(2) NOT NULL,
    nombre              VARCHAR2(30) NOT NULL,
    apellido            VARCHAR2(30) NOT NULL,
    cargo               VARCHAR2(20) NOT NULL
);

ALTER TABLE directortecnico ADD CONSTRAINT directortecnico_pk PRIMARY KEY ( iddirectortecnico );

CREATE TABLE equipo (
    idequipo             NUMBER(2) NOT NULL,
    nombre               VARCHAR2(20) NOT NULL,
    confederacion        VARCHAR2(20) NOT NULL,
    iddirectortecnico    NUMBER(2) NOT NULL,
    idasistentetecnico   NUMBER(2) NOT NULL,
    grupo                VARCHAR2(1) NOT NULL
);

ALTER TABLE equipo ADD CONSTRAINT equipo_pk PRIMARY KEY ( idequipo );

CREATE TABLE estadio (
    idestadio   NUMBER(3) NOT NULL,
    nombre      VARCHAR2(30) NOT NULL,
    sede        VARCHAR2(20) NOT NULL,
    capacidad   NUMBER(5) NOT NULL
);

ALTER TABLE estadio ADD CONSTRAINT estadio_pk PRIMARY KEY ( idestadio );

CREATE TABLE gol (
    idgol       NUMBER(3) NOT NULL,
    minuto      NUMBER(3) NOT NULL,
    tiempo      VARCHAR2(30) NOT NULL,
    tipo        VARCHAR2(20) NOT NULL,
    usovar      VARCHAR2(2) NOT NULL,
    idjugador   NUMBER(10) NOT NULL,
    idpartido   NUMBER(2) NOT NULL
);

ALTER TABLE gol ADD CONSTRAINT gol_pk PRIMARY KEY ( idgol );

CREATE TABLE juez (
    idjuez         NUMBER(2) NOT NULL,
    nombre         VARCHAR2(30) NOT NULL,
    apellido       VARCHAR2(30) NOT NULL,
    nacionalidad   VARCHAR2(20) NOT NULL
);

ALTER TABLE juez ADD CONSTRAINT juez_pk PRIMARY KEY ( idjuez );

CREATE TABLE juezporpartido (
    idpartido   NUMBER(2) NOT NULL,
    idjuez      NUMBER(2) NOT NULL,
    rol         VARCHAR2(20) NOT NULL
);

ALTER TABLE juezporpartido ADD CONSTRAINT juezporpartido_pk PRIMARY KEY ( idpartido,
                                                                          idjuez );

CREATE TABLE jugador (
    idjugador         NUMBER(10) NOT NULL,
    nombre            VARCHAR2(30) NOT NULL,
    apellido          VARCHAR2(20) NOT NULL,
    fechanacimiento   DATE NOT NULL,
    estatura          NUMBER(3) NOT NULL,
    peso              NUMBER(3) NOT NULL,
    numero            NUMBER(2) NOT NULL,
    foto              BLOB DEFAULT empty_blob(),
    idequipo          NUMBER(2) NOT NULL,
    idpais            NUMBER(3) NOT NULL
);

ALTER TABLE jugador ADD CONSTRAINT jugador_pk PRIMARY KEY ( idjugador );

CREATE TABLE jugadorporpartido (
    idpartido   NUMBER(2) NOT NULL,
    idjugador   NUMBER(10) NOT NULL,
    posicion    VARCHAR2(20) NOT NULL,
    tipo        VARCHAR2(10) NOT NULL
);

ALTER TABLE jugadorporpartido ADD CONSTRAINT jugadorporpartido_pk PRIMARY KEY ( idpartido,
                                                                                idjugador );

CREATE TABLE pais (
    idpais             NUMBER(3) NOT NULL,
    nombre             VARCHAR2(20) NOT NULL,
    numerohabitantes   NUMBER(9) NOT NULL
);

ALTER TABLE pais ADD CONSTRAINT pais_pk PRIMARY KEY ( idpais );

CREATE TABLE partido (
    idpartido           NUMBER(2) NOT NULL,
    fecha               DATE NOT NULL,
    idequipolocal       NUMBER(2) NOT NULL,
    idequipovisitante   NUMBER(2) NOT NULL,
    idcalendario        NUMBER(2) NOT NULL,
    idestadio           NUMBER(3) NOT NULL
);

ALTER TABLE partido ADD CONSTRAINT partido_pk PRIMARY KEY ( idpartido );

CREATE TABLE silla (
    idsilla       NUMBER(2) NOT NULL,
    fila          VARCHAR2(1) NOT NULL,
    disponible    VARCHAR2(2) NOT NULL,
    idcategoria   NUMBER(1) NOT NULL
);

ALTER TABLE silla ADD CONSTRAINT silla_pk PRIMARY KEY ( idsilla,
                                                        fila );

CREATE TABLE sillaporestadio (
    idsilla     NUMBER(2) NOT NULL,
    fila        VARCHAR2(1) NOT NULL,
    idestadio   NUMBER(3) NOT NULL,
    ejemplo     VARCHAR2(2)
);

ALTER TABLE sillaporestadio ADD CONSTRAINT sillaporestadio_pk PRIMARY KEY ( idsilla,
                                                                            fila,
                                                                            idestadio );

CREATE TABLE tarjeta (
    idtarjeta   NUMBER(3) NOT NULL,
    minuto      NUMBER(2) NOT NULL,
    tipo        VARCHAR2(10) NOT NULL,
    idpartido   NUMBER(2) NOT NULL,
    idjugador   NUMBER(10) NOT NULL
);

ALTER TABLE tarjeta ADD CONSTRAINT tarjeta_pk PRIMARY KEY ( idtarjeta );

ALTER TABLE categoriaporestadio
    ADD CONSTRAINT categoriaporestadio_categoria_fk FOREIGN KEY ( idcategoria )
        REFERENCES categoria ( idcategoria );

ALTER TABLE categoriaporestadio
    ADD CONSTRAINT categoriaporestadio_estadio_fk FOREIGN KEY ( idestadio )
        REFERENCES estadio ( idestadio );

ALTER TABLE categoriaporpartido
    ADD CONSTRAINT categoriaporpartido_categoria_fk FOREIGN KEY ( idcategoria )
        REFERENCES categoria ( idcategoria );

ALTER TABLE categoriaporpartido
    ADD CONSTRAINT categoriaporpartido_partido_fk FOREIGN KEY ( idpartido )
        REFERENCES partido ( idpartido );

ALTER TABLE clienteporpartido
    ADD CONSTRAINT clienteporpartido_cliente_fk FOREIGN KEY ( idcliente )
        REFERENCES cliente ( idcliente );

ALTER TABLE clienteporpartido
    ADD CONSTRAINT clienteporpartido_partido_fk FOREIGN KEY ( idpartido )
        REFERENCES partido ( idpartido );

ALTER TABLE clubesporjugador
    ADD CONSTRAINT clubesporjugador_club_fk FOREIGN KEY ( idclub )
        REFERENCES club ( idclub );

ALTER TABLE clubesporjugador
    ADD CONSTRAINT clubesporjugador_jugador_fk FOREIGN KEY ( idjugador )
        REFERENCES jugador ( idjugador );

ALTER TABLE equipo
    ADD CONSTRAINT equipo_directortecnico_fk FOREIGN KEY ( idasistentetecnico )
        REFERENCES directortecnico ( iddirectortecnico );

ALTER TABLE equipo
    ADD CONSTRAINT equipo_directortecnico_fkv2 FOREIGN KEY ( iddirectortecnico )
        REFERENCES directortecnico ( iddirectortecnico );

ALTER TABLE gol
    ADD CONSTRAINT gol_jugador_fk FOREIGN KEY ( idjugador )
        REFERENCES jugador ( idjugador );

ALTER TABLE gol
    ADD CONSTRAINT gol_partido_fk FOREIGN KEY ( idpartido )
        REFERENCES partido ( idpartido );

ALTER TABLE juezporpartido
    ADD CONSTRAINT juezporpartido_juez_fk FOREIGN KEY ( idjuez )
        REFERENCES juez ( idjuez );

ALTER TABLE juezporpartido
    ADD CONSTRAINT juezporpartido_partido_fk FOREIGN KEY ( idpartido )
        REFERENCES partido ( idpartido );

ALTER TABLE jugador
    ADD CONSTRAINT jugador_equipo_fk FOREIGN KEY ( idequipo )
        REFERENCES equipo ( idequipo );

ALTER TABLE jugador
    ADD CONSTRAINT jugador_pais_fk FOREIGN KEY ( idpais )
        REFERENCES pais ( idpais );

ALTER TABLE jugadorporpartido
    ADD CONSTRAINT jugadorporpartido_jugador_fk FOREIGN KEY ( idjugador )
        REFERENCES jugador ( idjugador );

ALTER TABLE jugadorporpartido
    ADD CONSTRAINT jugadorporpartido_partido_fk FOREIGN KEY ( idpartido )
        REFERENCES partido ( idpartido );

ALTER TABLE partido
    ADD CONSTRAINT partido_calendario_fk FOREIGN KEY ( idcalendario )
        REFERENCES calendario ( idcalendario );

ALTER TABLE partido
    ADD CONSTRAINT partido_equipo_fk FOREIGN KEY ( idequipovisitante )
        REFERENCES equipo ( idequipo );

ALTER TABLE partido
    ADD CONSTRAINT partido_equipo_fkv2 FOREIGN KEY ( idequipolocal )
        REFERENCES equipo ( idequipo );

ALTER TABLE partido
    ADD CONSTRAINT partido_estadio_fk FOREIGN KEY ( idestadio )
        REFERENCES estadio ( idestadio );

ALTER TABLE silla
    ADD CONSTRAINT silla_categoria_fk FOREIGN KEY ( idcategoria )
        REFERENCES categoria ( idcategoria );

ALTER TABLE tarjeta
    ADD CONSTRAINT tarjeta_jugador_fk FOREIGN KEY ( idjugador )
        REFERENCES jugador ( idjugador );

ALTER TABLE tarjeta
    ADD CONSTRAINT tarjeta_partido_fk FOREIGN KEY ( idpartido )
        REFERENCES partido ( idpartido );

ALTER TABLE sillaporestadio
    ADD CONSTRAINT sillaporestadio_silla_fk FOREIGN KEY ( idsilla, fila )
        REFERENCES silla ( idsilla, fila );

ALTER TABLE sillaporestadio
    ADD CONSTRAINT sillaporestadio_estadio_fk FOREIGN KEY ( idestadio )
        REFERENCES estadio ( idestadio );       
        

--------------------------------------------------------------------------------------------------------------------------------        

INSERT INTO cliente VALUES (
    1,
    'Juan',
    'Perez',
    1231,
    'jprez@gmail.com',
    'callesiempreviva123',
    1200
);

INSERT INTO cliente VALUES (
    2,
    'Juana',
    'Gutierrez',
    4891,
    'jgut@gmail.com',
    'elmstreet234',
    800
);

INSERT INTO cliente VALUES (
    3,
    'Laura',
    'Lopez',
    09472,
    'llop@gmail.com',
    'howarts547',
    900
);

INSERT INTO cliente VALUES (
    4,
    'Camilo',
    'Barahona',
    6117,
    'cbar@gmail.com',
    'avshire923',
    736
);

INSERT INTO cliente VALUES (
    5,
    'Sebastian',
    'Gama',
    7487,
    'sgam@gmail.com',
    'av14124',
    777
);

INSERT INTO cliente VALUES (
    6,
    'Daniela',
    'Garrido',
    1867,
    'dgar@gmail.com',
    'calle65675',
    2100
);

INSERT INTO cliente VALUES (
    7,
    'Alfonso',
    'Gomez',
    3478,
    'agom@gmail.com',
    'oxfordavenue',
    999
);

INSERT INTO cliente VALUES (
    8,
    'Diego',
    'Ubaque',
    7512,
    'duba@gmail.com',
    'orientañavenue',
    600
);

INSERT INTO cliente VALUES (
    9,
    'Leticia',
    'Huertas',
    4454,
    'lhue@gmail.com',
    'spoonerstreet',
    918
);

INSERT INTO cliente VALUES (
    10,
    'Margarita',
    'Casas',
    6756,
    'mcas@gmail.com',
    'belfast565',
    899
);

COMMIT;

INSERT INTO directortecnico VALUES (
    1,
    'Jose',
    'Pekerman',
    'Principal'
);

INSERT INTO directortecnico VALUES (
    2,
    'Eduardo',
    'Urtasun',
    'Auxiliar'
);

INSERT INTO directortecnico VALUES (
    3,
    'Adam',
    'Nawalka',
    'Principal'
);

INSERT INTO directortecnico VALUES (
    4,
    'Waldemar',
    'Fornalik',
    'Auxiliar'
);

INSERT INTO directortecnico VALUES (
    5,
    'Aliou',
    'Cisse',
    'Principal'
);

INSERT INTO directortecnico VALUES (
    6,
    'Khalilou',
    'Fadiga',
    'Auxiliar'
);

INSERT INTO directortecnico VALUES (
    7,
    'Vahid',
    'Halilhodzic',
    'Principal'
);

INSERT INTO directortecnico VALUES (
    8,
    'Hiromi',
    'Hara',
    'Auxiliar'
);

INSERT INTO directortecnico VALUES (
    9,
    'Roberto',
    'Montoliu',
    'Principal'
);

INSERT INTO directortecnico VALUES (
    10,
    'Marc',
    'Wilmots',
    'Auxiliar'
);

INSERT INTO directortecnico VALUES (
    11,
    'Hernan',
    'Gomez',
    'Principal'
);

INSERT INTO directortecnico VALUES (
    12,
    'Victor',
    'Mendieta',
    'Auxiliar'
);

INSERT INTO directortecnico VALUES (
    13,
    'Nabil',
    'Maaloul',
    'Principal'
);

INSERT INTO directortecnico VALUES (
    14,
    'Sami',
    'Trabelsi',
    'Auxiliar'
);

INSERT INTO directortecnico VALUES (
    15,
    'Sam',
    'Allardyce',
    'Principal'
);

INSERT INTO directortecnico VALUES (
    16,
    'Eddie',
    'Howe',
    'Auxiliar'
);

COMMIT;

INSERT INTO juez VALUES (
    1,
    'Clement',
    'Turpin',
    'Frances'
);

INSERT INTO juez VALUES (
    2,
    'Felix',
    'Brych',
    'Aleman'
);

INSERT INTO juez VALUES (
    3,
    'Szymon',
    'Marciniak',
    'Polaco'
);

INSERT INTO juez VALUES (
    4,
    'Wilmar',
    'Roldan',
    'Colombiano'
);

INSERT INTO juez VALUES (
    5,
    'Cunha',
    'Andres',
    'Uruguayo'
);

INSERT INTO juez VALUES (
    6,
    'Cesar',
    'Ramos',
    'Mexicano'
);

INSERT INTO juez VALUES (
    7,
    'Piiti',
    'John',
    'Panameño'
);

INSERT INTO juez VALUES (
    8,
    'Norbert',
    'Hauata',
    'Tailandes'
);

INSERT INTO juez VALUES (
    9,
    'Grisha',
    'Ghead',
    'Egipcio'
);

INSERT INTO juez VALUES (
    10,
    'Tessema',
    'Bamlak',
    'Etiope'
);

INSERT INTO juez VALUES (
    11,
    'Sato',
    'Ryuji',
    'Japones'
);

INSERT INTO juez VALUES (
    12,
    'Faghani',
    'Alireza',
    'Irani'
);

COMMIT;

INSERT INTO equipo VALUES (
    1,
    'Colombia',
    'CONMEBOL',
    1,
    2,
    'H'
);

INSERT INTO equipo VALUES (
    2,
    'Polonia',
    'UEFA',
    3,
    4,
    'H'
);

INSERT INTO equipo VALUES (
    3,
    'Senegal',
    'CAF',
    5,
    6,
    'H'
);

INSERT INTO equipo VALUES (
    4,
    'Japon',
    'AFC',
    7,
    8,
    'H'
);

INSERT INTO equipo VALUES (
    5,
    'Belgica',
    'UEFA',
    9,
    10,
    'H'
);

INSERT INTO equipo VALUES (
    6,
    'Panama',
    'CONCACAF',
    11,
    12,
    'H'
);

INSERT INTO equipo VALUES (
    7,
    'Tunez',
    'CAF',
    13,
    14,
    'H'
);

INSERT INTO equipo VALUES (
    8,
    'Inglaterra',
    'UEFA',
    15,
    16,
    'H'
);

COMMIT;

INSERT INTO club VALUES (
    1,
    'Atletico Nacional',
    'Jorge Almiron',
    'Atanasio Giradot',
    1947
);

INSERT INTO club VALUES (
    2,
    'Arsenal',
    'Arsene Wenger',
    'Emirates Stadium',
    1886
);

INSERT INTO club VALUES (
    3,
    'Santa Fe',
    'Gregorio Perez',
    'El Campin',
    1941
);

INSERT INTO club VALUES (
    4,
    'Deportivo Cali',
    'Gerardo Pelusso',
    'Estadio Cali',
    1912
);

INSERT INTO club VALUES (
    5,
    'Millonarios',
    'Miguel Russo',
    'El Campin',
    1946
);

INSERT INTO club VALUES (
    6,
    'Palmeiras',
    'Roger Machado',
    'Allianz Parque',
    1914
);

INSERT INTO club VALUES (
    7,
    'A. C. Milan',
    'Gennaro Gattuso',
    'San Siro',
    1899
);

INSERT INTO club VALUES (
    8,
    'Ajax',
    'Erik Ten Hag',
    'Amsterdam Arena',
    1900
);

INSERT INTO club VALUES (
    9,
    'Pachuca',
    'Diego Alonso',
    'Hidalgo',
    1892
);

INSERT INTO club VALUES (
    10,
    'Boca Juniors',
    'Guillermo Barros',
    'Alberto Armando',
    1905
);

INSERT INTO club VALUES (
    11,
    'Girona FC',
    'Pablo Machin',
    'Municipal de Montilivi',
    1930
);

INSERT INTO club VALUES (
    12,
    'PSV Eindhoven',
    'Philip Cocu',
    'Philips Stadion',
    1913
);

INSERT INTO club VALUES (
    13,
    'Aston Villa',
    'Steve Bruce',
    'Villa Park',
    1874
);

INSERT INTO club VALUES (
    14,
    'ACF Fiorentina',
    'Stefano Pioli',
    'Artemio Franchi',
    1926
);

INSERT INTO club VALUES (
    15,
    'Deportes Tolima',
    'Alberto Gamero',
    'Manuel Murillo',
    1954
);

INSERT INTO club VALUES (
    16,
    'Atletico Huila',
    'Nestor Craviotto',
    'Guillermo Plazas',
    1990
);

INSERT INTO club VALUES (
    17,
    'Llevant U. E',
    'Paco Lopez',
    'Ciutat de Valencia',
    1909
);

INSERT INTO club VALUES (
    18,
    'Udinese',
    'Mssimo Oddo',
    'Stadio Friuli',
    1896
);

INSERT INTO club VALUES (
    19,
    'Club America',
    'Miguel Herrera',
    'Azteca',
    1916
);

INSERT INTO club VALUES (
    20,
    'Juventus F. C.',
    'Massimiliano Allegri',
    'Allianz Studium',
    1897
);

INSERT INTO club VALUES (
    21,
    'Bayern de Munich',
    'Jupp Heynckes',
    'Allianz Arena',
    1900
);

INSERT INTO club VALUES (
    22,
    'Real Madrid',
    'Zinedine Zidane',
    'Santiago Bernabeu',
    1902
);

INSERT INTO club VALUES (
    23,
    'F. C. Porto',
    'Sergio Conceicao',
    'Estadio do Dragao',
    1893
);

INSERT INTO club VALUES (
    24,
    'Atletico Junior',
    'Julio Comesaña',
    'Metropolitano',
    1924
);

INSERT INTO club VALUES (
    25,
    'A. S. Monaco',
    'Leonardo Jardim',
    'Stade Louis II',
    1924
);

INSERT INTO club VALUES (
    26,
    'Chelsea F. C.',
    'Antonio Conte',
    'Stamford Bridge',
    1905
);

INSERT INTO club VALUES (
    27,
    'Atletico Madrid',
    'Diego Simeone',
    'Metropolitano',
    1903
);

INSERT INTO club VALUES (
    28,
    'Villarreal CF',
    'Javier Calleja',
    'Estadio de la Ceramica',
    1923
);

INSERT INTO club VALUES (
    29,
    'U. C. Sampdoria',
    'Marco Giampaolo',
    'Stadio Luigi',
    1946
);

COMMIT;

INSERT INTO pais VALUES (
    1,
    'Colombia',
    48650000
);

INSERT INTO pais VALUES (
    2,
    'Polonia',
    37950000
);

INSERT INTO pais VALUES (
    3,
    'Brasil',
    207700000
);

INSERT INTO pais VALUES (
    4,
    'Senegal',
    15410000
);

INSERT INTO pais VALUES (
    5,
    'Francia',
    66900000
);

INSERT INTO pais VALUES (
    6,
    'Japon',
    127000000
);

COMMIT;


INSERT INTO jugador VALUES (
    1,
    'David',
    'Ospina',
    TO_DATE('31-08-1988','DD-MM-YYYY'),
    183,
    80,
    1,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    2,
    'Camilo',
    'Vargas',
    TO_DATE('09-03-1989','DD-MM-YYYY'),
    185,
    80,
    12,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    3,
    'Davinson',
    'Sanchez',
    TO_DATE('12-06-1996','DD-MM-YYYY'),
    188,
    83,
    23,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    4,
    'Yerry',
    'Mina',
    TO_DATE('23-09-1994','DD-MM-YYYY'),
    195,
    94,
    13,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    5,
    'Oscar',
    'Murillo',
    TO_DATE('18-04-1988','DD-MM-YYYY'),
    184,
    84,
    3,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    6,
    'Cristian',
    'Zapata',
    TO_DATE('30-09-1986','DD-MM-YYYY'),
    187,
    82,
    2,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    7,
    'Frank',
    'Fabra',
    TO_DATE('22-02-1991','DD-MM-YYYY'),
    172,
    72,
    18,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    8,
    'Johan',
    'Mojica',
    TO_DATE('21-08-1992','DD-MM-YYYY'),
    182,
    80,
    26,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    9,
    'Santiago',
    'Arias',
    TO_DATE('13-01-1992','DD-MM-YYYY'),
    175,
    68,
    4,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    10,
    'Wilmar',
    'Barrios',
    TO_DATE('16-10-1993','DD-MM-YYYY'),
    178,
    74,
    5,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    11,
    'Carlos',
    'Sanchez',
    TO_DATE('06-02-1986','DD-MM-YYYY'),
    182,
    80,
    6,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    12,
    'Jefferson',
    'Lerma',
    TO_DATE('25-10-1994','DD-MM-YYYY'),
    180,
    79,
    25,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    13,
    'Abel',
    'Aguilar',
    TO_DATE('06-01-1985','DD-MM-YYYY'),
    186,
    82,
    8,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    14,
    'Mateus',
    'Uribe',
    TO_DATE('21-03-1991','DD-MM-YYYY'),
    182,
    71,
    21,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    15,
    'James',
    'Rodriguez',
    TO_DATE('12-07-1991','DD-MM-YYYY'),
    180,
    75,
    10,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    16,
    'Giovanni',
    'Moreno',
    TO_DATE('01-07-1986','DD-MM-YYYY'),
    190,
    83,
    20,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    17,
    'Juan',
    'Quintero',
    TO_DATE('18-01-1993','DD-MM-YYYY'),
    167,
    65,
    15,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    18,
    'Yimmi',
    'Chara',
    TO_DATE('02-04-1991','DD-MM-YYYY'),
    162,
    65,
    8,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    19,
    'Duvan',
    'Zapata',
    TO_DATE('01-04-1991','DD-MM-YYYY'),
    189,
    86,
    91,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    20,
    'Radamel',
    'Falcao',
    TO_DATE('10-02-1986','DD-MM-YYYY'),
    177,
    72,
    9,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    21,
    'Luis',
    'Muriel',
    TO_DATE('16-04-1991','DD-MM-YYYY'),
    179,
    79,
    14,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    22,
    'Carlos',
    'Bacca',
    TO_DATE('08-09-1986','DD-MM-YYYY'),
    181,
    77,
    7,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    23,
    'Miguel',
    'Borja',
    TO_DATE('26-01-1993','DD-MM-YYYY'),
    183,
    80,
    16,
    EMPTY_BLOB(),
    1,
    1
);

INSERT INTO jugador VALUES (
    24,
    'Robert',
    'Lewandowski',
    TO_DATE('21-08-1988','DD-MM-YYYY'),
    185,
    78,
    9,
    EMPTY_BLOB(),
    2,
    2
);

INSERT INTO jugador VALUES (
    25,
    'Sadio',
    'Mane',
    TO_DATE('10-04-1992','DD-MM-YYYY'),
    175,
    72,
    19,
    EMPTY_BLOB(),
    3,
    4
);

INSERT INTO jugador VALUES (
    26,
    'Keisuke',
    'Honda',
    TO_DATE('13-06-1986','DD-MM-YYYY'),
    182,
    79,
    2,
    EMPTY_BLOB(),
    4,
    6
);

COMMIT;


INSERT INTO categoria VALUES ( 1 );

INSERT INTO categoria VALUES ( 2 );

INSERT INTO categoria VALUES ( 3 );

INSERT INTO categoria VALUES ( 4 );

COMMIT;

INSERT INTO silla VALUES (
    1,
    'A',
    'si',
    1
);

INSERT INTO silla VALUES (
    2,
    'B',
    'si',
    2
);

INSERT INTO silla VALUES (
    3,
    'C',
    'si',
    3
);

INSERT INTO silla VALUES (
    4,
    'D',
    'si',
    4
);

INSERT INTO silla VALUES (
    5,
    'E',
    'si',
    1
);

INSERT INTO silla VALUES (
    6,
    'F',
    'si',
    1
);

INSERT INTO silla VALUES (
    7,
    'A',
    'si',
    2
);

INSERT INTO silla VALUES (
    8,
    'B',
    'si',
    3
);

INSERT INTO silla VALUES (
    9,
    'C',
    'si',
    4
);

INSERT INTO silla VALUES (
    10,
    'D',
    'si',
    1
);

INSERT INTO silla VALUES (
    11,
    'E',
    'si',
    1
);

INSERT INTO silla VALUES (
    12,
    'F',
    'si',
    4
);

INSERT INTO silla VALUES (
    13,
    'A',
    'si',
    3
);

INSERT INTO silla VALUES (
    14,
    'B',
    'si',
    2
);

INSERT INTO silla VALUES (
    15,
    'C',
    'si',
    1
);

INSERT INTO silla VALUES (
    16,
    'D',
    'si',
    1
);

INSERT INTO silla VALUES (
    17,
    'E',
    'si',
    4
);

INSERT INTO silla VALUES (
    18,
    'F',
    'si',
    3
);

INSERT INTO silla VALUES (
    19,
    'A',
    'si',
    2
);

INSERT INTO silla VALUES (
    20,
    'B',
    'si',
    1
);

INSERT INTO silla VALUES (
    21,
    'C',
    'si',
    1
);

INSERT INTO silla VALUES (
    22,
    'D',
    'si',
    4
);

INSERT INTO silla VALUES (
    23,
    'E',
    'si',
    4
);

INSERT INTO silla VALUES (
    24,
    'F',
    'si',
    3
);

INSERT INTO silla VALUES (
    25,
    'A',
    'si',
    3
);

INSERT INTO silla VALUES (
    26,
    'B',
    'si',
    2
);

INSERT INTO silla VALUES (
    27,
    'C',
    'si',
    2
);

INSERT INTO silla VALUES (
    28,
    'D',
    'si',
    1
);

INSERT INTO silla VALUES (
    29,
    'E',
    'si',
    1
);

INSERT INTO silla VALUES (
    30,
    'F',
    'si',
    4
);

INSERT INTO silla VALUES (
    31,
    'A',
    'si',
    4
);

INSERT INTO silla VALUES (
    32,
    'B',
    'si',
    4
);

INSERT INTO silla VALUES (
    33,
    'C',
    'si',
    4
);

INSERT INTO silla VALUES (
    34,
    'D',
    'si',
    4
);

INSERT INTO silla VALUES (
    35,
    'E',
    'si',
    3
);

INSERT INTO silla VALUES (
    36,
    'F',
    'si',
    3
);

INSERT INTO silla VALUES (
    37,
    'A',
    'si',
    3
);

INSERT INTO silla VALUES (
    38,
    'B',
    'si',
    3
);

INSERT INTO silla VALUES (
    39,
    'C',
    'si',
    3
);

INSERT INTO silla VALUES (
    40,
    'D',
    'si',
    2
);

INSERT INTO silla VALUES (
    41,
    'E',
    'si',
    2
);

INSERT INTO silla VALUES (
    42,
    'A',
    'si',
    2
);

INSERT INTO silla VALUES (
    43,
    'F',
    'si',
    2
);

INSERT INTO silla VALUES (
    44,
    'D',
    'si',
    2
);

INSERT INTO silla VALUES (
    45,
    'C',
    'si',
    1
);

INSERT INTO silla VALUES (
    46,
    'E',
    'si',
    1
);

INSERT INTO silla VALUES (
    47,
    'F',
    'si',
    1
);

INSERT INTO silla VALUES (
    48,
    'E',
    'si',
    1
);

INSERT INTO silla VALUES (
    49,
    'A',
    'si',
    1
);

INSERT INTO silla VALUES (
    50,
    'B',
    'si',
    3
);

INSERT INTO silla VALUES (
    51,
    'C',
    'si',
    2
);

INSERT INTO silla VALUES (
    52,
    'D',
    'si',
    1
);

INSERT INTO silla VALUES (
    53,
    'E',
    'si',
    4
);

INSERT INTO silla VALUES (
    54,
    'F',
    'si',
    3
);

INSERT INTO silla VALUES (
    55,
    'F',
    'si',
    2
);

INSERT INTO silla VALUES (
    56,
    'E',
    'si',
    1
);

INSERT INTO silla VALUES (
    57,
    'D',
    'si',
    4
);

INSERT INTO silla VALUES (
    58,
    'C',
    'si',
    3
);

INSERT INTO silla VALUES (
    59,
    'B',
    'si',
    2
);

INSERT INTO silla VALUES (
    60,
    'A',
    'si',
    1
);

COMMIT;

INSERT INTO estadio VALUES (
    1,
    'Ekaterinburg Arena',
    'Ekaterinburg',
    35000
);

INSERT INTO estadio VALUES (
    2,
    'Kaliningrad Stadium',
    'Kaliningrad',
    35000
);

INSERT INTO estadio VALUES (
    3,
    'Kazan Arena',
    'Kazan',
    45000
);

INSERT INTO estadio VALUES (
    4,
    'Spartak Stadium',
    'Moscow',
    45000
);

INSERT INTO estadio VALUES (
    5,
    'Luzhniki Stadium',
    'Moscow',
    80000
);

INSERT INTO estadio VALUES (
    6,
    'Nizhny Novgorod Stadium',
    'Nizhny Novgorod',
    45000
);

INSERT INTO estadio VALUES (
    7,
    'Rostov Arena',
    'Rostov on Don',
    45000
);

INSERT INTO estadio VALUES (
    8,
    'Samara Arena',
    'Samara',
    45000
);

INSERT INTO estadio VALUES (
    9,
    'Saint Petersburg Stadium',
    'Saint Petersburg',
    67000
);

INSERT INTO estadio VALUES (
    10,
    'Mordovia Arena',
    'Saransk',
    44000
);

INSERT INTO estadio VALUES (
    11,
    'Fisht Stadium',
    'Sochi',
    48000
);

INSERT INTO estadio VALUES (
    12,
    'Volgograd Arena',
    'Volgograd',
    45000
);

COMMIT;

INSERT INTO calendario VALUES (
    1,
    'Grupos'
);

INSERT INTO calendario VALUES (
    2,
    'Grupos'
);

INSERT INTO calendario VALUES (
    3,
    'Grupos'
);

INSERT INTO calendario VALUES (
    4,
    'Grupos'
);

INSERT INTO calendario VALUES (
    5,
    'Grupos'
);

INSERT INTO calendario VALUES (
    6,
    'Grupos'
);

INSERT INTO calendario VALUES (
    7,
    'Grupos'
);

INSERT INTO calendario VALUES (
    8,
    'Grupos'
);

INSERT INTO calendario VALUES (
    9,
    'Grupos'
);

INSERT INTO calendario VALUES (
    10,
    'Grupos'
);

COMMIT;

INSERT INTO partido VALUES (
    1,
    TO_DATE('19-06-2018 07:00','DD-MM-YYYY HH24:MI'),
    1,
    4,
    1,
    10
);

INSERT INTO partido VALUES (
    2,
    TO_DATE('19-06-2018 10:00','DD-MM-YYYY HH24:MI'),
    2,
    3,
    2,
    4
);

INSERT INTO partido VALUES (
    3,
    TO_DATE('24-06-2018 10:00','DD-MM-YYYY HH24:MI'),
    4,
    3,
    3,
    1
);

INSERT INTO partido VALUES (
    4,
    TO_DATE('24-06-2018 13:00','DD-MM-YYYY HH24:MI'),
    2,
    1,
    4,
    3
);

INSERT INTO partido VALUES (
    5,
    TO_DATE('28-06-2018 09:00','DD-MM-YYYY HH24:MI'),
    3,
    1,
    5,
    8
);

INSERT INTO partido VALUES (
    6,
    TO_DATE('28-06-2018 09:00','DD-MM-YYYY HH24:MI'),
    4,
    2,
    6,
    12
);

COMMIT;

INSERT INTO tarjeta VALUES (
    1,
    19,
    'Amarilla',
    1,
    11
);

INSERT INTO tarjeta VALUES (
    2,
    22,
    'Amarilla',
    2,
    25
);

INSERT INTO tarjeta VALUES (
    3,
    7,
    'Amarilla',
    4,
    15
);

INSERT INTO tarjeta VALUES (
    4,
    45,
    'Roja',
    4,
    24
);

INSERT INTO tarjeta VALUES (
    5,
    31,
    'Amarilla',
    6,
    26
);

INSERT INTO tarjeta VALUES (
    6,
    31,
    'Amarilla',
    5,
    22
);

COMMIT;

INSERT INTO gol VALUES (
    1,
    15,
    'Primer Tiempo',
    'Remate',
    'No',
    20,
    1
);

INSERT INTO gol VALUES (
    2,
    22,
    'Primer Tiempo',
    'Cabeza',
    'Si',
    20,
    4
);

INSERT INTO gol VALUES (
    3,
    9,
    'Segundo Tiempo',
    'Tiro Libre',
    'No',
    24,
    4
);

INSERT INTO gol VALUES (
    4,
    18,
    'Segundo Tiempo',
    'Remate',
    'No',
    14,
    4
);

INSERT INTO gol VALUES (
    5,
    4,
    'Extra Segundo Tiempo',
    'Cabeza',
    'Si',
    24,
    4
);

INSERT INTO gol VALUES (
    6,
    2,
    'Primer Tiempo',
    'Remate',
    'No',
    10,
    5
);

INSERT INTO gol VALUES (
    7,
    35,
    'Primer Tiempo',
    'Cabeza',
    'No',
    23,
    5
);

INSERT INTO gol VALUES (
    8,
    42,
    'Primer Tiempo',
    'Penalty',
    'Si',
    25,
    5
);

INSERT INTO gol VALUES (
    9,
    20,
    'Segundo Tiempo',
    'Tiro Libre',
    'No',
    15,
    5
);

INSERT INTO gol VALUES (
    10,
    45,
    'Segundo Tiempo',
    'Penalty',
    'Si',
    24,
    2
);

COMMIT;

INSERT INTO clienteporpartido VALUES (
    1,
    1,
    'Efectivo'
);

INSERT INTO clienteporpartido VALUES (
    2,
    1,
    'Tarjeta'
);

INSERT INTO clienteporpartido VALUES (
    3,
    2,
    'Tarjeta'
);

INSERT INTO clienteporpartido VALUES (
    4,
    3,
    'Efectivo'
);

INSERT INTO clienteporpartido VALUES (
    5,
    4,
    'Efectivo'
);

INSERT INTO clienteporpartido VALUES (
    2,
    4,
    'Tarjeta'
);

INSERT INTO clienteporpartido VALUES (
    6,
    4,
    'Efectivo'
);

INSERT INTO clienteporpartido VALUES (
    7,
    5,
    'Tarjeta'
);

INSERT INTO clienteporpartido VALUES (
    1,
    5,
    'Efectivo'
);

INSERT INTO clienteporpartido VALUES (
    8,
    6,
    'Efectivo'
);

INSERT INTO clienteporpartido VALUES (
    9,
    6,
    'Tarjeta'
);

INSERT INTO clienteporpartido VALUES (
    10,
    6,
    'Efectivo'
);

COMMIT;

INSERT INTO categoriaporestadio VALUES (
    1,
    1,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    1,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    1,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    1,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    1,
    2,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    2,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    2,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    2,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    1,
    3,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    3,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    3,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    3,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    1,
    4,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    4,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    4,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    4,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    1,
    5,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    5,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    5,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    5,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    1,
    6,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    6,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    6,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    6,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    1,
    7,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    7,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    7,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    7,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    1,
    8,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    8,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    8,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    8,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    1,
    9,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    9,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    9,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    9,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    1,
    10,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    10,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    10,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    10,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    1,
    11,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    11,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    11,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    11,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    1,
    12,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    2,
    12,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    3,
    12,
    '.'
);

INSERT INTO categoriaporestadio VALUES (
    4,
    12,
    '.'
);

COMMIT;

INSERT INTO categoriaporpartido VALUES (
    1,
    1,
    210
);

INSERT INTO categoriaporpartido VALUES (
    2,
    1,
    165
);

INSERT INTO categoriaporpartido VALUES (
    3,
    1,
    105
);

INSERT INTO categoriaporpartido VALUES (
    4,
    1,
    20
);

INSERT INTO categoriaporpartido VALUES (
    1,
    2,
    210
);

INSERT INTO categoriaporpartido VALUES (
    2,
    2,
    165
);

INSERT INTO categoriaporpartido VALUES (
    3,
    2,
    105
);

INSERT INTO categoriaporpartido VALUES (
    4,
    2,
    20
);

INSERT INTO categoriaporpartido VALUES (
    1,
    3,
    210
);

INSERT INTO categoriaporpartido VALUES (
    2,
    3,
    165
);

INSERT INTO categoriaporpartido VALUES (
    3,
    3,
    105
);

INSERT INTO categoriaporpartido VALUES (
    4,
    3,
    20
);

INSERT INTO categoriaporpartido VALUES (
    1,
    4,
    210
);

INSERT INTO categoriaporpartido VALUES (
    2,
    4,
    165
);

INSERT INTO categoriaporpartido VALUES (
    3,
    4,
    105
);

INSERT INTO categoriaporpartido VALUES (
    4,
    4,
    20
);

INSERT INTO categoriaporpartido VALUES (
    1,
    5,
    210
);

INSERT INTO categoriaporpartido VALUES (
    2,
    5,
    165
);

INSERT INTO categoriaporpartido VALUES (
    3,
    5,
    105
);

INSERT INTO categoriaporpartido VALUES (
    4,
    5,
    20
);

INSERT INTO categoriaporpartido VALUES (
    1,
    6,
    210
);

INSERT INTO categoriaporpartido VALUES (
    2,
    6,
    165
);

INSERT INTO categoriaporpartido VALUES (
    3,
    6,
    105
);

INSERT INTO categoriaporpartido VALUES (
    4,
    6,
    20
);

COMMIT;

INSERT INTO clubesporjugador VALUES (
    1,
    1
);

INSERT INTO clubesporjugador VALUES (
    1,
    2
);

INSERT INTO clubesporjugador VALUES (
    2,
    3
);

INSERT INTO clubesporjugador VALUES (
    2,
    1
);

INSERT INTO clubesporjugador VALUES (
    2,
    4
);

INSERT INTO clubesporjugador VALUES (
    3,
    1
);

INSERT INTO clubesporjugador VALUES (
    3,
    8
);

INSERT INTO clubesporjugador VALUES (
    4,
    3
);

INSERT INTO clubesporjugador VALUES (
    4,
    6
);

INSERT INTO clubesporjugador VALUES (
    5,
    1
);

INSERT INTO clubesporjugador VALUES (
    5,
    9
);

INSERT INTO clubesporjugador VALUES (
    6,
    4
);

INSERT INTO clubesporjugador VALUES (
    6,
    17
);

INSERT INTO clubesporjugador VALUES (
    6,
    25
);

INSERT INTO clubesporjugador VALUES (
    6,
    7
);

INSERT INTO clubesporjugador VALUES (
    7,
    4
);

INSERT INTO clubesporjugador VALUES (
    7,
    10
);

INSERT INTO clubesporjugador VALUES (
    8,
    4
);

INSERT INTO clubesporjugador VALUES (
    8,
    11
);

INSERT INTO clubesporjugador VALUES (
    9,
    12
);

INSERT INTO clubesporjugador VALUES (
    10,
    10
);

INSERT INTO clubesporjugador VALUES (
    10,
    15
);

INSERT INTO clubesporjugador VALUES (
    11,
    13
);

INSERT INTO clubesporjugador VALUES (
    11,
    14
);

INSERT INTO clubesporjugador VALUES (
    12,
    16
);

INSERT INTO clubesporjugador VALUES (
    12,
    17
);

INSERT INTO clubesporjugador VALUES (
    13,
    4
);

INSERT INTO clubesporjugador VALUES (
    13,
    18
);

INSERT INTO clubesporjugador VALUES (
    14,
    19
);

INSERT INTO clubesporjugador VALUES (
    15,
    21
);

INSERT INTO clubesporjugador VALUES (
    15,
    22
);

INSERT INTO clubesporjugador VALUES (
    15,
    23
);

INSERT INTO clubesporjugador VALUES (
    16,
    1
);

INSERT INTO clubesporjugador VALUES (
    17,
    1
);

INSERT INTO clubesporjugador VALUES (
    17,
    23
);

INSERT INTO clubesporjugador VALUES (
    18,
    1
);

INSERT INTO clubesporjugador VALUES (
    18,
    15
);

INSERT INTO clubesporjugador VALUES (
    18,
    24
);

INSERT INTO clubesporjugador VALUES (
    19,
    18
);

INSERT INTO clubesporjugador VALUES (
    19,
    29
);

INSERT INTO clubesporjugador VALUES (
    20,
    23
);

INSERT INTO clubesporjugador VALUES (
    20,
    25
);

INSERT INTO clubesporjugador VALUES (
    20,
    26
);

INSERT INTO clubesporjugador VALUES (
    20,
    27
);

INSERT INTO clubesporjugador VALUES (
    21,
    4
);

INSERT INTO clubesporjugador VALUES (
    21,
    18
);

INSERT INTO clubesporjugador VALUES (
    21,
    29
);

INSERT INTO clubesporjugador VALUES (
    22,
    7
);

INSERT INTO clubesporjugador VALUES (
    22,
    24
);

INSERT INTO clubesporjugador VALUES (
    22,
    28
);

INSERT INTO clubesporjugador VALUES (
    23,
    1
);

INSERT INTO clubesporjugador VALUES (
    23,
    3
);

INSERT INTO clubesporjugador VALUES (
    24,
    21
);

INSERT INTO clubesporjugador VALUES (
    25,
    26
);

INSERT INTO clubesporjugador VALUES (
    26,
    7
);

INSERT INTO clubesporjugador VALUES (
    26,
    9
);

INSERT INTO clubesporjugador VALUES (
    23,
    6
);

COMMIT;

INSERT INTO juezporpartido VALUES (
    1,
    5,
    'Principal'
);

INSERT INTO juezporpartido VALUES (
    1,
    12,
    'De Linea'
);

INSERT INTO juezporpartido VALUES (
    1,
    7,
    'Auxiliar'
);

INSERT INTO juezporpartido VALUES (
    1,
    2,
    'VAR'
);

INSERT INTO juezporpartido VALUES (
    2,
    9,
    'Principal'
);

INSERT INTO juezporpartido VALUES (
    2,
    6,
    'De Area'
);

INSERT INTO juezporpartido VALUES (
    2,
    7,
    'Auxiliar'
);

INSERT INTO juezporpartido VALUES (
    2,
    1,
    'VAR'
);

INSERT INTO juezporpartido VALUES (
    3,
    3,
    'Principal'
);

INSERT INTO juezporpartido VALUES (
    3,
    12,
    'De Linea'
);

INSERT INTO juezporpartido VALUES (
    3,
    7,
    'De Area'
);

INSERT INTO juezporpartido VALUES (
    3,
    2,
    'VAR'
);

INSERT INTO juezporpartido VALUES (
    4,
    1,
    'Principal'
);

INSERT INTO juezporpartido VALUES (
    4,
    8,
    'De linea'
);

INSERT INTO juezporpartido VALUES (
    4,
    11,
    'De Area'
);

INSERT INTO juezporpartido VALUES (
    4,
    3,
    'Auxiliar'
);

INSERT INTO juezporpartido VALUES (
    5,
    4,
    'Principal'
);

INSERT INTO juezporpartido VALUES (
    5,
    6,
    'De Linea'
);

INSERT INTO juezporpartido VALUES (
    5,
    9,
    'Auxiliar'
);

INSERT INTO juezporpartido VALUES (
    6,
    5,
    'VAR'
);

INSERT INTO juezporpartido VALUES (
    6,
    4,
    'Principal'
);

INSERT INTO juezporpartido VALUES (
    6,
    3,
    'De Linea'
);

INSERT INTO juezporpartido VALUES (
    6,
    12,
    'De Area'
);

INSERT INTO juezporpartido VALUES (
    6,
    1,
    'VAR'
);

COMMIT;

INSERT INTO jugadorporpartido VALUES (
    1,
    1,
    'Arquero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    2,
    'Arquero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    3,
    'Defensa Central',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    4,
    'Defensa Central',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    5,
    'Defensa Central',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    6,
    'Defensa Central',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    7,
    'Defensa Lateral',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    8,
    'Defensa Lateral',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    9,
    'Defensa Lateral',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    10,
    'Mediocampista',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    11,
    'Mediocampista',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    12,
    'Mediocampista',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    13,
    'Mediocampista',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    14,
    'Mediocampista',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    15,
    'Volante',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    16,
    'Volante',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    17,
    'Volante',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    18,
    'Delantero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    19,
    'Delantero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    20,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    21,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    22,
    'Delantero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    23,
    'Delantero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    1,
    26,
    'Volante',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    1,
    'Arquero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    2,
    'Arquero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    3,
    'Defensa Central',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    4,
    'Defensa Lateral',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    5,
    'Defensa Central',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    6,
    'Defensa Central',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    7,
    'Defensa Central',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    8,
    'Defensa Lateral',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    9,
    'Defensa Lateral',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    10,
    'Mediocampista',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    11,
    'Mediocampista',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    12,
    'Volante',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    13,
    'Mediocampista',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    14,
    'Mediocampista',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    15,
    'Mediocampista',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    16,
    'Volante',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    17,
    'Volante',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    18,
    'Delantero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    19,
    'Delantero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    20,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    21,
    'Delantero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    22,
    'Delantero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    23,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    4,
    24,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    1,
    'Arquero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    2,
    'Arquero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    3,
    'Defensa Lateral',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    4,
    'Defensa Lateral',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    5,
    'Defensa Lateral',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    6,
    'Defensa Central',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    7,
    'Defensa Central',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    8,
    'Defensa Central',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    9,
    'Defensa Central',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    10,
    'Mediocampista',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    11,
    'Mediocampista',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    12,
    'Mediocampista',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    13,
    'Mediocampista',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    14,
    'Mediocampista',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    15,
    'Volante',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    16,
    'Volante',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    17,
    'Volante',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    18,
    'Delantero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    19,
    'Delantero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    20,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    21,
    'Delantero',
    'Suplente'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    22,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    23,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    5,
    25,
    'Mediocampista',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    2,
    24,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    2,
    25,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    3,
    26,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    3,
    25,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    6,
    26,
    'Delantero',
    'Titular'
);

INSERT INTO jugadorporpartido VALUES (
    6,
    24,
    'Mediocampista',
    'Titular'
);

COMMIT;


INSERT INTO sillaporestadio VALUES (
    1,
    'A',
    1,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    2,
    'B',
    5,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    3,
    'C',
    9,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    4,
    'D',
    4,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    5,
    'E',
    4,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    6,
    'F',
    9,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    7,
    'A',
    2,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    8,
    'B',
    2,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    9,
    'C',
    10,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    10,
    'D',
    3,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    11,
    'E',
    4,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    12,
    'F',
    5,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    13,
    'A',
    12,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    14,
    'B',
    10,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    15,
    'C',
    7,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    16,
    'D',
    5,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    17,
    'E',
    8,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    18,
    'F',
    1,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    19,
    'A',
    10,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    20,
    'B',
    9,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    21,
    'C',
    3,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    22,
    'D',
    11,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    23,
    'E',
    6,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    24,
    'F',
    12,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    25,
    'A',
    2,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    26,
    'B',
    4,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    27,
    'C',
    8,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    28,
    'D',
    7,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    29,
    'E',
    10,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    30,
    'F',
    3,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    31,
    'A',
    11,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    32,
    'B',
    10,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    33,
    'C',
    7,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    34,
    'D',
    6,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    35,
    'E',
    12,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    36,
    'F',
    2,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    37,
    'A',
    11,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    38,
    'B',
    5,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    39,
    'C',
    1,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    40,
    'D',
    9,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    41,
    'E',
    2,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    42,
    'A',
    2,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    43,
    'F',
    11,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    44,
    'D',
    5,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    45,
    'C',
    4,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    46,
    'E',
    12,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    47,
    'F',
    11,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    48,
    'E',
    8,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    49,
    'A',
    7,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    50,
    'B',
    3,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    51,
    'C',
    1,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    52,
    'D',
    3,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    53,
    'E',
    12,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    54,
    'F',
    5,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    55,
    'F',
    7,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    56,
    'E',
    8,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    57,
    'D',
    11,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    58,
    'C',
    10,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    59,
    'B',
    6,
    '.'
);

INSERT INTO sillaporestadio VALUES (
    60,
    'A',
    6,
    '.'
);

COMMIT;