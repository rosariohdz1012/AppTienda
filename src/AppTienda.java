import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppTienda {

    Conexion conectando = Conexion.getConexion();//Obtener conexion

    public static void main(String[] args) {

        AppTienda tiendita = new AppTienda();
        JTextArea jtProductos = new JTextArea(10,30);
        JScrollPane jsProductos = new JScrollPane(jtProductos);

        int opcion = 0;
        String menu = "Selecciona una opcion del menu: " +
                "\n1. Registrar producto. "
                + "\n2. Mostrar productos. "
                + "\n3. Salir";

        while (opcion!=3){
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null,
                    menu,"Registro de productos",JOptionPane.INFORMATION_MESSAGE));

            switch (opcion){
                case 1:
                    String nombre = JOptionPane.showInputDialog(null,"Ingresar nombre de producto",
                            "Registro",JOptionPane.INFORMATION_MESSAGE);
                    float precio = Float.parseFloat(JOptionPane.showInputDialog(null,"Ingresar precio",
                            "Registro",JOptionPane.INFORMATION_MESSAGE));
                    int cantidad = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingresar cantidad",
                            "Registro",JOptionPane.INFORMATION_MESSAGE));
                    String caducidad = JOptionPane.showInputDialog(null,"Ingresar la fecha de caducidad \n" +
                            "Ejemplo: 2015-12-23",
                            "Registro",JOptionPane.INFORMATION_MESSAGE);

                    tiendita.registrarProducto(nombre,precio,cantidad,caducidad);
                    break;
                case 2:
                    jtProductos.setText("Producto\tPrecio\tCantidad\tCaducidad\n");
                    jtProductos.append(tiendita.listar());
                    JOptionPane.showMessageDialog(null,jsProductos,
                            "Lista productos",JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 3:

                    JOptionPane.showMessageDialog(null,"Gracias por usar la aplicacion",
                            "Adios",JOptionPane.INFORMATION_MESSAGE);
                    opcion=3;
                    break;
                default:
                    JOptionPane.showMessageDialog(null,"Selecciona una opcion del menu.",
                            "Advertencia",JOptionPane.WARNING_MESSAGE);
            }
        }
              System.exit(0);

    }

    public void registrarProducto(String nombre, float precio, int cantidad, String caducidad){
        Connection cn = conectando.conectarBD();//Realiza y guarda la conexion para ser utilizada
        String consulta = "INSERT INTO producto (nombre,precio,cantidad,caducidad) values (?, ?, ?, ?)";//consulta con parametros
        PreparedStatement registrar;//Utilizar cuando nuestra consulta se integran parametos


        try {
           registrar = cn.prepareStatement(consulta);//prepara la consulta
           registrar.setString(1,nombre);//se ingresan los parametros a la columna de la tabla de la consulta
           registrar.setFloat(2,precio);
           registrar.setInt(3,cantidad);
           registrar.setString(4,caducidad);
           registrar.executeLargeUpdate();//Ejecuta la consulta y realiza la actualizacion de la tabla
           JOptionPane.showMessageDialog(null,"Producto guardado con exito.",
                   "Mensaje",JOptionPane.INFORMATION_MESSAGE);
           registrar.close();
           cn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Revisar los datos ingresados.",
                    "Advertencia al registrar",JOptionPane.WARNING_MESSAGE);
        }
    }

    public String listar (){
        Connection cn = conectando.conectarBD();//Se realiza y guarda la conexcion para ser utilizada
        String consulta = "select * from producto ";//Consulta
        String listaProducto="-------------------------------------------------------------------------------\n";
        try {
            PreparedStatement ps = cn.prepareStatement(consulta);//Preparacion de la consulta
            ResultSet productos = ps.executeQuery();// Ejecucion de la consulta y obtenner la lista de productos

            while (productos.next()){//Se recorre la lista de productos que estan en Resulset productos
                listaProducto += "" + productos.getString("nombre") + "\t"
                    + productos.getString("precio") + "\t"
                    + productos.getString("cantidad")+ "\t"
                    + productos.getString("caducidad") + "\n";
            }
            cn.close();
            ps.close();
            productos.close();

        } catch (SQLException e) {

        }catch (Exception e){

        }
        return listaProducto;//Se retorna la cadena con los productos
    }
}