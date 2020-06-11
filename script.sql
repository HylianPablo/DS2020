--Derby does not support DROP TABLE IF EXISTS 
DROP TABLE DISPONIBILIDADEMPLEADO;
DROP TABLE VINCULACIONCONLAEMPRESA;
DROP TABLE ROLESENEMPRESA;
DROP TABLE TIPODEDISPONIBILIDAD;
DROP TABLE TIPODEVINCULACION;
DROP TABLE TIPODEROL;
DROP TABLE LINEAPEDIDO;
DROP TABLE PEDIDO;
DROP TABLE LINEACOMPRA;
DROP TABLE COMPRA;
DROP TABLE ESTADOPEDIDO;
DROP TABLE FACTURA;
DROP TABLE ESTADOFACTURA;
DROP TABLE PREFERENCIA;
DROP TABLE REFERENCIA;
DROP TABLE VINO;
DROP TABLE BODEGA;
DROP TABLE CATEGORIA;
DROP TABLE DENOMINACIONORIGEN;
DROP TABLE ABONADO;
DROP TABLE EMPLEADO;
DROP TABLE PERSONA;

-- Enum
create table TIPODEROL
(
	IdTipo SMALLINT not null,
	NombreTipo VARCHAR(20) not null unique,
		PRIMARY KEY(IdTipo)
);

INSERT INTO TIPODEROL
VALUES  (1,'Almacen'),
        (2,'AtencionCliente'),
        (3,'Contabilidad');

-- Enum
create table TIPODEVINCULACION
(
	IdTipo SMALLINT not null,
	NombreTipo VARCHAR(20) not null unique,
		PRIMARY KEY(IdTipo)

);

INSERT INTO TIPODEVINCULACION
VALUES  (1,'Contratado'),
        (2,'Despedido'),
        (3,'EnERTE');

-- Enum
create table TIPODEDISPONIBILIDAD
(
	IdTipo SMALLINT not null,
	NombreTipo VARCHAR(20) not null unique,
		PRIMARY KEY(IdTipo)
);

INSERT INTO TIPODEDISPONIBILIDAD
VALUES  (1,'Vacaciones'),
        (2,'BajaTemporal'),
		(3, 'Trabajando');

-- Entity
CREATE TABLE PERSONA(
	Nif VARCHAR(9),
	Nombre VARCHAR(50) not null,
	Apellidos VARCHAR(50) not null,
	Direccion VARCHAR(50) not null,
	Telefono VARCHAR(50) not null,
	Email VARCHAR(50) not null,
	CuentaBancaria VARCHAR(30) not null,
	PRIMARY KEY(Nif)
);

-- Entity
create table EMPLEADO
(
	Nif VARCHAR(9),
	Password VARCHAR(20) not null,
	FechaInicioEnEmpresa DATE not null,
		PRIMARY KEY(Nif),
		FOREIGN KEY(Nif) REFERENCES PERSONA(Nif)
);

-- Association
create table ROLESENEMPRESA
(
	ComienzoEnRol DATE not null,
	Empleado VARCHAR(9) not null,
	Rol SMALLINT not null,
            FOREIGN KEY(Empleado) REFERENCES EMPLEADO(Nif),
            FOREIGN KEY(Rol) REFERENCES TIPODEROL(IdTipo)
);

-- Association
create table VINCULACIONCONLAEMPRESA
(
	inicio DATE not null,
	Empleado VARCHAR(9) not null,
	Vinculo SMALLINT not null,
		FOREIGN KEY(Empleado) REFERENCES EMPLEADO(Nif),
		FOREIGN KEY(Vinculo) REFERENCES TIPODEVINCULACION(IdTipo) 
);

-- Association
create table DISPONIBILIDADEMPLEADO
(
	Comienzo DATE not null,
	FinalPrevisto DATE,
	Empleado VARCHAR(9) not null,
	Disponibilidad SMALLINT not null,
		FOREIGN KEY(Empleado) REFERENCES EMPLEADO(Nif),
		FOREIGN KEY(Disponibilidad) REFERENCES TIPODEDISPONIBILIDAD(IdTipo)
);

-- Entity
CREATE TABLE ABONADO(
	NumeroAbonado INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	OpenIDref VARCHAR(50) unique,
	Nif VARCHAR(9) not null unique,
	PRIMARY KEY(NumeroAbonado),
	FOREIGN KEY(Nif) REFERENCES PERSONA(Nif)
);

-- Enum
CREATE TABLE ESTADOFACTURA(
	Id SMALLINT,
	Nombre VARCHAR(20) not null unique,
	PRIMARY KEY(id)
);

INSERT INTO ESTADOFACTURA
VALUES  (1,'emitida'),
 		(2,'vencida'),
 		(3,'pagada');

-- Entity
CREATE TABLE FACTURA(
	NumeroFactura INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	FechaEmision DATE,
	Importe REAL,
	Estado SMALLINT,
	FechaPago DATE,
	IdExtractoBancario VARCHAR(30),
	PRIMARY KEY(NumeroFactura),
	FOREIGN KEY(Estado) REFERENCES ESTADOFACTURA(Id)
);

-- Enum
CREATE TABLE CATEGORIA(
	Id SMALLINT,
	Nombre VARCHAR(20) not null unique,
	PRIMARY KEY(Id)
);

INSERT INTO CATEGORIA
VALUES  (1,'joven'), 
		(2,'crianza'), 
		(3,'reserva'), 
		(4,'gran reserva');

-- Entity
CREATE TABLE DENOMINACIONORIGEN(
	Id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	Nombre VARCHAR(50) not null unique,
	PRIMARY KEY(Id)
);

-- Entity
CREATE TABLE PREFERENCIA(
	IdDenominacion INTEGER,
	Categoria SMALLINT,
	NumeroAbonado INTEGER,
	FOREIGN KEY(IdDenominacion) REFERENCES DENOMINACIONORIGEN(Id),
	FOREIGN KEY(Categoria) REFERENCES CATEGORIA(Id),
	FOREIGN KEY(NumeroAbonado) REFERENCES ABONADO(NumeroAbonado)
);

-- Entity
CREATE TABLE BODEGA(
	Id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	Nombre VARCHAR(50) not null unique,
	CIF VARCHAR(9) not null unique,
	Direccion VARCHAR(50) not null,
	PRIMARY KEY(Id)
);

-- Entity
CREATE TABLE VINO(
	Id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	NombreComercial VARCHAR(50) not null unique,
	Ano SMALLINT,
	Comentario VARCHAR(200),
	IdDenominacion INTEGER,
	Categoria SMALLINT,
	IdBodega INTEGER,
	PRIMARY KEY(Id),
	FOREIGN KEY(IdDenominacion) REFERENCES DENOMINACIONORIGEN(Id),
	FOREIGN KEY(Categoria) REFERENCES CATEGORIA(Id),
	FOREIGN KEY(IdBodega) REFERENCES BODEGA(Id)
);

-- Entity
CREATE TABLE REFERENCIA(
	Codigo INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	EsPorCajas CHAR(1),
	ContenidoEnCL SMALLINT,
	Precio REAL,
	Disponible CHAR(1),
	VinoId INTEGER,
	PRIMARY KEY(Codigo),
	FOREIGN KEY(VinoId) REFERENCES VINO(Id)
);

-- Entity
CREATE TABLE COMPRA(
	IdCompra INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	FechaInicioCompra DATE,
	RecibidaCompleta CHAR(1),
	FechaCompraCompletada DATE,
	Importe REAL,
	Pagada CHAR(1),
	FechaPago DATE,
	IdProveedor INTEGER not null,
	PRIMARY KEY(IdCompra),
	FOREIGN KEY (IdProveedor) REFERENCES BODEGA(Id)
);

-- Entity
CREATE TABLE LINEACOMPRA(
	Id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	Unidades SMALLINT,
	Recibida CHAR(1),
	FechaRecepcion DATE,
	IdCompra INTEGER not null,
	CodigoReferencia INTEGER not null,
	PRIMARY KEY(Id),
	FOREIGN KEY(IdCompra) REFERENCES COMPRA(IdCompra),
	FOREIGN KEY(CodigoReferencia) REFERENCES REFERENCIA(Codigo)
);

-- Enum
CREATE TABLE ESTADOPEDIDO(
	Id SMALLINT,
	Nombre VARCHAR(20)not null unique,
	PRIMARY KEY(Id)
);

INSERT INTO ESTADOPEDIDO
VALUES (1,'pendiente'),
		(2,'tramitado'),
		(3,'completado'),
		(4,'servido'),
		(5,'facturado'),
		(6,'pagado');

-- Entity
CREATE TABLE PEDIDO(
	Numero INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	Estado SMALLINT,
	FechaRealizacion DATE,
	NotaEntrega VARCHAR(200),
	Importe REAL,
	FechaRecepcion DATE,
	FechaEntrega DATE,
	NumeroFactura INTEGER,
	NumeroAbonado INTEGER,
	PRIMARY KEY(Numero),
	FOREIGN KEY(Estado) REFERENCES ESTADOPEDIDO(Id),
	FOREIGN KEY(NumeroFactura) REFERENCES FACTURA(NumeroFactura),
	FOREIGN KEY(NumeroAbonado) REFERENCES ABONADO(NumeroAbonado)
);

-- Entity
CREATE TABLE LINEAPEDIDO(
	Id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	Unidades INTEGER,
	Completada CHAR(1),
	CodigoReferencia INTEGER,
	NumeroPedido INTEGER,
	IdLineaCompra INTEGER,
	PRIMARY KEY(Id),
	FOREIGN KEY(CodigoReferencia) REFERENCES REFERENCIA(Codigo),
	FOREIGN KEY(NumeroPedido) REFERENCES PEDIDO(Numero),
	FOREIGN KEY(IdLineaCompra) REFERENCES LINEACOMPRA(Id)
);

INSERT INTO PERSONA VALUES ('123456789','Pepe','Rodriguez Perez','Calle Falsa 123','999999999','email@email.com','ES34-999');
INSERT INTO PERSONA VALUES ('987654321','Luis','Fernanzdez Lopez','Calle Falsa 124','999999998','email2@email.com','ES34-9998');
INSERT INTO EMPLEADO VALUES ('123456789','password','2020-06-06');
INSERT INTO EMPLEADO VALUES ('987654321','password','2020-06-06');
INSERT INTO ROLESENEMPRESA VALUES ('2020-06-06','123456789',1);
INSERT INTO VINCULACIONCONLAEMPRESA VALUES ('2020-06-06','123456789',1);
INSERT INTO DISPONIBILIDADEMPLEADO VALUES ('2020-06-06','2020-07-06','123456789',3);
INSERT INTO DISPONIBILIDADEMPLEADO VALUES ('2020-06-06','2020-07-06','987654321',1);
INSERT INTO ABONADO(openIDref,nif) VALUES ('referencia0','123456789');
INSERT INTO FACTURA(FechaEmision,Importe,Estado,FechaPago,IdExtractoBancario) VALUES ('2020-06-06',20.00,1,'2020-07-06','extractobancario');
INSERT INTO FACTURA(FechaEmision,Importe,Estado,FechaPago,IdExtractoBancario) VALUES ('2019-05-06',20.00,1,'2019-07-06','extractobancario');
INSERT INTO DENOMINACIONORIGEN(Nombre) VALUES ('rueda');
INSERT INTO PREFERENCIA VALUES (1,1,1);
INSERT INTO BODEGA(Nombre,CIF,Direccion) VALUES ('bodega','111111111','calle falsa 0');
INSERT INTO VINO(NombreComercial,Ano,Comentario,IdDenominacion,Categoria,IdBodega) VALUES ('retola',2014,'vinazo',1,1,1);
INSERT INTO REFERENCIA(EsPorCajas,ContenidoEnCL,Precio,Disponible,VinoId) VALUES ('1',33,10.0,'1',1);
INSERT INTO COMPRA(FechaInicioCompra,RecibidaCompleta,FechaCompraCompletada,Importe,Pagada,FechaPago,IdProveedor) VALUES ('2020-06-06','1','2020-06-07',20.0,'1','2020-06-07',1);
INSERT INTO COMPRA(FechaInicioCompra,RecibidaCompleta,FechaCompraCompletada,Importe,Pagada,FechaPago,IdProveedor) VALUES ('2019-05-06','1','2019-05-07',20.0,'0','2020-06-07',1);
INSERT INTO LINEACOMPRA(Unidades,Recibida,FechaRecepcion,IdCompra,CodigoReferencia) VALUES (1,'1','2020-06-07',1,1);
INSERT INTO LINEACOMPRA(Unidades,Recibida,FechaRecepcion,IdCompra,CodigoReferencia) VALUES (1,'1','2019-05-07',2,1);
INSERT INTO LINEACOMPRA(Unidades,Recibida,FechaRecepcion,IdCompra,CodigoReferencia) VALUES (1,'1','2019-05-07',2,1);
INSERT INTO PEDIDO(Estado,FechaRealizacion,NotaEntrega,Importe,FechaRecepcion,FechaEntrega,NumeroFactura,NumeroAbonado) VALUES (1,'2020-06-06','fragil',10.0,'2020-06-07','2020-06-07',1,1);
INSERT INTO PEDIDO(Estado,FechaRealizacion,NotaEntrega,Importe,FechaRecepcion,FechaEntrega,NumeroFactura,NumeroAbonado) VALUES (2,'2020-06-06','fragil',10.0,'2020-06-07','2020-06-07',1,1);
INSERT INTO PEDIDO(Estado,FechaRealizacion,NotaEntrega,Importe,FechaRecepcion,FechaEntrega,NumeroFactura,NumeroAbonado) VALUES (2,'2020-06-06','fragil',10.0,'2020-06-07','2020-06-07',2,1);
INSERT INTO LINEAPEDIDO(Unidades,Completada,CodigoReferencia,NumeroPedido,IdLineaCompra) VALUES (1,'1',1,1,1);
INSERT INTO LINEAPEDIDO(Unidades,Completada,CodigoReferencia,NumeroPedido,IdLineaCompra) VALUES (2,'1',1,2,1);
INSERT INTO LINEAPEDIDO(Unidades,Completada,CodigoReferencia,NumeroPedido,IdLineaCompra) VALUES (3,'1',1,3,1);


