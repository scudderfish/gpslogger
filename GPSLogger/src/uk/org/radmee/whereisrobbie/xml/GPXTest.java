package uk.org.radmee.whereisrobbie.xml;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import uk.org.radmee.whereisrobbie.xml.gpx.Gpx;

public class GPXTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Serializer serializer = new Persister();
		File example = new File("/Users/dgs/Dropbox/test.gpx");
		try
		{
			Gpx gpx= serializer.read(Gpx.class, example);
			System.out.println(gpx.toString());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
