package com.dao.interfaces;

import java.util.List;

import com.dao.beans.Bill;
import com.dao.factories.DAOException;

public interface BillDAO {

    List<Bill> getAll() throws DAOException;

    Bill getById(Long id) throws DAOException;

    List<Bill> getByClientId(Long id) throws DAOException;

    List<Bill> getByCarId(Long id) throws DAOException;

    void createBill(Bill bill) throws DAOException;

    void updateBill(Bill bill) throws DAOException;

    void deleteBill(Bill bill) throws DAOException;

}
