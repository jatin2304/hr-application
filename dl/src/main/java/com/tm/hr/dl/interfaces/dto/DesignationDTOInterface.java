package com.tm.hr.dl.interfaces.dto;
import java.io.*;
public interface DesignationDTOInterface extends Comparable<DesignationDTOInterface>, Serializable
{
public void setCode(int code);
public int getCode();
public void setTitle(String title);
public String getTitle();
}