package guru.dalaigalama.barcodeneutralizer;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback,
        Camera.PictureCallback
{
    private Camera cam;
    private SurfaceHolder holder;

    @Override
    protected void onPause()
    {
        super.onPause();
        if (cam != null)
        {
            cam.stopPreview();
            cam.release();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        SurfaceView preView = (SurfaceView) findViewById(R.id.camView);
        holder = preView.getHolder();
        holder.addCallback(this);
    }

    private Camera getCameraInstance()
    {
        Camera c = null;
        try
        {
            c = Camera.open();
        }
        catch (Exception e)
        {
            String fail = (String) getResources().getText(R.string.cam_fail);
            System.err.println(fail);
            e.printStackTrace();
        }
        return c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        cam = getCameraInstance();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        cam.stopPreview();
        Camera.Parameters params = cam.getParameters();
        params.setPreviewSize(width, height);
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
        cam.setParameters(params);
        try
        {
            cam.setPreviewDisplay(holder);
        }
        catch (IOException e)
        {
            String fail = (String) getResources().getText(R.string.preview_fail);
            System.err.println(fail);
            e.printStackTrace();
        }
        cam.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }
}
