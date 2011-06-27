package uk.org.radmee.gpslogger.senders.url;

import uk.org.radmee.gpslogger.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class URLSettingsActivity extends PreferenceActivity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.urlsettings);
	}
}
