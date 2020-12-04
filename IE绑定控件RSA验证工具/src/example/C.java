package example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.icbc.inbs.security.ICBCComputerBind;
import com.icbc.inbs.security.ICBCMark;

public class C {
	public static void main(String[] args) throws IOException{
		File file = new File("C:\\Users\\wangJin\\AppData\\Local\\encrypt_mingwen2.txt");
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[128];
		fis.read(buffer, 0, 128);
		//System.out.println("file = " + new String(buffer));
		printHexString(buffer);
		
		System.out.println("------------------------------");
		File file1 = new File("C:\\Users\\wangJin\\AppData\\Local\\encrypt_outdata.txt");
		FileInputStream fis1 = new FileInputStream(file1);
		byte[] buffer1 = new byte[128];
		fis1.read(buffer1, 0, 128);
		//System.out.println("file = " + new String(buffer));
		printHexString(buffer1);
	}
	
	public static void printHexString( byte[] b) { 
		for (int i = 0; i < b.length; i++) { 
		String hex = Integer.toHexString(b[i] & 0xFF); 
		if (hex.length() == 1) { 
		hex = '0' + hex; 
		} 
		System.out.print(hex.toUpperCase() ); 
		} 

		} 
	
	public static void test(){
		//ICBCComputerBind ic = new ICBCComputerBind(arg0, arg1, arg2);
		ICBCMark im = new ICBCMark();
		//im.
	}
}
