package demo.dkx.com.mytest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 杜凯旋 on 2016/10/31.
 */
public class MyGl implements GLSurfaceView.Renderer {

    private  final  IntBuffer  mVertexBuffer;
    private final IntBuffer nBuffer;
    private final IntBuffer normals;
    Context context;
    float xrot, yrot, zrot;
    int[] texture;
    int i=0;
    boolean key = false;
    int one =0x10000;
    FloatBuffer lightAmbient= FloatBuffer.wrap(new float[]{0.5f,0.5f,0.5f,1.0f});
    FloatBuffer lightDiffuse= FloatBuffer.wrap(new float[]{0.5f,1.0f,1.0f,1.0f});
    FloatBuffer lightPosition = FloatBuffer.wrap(new float[]{0.0f,0.0f,1.0f,1.0f});
    public MyGl(Context context){
        this.context=context;
        int texCoord[]={
                one,0,0,0,0,one,one,one,
                0,0,0,one,one,one,one,0,
                one,one,one,0,0,0,0,one,
                0,one,one,one,one,0,0,0,
                0,0,0,one,one,one,one,0,
                one,0,0,0,0,one,one,one,
        };
        int vertices[]={
                -one,-one,one,
                one,-one,one,
                one,one,one,
                -one,one,one,
                -one,-one,-one,
                -one,one,-one,
                one,one,-one,
                one,-one,-one,
                -one,one,-one,
                -one,one,one,
                one,one,one,
                one,one,-one,
                -one,-one,-one,
                one,-one,-one,
                one,-one,one,
                -one,-one,one,
                one,-one,-one,
                one,one,-one,
                one,one,one,
                one,-one,one,
                -one,-one,-one,
                -one,-one,one,
                -one,one,one,
                -one,one,-one,
        };
        int nolmos[]={
                0,0,one,
                0,0,one,
                0,0,one,
                0,0,one,
                0,0,-one,
                0,0,-one,
                0,0,-one,
                0,0,-one,
                0,one,0,
                0,one,0,
                0,one,0,
                0,one,0,
                0,-one,0,
                0,-one,0,
                0,-one,0,
                0,-one,0,
                one,0,0,
                one,0,0,
                one,0,0,
                one,0,0,
                -one,0,0,
                -one,0,0,
                -one,0,0,
                -one,0,0,
        };

        ByteBuffer  vbb  =  ByteBuffer.allocateDirect(vertices.length  *  4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer  =  vbb.asIntBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        ByteBuffer  v  =  ByteBuffer.allocateDirect(texCoord.length *4);
        v.order(ByteOrder.nativeOrder());
        nBuffer  =  v.asIntBuffer();
        nBuffer.put(texCoord);
        nBuffer.position(0);
        ByteBuffer  vv  =  ByteBuffer.allocateDirect(nolmos.length *4);
        vv.order(ByteOrder.nativeOrder());
        normals  =  vv.asIntBuffer();
        normals.put(nolmos);
        normals.position(0);


    }


    ByteBuffer indices = ByteBuffer.wrap(new byte[]{
            0,1,3,2,
            4,5,7,6,
            8,9,11,10,
            12,13,15,14,
            16,17,19,18,
            20,21,23,22,
    });
    ByteBuffer indices1 = ByteBuffer.wrap(new byte[]{
            0,1,3,2,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
    });
    ByteBuffer indices2 = ByteBuffer.wrap(new byte[]{
            0,0,0,0,
            4,5,7,6,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
    });
    ByteBuffer indices3 = ByteBuffer.wrap(new byte[]{
            0,0,0,0,
            0,0,0,0,
            8,9,11,10,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
    });
    ByteBuffer indices4 = ByteBuffer.wrap(new byte[]{
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            12,13,15,14,
            0,0,0,0,
            0,0,0,0,
    });
    ByteBuffer indices5 = ByteBuffer.wrap(new byte[]{
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            16,17,19,18,
            0,0,0,0,
    });
    ByteBuffer indices6 = ByteBuffer.wrap(new byte[]{
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            20,21,23,22,
    });
    @Override
    public void onDrawFrame(GL10 gl)
    {
// 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
// 重置当前的模型观察矩阵
        gl.glLoadIdentity();
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glTranslatef(0.0f, 0.0f, -5.0f);
//设置3个方向的旋转
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

// 绑定纹理
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//纹理和四边形对应的顶点
        gl.glNormalPointer(GL10.GL_FIXED, 0, normals);
//gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, nBuffer);
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, nBuffer);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[i]);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP,24, GL10.GL_UNSIGNED_BYTE, indices);

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        xrot+=0.5f;
        yrot+=0.6f;
        zrot+=0.3f;
        if(key){
            gl.glEnable(GL10.GL_BLEND); // 打开混合
            gl.glDisable(GL10.GL_DEPTH_TEST);
        }else{
            gl.glDisable(GL10.GL_BLEND);
            gl.glEnable(GL10.GL_DEPTH_TEST);
        }
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        float ratio = (float) width / height;
//设置OpenGL场景的大小
        gl.glViewport(0, 0, width, height);
//设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
//重置投影矩阵
        gl.glLoadIdentity();
// 设置视口的大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
// 选择模型观察矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
// 重置模型观察矩阵
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
// 黑色背景
        gl.glDisable(GL10.GL_DITHER);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnable(GL10.GL_CULL_FACE);
// 启用阴影平滑
        gl.glShadeModel(GL10.GL_SMOOTH);
// 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
//启用纹理映射
        gl.glClearDepthf(1.0f);
//深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);
//精细的透视修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
//允许2D贴图,纹理
        gl.glEnable(GL10.GL_TEXTURE_2D);
        IntBuffer intBuffer = IntBuffer.allocate(6);
// 创建纹理
        gl.glGenTextures(6, intBuffer);
//loadTexture(gl,context);
        texture = intBuffer.array();
// 设置要使用的纹理
        GLImage.load(context.getResources());
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[i]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mt[i], 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[1]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0,GLImage.mt[1], 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[2]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0,GLImage.mt[2], 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[3]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0,GLImage.mt[3], 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[4]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0,GLImage.mt[4], 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[5]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0,GLImage.mt[5], 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
//GLImage.load(getResources());
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient);

        //设置漫射光
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuse);

        //设置光源位置
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition);

        //开启一号光源
        gl.glEnable(GL10.GL_LIGHT1);

        //开启混合
        gl.glEnable(GL10.GL_BLEND);
    }
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==event.ACTION_UP){
            i++;
            if(i>=6){
                i=0;
            }
        }
        if(event.getAction()==event.ACTION_MOVE
                ){
            key=!key;
        }
        return false;
    }

}
class GLImage
{
    public static Bitmap mt[] = new Bitmap[6];
    //public static Bitmap mBitmap=null;
    public static void load(Resources resources)
    {
// mBitmap = BitmapFactory.decodeResource(resources, R.drawable.xiaofei1);
        mt[0] = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher);
        mt[1] = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher);
        mt[2] = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher);
        mt[3] = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher);
        mt[4] = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher);
        mt[5] = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher);
    }
}
