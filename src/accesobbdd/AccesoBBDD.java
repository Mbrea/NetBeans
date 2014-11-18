package accesobbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mb
 */
public class AccesoBBDD {

    Connection cn;

    AccesoBBDD() throws SQLException {
        cn = DriverManager.getConnection("jdbc:h2:C:\\Users\\Justy\\Desktop", "sa", "");
    }

    void datosPaqDni(String dni) throws SQLException {
        PreparedStatement sql = cn.prepareStatement("select distinct p.dnid, p.descripcion, p.peso, p.alto, p.ancho, p.profundidad,p.fecha_entrega from paquete p, destinatario d where p.dnid = ? order by fecha_entrega;");
        sql.setString(1, dni);
        ResultSet rs = sql.executeQuery();
        while (rs.next()) {
            System.out.println(" ");
            System.out.println("Destinatario: "+rs.getString(1));
            System.out.print(" Descripcion: " + rs.getString(2));
            System.out.print(" Peso: " + rs.getString(3));
            System.out.print(" Altura: " + rs.getString(4));
            System.out.print(" Ancho: " + rs.getString(5));
            System.out.print(" Profundidad: " + rs.getString(6));
            System.out.print(" Fecha entrega: " + rs.getString(7));
            System.out.println(" ");
        }
    }

    void totalKg(String poblacion) throws SQLException {
        PreparedStatement sql = cn.prepareStatement("select sum(p.peso) from paquete p, destinatario d where p.dnid=d.dnid and d.poblacion=?;");
        sql.setString(1, poblacion);
        ResultSet rs = sql.executeQuery();
        System.out.println("Peso total repartido en la poblacion de " + poblacion + " es de: " + rs.getString(1));
    }

    void descripEntreFechas(String fecha1, String fecha2) throws SQLException {
        PreparedStatement sql = cn.prepareStatement("select p.descripcion, p.fecha_entrega,d.nombre, d.direccion from paquete p , destinatario d where p.dnid=d.dnid and fecha_entrega between ? and ?);");
        sql.setString(1, fecha1);
        sql.setString(2, fecha2);
        ResultSet rs = sql.executeQuery();
        while (rs.next()) {
            System.out.println("Descripcion: " + rs.getString(1));
            System.out.println("Fecha entrega: " + rs.getString(2));
            System.out.println("Nombre: " + rs.getString(3));
            System.out.println("Direccion: " + rs.getString(4));
        }
    }

    void visualizaPaqCam(String dnit) throws SQLException {
        PreparedStatement sql = cn.prepareStatement("select p.?,p.codigo,d.nombre,d.direccion,d.poblacion,t.matricula from paquetes p, destinatarios d, transportista t on where p.dnid=d.dnid and p.dnit=? ");
        sql.setString(1, dnit);
        sql.setString(2, dnit);
   
        ResultSet rs = sql.executeQuery();       
        while (rs.next()) {
            System.out.println("Codigo paquete: " + rs.getString(1));
            System.out.println("Nombre destinatario: " + rs.getString(2));
            System.out.println("Direccion destinatario: " + rs.getString(3));
            System.out.println("Poblacion destinatatio: " + rs.getString(4));
            System.out.println("Matricula camion: " + rs.getString(5)+
                    " empleado por transportista con el dni= "+dnit);
        }
    }

    public static void main(String[] args) throws SQLException {
        AccesoBBDD practica2 = new AccesoBBDD();
        practica2.datosPaqDni("11111111A");
        //practica2.totalKg("MANACOR");
        //practica2.descripEntreFechas("2013-11-10", "2014-11-10");
        //practica2.visualizaPaqCam("99999999A");
    }
}
