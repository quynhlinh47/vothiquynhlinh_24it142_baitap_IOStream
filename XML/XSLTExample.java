package XML;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
//5. Chuyển đổi XML sử dụng XSLT
public class XSLTExample {
    public static void main(String[] args) {
        try {
            // Tạo TransformerFactory
            TransformerFactory factory = TransformerFactory.newInstance();

            // Tạo Transformer từ XSLT
            Source xslt = new StreamSource(new File("D:\\workspace\\.metadata\\Java_XML\\src\\XML\\products.xsl"));
            Transformer transformer = factory.newTransformer(xslt);

            // Chuyển đổi XML thành HTML
            Source xml = new StreamSource(new File("file:///D:/workspace/.metadata/Java_XML/src/XML/products.xml"));
            transformer.transform(xml, new StreamResult(new File("file:///D:/workspace/.metadata/Java_XML/src/XML/output.html")));
            System.out.println("Transformation completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


