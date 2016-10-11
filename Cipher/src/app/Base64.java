package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Base64 {

	private static String BASE64CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    public static String codificar(byte[] arqBytes) {
    	
    	System.out.println( "Iniciando codificação" );
    	
        StringBuilder saida = new StringBuilder(( arqBytes.length * 4 ) / 3);
        int ponteiro;
        for ( int i = 0; i < arqBytes.length; i += 3 ) {
            ponteiro = ( arqBytes[ i ] & 0xFC ) >> 2;
            saida.append( BASE64CHARS.charAt( ponteiro ) );
            ponteiro = ( arqBytes[ i ] & 0x03 ) << 4;
            if ( i + 1 >= arqBytes.length ) {
            	saida.append( BASE64CHARS.charAt( ponteiro ) ).append( "==" );
            	continue;
            }
            
            ponteiro |= ( arqBytes[ i + 1 ] & 0xF0 ) >> 4;
            saida.append( BASE64CHARS.charAt( ponteiro ) );
            ponteiro = ( arqBytes[ i + 1 ] & 0x0F ) << 2;

            if ( i + 2 >= arqBytes.length ) {
            	saida.append( BASE64CHARS.charAt( ponteiro ) ).append( '=' );
            	continue;
            }
            
            ponteiro |= ( arqBytes[ i + 2 ] & 0xC0 ) >> 6;
            saida.append( BASE64CHARS.charAt( ponteiro ) );
            ponteiro = arqBytes[ i + 2 ] & 0x3F;
            saida.append( BASE64CHARS.charAt( ponteiro ) );
        }
        
        System.out.println( "Encerrando codificação: " + saida );
        return saida.toString();
    }
    
    private static String codificarArquivo( File arq ) throws Exception {
    	
    	FileInputStream fis = null;

        byte[] bFile = new byte[ ( int ) arq.length() ];

	    fis = new FileInputStream( arq );
	    fis.read( bFile );
	    fis.close();

    	return codificar( bFile );
    }
    
    public static byte[] decodificar( String textoEncodado )    {
    	   
        if ( textoEncodado.length() % 4 != 0 ) return null;

        System.out.println( "Iniciando decodificação: " + textoEncodado );
        
        int menosTamanho = 0, y = 0;
        if( textoEncodado.indexOf( '=' ) > 0 ) {
        	menosTamanho = ( textoEncodado.length() - textoEncodado.indexOf( '=' ) );
        }
        
        byte bytesDecodificados[] = new byte[ ( ( textoEncodado.length() * 3 ) / 4 ) - menosTamanho ];
        char[] txtEncArr = textoEncodado.toCharArray();
        int ponteiro[] = new int[ 4 ];
        
        for ( int i = 0; i < txtEncArr.length; i += 4 )	{
            ponteiro[ 0 ] = BASE64CHARS.indexOf( txtEncArr[ i ] );
            ponteiro[ 1 ] = BASE64CHARS.indexOf( txtEncArr[ i + 1 ] );
            ponteiro[ 2 ] = BASE64CHARS.indexOf( txtEncArr[ i + 2 ] );
            ponteiro[ 3 ] = BASE64CHARS.indexOf( txtEncArr[ i + 3 ] );
            
            bytesDecodificados[ y++ ] = ( byte ) ( ( ponteiro[ 0 ] << 2 ) | ( ponteiro[ 1 ] >> 4 ) );
            
            if ( ponteiro[ 2 ] >= 64 ) continue;
            
            bytesDecodificados[ y++ ] = ( byte ) ( ( ponteiro[ 1 ] << 4) | ( ponteiro[ 2 ] >> 2 ) );
            
            if ( ponteiro[ 3 ] >= 64 ) continue;

            bytesDecodificados[ y++ ] = ( byte ) ( ( ponteiro[ 2 ] << 6 ) | ponteiro[ 3 ] );
        }

        System.out.println( "Encerrando decodificação" );
        return bytesDecodificados;
    }
    
    public static void main( String[] args ) throws Exception {
		
    	File arq1 = new File( "D:\\Testando.txt" );
    	String stringona = codificarArquivo( arq1 );

    	File arq2 = new File( "D:\\TestandoDecodado.txt" );
    	FileOutputStream fos = new FileOutputStream( arq2 );
    	fos.write( decodificar( stringona ) );
    	fos.close();
    	
    	System.out.println(arq1.compareTo(arq2));
	}
}
