package com.helloworld.box2dprueba.animaciones;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;

public class AnimacionPersonaje extends Animacion {

    private Sprite[] animationFramesUp, animationFramesDown, animationFramesLeft, animationFramesRight; //Conjunto de frames en Sprits
    private com.badlogic.gdx.graphics.g2d.Animation animationUp, animationDown, animationLeft, animationRight; //Animaciones


    //Constructor
    public AnimacionPersonaje(String texturePath, int frameWidth, int frameHeight, int frames)
    {
        super(texturePath, frameWidth, frameHeight, frames);
    }


    //setters & getters

    public Sprite[] getAnimationFramesUp() {
        return animationFramesUp;
    }

    public void setAnimationFramesUp(Sprite[] animationFramesUp) {
        this.animationFramesUp = animationFramesUp;
    }

    public Sprite[] getAnimationFramesDown() {
        return animationFramesDown;
    }

    public void setAnimationFramesDown(Sprite[] animationFramesDown) {
        this.animationFramesDown = animationFramesDown;
    }

    public Sprite[] getAnimationFramesLeft() {
        return animationFramesLeft;
    }

    public void setAnimationFramesLeft(Sprite[] animationFramesLeft) {
        this.animationFramesLeft = animationFramesLeft;
    }

    public Sprite[] getAnimationFramesRight() {
        return animationFramesRight;
    }

    public void setAnimationFramesRight(Sprite[] animationFramesRight) {
        this.animationFramesRight = animationFramesRight;
    }

    public com.badlogic.gdx.graphics.g2d.Animation getAnimationUp() {
        return animationUp;
    }

    public void setAnimationUp(com.badlogic.gdx.graphics.g2d.Animation animationUp) {
        this.animationUp = animationUp;
    }

    public com.badlogic.gdx.graphics.g2d.Animation getAnimationDown() {
        return animationDown;
    }

    public void setAnimationDown(com.badlogic.gdx.graphics.g2d.Animation animationDown) {
        this.animationDown = animationDown;
    }

    public com.badlogic.gdx.graphics.g2d.Animation getAnimationLeft() {
        return animationLeft;
    }

    public void setAnimationLeft(com.badlogic.gdx.graphics.g2d.Animation animationLeft) {
        this.animationLeft = animationLeft;
    }

    public com.badlogic.gdx.graphics.g2d.Animation getAnimationRight() {
        return animationRight;
    }

    public void setAnimationRight(com.badlogic.gdx.graphics.g2d.Animation animationRight) {
        this.animationRight = animationRight;
    }


    //otros metodos

    @Override
    public void crearAnimacion()
    {
        int i=0, counter=1;

        animationFramesUp = new Sprite[getAnimationFrames()];
        animationFramesDown = new Sprite[getAnimationFrames()];
        animationFramesLeft = new Sprite[getAnimationFrames()];
        animationFramesRight = new Sprite[getAnimationFrames()];

        setFrames(new ArrayList<Sprite>());

        for (TextureAtlas.AtlasRegion region:
                textureAtlas.getRegions()) {
            getFrames().add(new Sprite(region));
        }

        for (Sprite sprite:
               getFrames()) {

            if(i >= getAnimationFrames())
                i=0;

            if(counter<=getAnimationFrames())
                animationFramesDown[i] = sprite;
            if(counter>getAnimationFrames() && counter<=getAnimationFrames()*2)
                animationFramesLeft[i] = sprite;
            if(counter>getAnimationFrames()*2 && counter<=getAnimationFrames()*3)
                animationFramesRight[i] = sprite;
            if(counter>getAnimationFrames()*3 && counter<=getAnimationFrames()*4)
                animationFramesUp[i] = sprite;

            i++;
            counter++;
        }


        animationDown = new com.badlogic.gdx.graphics.g2d.Animation(0.1f, animationFramesDown);
        animationLeft = new com.badlogic.gdx.graphics.g2d.Animation(0.1f, animationFramesLeft);
        animationRight = new com.badlogic.gdx.graphics.g2d.Animation(0.1f, animationFramesRight);
        animationUp = new com.badlogic.gdx.graphics.g2d.Animation(0.1f, animationFramesUp);
        setAnimacionActual(new com.badlogic.gdx.graphics.g2d.Animation(0, animationFramesUp));

        setCurrentFrame();
    }


}
