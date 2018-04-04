package pokazaniya.timofeev.com.pokazaniya;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.view.View;

import dialogs.AddTpToDatabaseDialog;

public class SettingsActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
	}


	public void showAddTpSettingsDialog(View view){
		AddTpToDatabaseDialog addTpSettingsDialog = new AddTpToDatabaseDialog();
		addTpSettingsDialog.show(getSupportFragmentManager(), "tpSettingsDialog");
	}
	
}
