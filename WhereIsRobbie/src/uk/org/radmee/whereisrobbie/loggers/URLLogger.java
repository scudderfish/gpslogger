package uk.org.radmee.whereisrobbie.loggers;

import java.io.FileNotFoundException;

import uk.org.radmee.whereisrobbie.common.AppSettings;
import uk.org.radmee.whereisrobbie.common.Utilities;
import android.location.Location;

public class URLLogger implements IFileLogger
{
	@Override
	public void Write(Location loc) throws Exception
	{
		String customURLFormat = AppSettings.getCustomURL();
		if (customURLFormat == null || customURLFormat.trim().equals(""))
		{
			return;
		}
		String customURL = String.format(customURLFormat, 	loc.getLatitude(), 
															loc.getLongitude(), 
															loc.getAltitude(),
															loc.getBearing(), 
															loc.getSpeed(), 
															loc.getAccuracy(), 
															loc.getTime());
		
		try
		{
		    Utilities.GetUrl(customURL);
		}
		catch(FileNotFoundException e)
		{
		    //Swallow.
		}
	}

	@Override
	public void Annotate(String description) throws Exception
	{
		return;
	}

}
