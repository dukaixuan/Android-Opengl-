package demo.dkx.com.mytest;

import android.app.Activity;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;


public class MainActivity extends Activity {

//    TextView tv;
    Rotate3d rotate;
    GLSurfaceView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得窗口对象
        Window myWindow = this.getWindow();
        //设置Flag标识
        myWindow.setFlags(flag, flag);

        tv=new GLSurfaceView(this);
        tv.setRenderer(new MyGl(this));
        setContentView(tv);

//        tv = (TextView) findViewById(R.id.tv);
//        tv= (GLSurfaceView) findViewById(R.id.tv);

        rotate = new Rotate3d();
        rotate.setDuration(10000);
        rotate.setRepeatCount(-1);
//        tv.measure(0, 0);
    }

    class Rotate3d extends Animation {
    private float mCenterX = 0;
    private float mCenterY = 0;

    public void setCenter(float centerX, float centerY) {
        mCenterX = centerX;
        mCenterY = centerY;
    }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            Matrix matrix = t.getMatrix();
            Camera camera = new Camera();
            camera.save();
            camera.rotateY(360 * interpolatedTime);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-mCenterX, -mCenterY);
            Log.i("TTT",mCenterX+"  "+mCenterY);
            matrix.postTranslate(mCenterX, mCenterY);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doClick(View v) {
        rotate.setCenter(tv.getMeasuredWidth() / 2.0f, tv.getMeasuredHeight() / 2.0f);
        rotate.setFillAfter(true); // 使动画结束后定在最终画面，如果不设置为true，则将会回到初始画面
        tv.startAnimation(rotate);
        rotate.start();
    }
}
