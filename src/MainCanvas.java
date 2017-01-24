import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import listener.CustomShapeRotateListener;
import listener.CustomTranslateListener;
import listener.CustomZoomListener;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

/**
 * Created by Dmitriy Chalienko on 23.01.2017.
 */
public class MainCanvas extends GLCanvas implements GLEventListener {

    private static MainCanvas instance;
    private static GLU glu = new GLU();
    private CustomZoomListener zoomListener;
    private CustomShapeRotateListener customShapeRotateListener;
    private CustomTranslateListener customTranslateListener;
    private static final float DELTA_ZOOM = 1f;


    private MainCanvas() {
        this.addGLEventListener(this);
    }

    public static MainCanvas getInstance() {
        if (instance == null) {
            instance = new MainCanvas();
        }
        return instance;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        if (instance != null) {
            zoomListener = new CustomZoomListener(DELTA_ZOOM).setZoom(-14f);
            customShapeRotateListener = new CustomShapeRotateListener();
            customTranslateListener = new CustomTranslateListener();
            instance.addMouseMotionListener(customTranslateListener);
            instance.addMouseMotionListener(customShapeRotateListener);
            instance.addMouseListener(customTranslateListener);
            instance.addMouseListener(customShapeRotateListener);
            instance.addMouseWheelListener(zoomListener);
        } else {
            throw new RuntimeException("Instance of MainCanvas is null");
        }
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0, 0, 0, 0);

//        gl.glLoadIdentity();
    }



    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
//        gl.glViewport(0,0, width, height);
        if (height == 0) height = 1;
        float aspect = (float) width / height;
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(90, aspect, 10, 15.0);
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity(); // reset

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL_COLOR_BUFFER_BIT );
        gl.glLoadIdentity();
        gl.glTranslated(customTranslateListener.getXPosition(), customTranslateListener.getYPosition(), zoomListener.getZoom());
        gl.glRotatef((float) customShapeRotateListener.getAngleX(), 0f, 1f, 0f);

        gl.glRotatef((float) customShapeRotateListener.getAngleY(), 1f, 0f, 0f);

        gl.glColor3d(0.1,0.6,0);



//        gl.glDepthRange(0.0, 0.5);
        gl.glBegin(GL2.GL_QUADS);
        int length = 1;
        gl.glVertex3d(-length + 1, -length  + 1, 0);
        gl.glVertex3d(-length + 1, length + 1, 0);
        gl.glVertex3d(length + 1, length + 1, 0);
        gl.glVertex3d(length + 1, -length + 1, 0);

        gl.glVertex3d(length + 1, -length + 1, 0);
        gl.glVertex3d(length + 1, length + 1, 0);
        gl.glVertex3d(length + 1, length + 1, 1);
        gl.glVertex3d(length + 1, -length + 1, 1);

        gl.glVertex3d(length + 1, -length + 1, 0);
        gl.glVertex3d(-length + 1, length + 1, 0);
        gl.glVertex3d(-length + 1, length + 1, 1);
        gl.glVertex3d(length + 1, -length + 1, 1);

        gl.glVertex3d(-length + 1, -length + 1, 1);
        gl.glVertex3d(-length + 1, length + 1, 1);
        gl.glVertex3d(length + 1, length + 1, 1);
        gl.glVertex3d(length + 1, -length + 1, 1);
        gl.glEnd();

//        gl.glDepthRange(-1, 0.5);
//        gl.glDepthRange(0.5, 1.0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glClearColor(0, 0, 0, 0);
        gl.glColor3d(0,0.5,0.7);
        gl.glVertex3d(-length, -length, 4);
        gl.glVertex3d(-length, length, 4);
        gl.glVertex3d(length, length, 4);
        gl.glVertex3d(length, -length, 4);

        gl.glVertex3d(length, -length, 4);
        gl.glVertex3d(length, length, 4);
        gl.glVertex3d(length, length, 5);
        gl.glVertex3d(length, -length, 5);

        gl.glVertex3d(length, -length, 4);
        gl.glVertex3d(-length, length, 4);
        gl.glVertex3d(-length, length, 5);
        gl.glVertex3d(length, -length, 5);

        gl.glVertex3d(-length, -length, 5);
        gl.glVertex3d(-length, length, 5);
        gl.glVertex3d(length, length, 5);
        gl.glVertex3d(length, -length, 5);
        gl.glEnd();


    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

}
