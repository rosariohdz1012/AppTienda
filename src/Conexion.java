import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    //variables para la conexion
    private static Connection conexion;//Permite conectarnos ala base de datos (java.sql)
    private static Conexion cn;//Variable para crear una sola instancia de conexion
    //Variables de configuracion
    private static  final String DRIVER="com.mysql.cj.jdbc.Driver";//Driver de concexion
    private static final String DB = "jdbc:mysql://localhost/bd_tienda";//Direccion de base de datos
    private static final String USUARIO = "root";//Usuario de base de datos
    private static final String CONTRASENIA = "";//Contrasenia de base de datos

    private Conexion(){
        //Constructor privado, evita que se cree una instancia fuera de esta clase
    }

    public Connection conectarBD(){
        //Metodo que devuelve la conexion de java.sql
        try{
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(DB,USUARIO,CONTRASENIA);
            System.out.println("Conexion Exitosa.. ");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getException());
        } catch (SQLException e) {
            System.out.println("Conexion fallida a la base de datos");
        }
        return conexion;
    }

    public static Conexion getConexion(){
        if(cn==null){
            cn = new Conexion();//Se crea la instancia unica a utilizar
        }
        return cn;
    }

    public void cerrarConexion() throws SQLException{
        //Metodo para cerrar la conexion ala base de datos
        try {
            conexion.close();
        }catch (Exception e) {
            System.out.println("Conexion cerrada con exito..");
            conexion.close();
        }finally {
            conexion.close();
        }
    }
}
