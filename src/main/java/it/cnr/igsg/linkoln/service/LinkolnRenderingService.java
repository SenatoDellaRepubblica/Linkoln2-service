package it.cnr.igsg.linkoln.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import it.cnr.igsg.linkoln.HtmlAnnotation;
import it.cnr.igsg.linkoln.Linkoln;
import it.cnr.igsg.linkoln.service.impl.HtmlPostProcessing;

public abstract class LinkolnRenderingService extends LinkolnAnnotationService {

	//A name to refer a specific renderer (like "html", "html-debug", "rdfa", etc.)
	public abstract String name();
	
	protected void postProcess() {
		
		HtmlPostProcessing hpp = new HtmlPostProcessing();
		
		hpp.setCuts(getLinkolnDocument().htmlCuts);
		
		try {
			
			hpp.yyreset(new StringReader(getOutput()));
			
			hpp.yylex();
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return;
		}
		
		ArrayList<String> finalContents = new ArrayList<String>();
		
		int index = 0;
		
		for(Object originalContent : getLinkolnDocument().originalContents) {
			
			if(originalContent instanceof HtmlAnnotation) {
				
				finalContents.add(((HtmlAnnotation) originalContent).toString());
				continue;
			}
			
			finalContents.add(hpp.getContents().get(index).toString());
			index++;
		}
		
		StringBuilder finalContent = new StringBuilder();
		
		for(String content : finalContents) {
			
			finalContent.append(content);
		}

		output = finalContent.toString().trim();
		
		if(getLinkolnDocument().isPlainText && Linkoln.HTML_ADD_HEADER) {
			
			String preOpen = "";
			String preClose = "";
			
			if(Linkoln.HTML_USE_PRE) {
				
				preOpen = "<pre>";
				preClose = "</pre>";
				
				output = output.replaceAll("\r\n", "<br/>").replaceAll("\n", "<br/>");
			}
			//Add an HTML header and footer for plain texts
			String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML//EN\" \"xhtml-strict.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<body>" + preOpen + "\n";
			String footer = "\n" + preClose + "</body></html>";
			
			output = header + output + footer;
		}		
	}
	
}
