package PO52.Zubkov.wdad;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {
    private String firstName;
    private String secondName;
    private Date hiredate;
    private int salary;
    private JobTitle jobTitle;

    public Employee(String firstName, String secondName, Date hiredate, int salary, JobTitle jobTitle) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.hiredate = hiredate;
        this.salary = salary;
        this.jobTitle = jobTitle;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getSecondName() {
        return secondName;
    }


    public Date getHiredate() {
        return hiredate;
    }


    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }
}