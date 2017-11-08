package wdad.learn.rmi.Client;

import wdad.Department;
import wdad.Employee;
import wdad.JobTitle;

import javax.xml.crypto.dsig.TransformException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface XmlDataManager extends Remote {
    int salaryAverage() throws RemoteException,TransformException; // возвращает среднюю заработную плату сотрудников организации.
    int salaryAverage(String departmentName) throws RemoteException,TransformException; // возвращает среднюю заработную плату сотрудников заданного департамента.
    void setJobTitle(Employee employee, JobTitle newJobTitle) throws RemoteException,TransformException; //изменяет должность сотрудника
    void setSalary(Employee employee, int newSalary) throws RemoteException,TransformException; // изменяет размер заработной платы сотрудника.
    void fireEmployee(Employee employee) throws RemoteException,TransformException; // удаляющий информацию о сотруднике.
    void add(Department department) throws RemoteException,TransformException; // добавляющий информацию о департаменте. Если такой департамент уже есть, добавляет (или заменяет) в него информацию по сотрудникам.
}


