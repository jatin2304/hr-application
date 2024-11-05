package com.tm.hr.bl.managers;
public class Managers
{
private enum ManagerType{DESIGNATION,EMPLOYEE};
public static ManagerType DESIGNATION=ManagerType.DESIGNATION;
public static ManagerType EMPLOYEE=ManagerType.EMPLOYEE;
public static String getManagerType(ManagerType managerType)
{
if(managerType==DESIGNATION) return "DesignationManager";
else if(managerType==EMPLOYEE) return "EmployeeManager";
return "";
}
//inner class for designation
static public class Designation
{
private enum Action{ADD_DESIGNATION,
UPDATE_DESIGNATION,
REMOVE_DESIGNATION,
GET_DESIGNATION_BY_CODE,
GET_DESIGNATION_BY_TITLE,
GET_DESIGNATION_COUNT,
GET_DESIGNATIONS,
DESIGNATION_CODE_EXISTS,
DESIGNATION_TITLE_EXISTS};
public static Action ADD_DESIGNATION=Action.ADD_DESIGNATION;
public static Action UPDATE_DESIGNATION=Action.UPDATE_DESIGNATION;
public static Action REMOVE_DESIGNATION=Action.REMOVE_DESIGNATION;
public static Action GET_DESIGNATION_BY_CODE=Action.GET_DESIGNATION_BY_CODE;
public static Action GET_DESIGNATION_BY_TITLE=Action.GET_DESIGNATION_BY_TITLE;
public static Action GET_DESIGNATION_COUNT=Action.GET_DESIGNATION_COUNT;
public static Action DESIGNATION_CODE_EXISTS=Action.DESIGNATION_CODE_EXISTS;
public static Action DESIGNATION_TITLE_EXISTS=Action.DESIGNATION_TITLE_EXISTS;
public static Action GET_DESIGNATIONS=Action.GET_DESIGNATIONS;

public static String getAction(Designation.Action action)
{
if(action==Designation.ADD_DESIGNATION) return "addDesignation";
else if(action==Designation.UPDATE_DESIGNATION) return "updateDesignation";
else if(action==Designation.REMOVE_DESIGNATION) return "removeDesignation";
else if(action==Designation.GET_DESIGNATION_BY_CODE) return "getDesignationByCode";
else if(action==Designation.GET_DESIGNATION_BY_TITLE) return "getDesignationByTitle";
else if(action==Designation.GET_DESIGNATION_COUNT) return "getDesignationCount";
else if(action==Designation.DESIGNATION_CODE_EXISTS) return "designationCodeExists";
else if(action==Designation.DESIGNATION_TITLE_EXISTS) return "designationTitleExists";
else if(action==Designation.GET_DESIGNATIONS) return "getDesignations";
return " ";
}
}
//inner class for employee
static public class Employee
{
private enum Action{
ADD_EMPLOYEE,
UPDATE_EMPLOYEE,
REMOVE_EMPLOYEE,
GET_EMPLOYEE_BY_CODE,
GET_EMPLOYEE_BY_TITLE,
GET_EMPLOYEE_COUNT,
GET_EMPLOYEES,
EMPLOYEE_CODE_EXISTS,
EMPLOYEE_TITLE_EXISTS
};
public static Action ADD_EMPLOYEE=Action.ADD_EMPLOYEE;
public static Action UPDATE_EMPLOYEE=Action.UPDATE_EMPLOYEE;
public static Action REMOVE_EMPLOYEE=Action.REMOVE_EMPLOYEE;
public static Action GET_EMPLOYEE_BY_CODE=Action.GET_EMPLOYEE_BY_CODE;
public static Action GET_EMPLOYEE_BY_TITLE=Action.GET_EMPLOYEE_BY_TITLE;
public static Action GET_EMPLOYEE_COUNT=Action.GET_EMPLOYEE_COUNT;
public static Action EMPLOYEE_CODE_EXISTS=Action.EMPLOYEE_CODE_EXISTS;
public static Action EMPLOYEE_TITLE_EXISTS=Action.EMPLOYEE_TITLE_EXISTS;
public static Action GET_EMPLOYEES=Action.GET_EMPLOYEES;

public static String getAction(Employee.Action action)
{
if(action==Employee.ADD_EMPLOYEE) return "addEmployee";
else if(action==Employee.UPDATE_EMPLOYEE) return "updateEmployee";
else if(action==Employee.REMOVE_EMPLOYEE) return "removeEmployee";
else if(action==Employee.GET_EMPLOYEE_BY_CODE) return "getEmployeeByCode";
else if(action==Employee.GET_EMPLOYEE_BY_TITLE) return "getEmployeeByTitle";
else if(action==Employee.GET_EMPLOYEE_COUNT) return "getEmployeeCount";
else if(action==Employee.EMPLOYEE_CODE_EXISTS) return "employeeCodeExists";
else if(action==Employee.EMPLOYEE_TITLE_EXISTS) return "employeeTitleExists";
else if(action==Employee.GET_EMPLOYEES) return "getEmployees";
return " ";
}
}
}



