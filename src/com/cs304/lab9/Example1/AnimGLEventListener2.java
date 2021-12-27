package com.cs304.lab9.Example1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.cs304.lab9.AnimListener;
import com.cs304.lab9.Texture.TextureReader;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import com.sun.opengl.util.j2d.TextRenderer;
import java.io.File;
import java.io.FileInputStream;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;


public class AnimGLEventListener2 implements GLEventListener, MouseListener {
    boolean audioState;
    Clip clip;
    AudioInputStream audio;
    File audioFile;
    GLAutoDrawable drawables = null;
    private static final int X_MIN = 0;

    TextRenderer renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
    private static final int X_MAX = 700;
    private static final int Y_MIN = 0;
    private static final int Y_MAX = 700;
    private static final double ONE_DEGREE = (Math.PI / 180);
    private static final double THREE_SIXTY = 2 * Math.PI;
    private static final double RADIUS = 5;
    private int[] row = {91, 182, 273, 364, 455, 546};
    private int[] column = {71, 151, 231, 315, 401, 481, 561};
    private int[] range = {71, 135, 220, 300, 380, 460, 540, 620};
    private Balls[][][] game = new Balls[6][7][2];
    int maxWidth = 700;
    int maxHeight = 700;
    int counter = 0;
    boolean isPaused = false;
    //GLCanvas glc;
    double xpos;
    double x, y;
    double ypos;
    int m = 200;
    GLCanvas glc;
    int player = 0;
//    double y = 0;
    String textureNames[] = {"red.png", "green.png", "Game.png", "paused.png", "P1win.png","P2win.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    public void init(GLAutoDrawable gld) {
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                game[j][i][0] = new Balls(column[i], row[j]);
                game[j][i][1] = new Balls(column[i], row[j]);
            }
        }
        drawables = gld;

        GL gl = drawables.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(X_MIN, X_MAX, Y_MIN, Y_MAX, 0, 1.0);
        float red = (float) Math.random();
        float green = (float) Math.random();
        float blue = (float) Math.random();
        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture("C:\\Users\\Sief Aldeen\\Documents\\NetBeansProjects\\CS304\\Assets\\monsters" + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
    public void display(GLAutoDrawable gld) {
                GL gl = gld.getGL();
      boolean w1=win(1);
      boolean w0 = win(0);
                if (w0) {
            DrawWinP1(gl);
        }else if (w1){
                    DrawWinP2(gl);
        }else{

        if (!isPaused) {
            gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
            gl.glLoadIdentity();
            DrawBackground(gl);
            for (int j = 0; j < 6; j++) {
                for (int i = 0; i < 7; i++) {
                    if (game[j][i][0].v == true) {
                        DrawSprite(gl, game[j][i][0].x, game[j][i][0].y, 0, (float) 1.7);
                    }
                    if (game[j][i][1].v == true) {
                        DrawSprite(gl, game[j][i][1].x, game[j][i][1].y, 1, (float) 1.7);
                    }
                }
            }
            for (int i = 0; i < 7; i++) {
                if (x > range[i] && x < range[i + 1]) {
                    for (int j = 0; j < 6; j++) {
                        if (game[j][i][0].v == false && game[j][i][1].v == false) {
                            game[j][i][player].v = true;
                            player = (player == 1 ? 0 : 1);
                            x = 0;
                            break;
                        }
                    }
                }
            }
        } else {
            DrawPasued(gl);
        }
            if (x >= 638 && x <= 684 && y >= 8 && y <= 60) {
                isPaused = !isPaused;
            }
            x = y = 0;

        }}
    

    public boolean win(int b) {
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                if (i <= 3 && j < 3 && game[j][i][b].v && game[j + 1][i + 1][b].v && game[j + 2][i + 2][b].v && game[j + 3][i + 3][b].v) {
                    return true;
                }
                if (i >= 3 && j < 3 && game[j][i][b].v && game[j + 1][i - 1][b].v && game[j + 2][i - 2][b].v && game[j + 3][i - 3][b].v) {
                    return true;
                }
                if (i <= 3 && game[j][i][b].v && game[j][i + 1][b].v && game[j][i + 2][b].v && game[j][i + 3][b].v) {
                    return true;
                }
                if (j < 3 && game[j][i][b].v && game[j + 1][i][b].v && game[j + 2][i][b].v && game[j + 3][i][b].v) {
                    return true;
                }
            }
        }
        return false;
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawSprite(GL gl, double x, double y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawBackground(GL gl) {

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[2]);	// Turn Blending On
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawPasued(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[3]);	// Turn Blending On
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }
     public void DrawWinP1(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[4]);	// Turn Blending On
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }
public void DrawWinP2(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[5]);	// Turn Blending On
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }
public void play() {
try {
           audioState = true;
           audioFile = new File("C:\\Users\\Sief Aldeen\\Documents\\NetBeansProjects\\CS304\\background_music.wav").getAbsoluteFile();
           audio = AudioSystem.getAudioInputStream(audioFile );
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
            clip.loop(100);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
}

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        GL gl = drawables.getGL();
        x = mouseEvent.getX();
        y = mouseEvent.getY();
        Component c = mouseEvent.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();

        xpos = x;
        ypos = y;
        double xP = ((x / width) * 1600);
        double yP = ((x / height) * 1000);
              counter++;

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
