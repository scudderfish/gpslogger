package uk.org.radmee.whereisrobbie.loggers;

import java.util.ArrayList;

import org.xml.sax.ContentHandler;


public interface IGpsLoggerSaxHandler extends ContentHandler
{

	public ArrayList<GpxPoint> GetPoints();

}
