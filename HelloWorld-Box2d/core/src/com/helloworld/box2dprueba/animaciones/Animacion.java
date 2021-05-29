package com.helloworld.box2dprueba.animaciones;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;

public class Animacion {

    TextureAtlas textureAtlas;
    private List<Sprite> tmpFrames;
    private Sprite[] animationFramesUp, animationFramesDown, animationFramesLeft, animationFramesRight; //Conjunto de frames en Sprits
    private Sprite currentFrame; //Frame actualmente en pantalla
    private int frameWidth, frameHeight;
    private Animation<Sprite> animacionActual, animationUp, animationDown, animationLeft, animationRight; //Animaciones
    private float stateTime;


    //Constructor
    public Animacion(String texturePath, int frameWidth, int frameHeight, int frames)
    {
        this.stateTime = 0f;
        this.textureAtlas = new TextureAtlas(texturePath);
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        animacionesConSprites(frames);
        setCurrentFrame();
    }


    //setters & getters

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public List<Sprite> getTmpFrames() {
        return tmpFrames;
    }

    public void setTmpFrames(List<Sprite> tmpFrames) {
        this.tmpFrames = tmpFrames;
    }

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

    public Sprite getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame()
    {
        this.currentFrame = animacionActual.getKeyFrame(this.stateTime, true);
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public Animation<Sprite> getAnimacionActual() {
        return animacionActual;
    }

    public void setAnimacionActual(Animation<Sprite> animacionActual) {
        this.animacionActual = animacionActual;
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

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }


    //otros metodos

    public void animacionesConSprites(int frames)
    {
        int i=0, counter=1;

        animationFramesUp = new Sprite[frames];
        animationFramesDown = new Sprite[frames];
        animationFramesLeft = new Sprite[frames];
        animationFramesRight = new Sprite[frames];

        tmpFrames = new ArrayList<>();

        for (TextureAtlas.AtlasRegion region:
                textureAtlas.getRegions()) {
            tmpFrames.add(new Sprite(region));
        }

        for (Sprite sprite:
                tmpFrames) {

            if(i >= frames)
                i=0;

            if(counter<=frames)
                animationFramesDown[i] = sprite;
            if(counter>frames && counter<=frames*2)
                animationFramesLeft[i] = sprite;
            if(counter>frames*2 && counter<=frames*3)
                animationFramesRight[i] = sprite;
            if(counter>frames*3 && counter<=frames*4)
                animationFramesUp[i] = sprite;

            i++;
            counter++;
        }


        animationDown = new Animation(0.1f, animationFramesDown);
        animationLeft = new Animation(0.1f, animationFramesLeft);
        animationRight = new Animation(0.1f, animationFramesRight);
        animationUp = new Animation(0.1f, animationFramesUp);
        animacionActual = new Animation(0, animationFramesUp);

    }

    //hace un update del stateTime que es la variable que nos indicaria hace cuanto estan las animaciones creadas
    //se updetea con el tiempo entre frames
    public void updateStateTime(float delta)
    {
        this.stateTime += delta;
    }

    public void dispose()
    {
        textureAtlas.dispose();
    }


}
