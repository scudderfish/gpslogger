package uk.org.radmee.whereisrobbie.senders.url;

import uk.org.radmee.whereisrobbie.R;
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
