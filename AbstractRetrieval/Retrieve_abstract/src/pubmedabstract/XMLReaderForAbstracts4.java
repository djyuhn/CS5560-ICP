package pubmedabstract;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class XMLReaderForAbstracts4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter("mental_illness_data/abstracts.txt"));
		    File file = new File("mental_illness_data/abstracts.xml");

		    if(file.exists()) {
		        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        DocumentBuilder db = dbf.newDocumentBuilder();
		        Document doc = db.parse(file);
		        doc.getDocumentElement().normalize();
		        System.out.println("Root element " + doc.getDocumentElement().getNodeName());
		        NodeList nodeLst = doc.getElementsByTagName("PubmedArticle");
		        for (int s = 0; s < nodeLst.getLength(); s++) {
		            Node fstNode = nodeLst.item(s);

		            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
		                Element fstElmnt = (Element) fstNode;
		                NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("MedlineCitation");
		                Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		                NodeList articleNodeList = fstNmElmnt.getElementsByTagName("Article");
		                String IDs = null;
		                for(int t=0;t<articleNodeList.getLength();t++) {
		                    Element articleElement = (Element)articleNodeList.item(t);
		                    NodeList abstractNodeList=articleElement.getElementsByTagName("Abstract");
		                    if (abstractNodeList.item(0).getNodeType()==Node.ELEMENT_NODE) {
		                        Element abstractElement = (Element)abstractNodeList.item(t);
		                        NodeList abstract2=abstractElement.getElementsByTagName("AbstractText");
		                        for(int t1=0;t1<fstNmElmntLst.getLength();t1++) {
		                            StringBuilder text = new StringBuilder();
		                            Element abstrElement = (Element) abstract2.item(t1);
		                            NodeList fstNm = abstrElement.getChildNodes();
		                            text.append(fstNm.item(0).getNodeValue());
		                            writer.write(text.toString());
		                            writer.write("\n");
		                            System.out.println(text.toString());
		                        }
		                    }
		                }
		                System.out.println("Done");
		            }
		        }
		        writer.close();
		    }
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
