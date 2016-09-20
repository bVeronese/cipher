package app;

public class CaesarCipher {
	
	private static String texto = "N NGVIVQNQR QR UBWR FREN PBAFGEHVE HZ CEBTENZN AN YVATHNTRZ QR FHN" + 
								"CERSRERAPVN DHR RKRPHGR N PBQVSVPNPNB R N QRPBQVSVPNPNB QR NEDHVIBF HFNAQB" + 
								"ONFR. FRH CEBTENZN QRIR FRE PNCNM QR YRE R PBQVSVPNE NEDHVIBF GRKGB RZ" + 
								"DHNYDHRE SBEZNGNPNB, ORZ PBZB NEDHVIBF OVANEVBF. B ERFHYGNQB QRIR FRE HZ" + 
								"NEDHVIB PBQVSVPNQB PBZ B ZRFZB ABZR QB BEVTVANY.O VAQVPNAQB HZ NEDHVIB" + 
								"ONFR. CBFGNE B ERFHYGNQB SVANY AN FHN CNFGN CRFFBNY AB RHERXN";
	
	private static void descriptografar(String string) {
		
		System.out.println("Iniciando processo de quebra de criptografia\n");
		
		for(int i = 1; i <= 26; i++) {
			criptografar(string, -i);
		}
		
		System.out.println("Encerrando processo de quebra de criptografia\n");
	}
	
	private static String criptografar(String string, int chave) {
		
		chave = chave % 26 + 26;
	    StringBuilder encoded = new StringBuilder();
	    for (char i : string.toCharArray()) {
	        if (Character.isLowerCase(i)) {
	            int j = (i - 'a' + chave) % 26;
	            encoded.append((char) (j + 'a'));
	        }
	        else if(Character.isUpperCase(i)){
	            int h = (i - 'A' + chave) % 26;
	            encoded.append((char) (h + 'A'));
	        }
	        else {
	            encoded.append(i);
	        }
	    }
		    
		System.out.println(encoded + " => chave: " + Math.abs(26 - chave));
		return encoded.toString();
	}

	public static void main(String[] args) {
		
		descriptografar(texto);
		
		descriptografar(criptografar("MAMAE EU QUERO LEITE", 24));
	}

}
