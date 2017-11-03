package step_definitions;

import com.mysql.cj.jdbc.MysqlDataSourceFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
/**
 *
 * @author Natália
 */
public class DbUnitUtil
{
    private static final String XML_FILE = "dbUnitData/dataset.xml";

    @SuppressWarnings("UseSpecificCatch")
    public static void inserirDados()
    {
        Connection conn = null;
        IDatabaseConnection db_conn = null;
        try
        {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sisres?serverTimezone=UTC", "root", "root");
            db_conn = new DatabaseConnection(conn);
            
             DatabaseConfig dbConfig = db_conn.getConfig();
             dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory() );
            
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            IDataSet dataSet = builder.build(new File(XML_FILE));
            DatabaseOperation.CLEAN_INSERT.execute(db_conn, dataSet);
        } catch (Exception ex)
        {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();
                }

                if (db_conn != null)
                {
                    db_conn.close();
                }
            } catch (SQLException ex)
            {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        }
    }
}