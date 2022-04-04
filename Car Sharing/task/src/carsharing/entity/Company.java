package carsharing.entity;

import java.util.List;
import java.util.function.Supplier;

public class Company {
    private String name;
    private Integer id;

    public Company() {}

    public Company(String name) {
        this.name = name;
    }

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void printCompanyList(List<Company> companyList) {
        int i = 1;
        for (Company a : companyList) {
            System.out.println(i++ + ". " + a.getName());
        }
    }
}
