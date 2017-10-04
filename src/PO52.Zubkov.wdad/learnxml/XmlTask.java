package PO52.Zubkov.wdad.learnxml;

import org.w3c.dom.*;

import java.io.File;
import java.io.IOException;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlTask {
Document doc;
String filePath;

    public XmlTask(String filePath) throws Exception
    {
        this.filePath = filePath;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        df.setValidating(false);
        DocumentBuilder db = df.newDocumentBuilder();
        doc = db.parse(new File(filePath));
    }

    public int salaryAverage() //воhзвращает среднюю заработную плату сотрудников организации.
     {
         int salary = 0;
         NodeList nodeList = doc.getElementsByTagName("employee");
         for (int i = 0; i < nodeList.getLength(); i++) {
             Element element = (Element) nodeList.item(i);
             salary += Integer.parseInt(element.getElementsByTagName("salary").item(0).getTextContent());
         }
         return salary / nodeList.getLength();
    }

    public int salaryAverage(String departmentName) //возвращает среднюю заработную плату сотрудников заданного департамента
    {
        int salary = 0;
        NodeList nodeList = doc.getElementsByTagName("department");
        NodeList employeeList;
        Element element;
        for (int i = 0; i < nodeList.getLength(); i++) {
            element=(Element) nodeList.item(i);
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

    public void setJobTitile(String firstName, String secondName, String newJobTitle) throws TransformException
    //изменяет должность сотрудника
    {
        NodeList nodeList = doc.getElementsByTagName("employee");
        Element element;
        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            if ((element.getAttribute("firstname").equals(firstName))
                    & (element.getAttribute("secondname").equals(secondName))){
                NamedNodeMap atribute = element.getElementsByTagName("jobtitle").item(0).getAttributes();
                Node id = atribute.getNamedItem("value");
                id.setTextContent(newJobTitle);
                saveXml();
            break;
        }
        }
    }

    public void setSalary(String firstName, String secondName, int newSalary) throws TransformException{
        NodeList nodeList = doc.getElementsByTagName("employee");
        Element element;
        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            if ((element.getAttribute("firstname").equals(firstName)) &
                    (element.getAttribute("secondname").equals(secondName))) {
                element.getElementsByTagName("salary").item(0).setTextContent(String.valueOf(newSalary));
                saveXml();
                break;
            }
        }
    }

    public void fireEmployee(String firstName, String secondName) throws TransformException{

            NodeList departmentList = doc.getElementsByTagName("department");
            NodeList employeeList;
            Element element;
            for (int i = 0; i < departmentList.getLength(); i++) {
                element = (Element) departmentList.item(i);
                employeeList = element.getElementsByTagName("employee");
                for (int j = 0; j < employeeList.getLength(); j++) {
                    element = (Element) employeeList.item(j);
                    if ((element.getAttribute("firstname").equals(firstName)) &
                            (element.getAttribute("secondname").equals(secondName))) {
                        departmentList.item(i).removeChild(element);
                        saveXml();
                        break;
                    }
                }
            }
    }

    public void saveXml() throws TransformException {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
            //  System.out.println("Изменения сохранены");
        }
        catch (TransformerException ex) {
        }
    }
}
