package uk.org.radmee.whereisrobbie;


import uk.org.radmee.whereisrobbie.common.Utilities;
import android.content.*;
import android.preference.PreferenceManager;



public class StartupReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		try
		{
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			boolean startImmediately = prefs.getBoolean("startonbootup", false);

			Utilities.LogInfo("Did the user ask for start on bootup? - "
					+ String.valueOf(startImmediately));

			if (startImmediately)
			{
				Utilities.LogInfo("Launching GPSLoggingService");
				Intent serviceIntent = new Intent(context, GpsLoggingService.class);
				serviceIntent.putExtra("immediate", true);
				context.startService(serviceIntent);
			}
		}
		catch (Exception ex)
		{
			Utilities.LogError("StartupReceiver", ex);

		}

	}

}
