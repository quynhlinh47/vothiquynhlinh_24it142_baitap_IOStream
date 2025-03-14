package Btap_IOStream;
import java.io.File;

public class bai_5 {
	public static void main(String[] args) {
		String folderPath = "C:\\Users\\DELL\\Documents";
		
		File folder = new File(folderPath);
		
		// kiểm tra có tồn tại ko, có phải thư mục ko?
		if(folder.exists() && folder.isDirectory()) {
			File [] files = folder.listFiles(); // lấy danh sách file và thư mucj con
			
			if(files != null && files.length > 0) {
				System.out.println("Danh sách file trong thư mục: " + folderPath);
				
				for(File file : files) {
					if(file.isFile()) {
						System.out.println("File: "+ file.getName());
					} else if(file.isDirectory()) {
						System.out.println("Thư mục: "+ file.getName());
					}
				}
			}  else {
				System.out.println("Thư mục trống.");
			}
	  } else {
		  System.out.println("Đường dẫn ko hợp lệ hoặc không phải là thư mục..");
	  }
	}
}
