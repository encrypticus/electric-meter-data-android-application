package pokazaniya.timofeev.com.pokazaniya.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LEDService extends Service {

    final String LOG_TAG = "log";
    Camera camera;
    Camera.Parameters parameters;
    boolean ledIsChecked = false;
    Thread thread;
    Runnable runnable;

    public LEDService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getCamera();
        Log.d(LOG_TAG, "onCreate()");
        Toast.makeText(getApplicationContext(), "service started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        runnable = new Runnable() {
            @Override
            public void run() {
                ledOn();
            }
        };
        thread = new Thread(runnable);
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ledOff();
        cameraRelease();
        if(thread != null){
            Thread end = thread;
            thread = null;
            end.interrupt();
        }
        Log.d(LOG_TAG, "onDestroy()");
        Toast.makeText(getApplicationContext(), "service stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * инициализация камеры
     */
    void getCamera() {
        if (camera == null) {
            camera = Camera.open();
            parameters = camera.getParameters();
        }
    }
    /**
     * включение led
     */
    void ledOn() {
        if (camera == null || parameters == null)
            return;//если камера не иницализирована - прервать выполнение метода
        parameters = camera.getParameters();//иницализация параметров камеры
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//включить led
        camera.setParameters(parameters);//установить камере данные параметры
        camera.startPreview();//стартануть камеру
        ledIsChecked = true;// установить флаг в значение true
    }
    /**
     * выключение led
     */
    void ledOff() {
        if (camera == null || parameters == null)
            return;//если камера не инициализирована - превать метод
        parameters = camera.getParameters();//инициализация параметров камеры
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//выключить led
        camera.setParameters(parameters);//устанавить камере данные параметры
        camera.stopPreview();//остановить камеру
        ledIsChecked = false;//установить флаг в значение false
    }
    /**
     * выгрузка камеры
     */
    void cameraRelease(){
        if (camera != null) {
            camera.release();//освободить камеру
            camera = null;
        }
    }
}
