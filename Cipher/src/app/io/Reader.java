package app.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader implements Runnable {

	private File file;
	private FileReader fr;
	
	public Reader(File file) {
		this.file = file;
	}
	
	@Override
	public void run() {
		
		try {
			fr = new FileReader(file);
		
			char[] a = new char[50];
			fr.read(a);
			for (char c : a) {
				System.out.println(c);
			}
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		
	}

}
