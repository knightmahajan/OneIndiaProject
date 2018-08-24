package airlineBooking;
import java.sql.SQLException;

interface do
{
	public static final String DB_URL = "jdbc:mysql://localhost:3306/"+ "oneIndia?zeroDateTimeBehavior=CONVERT_TO_NULL";
   	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
   	public static final String USER = "root";
   	public static final String PASS = "root";
}

public class db implements do
{
	public db() throws SQLException 
	{
		try
        {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement(); 
            String g = textField.getText();
            String g1 = textField1.getText();
            String g2 = dates.getValue().toString();
            String g3 = combo.getSelectionModel().getSelectedItem().toString();
            System.out.println(g + g1 + g2 + g3);
            stmt.executeUpdate("insert into oneindia values(' " + g + " ' , ' " + g1 + " ' , ' " + g2 + " ' , ' " + g3 + " ')");
            stmt.close();
            con.close();
            System.out.println("Connection successful!");
            dispose();
      } 
        catch(ClassNotFoundException ae){System.out.println("Connection Failed!");}
	}
}