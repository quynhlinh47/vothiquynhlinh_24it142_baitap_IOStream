package baitap_AES_RSA.bai3;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Base64;

public class EncryptionGUI extends JFrame {
    private JTextField inputField;
    private JTextArea encryptedArea, decryptedArea;
    private final String secretKey = "1234567890123456"; // AES 128-bit key

    public EncryptionGUI() {
        setTitle("Mã hóa & Giải mã AES - Đa luồng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Nhập dữ liệu"));
        inputPanel.add(inputField, BorderLayout.CENTER);

        // Output
        encryptedArea = new JTextArea(4, 20);
        decryptedArea = new JTextArea(4, 20);
        encryptedArea.setLineWrap(true);
        decryptedArea.setLineWrap(true);
        encryptedArea.setWrapStyleWord(true);
        decryptedArea.setWrapStyleWord(true);

        JPanel outputPanel = new JPanel(new GridLayout(2, 1));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Kết quả"));
        outputPanel.add(new JScrollPane(encryptedArea));
        outputPanel.add(new JScrollPane(decryptedArea));

        // Buttons
        JButton encryptBtn = new JButton("Encrypt");
        JButton decryptBtn = new JButton("Decrypt");

        encryptBtn.addActionListener(this::encryptAction);
        decryptBtn.addActionListener(this::decryptAction);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(encryptBtn);
        buttonPanel.add(decryptBtn);

        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void encryptAction(ActionEvent e) {
        String input = inputField.getText();
        Thread encryptThread = new Thread(() -> {
            try {
                String encrypted = encrypt(input);
                SwingUtilities.invokeLater(() -> encryptedArea.setText("Encrypted: " + encrypted));
            } catch (Exception ex) {
                showError("Lỗi mã hóa: " + ex.getMessage());
            }
        });
        encryptThread.start();
    }

    private void decryptAction(ActionEvent e) {
        String encryptedText = encryptedArea.getText().replace("Encrypted: ", "").trim();
        Thread decryptThread = new Thread(() -> {
            try {
                String decrypted = decrypt(encryptedText);
                SwingUtilities.invokeLater(() -> decryptedArea.setText("Decrypted: " + decrypted));
            } catch (Exception ex) {
                showError("Lỗi giải mã: " + ex.getMessage());
            }
        });
        decryptThread.start();
    }

    private String encrypt(String plainText) throws Exception {
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private String decrypt(String encryptedText) throws Exception {
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getDecoder().decode(encryptedText);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    private void showError(String message) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EncryptionGUI::new);
    }
}
