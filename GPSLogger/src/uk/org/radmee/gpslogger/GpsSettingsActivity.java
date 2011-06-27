package uk.org.radmee.gpslogger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import uk.org.radmee.gpslogger.R;
import uk.org.radmee.gpslogger.common.Utilities;

public class GpsSettingsActivity extends PreferenceActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean useImperial = prefs.getBoolean("useImperial", false);

		final EditTextPreference distanceBeforeLogging = (EditTextPreference) findPreference("distance_before_logging");

		if (useImperial)
		{
			distanceBeforeLogging.setDialogTitle(R.string.settings_distance_in_feet);
			distanceBeforeLogging.getEditText().setHint(R.string.settings_enter_feet);
		}
		else
		{
			distanceBeforeLogging.setDialogTitle(R.string.settings_distance_in_meters);
			distanceBeforeLogging.getEditText().setHint(R.string.settings_enter_meters);
		}

		CheckBoxPreference imperialCheckBox = (CheckBoxPreference) findPreference("useImperial");
		imperialCheckBox.setOnPreferenceChangeListener(new OnPreferenceChangeListener()
		{

			public boolean onPreferenceChange(Preference preference, final Object newValue)
			{

				Utilities.ShowProgress(GpsSettingsActivity.this, getString(R.string.settings_converting_title),
						getString(R.string.settings_converting_description));

				new Thread()
				{

					public void run()
					{

						try
						{
							sleep(3000); // Give user time to read the message
						}
						catch (InterruptedException e)
						{

							Log.e("Settings", e.getMessage());

						}
						boolean useImp = new Boolean(newValue.toString());

						String minimumDistanceString = prefs.getString("distance_before_logging", "0");

						int minimumDistance;

						if (minimumDistanceString != null && minimumDistanceString.length() > 0)
						{
							minimumDistance = Integer.valueOf(minimumDistanceString);
						}
						else
						{
							minimumDistance = 0;
						}

						SharedPreferences.Editor editor = prefs.edit();

						if (useImp == true)
						{
							distanceBeforeLogging.setDialogTitle(R.string.settings_distance_in_feet);
							distanceBeforeLogging.getEditText().setHint(R.string.settings_enter_feet);

							minimumDistance = Utilities.MetersToFeet(minimumDistance);

						}
						else
						{
							minimumDistance = Utilities.FeetToMeters(minimumDistance);
							distanceBeforeLogging.setDialogTitle(R.string.settings_distance_in_meters);
							distanceBeforeLogging.getEditText().setHint(R.string.settings_enter_meters);

						}

						if (minimumDistance >= 9999)
						{
							minimumDistance = 9999;
						}

						editor.putString("distance_before_logging", String.valueOf(minimumDistance));

						editor.commit();

						handler.post(updateResults);
						Utilities.HideProgress();
					};
				}.start();

				return true;
			}

		});


		Preference enableDisablePref = (Preference) findPreference("enableDisableGps");

		enableDisablePref.setOnPreferenceClickListener(new OnPreferenceClickListener()
		{

			public boolean onPreferenceClick(Preference preference)
			{
				startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
				return true;
			}

		});
		
		Preference osmSetupPref = (Preference) findPreference("osm_setup");
		osmSetupPref.setOnPreferenceClickListener(new OnPreferenceClickListener()
		{
			
			public boolean onPreferenceClick(Preference preference)
			{
				startActivity(Utilities.GetOsmSettingsIntent(getBaseContext()));

				return true;
			}
		});
		Preference urlSetupPref = (Preference) findPreference("url_setup");
		urlSetupPref.setOnPreferenceClickListener(new OnPreferenceClickListener()
		{
			
			public boolean onPreferenceClick(Preference preference)
			{
				startActivity(Utilities.GetUrlSettingsIntent(getBaseContext()));

				return true;
			}
		});

	}

	private final Handler handler = new Handler();
	private final Runnable updateResults = new Runnable()
	{
		public void run()
		{
			finish();

			startActivity(getIntent());
		}

	};

}
