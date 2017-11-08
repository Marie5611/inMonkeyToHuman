package wdad.learn.rmi.Server;

import wdad.Department;
import wdad.Employee;
import wdad.JobTitle;
import wdad.learn.rmi.Client.XmlDataManager;
import org.w3c.dom.*;

import javax.xml.crypto.dsig.TransformException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

public class XmlDataManagerImpl extends UnicastRemoteObject implements XmlDataManager {
    private Document doc;


    public XmlDataManagerImpl() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        if (new File("src/wdad/learn/rmi/Server/xmlExample.xml").exists()==false)
        {doc = builder.parse(new File("wdad/learn/rmi/Server/xmlExample.xml"));}
        else doc = builder.parse(new File("src/wdad/learn/rmi/Server/xmlExample.xml"));
    }

    @Override
    public int salaryAverage() {
        int salary = 0;
        NodeList nodeList = doc.getElementsByTagName("employee");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            salary += Integer.parseInt(element.getElementsByTagName("salary").item(0).getTextContent());
        }
        return salary / nodeList.getLength();
    }

    @Override
    public int salaryAverage(String departmentName) {
        int salary = 0;
        NodeList nodeList = doc.getElementsByTagName("department");
        NodeList employeeList;
        Element element;
        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            if (element.getAttribute("name").equals(departmentName)) {
                employeeList = element.getElementsByTagName("employee");
                for (int j = 0; j < employeeList.getLength(); j++) {
                    element = (Element) employeeList.item(j);
                    salary += Integer.parseInt(element.getElementsByTagName("salary").item(0).getTextContent());
                }
                return salary / employeeList.getLength();
            }
        }
        return -1;
    }

    @Override
    public void setJobTitle(Employee employee, JobTitle newJobTitle) throws TransformException {
        NodeList nodeList = doc.getElementsByTagName("employee");
        Element element;
        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            if ((element.getAttribute("firstname").equals(employee.getFirstName())) &
                    (element.getAttribute("secondname").equals(employee.getSecondName()))) {
                NamedNodeMap attribute = element.getElementsByTagName("jobtitle").item(0).getAttributes();
                Node value = attribute.getNamedItem("value");
                value.setTextContent(newJobTitle.toString());
                saveTransformXML();
                break;
            }
        }
    }

    @Override
    public void setSalary(Employee employee, int newSalary) throws TransformException {
        NodeList nodeList = doc.getElementsByTagName("employee");
        Element element;
        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            if ((element.getAttribute("firstname").equals(employee.getFirstName())) &
                    (element.getAttribute("secondname").equals(employee.getSecondName()))) {
                element.getElementsByTagName("salary").item(0).setTextContent(String.valueOf(newSalary));
                saveTransformXML();
                break;
            }
        }
    }

    @Override
    public void fireEmployee(Employee employee) throws TransformException {
        NodeList departmentList = doc.getElementsByTagName("department");
        NodeList employeeList;
        Element element;
        for (int i = 0; i < departmentList.getLength(); i++) {
            element = (Element) departmentList.item(i);
            employeeList = element.getElementsByTagName("employee");
            for (int j = 0; j < employeeList.getLength(); j++) {
                element = (Element) employeeList.item(j);
                if ((element.getAttribute("firstname").equals(employee.getFirstName())) &
                        (element.getAttribute("secondname").equals(employee.getSecondName()))) {
                    departmentList.item(i).removeChild(element);
                    saveTransformXML();
                    break;
                }
            }
        }
    }

    @Override
    public void add(Department department) throws TransformException  {
        Element elementDepart=doc.createElement("department");
        Element elementEmployee;
        Element elementTag;
        elementDepart.setAttribute("name",department.getName());
        List<Employee> employees=department.getEmployees();
        for (int i = 0; i <employees.size(); i++) {
            elementEmployee=doc.createElement("employee");
            elementEmployee.setAttribute("firstname",employees.get(i).getFirstName());
            elementEmployee.setAttribute("secondname",employees.get(i).getSecondName());
            elementTag=doc.createElement("hiredate");
            Date date=employees.get(i).getHiredate();
            elementTag.setTextContent(date.getDay()+"-"+date.getMonth()+"-"+date.getYear());
            elementEmployee.appendChild(elementTag);
            elementTag=doc.createElement("salary");
            elementTag.setTextContent(String.valueOf(employees.get(i).getSalary()));
            elementEmployee.appendChild(elementTag);
            elementTag=doc.createElement("jobtitle");
            elementTag.setAttribute("jobtitle",employees.get(i).getJobTitle().toString());
            elementEmployee.appendChild(elementTag);
            elementDepart.appendChild(elementEmployee);
        }
        Element element = (Element) doc.getElementsByTagName("organization").item(0);
        element.appendChild(elementDepart);
        saveTransformXML();
    }

    private void saveTransformXML() throws TransformException {
        StreamResult result;
        try {
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            DOMSource source = new DOMSource(doc);
            if (new File("src/wdad/learn/rmi/Server/xmlExample.xml").exists()==false)
                result = new StreamResult(new File("wdad\\learn\\rmi\\Server\\xmlExample.xml"));
            else result = new StreamResult(new File("src\\wdad\\learn\\rmi\\Server\\xmlExample.xml"));
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

