import java.sql.*;

public class quiz_database {

    //Connects to database
    public static void main(String args[]){

        String url = "jdbc:sqlite:quiz.db";

        try{
            var conn = DriverManager.getConnection(url);
        }
        catch(SQLException e){
            System.out.println(e.getStackTrace());
        }
    }
}
