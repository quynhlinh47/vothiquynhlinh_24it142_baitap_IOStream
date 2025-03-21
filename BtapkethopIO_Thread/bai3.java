package BtapkethopIO_Thread;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//Viết chương trình sử dụng Thread để sao chép nội dung từ file nguồn sang file đích.
public class bai3 {
	public static void main(String[] args) {
        String sourceFile = "src/BtapkethopIO_Thread/source.txt";
        String destinationFile = "src/BtapkethopIO_Thread/destination.txt";
        
        FileCopyThread copyThread = new FileCopyThread(sourceFile, destinationFile);
        copyThread.start();
    
	}
}
class FileCopyThread extends Thread{
	private String srcFile;
	private String desFile;
	
	public FileCopyThread(String srcFile, String desFile) {
		this.srcFile = srcFile;
		this.desFile = desFile;
	}
	
	@Override
	public void run() {
		try(FileInputStream fis = new FileInputStream(srcFile);
		    FileOutputStream fos = new FileOutputStream(desFile)){
			
			byte[] buffer = new byte[1024];
			int bytesRead;
			while((bytesRead = fis.read(buffer)) != -1) {
				fos.write(buffer, 0 , bytesRead);
			}
			System.out.println("File copied successfully from "+ srcFile);
		} catch(IOException e){
			System.out.println("Error: "+ e.getMessage());
		}
	}
}
