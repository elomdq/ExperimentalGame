package com.helloworld.box2dprueba.animaciones;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;

public class AnimacionPersonaje extends Animacion{

    private Sprite[] animationFramesUp, animationFramesDown, animationFramesLeft, animationFramesRight; //Conjunto de frames en Sprits
    private Animation<Sprite> animationUp, animationDown, animationLeft, animationRight; //Animaciones


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

    public Animation<Sprite> getAnimationUp() {
        return animationUp;
    }

    public void setAnimationUp(Animation<Sprite> animationUp) {
        this.animationUp = animationUp;
    }

    public Animation<Sprite> getAnimationDown() {
        return animationDown;
    }

    public void setAnimationDown(Animation<Sprite> animationDown) {
        this.animationDown = animationDown;
    }

    public Animation<Sprite> getAnimationLeft() {
        return animationLeft;
    }

    public void setAnimationLeft(Animation<Sprite> animationLeft) {
        this.animationLeft = animationLeft;
    }

    public Animation<Sprite> getAnimationRight() {
        return animationRight;
    }

    public void setAnimationRight(Animation<Sprite> animationRight) {
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


        animationDown = new Animation(0.1f, animationFramesDown);
        animationLeft = new Animation(0.1f, animationFramesLeft);
        animationRight = new Animation(0.1f, animationFramesRight);
        animationUp = new Animation(0.1f, animationFramesUp);
        setAnimacionActual(new Animation(0, animationFramesUp));

        setCurrentFrame();
    }


}
