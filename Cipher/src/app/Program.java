package app;

import java.io.File;

public class Program {

	public static void main(String[] args) {

		String filename;
		
		if (args.length < 1)
			System.exit(1);

		if (args[0] == null || !(args[0] instanceof String)) {
			System.err.println("First argument is invalid.");
			System.exit(1);
		}
		
		filename = args[0]; 
		File file = new File(filename);

		if(file.isDirectory())
			System.exit(1);

	}

}