package org.chat;

import org.chat.DTO.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import static org.chat.Servidor.mostrarTexto;

public class Helpers {

    public void listadoDeOpciones(Servidor servidor, Servidor.ClientHandler cliente) {
        servidor.enviarMensajeATodos("Por favor digite el numero con la acción que desea realizar: \n" +
                        "1. Ingresar al Chat\n" +
                        "2. Insertar Datos\n" +
                        "3. Consultar Empleados\n" +
                        "4. Eliminar Empleados\n" +
                        "0. Regresar\n",
                cliente);
    }

    public void mostrarOpciones(Servidor servidor, Servidor.ClientHandler cliente, DataInputStream entrada, String nombreUsuario) throws IOException {
        String mensaje = "";
        do {
            listadoDeOpciones(servidor, cliente);
            mensaje = entrada.readUTF();

            if (!mensaje.isEmpty()) {
                switch (mensaje) {
                    case "1":
                        break;
                    case "2":
                        insertarDatos(servidor, cliente, entrada, nombreUsuario);
                        break;
                    case "3":
                        consultarEmpleado(servidor, cliente, entrada);
                        break;
                    case "4":
                        break;
                    case "5":
                        break;
                    default:
                        break;
                }
            }
        } while (!"0".equals(mensaje));
    }

    public void insertarDatos(Servidor servidor, Servidor.ClientHandler cliente, DataInputStream entrada, String nombreUsuario) throws IOException {
        String mensaje = "";
        do {
            listadoDeOpcionesInsertarDatos(servidor, cliente);
            mensaje = entrada.readUTF();

            if (!mensaje.isEmpty()) {
                switch (mensaje) {
                    case "1":
                        crearPais(servidor, cliente, entrada, nombreUsuario);
                        break;
                    case "2":
                        crearCiudad(servidor, cliente, entrada, nombreUsuario);
                        break;
                    case "3":
                        crearLocalizacion(servidor, cliente, entrada, nombreUsuario);
                        break;
                    case "4":
                        crearDepartamento(servidor, cliente, entrada, nombreUsuario);
                        break;
                    case "5":
                        crearCargo(servidor, cliente, entrada, nombreUsuario);
                        break;
                    case "6":
                        crearEmpleado(servidor, cliente, entrada, nombreUsuario);
                        break;
                    default:
                        break;
                }
            }
        } while (!"0".equals(mensaje));
    }

    public void listadoDeOpcionesInsertarDatos(Servidor servidor, Servidor.ClientHandler cliente) {
        servidor.enviarMensajeATodos("Ahora digite el número con la acción que desea realizar: \n" +
                        "1. Insertar Pais\n" +
                        "2. Insertar Ciudad\n" +
                        "3. Insertar Localización\n" +
                        "4. Insertar Departamento\n" +
                        "5. Insertar Cargo\n" +
                        "6. Insertar Empleado\n" +
                        "0. Regresar\n",
                cliente
        );
    }

    public void consultarEmpleado(Servidor servidor, Servidor.ClientHandler cliente, DataInputStream entrada) throws IOException {

        Connection con = DataBase.establecerConexion();
        EmpleadoDB empleadoDB = new EmpleadoDB(con);
        List<EmpleadoDTO> empleados = empleadoDB.leerEmpleados();

        if(empleados.isEmpty()){
            servidor.enviarMensajeATodos("No hay empleados creados en base de datos: ", cliente);
            return;
        }

        for (EmpleadoDTO empleadoDTO : empleados) {
            servidor.enviarMensajeATodos("Empleado encontrado: ID: [" + empleadoDTO.getEmplId() + "] Nombre: " + empleadoDTO.getEmplPrimerNombre(), cliente);
        }

        String mensaje = "";
        servidor.enviarMensajeATodos("Ingrese el ID del empleado a consultar: ", cliente);
        mensaje = entrada.readUTF();

        if(!mensaje.isEmpty()){
            int idEmpleado = Integer.parseInt(mensaje);

            EmpleadoDTO empleado = empleadoDB.obtenerEmpleado(idEmpleado);

            if (empleado != null) {
                servidor.enviarMensajeATodos("Empleado encontrado: " + empleado.getEmplPrimerNombre(), cliente);
                servidor.enviarMensajeATodos("| ID  | Nombre  | Segundo Nombre | Email        | Fecha Nac   | Sueldo | Comision | Cargo | Dpto | Gerente | Historico |", cliente);
                servidor.enviarMensajeATodos("| " + empleado.getEmplId() + "  | " + empleado.getEmplPrimerNombre() + "  | " + empleado.getEmplSegundoNombre() + " | " + empleado.getEmplEmail() + "  | Fecha Nac   | Sueldo | Comision | Cargo | Dpto | Gerente | Historico |", cliente);
            } else {
                servidor.enviarMensajeATodos("Empleado no encontrado.", cliente);
            }
        }
    }

    public void crearPais(Servidor servidor, Servidor.ClientHandler cliente, DataInputStream entrada, String nombreUsuario) throws IOException {
        String mensaje;
        PaisDTO PaisDTO = new PaisDTO();

        servidor.enviarMensajeATodos("[" + nombreUsuario + "] acción => Crear Pais", cliente);

        servidor.enviarMensajeATodos("Ingrese el nombre del país: ", cliente);
        PaisDTO.setNombre(entrada.readUTF());

        Connection con = DataBase.establecerConexion();
        PaisDB PaisDB = new PaisDB(con);

        if (PaisDB.insertarPais(PaisDTO)) {
            servidor.enviarMensajeATodos("Pais insertado correctamente.", cliente);
            mensaje = "Pais creado con exito";
        } else {
            servidor.enviarMensajeATodos("Error al insertar Pais.", cliente);
            mensaje = "Error al insertar Pais.";
        }
        mostrarTexto("[" + nombreUsuario + "] => " + mensaje);
        servidor.enviarMensajeATodos("[" + nombreUsuario + "] => " + mensaje, cliente);
    }

    public void crearCiudad(Servidor servidor, Servidor.ClientHandler cliente, DataInputStream entrada, String nombreUsuario) throws IOException {
        String mensaje;
        CiudadDTO ciudadDTO = new CiudadDTO();

        servidor.enviarMensajeATodos("[" + nombreUsuario + "] acción => Crear Ciudad", cliente);

        servidor.enviarMensajeATodos("Ingrese el nombre de la ciudad: ", cliente);
        ciudadDTO.setCiudNombre(entrada.readUTF());

        servidor.enviarMensajeATodos("Ingrese el país ID de la ciudad: ", cliente);
        ciudadDTO.setCiudPaisId(Integer.parseInt(entrada.readUTF()));

        Connection con = DataBase.establecerConexion();
        CiudadDB ciudadDB = new CiudadDB(con);

        if (ciudadDB.insertarCiudad(ciudadDTO)) {
            servidor.enviarMensajeATodos("Ciudad insertada correctamente.", cliente);
            mensaje = "Ciudad creada con exito";
        } else {
            servidor.enviarMensajeATodos("Error al insertar ciudad.", cliente);
            mensaje = "Error al insertar ciudad.";
        }
        mostrarTexto("[" + nombreUsuario + "] => " + mensaje);
        servidor.enviarMensajeATodos("[" + nombreUsuario + "] => " + mensaje, cliente);
    }

    public void crearLocalizacion(Servidor servidor, Servidor.ClientHandler cliente, DataInputStream entrada, String nombreUsuario) throws IOException {
        String mensaje;
        LocalizacionDTO LocalizacionDTO = new LocalizacionDTO();

        servidor.enviarMensajeATodos("[" + nombreUsuario + "] acción => Crear Localización", cliente);

        servidor.enviarMensajeATodos("Ingrese la dirección de la localización: ", cliente);
        LocalizacionDTO.setLocalizDireccion(entrada.readUTF());

        servidor.enviarMensajeATodos("Ingrese la Ciudad id de la localización: ", cliente);
        LocalizacionDTO.setLocalizCiudadId(Integer.parseInt(entrada.readUTF()));

        Connection con = DataBase.establecerConexion();
        LocalizacionDB LocalizacionDB = new LocalizacionDB(con);

        if (LocalizacionDB.insertarLocalizacion(LocalizacionDTO)) {
            servidor.enviarMensajeATodos("Localización insertada correctamente.", cliente);
            mensaje = "Localización creada con exito";
        } else {
            servidor.enviarMensajeATodos("Error al insertar localización.", cliente);
            mensaje = "Error al insertar localización.";
        }
        mostrarTexto("[" + nombreUsuario + "] => " + mensaje);
        servidor.enviarMensajeATodos("[" + nombreUsuario + "] => " + mensaje, cliente);

    }

    public void crearDepartamento(Servidor servidor, Servidor.ClientHandler cliente, DataInputStream entrada, String nombreUsuario) throws IOException {
        String mensaje;
        DepartamentoDTO departamentoDTO = new DepartamentoDTO();

        servidor.enviarMensajeATodos("[" + nombreUsuario + "] acción => Crear Departamento", cliente);

        servidor.enviarMensajeATodos("Ingrese el nombre del departamento: ", cliente);
        departamentoDTO.setDptoNombre(entrada.readUTF());

        servidor.enviarMensajeATodos("Ingrese el ID de la localización: ", cliente);
        departamentoDTO.setDptoLocalizId(Integer.parseInt(entrada.readUTF()));

        Connection con = DataBase.establecerConexion();
        DepartamentoDB departamentoDB = new DepartamentoDB(con);

        if (departamentoDB.insertarDepartamento(departamentoDTO)) {
            servidor.enviarMensajeATodos("Departamento insertado correctamente.", cliente);
            mensaje = "Departamento creado con exito";
        } else {
            servidor.enviarMensajeATodos("Error al insertar departamento.", cliente);
            mensaje = "Error al insertar departamento.";
        }
        mostrarTexto("[" + nombreUsuario + "] => " + mensaje);
        servidor.enviarMensajeATodos("[" + nombreUsuario + "] => " + mensaje, cliente);
    }

    public void crearCargo(Servidor servidor, Servidor.ClientHandler cliente, DataInputStream entrada, String nombreUsuario) throws IOException {
        String mensaje;
        CargoDTO cargoDTO = new CargoDTO();

        servidor.enviarMensajeATodos("[" + nombreUsuario + "] acción => Crear Cargo", cliente);

        servidor.enviarMensajeATodos("Ingrese el nombre del cargo: ", cliente);
        cargoDTO.setCargoNombre(entrada.readUTF());

        servidor.enviarMensajeATodos("Ingrese el sueldo mínimo: ", cliente);
        cargoDTO.setCargoSueldoMinimo(Double.parseDouble(entrada.readUTF()));

        servidor.enviarMensajeATodos("Ingrese el sueldo máximo: ", cliente);
        cargoDTO.setCargoSueldoMaximo(Double.parseDouble(entrada.readUTF()));

        Connection con = DataBase.establecerConexion();
        CargoDB cargoDB = new CargoDB(con);

        if (cargoDB.insertarCargo(cargoDTO)) {
            servidor.enviarMensajeATodos("Cargo insertado correctamente.", cliente);
            mensaje = "Cargo creado con exito";
        } else {
            servidor.enviarMensajeATodos("Error al insertar cargo.", cliente);
            mensaje = "Error al insertar cargo.";
        }
        mostrarTexto("[" + nombreUsuario + "] => " + mensaje);
        servidor.enviarMensajeATodos("[" + nombreUsuario + "] => " + mensaje, cliente);

    }

    public void crearEmpleado(Servidor servidor, Servidor.ClientHandler cliente, DataInputStream entrada, String nombreUsuario) throws IOException {
        String mensaje;
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();

        servidor.enviarMensajeATodos("[" + nombreUsuario + "] acción => Crear Empleado", cliente);

        servidor.enviarMensajeATodos("Ingrese el primer nombre del empleado: ", cliente);
        empleadoDTO.setEmplPrimerNombre(entrada.readUTF());

        servidor.enviarMensajeATodos("Ingrese el segundo nombre del empleado: ", cliente);
        empleadoDTO.setEmplSegundoNombre(entrada.readUTF());

        servidor.enviarMensajeATodos("Ingrese el email del empleado: ", cliente);
        empleadoDTO.setEmplEmail(entrada.readUTF());

        servidor.enviarMensajeATodos("Ingrese la fecha de nacimiento del empleado (yyyy-mm-dd): ", cliente);
        empleadoDTO.setEmplFechaNac(entrada.readUTF());

        servidor.enviarMensajeATodos("Ingrese el sueldo del empleado: ", cliente);
        empleadoDTO.setEmplSueldo(Double.parseDouble(entrada.readUTF()));

        servidor.enviarMensajeATodos("Ingrese la comisión del empleado: ", cliente);
        empleadoDTO.setEmplComision(Double.parseDouble(entrada.readUTF()));

        servidor.enviarMensajeATodos("Ingrese el cargo ID del empleado: ", cliente);
        empleadoDTO.setEmplCargoId(Integer.parseInt(entrada.readUTF()));

        servidor.enviarMensajeATodos("Ingrese el departamento ID del empleado: ", cliente);
        empleadoDTO.setEmplDptoId(Integer.parseInt(entrada.readUTF()));

        servidor.enviarMensajeATodos("Ingrese el gerente ID del empleado: ", cliente);
        empleadoDTO.setEmplGerenteId(Integer.parseInt(entrada.readUTF()));

        Connection con = DataBase.establecerConexion();
        EmpleadoDB empleadoDB = new EmpleadoDB(con);

        if (empleadoDB.insertarEmpleado(empleadoDTO)) {
            servidor.enviarMensajeATodos("Empleado insertado correctamente.", cliente);
            mensaje = "Empleado creado con exito";
        } else {
            servidor.enviarMensajeATodos("Error al insertar empleado.", cliente);
            mensaje = "Error al insertar empleado.";
        }
        mostrarTexto("[" + nombreUsuario + "] => " + mensaje);
        servidor.enviarMensajeATodos("[" + nombreUsuario + "] => " + mensaje, cliente);
    }
}
