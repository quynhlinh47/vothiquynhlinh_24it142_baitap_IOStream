package XML;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Scanner;

//1. Viết 1 chương trình ghi dữ liệu vào 1 file XML
//Yêu cầu: Chương trình cho phép nhập các thông tin của sinh viên: id, name, age, major,….
//Sau khi nhập vào thì thêm thông tin của sinh viên mới vào file XML, nếu file XML chưa tồn tại thì tự động tạo file mới.

public class StudentXMLWriter {
    private static final String FILE_NAME = "student.xml";

    public static void main(String[] args) {
        try {
            File file = new File(FILE_NAME);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            Element root;

            if (file.exists()) {
                doc = builder.parse(file);
                root = doc.getDocumentElement();
            } else {
                doc = builder.newDocument();
                root = doc.createElement("students");
                doc.appendChild(root);
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Student ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Age: ");
            String age = scanner.nextLine();
            System.out.print("Enter Major: ");
            String major = scanner.nextLine();

            Element student = doc.createElement("student");
            student.setAttribute("id", id);

            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(name));
            student.appendChild(nameElement);

            Element ageElement = doc.createElement("age");
            ageElement.appendChild(doc.createTextNode(age));
            student.appendChild(ageElement);

            Element majorElement = doc.createElement("major");
            majorElement.appendChild(doc.createTextNode(major));
            student.appendChild(majorElement);

            root.appendChild(student);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_NAME));
            transformer.transform(source, result);

            System.out.println("Student data saved successfully to " + FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
