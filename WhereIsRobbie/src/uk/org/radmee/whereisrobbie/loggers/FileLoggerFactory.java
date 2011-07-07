package uk.org.radmee.whereisrobbie.loggers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.org.radmee.whereisrobbie.common.AppSettings;
import uk.org.radmee.whereisrobbie.common.Session;
import android.os.Environment;

public class FileLoggerFactory
{
	private static final Gpx11FileLogger GPX11_FILE_LOGGER = new Gpx11FileLogger(null, true, true);

    public static List<IFileLogger> GetFileLoggers()
	{

        
        File gpxFolder = new File(Environment.getExternalStorageDirectory(), "GPSLogger");
		if (!gpxFolder.exists())
		{
			gpxFolder.mkdirs();
		}
		
		List<IFileLogger> loggers = new ArrayList<IFileLogger>();
		
        if(AppSettings.isLogToURL())
        {
            loggers.add(new URLLogger());
        }

        if (AppSettings.shouldLogToGpx())
		{
			File gpxFile = new File(gpxFolder.getPath(), Session.getCurrentFileName() + ".gpx");
			GPX11_FILE_LOGGER.setFile(gpxFile);
			loggers.add(GPX11_FILE_LOGGER);
			
		}
		
		if(AppSettings.shouldLogToKml())
		{
			File kmlFile = new File(gpxFolder.getPath(), Session.getCurrentFileName() + ".kml");
			loggers.add(new Kml10FileLogger(kmlFile, AppSettings.shouldUseSatelliteTime()));
		}
		
		return loggers;
	}
}
