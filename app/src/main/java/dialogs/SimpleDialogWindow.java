package dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import pokazaniya.timofeev.com.pokazaniya.DbHelper;
import pokazaniya.timofeev.com.pokazaniya.R;

public class SimpleDialogWindow extends DialogFragment
{
	DbHelper db;//объект базы данных приложения
	/**
	 * объект непосредственно диалогового окна
	 */
	AlertDialog.Builder builder;
	AlertDialog dialog;
	//инициализация		
	void init(){
		db = new DbHelper(getActivity());
		builder = new AlertDialog.Builder(getActivity());
		//builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
	}

	AlertDialog createDialog(){
		dialog = builder.create();
		dialog.getWindow().getAttributes().windowAnimations = R.style.ScaleRotateAnimation;
		return dialog;
	}
	/**
     * основной метод, в котором создается диалоговое окно. Для создания непосредственно диалогового окна внутри метода
     * применяется класс AlertDialog.Builder
     *
     * @param savedInstanceState
     * @return
     */
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// инициализация
		init();
		//return builder.create();
		return null;
	}
}
