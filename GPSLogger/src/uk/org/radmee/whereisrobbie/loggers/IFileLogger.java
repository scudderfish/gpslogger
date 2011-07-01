package uk.org.radmee.whereisrobbie.loggers;

import android.location.Location;

public interface IFileLogger
{
	void Write(Location loc) throws Exception;
	void Annotate(String description) throws Exception;

}
