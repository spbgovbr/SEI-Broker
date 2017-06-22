package br.gov.ans.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

public class PDFUtil {
	
	public static byte[] getPDF(byte[] bytes) throws DocumentException{
		Tidy tidy = new Tidy();
		tidy.setXHTML(true);
		
		Document document = tidy.parseDOM(new ByteArrayInputStream(bytes), null);
		
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(document, null);
		renderer.layout(); 
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);
		
		return outputStream.toByteArray();
	}
}
