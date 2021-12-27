package com.cs304.lab9.Example1;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package project;
import com.cs304.lab9.AnimListener;
import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
public class Anim extends JFrame {
    public static void main(String[] args) {
        new Anim();
    }
    public Anim() {
        GLCanvas glcanvas;
        Animator animator;
        AnimGLEventListener2 listener = new AnimGLEventListener2();
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
    public void Anim1(){
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