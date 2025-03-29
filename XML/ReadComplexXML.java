package XML;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class ReadComplexXML {
    private static final String FILE_NAME = "company.xml";

    public static void main(String[] args) {
        try {
            File file = new File(FILE_NAME);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList employeeList = doc.getElementsByTagName("employee");

            for (int i = 0; i < employeeList.getLength(); i++) {
                Node employeeNode = employeeList.item(i);
                if (employeeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element employee = (Element) employeeNode;
                    String id = employee.getAttribute("id");
                    String name = employee.getElementsByTagName("name").item(0).getTextContent();
                    
                    Element contact = (Element) employee.getElementsByTagName("contact").item(0);
                    String email = contact.getElementsByTagName("email").item(0).getTextContent();
                    String phone = contact.getElementsByTagName("phone").item(0).getTextContent();
                    
                    Element department = (Element) employee.getElementsByTagName("department").item(0);
                    String departmentName = department.getElementsByTagName("name").item(0).getTextContent();
                    String location = department.getElementsByTagName("location").item(0).getTextContent();
                    
                    System.out.println("Employee ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Email: " + email);
                    System.out.println("Phone: " + phone);
                    System.out.println("Department: " + departmentName);
                    System.out.println("Location: " + location);
                    System.out.println("---------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

