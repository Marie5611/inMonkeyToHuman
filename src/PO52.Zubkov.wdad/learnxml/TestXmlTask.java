package PO52.Zubkov.wdad.learnxml;

public class TestXmlTask {
    public static void main(String[] args) throws Exception {
        XmlTask xmlTask = new XmlTask("C:\\Users\\Евгений\\Desktop\\starting-monkey-to-human-path\\src\\PO52.Zubkov.wdad\\learnxml\\xmlExample1.xml");
        System.out.println(xmlTask.salaryAverage());
        System.out.println(xmlTask.salaryAverage("Cook table"));
        xmlTask.setJobTitile("Elisa","Merson","manager");
        xmlTask.setSalary("Elisa","Merson",2);
    }
}
