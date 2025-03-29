package LibraryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class LibraryManager {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private File xmlFile = new File("books.xml");

    public LibraryManager() {
        frame = new JFrame("Quản lý thư viện sách");
        tableModel = new DefaultTableModel(new String[]{"Tên sách", "Tác giả", "Năm", "Nhà xuất bản", "Số trang", "Thể loại", "Giá", "Mã sách"}, 0);
        table = new JTable(tableModel);

        JButton btnAdd = new JButton("Thêm sách");
        JButton btnEdit = new JButton("Sửa sách");
        JButton btnDelete = new JButton("Xóa sách");
        JButton btnDisplay = new JButton("Hiển thị sách");

        JPanel panel = new JPanel();
        panel.add(btnAdd);
        panel.add(btnEdit);
        panel.add(btnDelete);
        panel.add(btnDisplay);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        btnDisplay.addActionListener(e -> displayBooks());
        btnAdd.addActionListener(e -> addBook());
        btnEdit.addActionListener(e -> editBook());
        btnDelete.addActionListener(e -> deleteBook());
    }

    private void displayBooks() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList bookList = doc.getElementsByTagName("book");
            tableModel.setRowCount(0);

            for (int i = 0; i < bookList.getLength(); i++) {
                Element book = (Element) bookList.item(i);
                String title = book.getElementsByTagName("title").item(0).getTextContent();
                String author = book.getElementsByTagName("author").item(0).getTextContent();
                String year = book.getElementsByTagName("year").item(0).getTextContent();
                String publisher = book.getElementsByTagName("publisher").item(0).getTextContent();
                String pages = book.getElementsByTagName("pages").item(0).getTextContent();
                String genre = book.getElementsByTagName("genre").item(0).getTextContent();
                String price = book.getElementsByTagName("price").item(0).getTextContent();
                String isbn = book.getElementsByTagName("isbn").item(0).getTextContent();

                tableModel.addRow(new Object[]{title, author, year, publisher, pages, genre, price, isbn});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addBook() {
        Vector<Object> book = new Vector<>();
        book.add(JOptionPane.showInputDialog("Tên sách:"));
        book.add(JOptionPane.showInputDialog("Tác giả:"));
        book.add(JOptionPane.showInputDialog("Năm xuất bản:"));
        book.add(JOptionPane.showInputDialog("Nhà xuất bản:"));
        book.add(JOptionPane.showInputDialog("Số trang:"));
        book.add(JOptionPane.showInputDialog("Thể loại:"));
        book.add(JOptionPane.showInputDialog("Giá sách:"));
        book.add(JOptionPane.showInputDialog("Mã sách:"));

        tableModel.addRow(book);
        saveBooksToXML();
    }

    private void editBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                String value = (String) JOptionPane.showInputDialog("Sửa " + tableModel.getColumnName(i) + ":", tableModel.getValueAt(selectedRow, i));
                tableModel.setValueAt(value, selectedRow, i);
            }
            saveBooksToXML();
        } else {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn một hàng để sửa.");
        }
    }

    private void deleteBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            saveBooksToXML();
        } else {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn một hàng để xóa.");
        }
    }

    private void saveBooksToXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("books");
            doc.appendChild(root);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Element book = doc.createElement("book");

                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    Element element = doc.createElement(tableModel.getColumnName(j).toLowerCase());
                    element.setTextContent(tableModel.getValueAt(i, j).toString());
                    book.appendChild(element);
                }

                root.appendChild(book);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LibraryManager();
    }
}

