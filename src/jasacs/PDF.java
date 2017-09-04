/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.openide.util.Exceptions;

/**
 *
 * @author 1412625
 */
public class PDF {
    public static void main(String[] args) throws DocumentException, IOException {
    //     String File_To_Convert = "WebContent/index.html";
              	String xhtml=  "<table border='1' cellpadding='1' cellspacing='1' style='width:100%;'><tbody><tr><td>test</td><td>test</td></tr><tr><td>test</td><td>test</td></tr><tr><td>test</td><td>test</td></tr></tbody></table>";    	 

      // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("hellopdf.pdf"));
        // step 3
        document.open();
        // step 4
      
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream("index.html"), new FileInputStream("style.css")); 
        
        
        //step 5
         document.close();
        System.out.println( "PDF Created!" );
    }
    public void createPDF(){
    
        try {
            Document document = new Document();  
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("pdf.pdf"));
            document.open();
         XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream("index.html"), new FileInputStream("style.css"));
            

            document.close();
            
            System.out.println( "PDF Created!" );
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException | DocumentException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    public void createPDF(String desc, String fos, String directory){

        FileOutputStream destination =  null;
        File b = new File(fos);
        String nx = desc.substring(0, desc.indexOf("'s ")).trim();
        try {
            String dc =  directory+"\\" +nx+".pdf";
            System.out.println("dest path ----------------------------------------======================"+dc);
            destination = new FileOutputStream(new File(dc));
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, destination);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(fos), new FileInputStream("style.css"));           
            document.close();
            System.out.println( "PDF Created!" );
            Path path = Paths.get(fos);
            Files.deleteIfExists(path);
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (DocumentException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            try {
                destination.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }         
        } 
}