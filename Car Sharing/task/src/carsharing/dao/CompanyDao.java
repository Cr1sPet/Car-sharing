package carsharing.dao;

import carsharing.entity.Company;

import java.util.List;

public interface CompanyDao {
    void addCompany(Company company);
    Company getByCompanyId(int i);
    List<Company> getAllCompanies();
}
