package pokazaniya.timofeev.com.pokazaniya;
import android.appwidget.*;
import android.content.*;
import android.widget.*;
import android.graphics.*;
import android.app.*;

public class Mwidget extends AppWidgetProvider
{

	@Override
	public void onEnabled(Context context)
	{
		// TODO: Implement this method
		super.onEnabled(context);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		// TODO: Implement this method
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		int length = appWidgetIds.length;
		for(int i=0;i<length;i++){
			updateWidget(context, appWidgetManager, appWidgetIds[i]);
		}

	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds)
	{
		// TODO: Implement this method
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context)
	{
		// TODO: Implement this method
		super.onDisabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		// TODO: Implement this method
		super.onReceive(context, intent);
	}

	static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);
		views.setOnClickPendingIntent(R.id.widgetImage, pending);
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}


}
