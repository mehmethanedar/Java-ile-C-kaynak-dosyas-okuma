/**
*
* @author B171210111  - Mehmet Nuri HANEDAR
* @author G161210084  - Barış PEKDEMİR
* @since 01.03.2019
* <p>
* Java ile C kaynak dosyasını okuayarak içerisindeki parametreleri fonksiyonları ve 
* operatör sayılarını okuyarak uygun ekran çıktılarını hazırladık.
* </p>
*/
package odev1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Odev1 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        
        BufferedReader br= new BufferedReader(new FileReader("Program.c"));
        int operator=0,fonksiyon=0,parametre=0;
        String satir;
        int yorum=0;
        
        //satır satır dosya okuma işlemi
        while((satir=br.readLine())!=null){
            
            //operatör sayılarını bulma
            for (int i = 0; i < satir.length()-1; i++) {
                
                //eğer bir yorum satırı ise bir sonraki satıra geçme
                if (satir.charAt(i)=='/'&&satir.charAt(i+1)=='/') {
                    break;
                }
                
                //eğer bir yorum satırı başlangıcı ise yorum satırı kapanana kadar bekletir
                if (satir.charAt(i)=='/'&&satir.charAt(i+1)=='*') {
                    yorum++;
                }
                if (yorum==0&&(satir.charAt(i)=='+'||satir.charAt(i)=='-'||satir.charAt(i)=='/'||satir.charAt(i)=='*'||satir.charAt(i)=='&'||satir.charAt(i)=='='||satir.charAt(i)=='<'||satir.charAt(i)=='>'||satir.charAt(i)=='!'||satir.charAt(i)=='|')) {
                    operator++;
                }
                if (yorum==0&&((satir.charAt(i)=='+'&&satir.charAt(i+1)=='+')||(satir.charAt(i)=='-'&&satir.charAt(i+1)=='-')||(satir.charAt(i)=='='&&satir.charAt(i+1)=='=')||(satir.charAt(i)=='&'&&satir.charAt(i+1)=='&')||(satir.charAt(i)=='|'&&satir.charAt(i+1)=='|'))) {
                    operator--;
                }
                
                //yorum satırının bitiş noktası
                if (yorum!=0&&(satir.charAt(i)=='*'&&satir.charAt(i+1)=='/')) {
                    yorum=0;
                }
            }
            
            String regex="^\\s{0,}([\\w.*]+)\\s{0,}([\\w.*]+)\\(([\\p{L}\\p{N}\\s\\*]{0,},\\s{0,}){0,}([\\p{L}\\p{N}\\s]{0,}),{0,}(([\\w.*]+)\\s([\\w.*]+)){0,}\\)\\s{0,}(\\{){0,}";
            Pattern kalip=Pattern.compile("^("+regex+")$");
            Matcher esl=kalip.matcher(satir);

            //Fonksiyon ve parametre sayılarını bulma
            for (int j = 0; j < satir.length()-1; j++) {
                
                //eğer bir yorum satırı ise bir sonraki satıra geçme
                if (satir.charAt(j)=='/'&&satir.charAt(j+1)=='/') {
                    break;
                }
                
                //eğer bir yorum satırı başlangıcı ise yorum satırı kapanana kadar bekletir
                if (satir.charAt(j)=='/'&&satir.charAt(j+1)=='*') {
                    yorum++;
                }
                if (yorum==0) {
                    while (esl.find()){
                        fonksiyon++;                
                        for(int i = 0; i < satir.length()-1;i++){
                            if(satir.substring(i,i+1).equals(","))
                                parametre++;  
                            if (satir.substring(i,i+1).equals("(")&&satir.substring(i+1,i+2).equals(")")) {
                                parametre--;
                            }
                        }
                        parametre++;
                    }
                }
                
                //yorum satırının bitiş noktası
                if (yorum!=0&&(satir.charAt(j)=='*'&&satir.charAt(j+1)=='/')) {
                    yorum=0;
                }
            }
        }
        
        System.out.println("Toplam Operatör Sayısı: "+operator);
        System.out.println("Toplam Fonksiyon Sayısı: "+fonksiyon);
        System.out.println("Toplam Parametre Sayısı: "+parametre);
        System.out.println("Fonksiyon İsimleri:");
        
        br.close();
        BufferedReader oku= new BufferedReader(new FileReader("Program.c"));
        String satir2;
        int gecici=0;
        
        // dosya okuyarak parametreleri ekran çıktısında yazdırma
        while((satir2=oku.readLine())!=null){
            String regex="^\\s{0,}([\\w.*]+)\\s{0,}([\\w.*]+)\\(([\\p{L}\\p{N}\\s\\*]{0,},\\s{0,}){0,}([\\p{L}\\p{N}\\s]{0,}),{0,}(([\\w.*]+)\\s([\\w.*]+)){0,}\\)\\s{0,}(\\{){0,}";
            Pattern kalip=Pattern.compile("^("+regex+")$");
            Matcher esl2=kalip.matcher(satir2);
            
            // Önce yorum satırı olup olmadığını kontrol etme işlemi
            for (int k = 0; k < satir2.length()-1; k++) {
                if (satir2.charAt(k)=='/'&&satir2.charAt(k+1)=='*') {
                    yorum++;
                }
                
                // Yorum satırı yok ise
                if (yorum==0) {
                    while (esl2.find()){
                    System.out.print(esl2.group(3)+" - Parametreler :");
                    
                        // parametre değerlerini ekrana yazdırma
                        for(int i = 0; i < satir2.length()-1;i++){
                            if(satir2.substring(i,i+1).equals(" ")){
                                gecici=i;
                                for (int j = i+1; j < satir2.length(); j++) {
                                    if (satir2.substring(j,j+1).equals(" ")) {
                                        i=j-1;
                                        break;
                                    }
                                    else if (satir2.substring(j,j+1).equals("(")&&satir2.substring(j+1,j+2).equals(")")) {
                                        break;
                                    }
                                    else if (satir2.substring(j,j+1).equals(",")||satir2.substring(j,j+1).equals(")")) {
                                        if (satir2.substring(j,j+1).equals(")"))
                                            System.out.print(satir2.substring(gecici,j));
                                        else
                                            System.out.print(satir2.substring(gecici,j+1));
                                    }
                                }
                            }                     
                        }
                        System.out.println("");
                    }
                }
                if (yorum!=0&&(satir2.charAt(k)=='*'&&satir2.charAt(k+1)=='/')) {
                    yorum=0;
                }
            }
        }        
        oku.close();
    }    
}
