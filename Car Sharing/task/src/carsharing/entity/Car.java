package carsharing.entity;

import carsharing.dao.CompanyDaoImpl;

import java.util.List;
import java.util.function.Supplier;

public class Car {
    private String name;
    private Integer id;
    private Integer companyId;
    private int isRented;

    public Car() {}

    public Car(int isRented) {
        this.isRented = isRented;
    }


    public Car(String name) {
        this.name = name;
    }

    public Car(Integer id, Integer companyId, String name, int isRented) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.isRented = isRented;
    }
    public Car(Integer companyId, String name) {
        this.name = name;
        this.companyId = companyId;
    }

    public Company getCompany() {
        return new CompanyDaoImpl().getByCompanyId(this.companyId);
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public int isRented() {
        return isRented;
    }

    public void setRented(int rented) {
        isRented = rented;
    }

    public static void printCarList(List<Car> carList, boolean flag) {
        int i = 1;
        System.out.println();
        if (carList.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            if (flag) {
                System.out.println("Car list:");
            } else {
                System.out.println("Choose a car:");
            }
            for (Car a : carList) {
                System.out.println(i++ + ". " + a.getName());
            }
        }
    }
}

