drop database if exists DBKinalExpressIN5BM;
create database DBKinalExpressIN5BM;
use DBKinalExpressIN5BM;

set global time_zone = '-6:00';
SET FOREIGN_KEY_CHECKS=0;


create table Clientes
( 
	clienteID int not null primary key,
	NIT varchar(10),
    nombresCliente VARCHAR(50),
    apellidosCliente varchar(50),
    direccionCliente varchar(150),
    telefonoCliente varchar(8),
    correoCliente varchar(45)
);


select * from Clientes;
-- AGREGAR
Delimiter $$
	create procedure sp_AgregarClientes (in clienteId int, in NIT varchar(10), in nombresCliente varchar(50), in apellidosCliente varchar(50), in direccionCliente varchar(150), in telefonoCliente varchar(8), in correoCliente varchar(45))
    begin
		insert into Clientes(clienteId, NIT, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente)
			values (clienteId, NIT, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente);
            
	End $$
Delimiter ;

call sp_AgregarClientes(1, '861823-1', 'Gerardo', 'Villa', 'Pollo Campero', 44443333, 'mbolanos-2023060');
call sp_AgregarClientes(2, '861823-2', 'Jordi', 'Alba', 'Pollo Brujo', 12990099, 'jalba-2020060');

-- LISTAR

Delimiter $$
	create procedure sp_ListarClientes()
		begin
			select
            C.clienteId,
            C.NIT,
            C.nombresCliente,
            C.apellidosCliente,
            C.direccionCliente,
            C.telefonoCliente,
            C.correoCliente
            from Clientes C;
		end $$
Delimiter ;

call sp_ListarClientes ();


-- BUSCAR

Delimiter $$
create procedure sp_buscarClientes()
begin
select
	Clientes.clienteId as 'ID de Cliente:',
	Clientes.nombresCliente as 'Nombre de Cliente:',
    Clientes.telefonoCliente as 'Telefono de Cliente:'
    from Clientes
    where clienteId = clienteId;
end $$
Delimiter ;

call sp_buscarClientes();

-- Editar
DELIMITER $$
create procedure sp_editarClientes(in clienteId int, in NIT varchar(10), in nombresCliente varchar(50), in apellidosCliente varchar(50), in direccionCliente varchar(150), in telefonoCliente varchar(8), in correoCliente varchar(45))
BEGIN
	update Clientes set
		clienteId = ID,
        NIT = NIT,
        nombresCliente = Nombre,
        apellidosCliente = Apellido,
        direccionCliente = Direccion,
        telefonoCliente = Telefono,
        correoCliente = Correo
			where clienteId = clienteId;
END$$
DELIMITER ;


 alter
 
-- BORRAR

DELIMITER $$
create procedure sp_eliminarClientes(clienteId int)
BEGIN
	delete from Clientes where clienteId = clienteId;
END$$
DELIMITER ;	







create table TipoProducto
( 
	codigoTipoProducto int not null primary key,
	descripcion varchar(45)
);

-- AGREGAR
Delimiter $$
	create procedure sp_AgregarTipoProducto (in codigoTipoProducto int, in descripcion varchar(45))
    begin
		insert into TipoProducto(codigoTipoProducto, descripcion)
			values (codigoTipoProducto, descripcion);
            
	End $$
Delimiter ;

call sp_AgregarTipoProducto(1, 'Helado de crema');
call sp_AgregarTipoProducto(2, 'Helado de sandia');

-- LISTAR

Delimiter $$
	create procedure sp_ListarTipoProducto()
		begin
			select
            T.codigoTipoProducto,
            T.descripcion
            from TipoProducto T;
		end $$
Delimiter ;

call sp_ListarTipoProducto ();


-- BUSCAR

Delimiter $$
create procedure sp_buscarTipoProducto()
begin
select
	TipoProducto.codigoTipoProducto as 'CODIGO PRODUCTO:',
	TipoProducto.descripcion as 'DESCRIPCION DE CLIENTE:'
    from TipoProducto
    where codigoTipoProducto = codigoTipoProducto;
end $$
Delimiter ;

call sp_buscarTipoProducto();

-- Editar
DELIMITER $$
create procedure sp_editarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
BEGIN
	update TipoProducto set
		codigoTipoProducto = CODIGO,
        descripcion = descripcion
			where codigoTipoProducto = codigoTipoProducto;
END$$
DELIMITER ;


 alter
 
-- BORRAR

DELIMITER $$
create procedure sp_eliminarTipoProducto(codigoTipoProducto int)
BEGIN
	delete from TipoProducto where codigoTipoProducto = codigoTipoProducto;
END$$
DELIMITER ;	







create table CargoEmpleado
( 
	codigoCargoEmpleado int not null primary key,
	nombreCargo varchar(45),
        descripcionCargo varchar(45)
);


-- AGREGAR
Delimiter $$
	create procedure sp_AgregarCargoEmpleado (in codigoCargoEmpleado int, in nombreCargo varchar(45), in descripcionCargo varchar(45))
    begin
		insert into CargoEmpleado(codigoCargoEmpleado,nombreCargo, descripcionCargo)
			values (codigoCargoEmpleado,nombreCargo, descripcionCargo);
            
	End $$
Delimiter ;

call sp_AgregarCargoEmpleado(1, 'Entrega', 'El trabajador hace servicio a domicilio');
call sp_AgregarCargoEmpleado(2, 'Cobrar', 'La cajera debera cobrar');

-- LISTAR

Delimiter $$
	create procedure sp_ListarCargoEmpleado()
		begin
			select
            C.codigoCargoEmpleado,
            C.nombreCargo,
            C.descripcionCargo
            from CargoEmpleado C;
		end $$
Delimiter ;

call sp_ListarCargoEmpleado ();


-- BUSCAR

Delimiter $$
create procedure sp_buscarCargoEmpleado()
begin
select
	CargoEmpleado.codigoCargoEmpleado as 'CODIGO EMPLEADO:',
	CargoEmpleado.nombreCargo as 'NOMBRE:',
    CargoEmpleado.descripcionCargo as 'DESCRIPCION:'
    from CargoEmpleado
    where codigoCargoEmpleado = codigoCargoEmpleado;
end $$
Delimiter ;

call sp_buscarCargoEmpleado();

-- Editar
DELIMITER $$
create procedure sp_editarCargoEmpleado(in codigoCargoEmpleado int, in nombreCargo varchar(45), in descripcionCargo varchar(45))
BEGIN
	update CargoEmpleado set
		codigoCargoEmpleado = CODIGO,
        nombreCargo = NOMBRE,
        descripcionCargo = DESCRIPCION
			where codigoCargoEmpleado = codigoCargoEmpleado;
END$$
DELIMITER ;


 alter
 
-- BORRAR

DELIMITER $$
create procedure sp_eliminarCargoEmpleado(codigoCargoEmpleado int)
BEGIN
	delete from CargoEmpleado where codigoCargoEmpleado = codigoCargoEmpleado;
END$$
DELIMITER ;	







create table Compras
( 
	numeroDocumento int not null primary key,
	fechaDocumento date,
        descripcion varchar(60),
        totalDocumento decimal(10,2)
);




-- AGREGAR
Delimiter $$
	create procedure sp_AgregarCompras (in numeroDocumento int, in fechaDocumento date, in descripcion varchar(60), in totalDocumento decimal(10,2))
    begin
		insert into Compras(numeroDocumento,fechaDocumento, descripcion,totalDocumento)
			values (numeroDocumento,fechaDocumento, descripcion,totalDocumento);
            
	End $$
Delimiter ;

call sp_AgregarCompras(1, '2024-01-05', 'X persona compro un helado de chocolate', 5);
call sp_AgregarCompras(2, '2024-01-06', 'X persona compro un helado de sandia', 5);

-- LISTAR

Delimiter $$
	create procedure sp_ListarCompras()
		begin
			select
            C.numeroDocumento,
            C.fechaDocumento,
            C.descripcion,
            C.totalDocumento
            from Compras C;
		end $$
Delimiter ;

call sp_ListarCompras ();


-- BUSCAR

Delimiter $$
create procedure sp_buscarCompras()
begin
select
	Compras.numeroDocumento as 'Numero Empleado:',
	Compras.fechaDocumento as 'FECHA:',
    Compras.descripcion as 'Descripcion:',
    Compras.totalDocumento as 'Total:'
    from Compras
    where numeroDocumento = numeroDocumento;
end $$
Delimiter ;

call sp_buscarCompras();

-- Editar
DELIMITER $$
create procedure sp_editarCompras(in numeroDocumento int, in fechaDocumento date, in descripcion varchar(60), in totalDocumento decimal(10,2))
BEGIN
	update Compras set
		numeroDocumento = NUMERO,
        fechaDocumento = FECHA,
        descripcion = DESCRIPCION,
        totalDocumento = TOTAL
			where codigoCargoEmpleado = codigoCargoEmpleado;
END$$
DELIMITER ;


 alter
 
-- BORRAR

DELIMITER $$
create procedure sp_eliminarCompras(numeroDocumento int)
BEGIN
	delete from Compras where numeroDocumento = numeroDocumento;
END$$
DELIMITER ;	







CREATE TABLE Proveedores(

codigoProveedor int,
nitProveedor varchar(10),
nombreProveedor varchar(60),
apellidosProveedor varchar(60),
direccionProveedor varchar(150),
razonSocial varchar(60),
contactoPrincipal varchar(100),
paginaWeb varchar(50),
PRIMARY KEY PK_codigoProveedor (codigoProveedor)

)Engine InnoDB;



delimiter $$

create procedure sp_agregarproveedor (
    in _codigoproveedor int,
    in _nitproveedor varchar(13),
    in _nombreproveedor varchar(60),
    in _apellidosproveedor varchar(60),
    in _direccionproveedor varchar(150),
    in _razonsocial varchar(60),
    in _contactoprincipal varchar(100),
    in _paginaweb varchar(50)
)
begin
    insert into Proveedores (codigoProveedor, nitProveedor, nombreProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    values (_codigoproveedor, _nitproveedor, _nombreProveedor, _apellidosproveedor, _direccionproveedor, _razonsocial, _contactoprincipal, _paginaweb);
end $$

delimiter ;



delimiter $$
-- -----------------------Listar proveedores--------------------------------

create procedure sp_mostrarproveedor ()
begin
    select
        codigoproveedor,
        nitproveedor,
        nombreproveedor,
        apellidosproveedor,
        direccionproveedor,
        razonsocial,
        contactoprincipal,
        paginaweb
    from
        Proveedores;
end $$

delimiter ;

CALL sp_mostrarproveedor ();
.-- ---------------------Buscar Proveedores --------------------------------

delimiter $$

create procedure sp_buscarproveedor (in _codigoproveedor int)
begin
    select
        codigoproveedor,
        nitproveedor,
        nombresproveedor,
        apellidosproveedor,
        direccionproveedor,
        razonsocial,
        contactoprincipal,
        paginaweb
    from
        Proveedores
    where
        codigoproveedor = _codigoproveedor;
end $$

delimiter ;


-- -----------------------Eliminar Proveedor------------------------------
delimiter $$

create procedure sp_eliminarproveedor (in _codigoproveedor int)
begin
    delete from Proveedores
    where codigoproveedor = _codigoproveedor;
end $$

delimiter ;


-- ----------------------------Editar Proveedor------------------------------------
delimiter $$

create procedure sp_editarproveedor (
    in _codigoproveedor int,
    in _nitproveedor varchar(13),
    in _nombresproveedor varchar(60),
    in _apellidosproveedor varchar(60),
    in _direccionproveedor varchar(150),
    in _razonsocial varchar(60),
    in _contactoprincipal varchar(100),
    in _paginaweb varchar(50)
)
begin
    update Proveedores
    set
        NITProveedor = _nitproveedor,
        nombresProveedor = _nombresproveedor,
        apellidosProveedor = _apellidosproveedor,
        direccionProveedor = _direccionproveedor,
        razonSocial = _razonsocial,
        contactoPrincipal = _contactoprincipal,
        paginaWeb = _paginaweb
    where
        codigoProveedor = _codigoproveedor;
end $$

delimiter ;







CREATE TABLE Productos(
	
codigoProducto varchar(15),
descripcionProducto varchar(15),
precioUnitario decimal(10,2),
precioDocena decimal(10,2),
precioMayor decimal(10,2),
imagenProducto varchar(45),
existencia int,
codigoTipoProducto int,
codigoProveedor int,
PRIMARY KEY PK_codigoProducto (codigoProducto),
FOREIGN KEY (codigoTipoProducto) REFERENCES TipoProducto(codigoTipoProducto),
FOREIGN KEY (codigoProveedor) REFERENCES Proveedores(codigoProveedor)
    
);





CREATE TABLE DetalleCompra(

codigoDetalleCompra int,
costoUnitario decimal(10,2),
cantidad int,
codigoProducto varchar(15),
numeroDocumento int,
PRIMARY KEY PK_codigoDetalleCompra (codigoDetalleCompra),
FOREIGN KEY (codigoProducto) REFERENCES Productos(codigoProducto),
FOREIGN KEY (numeroDocumento) REFERENCES Compras(numeroDocumento)
    
);





CREATE TABLE Empleados(

codigoEmpleado int,
nombresEmpleado varchar(50),
apellidosEmpleado varchar(50),
sueldo decimal(10,2),
direccion varchar(150),
turno varchar(15),
codigoCargoEmpleado int,
PRIMARY KEY PK_codigoEmpleado (codigoEmpleado),
FOREIGN KEY (codigoCargoEmpleado) REFERENCES CargoEmpleado(codigoCargoEmpleado)

);






CREATE TABLE Factura(

numeroDeFactura int,
estado varchar(50),
totalFactura decimal(10,2),
fechaFactura varchar(45),
codigoCliente int,
codigoEmpleado int,
PRIMARY KEY PK_numeroDeFactura (numeroDeFactura),
FOREIGN KEY (codigoCliente) REFERENCES Clientes(clienteID),
FOREIGN KEY (codigoEmpleado) REFERENCES Empleados(codigoEmpleado)

);





CREATE TABLE DetalleFactura(

codigoDetalleFactura int,
precioUnitario decimal(10,2),
cantidad int,
numeroDeFactura int,
codigoProducto varchar(15),
PRIMARY KEY PK_codigoDetalleFactura (codigoDetalleFactura),
FOREIGN KEY (numeroDeFactura) REFERENCES Factura(numeroDeFactura),
FOREIGN KEY (codigoProducto) REFERENCES Productos(codigoProducto)

);





DELIMITER $$

CREATE PROCEDURE sp_agregarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_descripcionProducto VARCHAR(15),
    IN p_precioUnitario DECIMAL(10,2),
    IN p_precioDocena DECIMAL(10,2),
    IN p_precioMayor DECIMAL(10,2),
    IN p_existencia INT,
    IN p_codigoTipoProducto INT,
    IN p_codigoProveedor INT
)
BEGIN
    INSERT INTO Productos(codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, existencia, codigoTipoProducto, codigoProveedor)
    VALUES(p_codigoProducto, p_descripcionProducto, p_precioUnitario, p_precioDocena, p_precioMayor, p_existencia, p_codigoTipoProducto, p_codigoProveedor);
END$$
DELIMITER ;

CALL sp_agregarProducto('P001', 'Arroz', 5.99, 68.99, 129.99, 100, 2, 2);
CALL sp_agregarProducto('P002', 'Frijoles', 3.49, 39.99, 74.99, 150, 2, 2);
CALL sp_agregarProducto('P003', 'Aceite', 8.99, 102.99, 194.99,  80, 3, 3);
CALL sp_agregarProducto('P004', 'Leche Entera', 2.99, 32.99, 62.99, 120, 4, 4);
CALL sp_agregarProducto('P005', 'Azúcar', 4.49, 51.99, 98.99, 90, 4, 5);

-- -----------------------Listar Productos--------------------------------

Delimiter $$
create procedure sp_mostrarProductos()
	begin
    select
		p.codigoProducto,
        p.descripcionProducto,
        p.precioUnitario,
        p.precioDocena,
        p.precioMayor,
        p.existencia,
        p.codigoTipoProducto,
        p.codigoProveedor
        from
        productos p;
	end$$
Delimiter ;

call sp_mostrarProductos();

-- ----------------------------Editar Producto------------------------------------

DELIMITER $$
CREATE PROCEDURE sp_actualizarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_nuevaDescripcionProducto VARCHAR(15),
    IN p_nuevoPrecioUnitario DECIMAL(10,2),
    IN p_nuevoPrecioDocena DECIMAL(10,2),
    IN p_nuevoPrecioMayor DECIMAL(10,2),
    IN p_nuevaExistencia INT,
    IN p_nuevoCodigoTipoProducto INT,
    IN p_nuevoCodigoProveedor INT
)
BEGIN
    UPDATE Productos
    SET descripcionProducto = p_nuevaDescripcionProducto,
        precioUnitario = p_nuevoPrecioUnitario,
        precioDocena = p_nuevoPrecioDocena,
        precioMayor = p_nuevoPrecioMayor,
        existencia = p_nuevaExistencia,
        codigoTipoProducto = p_nuevoCodigoTipoProducto,
        codigoProveedor = p_nuevoCodigoProveedor
    WHERE codigoProducto = p_codigoProducto;
END$$
DELIMITER ;

call sp_actualizarProducto('P001', 'Pollo', 8.99, 69.99, 130.99, 100, 2, 2);

-- -----------------------Eliminar Producto------------------------------

Delimiter $$
CREATE PROCEDURE sp_eliminarProducto(IN _codigoProducto VARCHAR(15))
BEGIN
    DELETE FROM Productos
    WHERE codigoProducto = _codigoProducto;
END $$

DELIMITER ;




-- CRUD de DetalleCompra 
DELIMITER $$

CREATE PROCEDURE sp_crearDetalleCompra(
    IN p_codigoDetalleCompra INT,
    IN p_costoUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_codigoProducto VARCHAR(15),
    IN p_numeroDocumento INT
)
BEGIN
    INSERT INTO DetalleCompra(codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento)
    VALUES(p_codigoDetalleCompra, p_costoUnitario, p_cantidad, p_codigoProducto, p_numeroDocumento);
END$$

CALL sp_crearDetalleCompra(1, 5.99, 5, 1, 1);
CALL sp_crearDetalleCompra(2,3.49, 5, 2, 2);


CREATE PROCEDURE sp_listarDetallesCompra()
BEGIN
    SELECT * FROM DetalleCompra;
END$$

CALL sp_listarDetallesCompra();


CREATE PROCEDURE sp_actualizarDetalleCompra(
    IN p_codigoDetalleCompra INT,
    IN p_nuevoCostoUnitario DECIMAL(10,2),
    IN p_nuevaCantidad INT,
    IN p_nuevoCodigoProducto VARCHAR(15),
    IN p_nuevoNumeroDocumento INT
)
BEGIN
    UPDATE DetalleCompra
    SET costoUnitario = p_nuevoCostoUnitario,
        cantidad = p_nuevaCantidad,
        codigoProducto = p_nuevoCodigoProducto,
        numeroDocumento = p_nuevoNumeroDocumento
    WHERE codigoDetalleCompra = p_codigoDetalleCompra;
END$$

CREATE PROCEDURE sp_eliminarDetalleCompra(
    IN p_codigoDetalleCompra INT
)
BEGIN
    DELETE FROM DetalleCompra
    WHERE codigoDetalleCompra = p_codigoDetalleCompra;
END$$

DELIMITER ;

-- CRUD de Empleados

DELIMITER $$

CREATE PROCEDURE sp_crearEmpleado(
    IN p_codigoEmpleado INT,
    IN p_nombresEmpleado VARCHAR(50),
    IN p_apellidosEmpleado VARCHAR(50),
    IN p_sueldo DECIMAL(10,2),
    IN p_direccion VARCHAR(150),
    IN p_turno VARCHAR(15),
    IN p_codigoCargoEmpleado INT
)
BEGIN
    INSERT INTO Empleados(codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado)
    VALUES(p_codigoEmpleado, p_nombresEmpleado, p_apellidosEmpleado, p_sueldo, p_direccion, p_turno, p_codigoCargoEmpleado);
END$$

CREATE PROCEDURE sp_listarEmpleados()
BEGIN
    SELECT * FROM Empleados;
END$$

call sp_listarEmpleados();

CREATE PROCEDURE sp_actualizarEmpleado(
    IN p_codigoEmpleado INT,
    IN p_nuevosNombresEmpleado VARCHAR(50),
    IN p_nuevosApellidosEmpleado VARCHAR(50),
    IN p_nuevoSueldo DECIMAL(10,2),
    IN p_nuevaDireccion VARCHAR(150),
    IN p_nuevoTurno VARCHAR(15),
    IN p_nuevoCodigoCargoEmpleado INT
)
BEGIN
    UPDATE Empleados
    SET nombresEmpleado = p_nuevosNombresEmpleado,
        apellidosEmpleado = p_nuevosApellidosEmpleado,
        sueldo = p_nuevoSueldo,
        direccion = p_nuevaDireccion,
        turno = p_nuevoTurno,
        codigoCargoEmpleado = p_nuevoCodigoCargoEmpleado
    WHERE codigoEmpleado = p_codigoEmpleado;
END$$

CREATE PROCEDURE sp_eliminarEmpleado(
    IN p_codigoEmpleado INT
)
BEGIN
    DELETE FROM Empleados
    WHERE codigoEmpleado = p_codigoEmpleado;
END$$

DELIMITER ;

-- CRUD de Factura
DELIMITER $$

CREATE PROCEDURE sp_crearFactura(
    IN p_numeroDeFactura INT,
    IN p_estado VARCHAR(50),
    IN p_totalFactura DECIMAL(10,2),
    IN p_fechaFactura VARCHAR(45),
    IN p_codigoCliente INT,
    IN p_codigoEmpleado INT
)
BEGIN
    INSERT INTO Factura(numeroDeFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado)
    VALUES(p_numeroDeFactura, p_estado, p_totalFactura, p_fechaFactura, p_codigoCliente, p_codigoEmpleado);
END$$

CREATE PROCEDURE sp_listarFacturas()
BEGIN
    SELECT * FROM Factura;
END$$

call sp_listarFacturas();

CREATE PROCEDURE sp_actualizarFactura(
    IN p_numeroDeFactura INT,
    IN p_nuevoEstado VARCHAR(50),
    IN p_nuevoTotalFactura DECIMAL(10,2),
    IN p_nuevaFechaFactura VARCHAR(45),
    IN p_nuevoCodigoCliente INT,
    IN p_nuevoCodigoEmpleado INT
)
BEGIN
    UPDATE Factura
    SET estado = p_nuevoEstado,
        totalFactura = p_nuevoTotalFactura,
        fechaFactura = p_nuevaFechaFactura,
        codigoCliente = p_nuevoCodigoCliente,
        codigoEmpleado = p_nuevoCodigoEmpleado
    WHERE numeroDeFactura = p_numeroDeFactura;
END$$

CREATE PROCEDURE sp_eliminarFactura(
    IN p_numeroDeFactura INT
)
BEGIN
    DELETE FROM Factura
    WHERE numeroDeFactura = p_numeroDeFactura;
END$$

DELIMITER ;

-- CRUD de DetalleFactura 
DELIMITER $$

CREATE PROCEDURE sp_crearDetalleFactura(
    IN p_codigoDetalleFactura INT,
    IN p_precioUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_numeroDeFactura INT,
    IN p_codigoProducto VARCHAR(15)
)
BEGIN
    INSERT INTO DetalleFactura(codigoDetalleFactura, precioUnitario, cantidad, numeroDeFactura, codigoProducto)
    VALUES(p_codigoDetalleFactura, p_precioUnitario, p_cantidad, p_numeroDeFactura, p_codigoProducto);
END$$

CREATE PROCEDURE sp_listarDetallesFactura()
BEGIN
    SELECT * FROM DetalleFactura;
END$$

call sp_listarDetallesFactura();

CREATE PROCEDURE sp_actualizarDetalleFactura(
    IN p_codigoDetalleFactura INT,
    IN p_nuevoPrecioUnitario DECIMAL(10,2),
    IN p_nuevaCantidad INT,
    IN p_nuevoNumeroDeFactura INT,
    IN p_nuevoCodigoProducto VARCHAR(15)
)
BEGIN
    UPDATE DetalleFactura
    SET precioUnitario = p_nuevoPrecioUnitario,
        cantidad = p_nuevaCantidad,
        numeroDeFactura = p_nuevoNumeroDeFactura,
        codigoProducto = p_nuevoCodigoProducto
    WHERE codigoDetalleFactura = p_codigoDetalleFactura;
END$$

CREATE PROCEDURE sp_eliminarDetalleFactura(
    IN p_codigoDetalleFactura INT
)
BEGIN
    DELETE FROM DetalleFactura
    WHERE codigoDetalleFactura = p_codigoDetalleFactura;
END$$

DELIMITER ;


create table EmailProveedor(
	codigoEmailProveedor int primary key not null,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
	foreign key(codigoProveedor) REFERENCES Proveedores(codigoProveedor)
);


Delimiter $$
	create procedure sp_AgregarEmailProveedor (in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)
    begin
		insert into EmailProveedor(codigoEmailProveedor,emailProveedor,descripcion,codigoProveedor)
			values (codigoEmailProveedor,emailProveedor,descripcion,codigoProveedor);
            
	End $$
Delimiter ;

create table TelefonoProveedor(
	codigoTelefonoProveedor int primary key not null,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int,
    foreign key(codigoProveedor) references Proveedores(codigoProveedor)
);


Delimiter $$
	create procedure sp_AgregarTelefonoProveedor (in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar(8), in observaciones varchar(45), in codigoProveedor int)
    begin
		insert into TelefonoProveedor(codigoTelefonoProveedor,numeroPrincipal,numeroSecundario,observaciones,codigoProveedor)
			values (codigoTelefonoProveedor,numeroPrincipal,numeroSecundario,observaciones,codigoProveedor);
            
	End $$
Delimiter ;


call sp_AgregarEmailProveedor(1, 'Saulmata@gmail.com', 'Descripcion ejemplo', 1);
select * from EmailProveedor;
call sp_agregarproveedor(1, '0614000000', 'Gasolinera Express', 'S.A.', 'Av. Principal 123, Zona 1', 'Gasolinera Express S.A.', 'Juan Pérez', 'www.gasolineraexpress.com');
call sp_agregarproveedor(2, '0614000000', 'Distribuidora de Alimentos', 'Dialiment S.A.', 'Av. Comercial 456, Zona 2', 'Dialiment S.A.', 'María Gómez', 'www.dialiment.com');
call sp_agregarproveedor(3, '0614000000', 'Bebidas Refrescantes', 'Refrescos del Sur S.A.', 'Calle Refrescante 789, Zona 3', 'Refrescos del Sur S.A.', 'Pedro Martínez', 'www.refrescosdelsur.com');
call sp_agregarproveedor(4, '0614000000', 'Lubricantes y Aceites', 'Lubriaceites Ltda.', 'Carrera Lubricante 101, Zona 4', 'Lubriaceites Ltda.', 'Luis Rodríguez', 'www.lubriaceites.com');
call sp_agregarproveedor(5, '0614000000', 'Productos de Limpieza', 'Limpiafacil S.A.', 'Pasaje Limpio 202, Zona 5', 'Limpiafacil S.A.', 'Ana López', 'www.limpiafacil.com');
call sp_AgregarTelefonoProveedor(1, '56895623', '78451232', 'Observaciones ejemplo', 1);
call sp_crearEmpleado(1, 'Juaquin', 'Guerra', 2220.00, 'direccion ejemplo', 'Mañanero', 1);
call sp_crearEmpleado(2, 'Juan', 'Lopez', 2300.00, 'direccion ejemplo', 'Nocturno', 2);
call sp_crearFactura(1, 'pagado', '130.95', '10 de mayo 2024', 1, 1);
call sp_crearFactura(2, 'pagado', '345.65', '13 de mayo 2024', 2, 2);
call sp_crearDetalleFactura(1, 8.00, 3, 1, 1);
call sp_crearDetalleFactura(2, 16.00, 5, 2, 2);

DELIMITER $$
create function fn_totalFactura(factId int) returns decimal(10,2) deterministic
BEGIN
	declare total decimal(10,2) default 0.0;
    declare i int default 1;
    declare detFactId int;
    declare precio decimal(10,2);

    factLoop : loop
        if factId = (select facturaId from detalleFactura where detalleFacturaId = i) then
			set precio = (select P.precio from productos P where productoId = (select productoId from detalleFactura where detalleFacturaId = i));
			set total = total + precio;
        end if;
		if i = (select count(facturaId) from detalleFactura) then
			leave factLoop;
		end if;
        set i = i + 1;
    end loop factLoop;
    return total;
END $$
DELIMITER ;

