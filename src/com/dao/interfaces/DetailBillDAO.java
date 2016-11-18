package com.dao.interfaces;

import java.util.List;

import com.dao.beans.DetailBill;
import com.dao.factories.DAOException;

public interface DetailBillDAO {

    List<DetailBill> getAll() throws DAOException;

    DetailBill getById(Long id) throws DAOException;

    List<DetailBill> getByBillId(Long id) throws DAOException;

    void createBillDetail(DetailBill billDetail) throws DAOException;

    void updateBillDetail(DetailBill billDetail) throws DAOException;

    void deleteBillDetail(DetailBill billDetail) throws DAOException;
}
