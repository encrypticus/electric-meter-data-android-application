package dialogs;
import android.app.*;
import android.os.*;
import android.view.View.*;
import android.content.*;
import android.widget.EditText;

import pokazaniya.timofeev.com.pokazaniya.R;
import util.*;
import util.Message;

public class AddTpToDatabaseDialog extends SetValueDialogWindow
{

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		super.init();
		builder.setTitle("Добавить ТП в базу данных");
		builder.setIcon(R.drawable.add_note);
		inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.setvalue, null);
		et = (EditText) view.findViewById(R.id.setvalue);
		builder.setPositiveButton("Добавить Тп", setValueListener);
		builder.setNegativeButton("Отмена", setValueListener);
		//установка содержимого окна
		builder.setView(view);
		return super.createDialog();
	}
	
	DialogInterface.OnClickListener setValueListener = new DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			switch (which){
				case Dialog.BUTTON_POSITIVE:
					String value = et.getText().toString();
					db.addTp(value);
					new Message(getContext()).showMessage("ТП добавлена в базу данных");
					break;
				case Dialog.BUTTON_NEGATIVE:
					break;
			}
		}
	
	};
	
}
