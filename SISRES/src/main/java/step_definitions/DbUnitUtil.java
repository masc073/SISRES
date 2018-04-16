package step_definitions;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

/**
 * Classe responsável por adicionar dados no banco de dados para a realização dos testes.
 *
 * @author Natália Amâncio
 */
public class DbUnitUtil
{

    private static String XML_FILE = "dbUnitData/dataset_vazio.xml";
    public static Dataset ultimo_executado;

    /** Seleciona o dataset que será executado.
     * @param dataset 
     */
    public static void selecionaDataset(Dataset dataset)
    {
        switch (dataset)
        {
            case Departamento:
                XML_FILE = "dbUnitData/dataset_departamento.xml";
                ultimo_executado = Dataset.Departamento;
                break;
            case Vazio:
                XML_FILE = "dbUnitData/dataset_vazio.xml";
                ultimo_executado = Dataset.Vazio;
                break;
            case Processo:
                XML_FILE = "dbUnitData/dataset_processo.xml";
                ultimo_executado = Dataset.Processo;
                break;
            case Requerimento:
                XML_FILE = "dbUnitData/dataset_requerimento.xml";
                ultimo_executado = Dataset.Requerimento;
                break;
            case Responsavel:
                XML_FILE = "dbUnitData/dataset_responsavel.xml";
                ultimo_executado = Dataset.Responsavel;
                break;
            case Atendimento_requerimento:
//                XML_FILE = "dbUnitData/dataset_atendimento_requerimento.xml";
                ultimo_executado = Dataset.Atendimento_requerimento;
                break;
        }
    }

    /** Reseta banco de dados.
     */
    public static void limpaBase(Connection connection)
    {
        try
        {
            Statement stmt = connection.createStatement();

            String sql;
           
            sql = "DELETE FROM processo";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM unidadeorganizacional";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM atividade";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM responsavel";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM feriado";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM requerimento";
            stmt.executeUpdate(sql);

        } catch (SQLException e)
        {
        }
    }

    /** Insere os dados no banco de dados.
     */
    @SuppressWarnings("UseSpecificCatch")
    public static void inserirDados()
    {
        Connection conn = null;
        IDatabaseConnection db_conn = null;
        String schema;
        try
        {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sisres", "root", "root");
            db_conn = new DatabaseConnection(conn);
            limpaBase(conn);
            schema = db_conn.getSchema();

            DatabaseConfig dbConfig = db_conn.getConfig();
            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());

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
