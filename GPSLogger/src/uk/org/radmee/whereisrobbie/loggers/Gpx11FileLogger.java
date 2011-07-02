package uk.org.radmee.whereisrobbie.loggers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.simpleframework.xml.core.Persister;

import uk.org.radmee.whereisrobbie.common.Session;
import uk.org.radmee.whereisrobbie.common.Utilities;
import uk.org.radmee.whereisrobbie.xml.gpx.BoundsType;
import uk.org.radmee.whereisrobbie.xml.gpx.Gpx;
import uk.org.radmee.whereisrobbie.xml.gpx.Gpx.Trk;
import uk.org.radmee.whereisrobbie.xml.gpx.Gpx.Trk.Trkseg;
import uk.org.radmee.whereisrobbie.xml.gpx.Gpx.Trk.Trkseg.Trkpt;
import android.location.Location;


class Gpx11FileLogger implements IFileLogger
{
    private File gpxFile = null;
    private boolean useSatelliteTime = false;
    private Gpx gpx;
    private Persister serializer;
    
    Gpx11FileLogger(File gpxFile, boolean useSatelliteTime, boolean addNewTrackSegment)
    {
        this.gpxFile = gpxFile;
        this.useSatelliteTime = useSatelliteTime;
        this.serializer = new Persister();

        if(gpxFile.exists())
        {
            try
            {
                gpx = serializer.read(Gpx.class, this.gpxFile);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            this.gpx = new Gpx();
            gpx.setBounds(new BoundsType());
            gpx.setCreator("WhereIsRobbie");
            gpx.setVersion("1.1");
            gpx.setTime(Utilities.GetIsoDateTime(new Date()));
            Trk trk = new Trk();
            Trkseg seg = new Trkseg();
            trk.getTrkseg().add(seg);
            gpx.getTrk().add(trk );
        }
    }

    @Override
    public void Write(Location loc) throws Exception
    {
        try
        {

            Trkpt point=getPoint(loc);            
            Trk trk = gpx.getTrk().get(gpx.getTrk().size()-1);
            if (Session.shouldAddNewTrackSegment())
            {
                trk.getTrkseg().add(new Trkseg());
            }

            Trkseg seg = trk.getTrkseg().get(trk.getTrkseg().size()-1);
            seg.getTrkpt().add(point);
            FileOutputStream out = new FileOutputStream(gpxFile);
            serializer.write(this.gpx, out);
            out.close();
        }
        catch (Exception e)
        {
            Utilities.LogError("Gpx11FileLogger.Write", e);
            throw new Exception("Could not write to GPX file");
//          Log.e("Main", callingClient.getString(R.string.could_not_write_to_file) + e.getMessage());
//          callingClient.SetStatus(callingClient.getString(R.string.could_not_write_to_file)
//                  + e.getMessage());
        }

        
    }
    
    
    private Trkpt getPoint(Location loc)
    {
        Date now;

        if (useSatelliteTime)
        {
            now = new Date(loc.getTime());
        }
        else
        {
            now = new Date();
        }

        Trkpt point = new Trkpt();
        
        point.setLat(loc.getLatitude());
        point.setLon(loc.getLongitude());
       
        if (loc.hasAltitude())
        {
            point.setEle(loc.getAltitude());
        }

        if (loc.hasBearing())
        {
            point.setCourse(loc.getBearing());
        }

        if (loc.hasSpeed())
        {
            point.setSpeed(loc.getSpeed());
        }

        point.setSrc(loc.getProvider());

        if(Session.getSatelliteCount()>0)
        {
            point.setSat(Session.getSatelliteCount());
        }

        String dateTimeString = Utilities.GetIsoDateTime(now);
        point.setTime(dateTimeString);
        
        
        
        return point;
    }

    @Override
    public void Annotate(String description) throws Exception
    {
        Trk trk = gpx.getTrk().get(gpx.getTrk().size()-1);
        Trkseg seg = trk.getTrkseg().get(trk.getTrkseg().size()-1);
        Trkpt point = seg.getTrkpt().get(seg.getTrkpt().size()-1);
        if (point != null)
        {
            point.setDesc(description);
        }
    }
}