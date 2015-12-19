package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.Random;

public class Program {

	public static void main(String[] args) {

		String filename;
		String cipheredFilePath;
		boolean mayIDecipher;
		
		if (args.length < 2)
			System.exit(1);

		if (args[0] == null || !(args[0] instanceof String)) {
			System.err.println("First argument is invalid. It must be the path to an existing file");
			System.exit(1);
		}
		
		filename = args[0];

		if (args[1] == null || !(args[1] instanceof String)) {
			System.err.println("Second argument is invalid. It must be the path to a non-existing file");
			System.exit(1);
		}
		
		cipheredFilePath = args[1];
		
		if(args.length == 3) {
			if ((args[2] == null || !(args[2] instanceof String))) {
				System.err.println("Third argument is invalid.");
				System.err.println("Valid Arguments: \n\t\t[-d] to decipher a file. Ignoring it..");
				mayIDecipher = false;
			} else {
				mayIDecipher = ("-d".equals(args[2]));
			}
		} else {
			mayIDecipher = false;
		}
		
		File file = new File(filename);

		if(file.isDirectory() || !file.isFile()) {
			System.err.println("First argument must be an existing file.");
			System.exit(1);
		}
			
		File newFile = new File(cipheredFilePath);
		
		if(newFile.exists()) {
			System.err.println(newFile.getAbsolutePath() + " already existis.");
			System.exit(1);
		}
		
		if(!mayIDecipher) {
			cipher(file, newFile);
		} else {
			decipher(file, newFile);
		}
		
	}
	
	public static void cipher(File file, File newFile) {
		
		int key = new Random(new Date().getTime()).nextInt(100);
		
		try(FileOutputStream writer = new FileOutputStream(newFile)) {
			
			writer.write(key);
			writer.write('#');
			
			try(FileInputStream reader = new FileInputStream(file)) {
				
				int bLetter;
				while((bLetter = reader.read()) != -1) {
					writer.write(bLetter+key);
				}
			} 
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public static void decipher(File file, File newFile) {
		
		int key = getKey(file);
		
		try(FileOutputStream writer = new FileOutputStream(newFile)) {
			
			try(FileInputStream reader = new FileInputStream(file)) {
				int bLetter;
				while((bLetter = reader.read()) != -1) {
					if(bLetter == '#')
						break;
				}
				while((bLetter = reader.read()) != -1) {
					writer.write(bLetter-key);
				}
			}
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public static int getKey(File file) {
		int key = -1;
		String sKey = "";
		
		try(Reader reader = new FileReader(file)) {
			int bLetter;
			
			while((bLetter = reader.read()) != -1) {
				if(bLetter == '#')
					break;
				
				sKey += bLetter; 
			}
			
			key = Integer.parseUnsignedInt(sKey);
		} catch (Exception e) {
			System.err.println("This file has not been ciphered by me.");
			System.exit(1);
		}
		
		if (key == -1) {
			System.err.println("This file has not been ciphered by me.");
			System.exit(1);
		}
			
		return key;
	}
	
}