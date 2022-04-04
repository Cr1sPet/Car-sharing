package carsharing;

import carsharing.entity.Company;

import java.sql.SQLException;
import java.util.List;
public interface CompanyDao {
    List<Company> getList() throws SQLException, ClassNotFoundException;
    void addCompany(Company company) throws SQLException, ClassNotFoundException;
//    void deleteCompany(Company company);
}
