package pubmedabstract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainRetriveFromIDs1 {


	public static void main(String[] args)
	{
		try
		{
//	        	  for(int i=2014;i<=2015;i++)
//	        	{
			// URL url = new URL("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=science%5bjournal%5d+AND+cancer+AND+"+i+"%5bpdat%5d");
			URL url = new URL("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=17284678,9997&retmode=text&rettype=abstract");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");

			if (conn.getResponseCode() != 200)
			{
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String d;
			File directory = new File("./");
			System.out.println(directory.getAbsolutePath());
			File f = new File("depression_treatment_abstracts/ids.xml");
			FileWriter fw= new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw=new BufferedWriter(fw);
			while((d=br.readLine())!=null)
			{
				System.out.println(d);
				bw.append(d);
			}
			bw.close();
			conn.disconnect();
//	        	}


		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
