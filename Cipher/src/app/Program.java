package app;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.Random;

public class Program {

	public static void main(String[] args) {

		String filename;
		String cipheredFilePath;
		boolean mayICipher;
		
		if (args.length < 3)
			System.exit(1);

		if (args[0] == null || !(args[0] instanceof String)) {
			System.err.println("First argument is invalid.");
			System.exit(1);
		}
		
		filename = args[0];

		if (args[1] == null || !(args[1] instanceof String)) {
			System.err.println("Second argument is invalid.");
			System.exit(1);
		}
		
		cipheredFilePath = args[1];
		
		if (args[2] == null || !(args[2] instanceof String)) {
			System.err.println("Third argument is invalid.");
			System.exit(1);
		}
		
		mayICipher = (!"-d".equals(args[2]));
		
		
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
		
		if(mayICipher) {
			cipher(file, newFile);
		} else {
			decipher(file, newFile);
		}
		
	}
	
	public static void cipher(File file, File newFile) {
		
		int key = new Random(new Date().getTime()).nextInt(100);
		
		try(Writer writer = new FileWriter(newFile)) {
			
			try(Reader reader = new FileReader(file)) {
				writer.write(key + "#");
				
				int intLetter; // Should be in the range 0 to 65535 (0x00-0xffff)
				while((intLetter = reader.read()) != -1) {
					writer.write((char) intLetter+key);
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public static void decipher(File file, File newFile) {
		
		int key = getKey(file);
		
		try(Writer writer = new FileWriter(newFile)) {
			
			try(Reader reader = new FileReader(file)) {
				int intLetter; // Should be in the range 0 to 65535 (0x00-0xffff)
				while((intLetter = reader.read()) != -1) {
					writer.write((char) intLetter-key);
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.exit(1);
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
			int intLetter;
			
			while((intLetter = reader.read()) != -1) {
				if(intLetter == (int) '#')
					break;
				
				sKey += (char) intLetter; 
			}
			
			key = Integer.parseUnsignedInt(sKey);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		if (key == -1) {
			System.err.println("Unnable to decipher. I failed you.");
			System.exit(1);
		}
			
		return key;
	}
}
