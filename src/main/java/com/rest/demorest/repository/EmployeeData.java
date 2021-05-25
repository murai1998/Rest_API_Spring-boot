package com.rest.demorest.repository;
import com.rest.demorest.helper.UploadCSV;
import com.rest.demorest.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.List;


@Repository("postgres")
public class EmployeeData implements EmployeInter{

    private final JdbcTemplate jdbcTemplate;
    public static List<Employee> DB = new ArrayList<>();
@Autowired
    public EmployeeData(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertEmployee(UUID id, Employee employee) {
    final String sql = "INSERT INTO employee (id, name, title, anniversary) VALUES(?, ?, ?,?)";
        jdbcTemplate.update(sql, new Object[]{id, employee.getName(), employee.getTitle(), employee.getAnniversary()});
        return 1;
    }

    @Override
    public List<Employee> getEmployee() {
    final String sql = "SELECT id, name, title, anniversary FROM employee";
        List<Employee> allEmployees = jdbcTemplate.query(sql, ((resultSet, i) -> {
            UUID id =UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            String title  = resultSet.getString("title");
            int anniversary = resultSet.getInt("anniversary");
            return new Employee(id, name, title, anniversary);
        }));
        return allEmployees;
    }

    @Override
    public int deleteById(UUID id) {
    final String sql = "DELETE FROM employee WHERE id = ?";
        int status = jdbcTemplate.update(sql, id);
        if(status != 0) {
            return 1;
        }
        return 0;
    }



    @Override
    public Optional<Employee> getEmployeeById(UUID id) {
    final String sql = "SELECT * FROM employee WHERE id = ?";

       Employee found = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> {
            UUID e_Id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            String title  = resultSet.getString("title");
            int anniversary = resultSet.getInt("anniversary");
            return new Employee(e_Id, name, title, anniversary);
        });

        return Optional.ofNullable(found);
    }


    @Override
    public int updateById(UUID id, Employee employeeNew) {
        final String sql = "UPDATE employee SET name = ?, title = ?, anniversary = ?  WHERE id = ?";
        int status =   jdbcTemplate.update(sql, new Object[]{ employeeNew.getName(), employeeNew.getTitle(), employeeNew.getAnniversary(), id});
        if(status != 0){
            System.out.println(status);
            return 1;
        }
        return 0;
    }


    public int saveCSV(MultipartFile file){
        try {
            final String sql = "INSERT INTO employee (id, name, title, anniversary) VALUES(?, ?, ?,?)";
            List<Employee> listEmployees = UploadCSV.cvsItems(file.getInputStream());
            System.out.println(listEmployees);
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement pStmt, int j) throws SQLException {
                   Employee employee = listEmployees.get(j);

                    pStmt.setObject(1, employee.getId());
                    pStmt.setString(2, employee.getName());
                    pStmt.setString(3, employee.getTitle());
                    pStmt.setInt(4, employee.getAnniversary());
                }

                @Override
                public int getBatchSize() {
                    return listEmployees.size();
                }

            });

            return 1;
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }

    }

}
