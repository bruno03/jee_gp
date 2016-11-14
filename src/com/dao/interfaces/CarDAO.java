package com.dao.interfaces;

import java.util.List;

import com.dao.beans.Car;
import com.dao.factories.DAOException;

public interface CarDAO {

    List<Car> getAll() throws DAOException;

    Car getById(Long id) throws DAOException;

    List<Car> getCarsByClientId(Long id) throws DAOException;

    void createCar(Car car) throws DAOException;
}
