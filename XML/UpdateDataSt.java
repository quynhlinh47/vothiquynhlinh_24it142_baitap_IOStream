package XML;
//4. Cập nhật dữ liệu trong file XML
//Viết 1 chương trình cập nhật dữ liệu của 1 sinh viên dựa trên id
//Yêu cầu: Tìm kiếm bằng id sinh viên nhập từ bàn phím
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Scanner;

public class UpdateDataSt {
    private static final String FILE_NAME = "student.xml";

    public static void main(String[] args) {
        try {
            File file = new File(FILE_NAME);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Student ID to update: ");
            String idToUpdate = scanner.nextLine();

            NodeList studentList = doc.getElementsByTagName("student");
            boolean found = false;

            for (int i = 0; i < studentList.getLength(); i++) {
                Node studentNode = studentList.item(i);
                if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element student = (Element) studentNode;
                    String id = student.getAttribute("id");
                    
                    if (id.equals(idToUpdate)) {
                        System.out.print("Enter new name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new age: ");
                        String newAge = scanner.nextLine();
                        System.out.print("Enter new major: ");
                        String newMajor = scanner.nextLine();
                        
                        student.getElementsByTagName("name").item(0).setTextContent(newName);
                        student.getElementsByTagName("age").item(0).setTextContent(newAge);
                        student.getElementsByTagName("major").item(0).setTextContent(newMajor);
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(FILE_NAME));
                transformer.transform(source, result);
                System.out.println("Student with ID " + idToUpdate + " updated successfully.");
            } else {
                System.out.println("Student ID not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

