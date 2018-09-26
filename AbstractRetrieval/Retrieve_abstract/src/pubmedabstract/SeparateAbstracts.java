package pubmedabstract;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class SeparateAbstracts {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            File file=new File("mental_illness_data/abstracts.txt");
            BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
            String dd;

            int abstractNum = 1;
            while ((dd=bufferedReader.readLine()) != null) {
                System.out.println(dd);
                try {
                    BufferedWriter abstractWriter = new BufferedWriter(new FileWriter("mental_illness_abstracts/abstract_" + abstractNum + ".txt"));
                    abstractWriter.append(dd);
                    abstractWriter.close();
                    abstractNum++;

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
