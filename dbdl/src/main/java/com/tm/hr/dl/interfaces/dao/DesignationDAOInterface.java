package com.tm.hr.dl.interfaces.dao;
import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import java.util.*;
public interface DesignationDAOInterface
{
public void add(DesignationDTOInterface ddto) throws DAOException;
public void update(DesignationDTOInterface ddto) throws DAOException;
public void delete(int code) throws DAOException;
public Set<DesignationDTOInterface> getAll() throws DAOException;
public DesignationDTOInterface getByCode(int code) throws DAOException;
public DesignationDTOInterface getByTitle(String title) throws DAOException;
public boolean codeExists(int code) throws DAOException;
public boolean titleExists(String title) throws DAOException;
public int getCount() throws DAOException;

}