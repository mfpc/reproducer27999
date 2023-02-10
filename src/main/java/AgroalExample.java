import io.agroal.api.AgroalDataSource;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Path("/my-data")
@ApplicationScoped
public class AgroalExample {

    @Inject
    AgroalDataSource dataSource;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMyData() throws SQLException {
        String value = "";
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT 'test' ");

        while (resultSet.next()) {
            value = resultSet.getString(1);
        }

        System.out.println("####################################### active count: "+dataSource.getMetrics().activeCount());
        System.out.println("####################################### available count: "+dataSource.getMetrics().availableCount());

        statement.close();
        resultSet.close();
        connection.close();

        return value;
    }
}