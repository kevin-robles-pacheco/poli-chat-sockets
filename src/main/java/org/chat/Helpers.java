package org.chat;

import org.chat.DTO.CiudadDTO;
import org.chat.DTO.EmpleadoDTO;
import org.chat.DTO.PaisDTO;

import java.io.DataInputStream;
import java.io.IOException;
import java.sql.Connection;

import static org.chat.Servidor.mostrarTexto;

public class Helpers {

    public void listadoDeOpciones(Servidor servidor, Servidor.ClientHandler cliente){
        servidor.enviarMensajeATodos("Por favor digite el numero con la acción que desea realizar: \n"+
                "1. Ingresar al Chat\n"+
                "2. Insertar Datos\n"+
                "3. Consultar Empleados\n"+
                "4. Eliminar Empleados\n"+
                "0. Regresar\n",
                cliente);
    }

    public void mostrarOpciones(Servidor servidor, Servidor.ClientHandler cliente, DataInputStream entrada, String nombreUsuario) throws IOException {
        String mensaje = "";
        do{
            listadoDeOpciones(servidor, cliente);
            mensaje = entrada.readUTF();

            if(!mensaje.isEmpty()) {
                switch (mensaje){
                    case "1":
                        break;
                    case "2":
                        insertarDatos(servidor, cliente, entrada, nombreUsuario);
                        break;
                    case "3":
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

            if(!mensaje.isEmpty()) {
                switch (mensaje){
                    case "1":
                        crearPais(servidor, cliente, entrada, nombreUsuario);
                        break;
                    case "2":
                        crearCiudad(servidor, cliente, entrada, nombreUsuario);
                        break;
                    case "3":
                        crearLocalizacion();
                        break;
                    case "4":
                        crearDepartamento();
                        break;
                    case "5":
                        crearCargo();
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

    public void listadoDeOpcionesInsertarDatos(Servidor servidor, Servidor.ClientHandler cliente){
        servidor.enviarMensajeATodos("Ahora digite el número con la acción que desea realizar: \n"+
                "1. Insertar Pais\n"+
                "2. Insertar Ciudad\n"+
                "3. Insertar Localización\n"+
                "4. Insertar Departamento\n"+
                "5. Insertar Cargo\n"+
                "6. Insertar Empleado\n"+
                "0. Regresar\n",
                cliente
        );
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
            servidor.enviarMensajeATodos("Empleado insertado correctamente.", cliente);
            mensaje = "Empleado creado con exito";
        } else {
            servidor.enviarMensajeATodos("Error al insertar empleado.", cliente);
            mensaje = "Error al insertar empleado.";
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

    public void crearLocalizacion(){

    }

    public void crearDepartamento(){

    }

    public void crearCargo(){

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
