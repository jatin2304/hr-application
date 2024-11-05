package com.tm.netcommon;
public class Request implements java.io.Serializable
{
private String manager;
private String action;
private Object [] argument;
public void setManager(String manager)
{
this.manager=manager;
}
public String getManager()
{
return this.manager;
}
public void setAction(String action)
{
this.action=action;
}
public String getAction()
{
return this.action;
}
public void setArgument(Object ...argument)
{
this.argument=argument;
}
public Object[] getArgument()
{
return this.argument;
}
}