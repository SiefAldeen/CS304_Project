/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs304.lab9.Example1;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;
import com.cs304.lab9.AnimListener;
import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Sief Aldeen
 */
public class Anim1 extends JFrame {


public Anim1(){
    GLCanvas glcanvas;
        Animator animator;
        OnePlayer listener = new OnePlayer();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addMouseListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(60);
        animator.add(glcanvas);
        animator.start();
        setTitle("Connect 4 ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(this);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
}
}