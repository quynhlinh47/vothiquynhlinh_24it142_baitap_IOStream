package XML;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Scanner;
// Tìm kiếm và xóa 1 trường dữ liệu trong XML
//Yêu cầu: Tạo 1 xml chứa các phần tử sinh viên như: id, tên, msv, lớp,…Nhập id cần xóa từ bàn phím và xóa phần tử đó trong XML

public class FindAndDeleteSt {
    private static final String FILE_NAME = "student.xml";

    public static void main(String[] args) {
        try {
            File file = new File(FILE_NAME);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Student ID to delete: ");
            String idToDelete = scanner.nextLine();

            NodeList studentList = doc.getElementsByTagName("student");
            boolean found = false;

            for (int i = 0; i < studentList.getLength(); i++) {
                Node studentNode = studentList.item(i);
                if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element student = (Element) studentNode;
                    String id = student.getAttribute("id");
                    
                    if (id.equals(idToDelete)) {
                        student.getParentNode().removeChild(student);
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
                System.out.println("Student with ID " + idToDelete + " deleted successfully.");
            } else {
                System.out.println("Student ID not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
