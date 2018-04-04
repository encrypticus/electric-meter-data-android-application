package util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Александр on 04.04.2018.
 */

public class Message {

    Context context;

    public Message(Context context){
        this.context = context;
    }

    public void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
