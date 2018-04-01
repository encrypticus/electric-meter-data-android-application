package dialogs;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import pokazaniya.timofeev.com.pokazaniya.*;

public class SetValueDialogWindow extends DialogWindow
{
	/**
     * создает layout из ресурса R.layout.setvalue.
	 * Этот layout содержит текстовое поле ввода,
	 * отображаемое в диалоговом окне, которое служит для
     * редактирования значения показаний
     */
	LayoutInflater inflater;
	/**
     * layout из R.layout.setvalue, который содержит
	 * текстовое поле для редактирования значения показаний
     */
	View view;
	/**
     * текстовое поле из R.layout.setvalue, служащее для редактирования значений показаний
     */
	EditText et;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		super.init();
		//установка заголовка окна
		builder.setTitle(R.string.insert);
		//установка иконки окна
		builder.setIcon(R.drawable.edit);
		inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.setvalue, null);
		et = (EditText) view.findViewById(R.id.setvalue);
		//установка содержимого окна
		builder.setView(view);
		return super.createDialog();
	}
	
}
