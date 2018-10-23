package pubmedabstract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


public class RetrieveAbstracts3 {


  public static void main(String[] args)
	    {
            int numOfAbstracts = 100; // The number of Abstracts to retrieve

	        try
	        {
	        	File file=new File("mental_illness_ids/ids.txt");
	        	BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
	        	String dd;
	        	String IDs = null;
	        	int i=0,j=1,k=1;
	        	while ((dd=bufferedReader.readLine())!=null || i < numOfAbstracts) {
                    System.out.println(dd);
                    if (i == 0)
                        IDs = dd;
                    else {
                        IDs += "," + dd;
                    }
                    i++;
                }

                if(i==(numOfAbstracts)) { // Retrieved the 10 abstract ids from the text file

                    URL url = new URL("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=" + IDs + "&retmode=xml&rettype=abstract");
                    System.out.println(url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/xml");

                    if (conn.getResponseCode() != 200)
                    {
                        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String d;
                    File f = new File("mental_illness_data/abstracts.xml");
                    FileWriter fw= new FileWriter(f.getAbsoluteFile());
                    BufferedWriter bw=new BufferedWriter(fw);
                    while((d=br.readLine())!=null) {
                        System.out.println(d);
                        bw.append(d);
                    }
                    bw.close();
                    conn.disconnect();
				}
	        	System.out.println("Done");

	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
}
