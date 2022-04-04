package carsharing.dao;

import carsharing.entity.Car;

import java.util.List;

public interface CarDao {
    void addCar(Car car);
    void updateCarStatus(Car car);
    Car getCarById(int id);
    List<Car> getCarsByCompanyId(int company_id);
}
