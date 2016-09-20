package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class MonoAlphabeticCipher {
	
	public static void main(String[] args) {

		String key = createAlphabet(3);
		
		String text;
		int ch;
		ch = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter 1 to Encrypt and 2 to Decrypt!"));
		text = JOptionPane.showInputDialog(null, "Enter plain/cipher text to encrypt?");
		text = text.toUpperCase();
		char[] ptextchars = text.toCharArray();
		switch (ch) {
		case 1:
			for (int i = 0; i < text.length(); i++) {
				ptextchars[i] = encrypt(key, ptextchars[i]);
			}
			break;
		case 2:
			for (int i = 0; i < text.length(); i++) {
				ptextchars[i] = decrypt(key, ptextchars[i]);
			}
			break;
		default:
			System.out.println("Invalid Choice!");
		}
		System.out.println(ptextchars);
	}
	
	private static String createAlphabet(int numCharSwap) {
		
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		List<Integer> alreadySwapped = new ArrayList<>();
		
	    final int N = alphabet.length();

	    Random r = new Random();

	    for (int i = 0; i < numCharSwap; i++) {
	    	int pos;
	    	
	    	do {
	    		pos = r.nextInt(N);	    		
	    	} while(alreadySwapped.contains(pos));
	    	
    		alreadySwapped.add(pos);
	 	    	
	    	int newpos;
	        do {
	        	newpos = r.nextInt(N);
	        } while(newpos == pos);
	        
	        char arr[] = alphabet.toCharArray();
	        
	        char tmp = arr[pos];
	        arr[pos] = arr[newpos];
	        arr[newpos] = tmp;
	        alphabet = new String(arr);
	    }
		return alphabet;
	}

	private static char encrypt(String key, char a) {
		int pos = (int) a;
		pos = (pos % 91) - 65;
		if(pos < 0) return a;
		a = key.charAt(pos);
		return a;
	}

	private static char decrypt(String key, char a) {
		int ascii = 0;
		for (int i = 0; i < key.length(); i++) {
			if (key.charAt(i) == a)
				ascii = i + 65;
		}
		return (char) ascii;
	}
}