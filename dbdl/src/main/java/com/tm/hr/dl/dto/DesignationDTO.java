package com.tm.hr.dl.dto;
import com.tm.hr.dl.interfaces.dto.*;
public class DesignationDTO implements DesignationDTOInterface
{
private int code;
private String title;
public DesignationDTO()
{
this.code=0;
this.title="";
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setTitle(String title)
{
this.title=title;
}
public String getTitle()
{
return this.title;
}
public boolean equals(Object other)
{
if(!(other instanceof DesignationDTO)) return false;
DesignationDTO ddto=(DesignationDTO)other;
return this.code==ddto.getCode();
}
public int hashCode()
{
return this.code;
}
public int compareTo(DesignationDTOInterface other)
{
return this.code-other.getCode();
}
}