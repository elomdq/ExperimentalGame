package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;

import com.helloworld.box2dprueba.utils.ICollision;

import com.helloworld.box2dprueba.animaciones.Animacion;
import com.helloworld.box2dprueba.entities.B2DSteeringEntity;

import java.util.ArrayList;
import java.util.List;


public abstract class Personaje extends Entidad implements ICollision {

    private Animacion animacion;

    private B2DSteeringEntity entity;


    //constructor
    public Personaje(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames)
    {
        super(world,x, y, width, height, isStatic, fixRotation);
        this.animacion = new Animacion(texturePath, frameWidth, frameHeight, frames);
        entity = new B2DSteeringEntity(this.getBody(), 1);
    }

    //setters & getters

    public Animacion getAnimacion() {
        return animacion;
    }

    public void setAnimacion(Animacion animacion) {
        this.animacion = animacion;
    }


    /*public float getStateTime()
    { return this.stateTime;}

    public void setStateTime(float time)
    {
        this.stateTime = time;
    }

    public void setCurrentFrame(*//*float stateTime*//*)
    {
        this.currentFrame = animation.getKeyFrame(this.stateTime, true);
    }

    public Sprite getCurrentFrame(Animation animation, float stateTime)
    {
        return currentFrame;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }


    public Animation getAnimationUp() {
        return animationUp;
    }


    public Animation getAnimationDown() {
        return animationDown;
    }


    public Animation getAnimationLeft() {
        return animationLeft;
    }


    public Animation getAnimationRight() {
        return animationRight;
    }*/



    //otros metodos

    //antes de realizar la sectorizacion de los frames tengo indicar la cantidad de frames
    //para definir el tamaño de los arrays de las texturas
    /*private void generarAnimaciones(int frameWidth, int frameHeight, int frames)
    {
        animationFramesUp = new TextureRegion[frames];
        animationFramesDown = new TextureRegion[frames];
        animationFramesLeft = new TextureRegion[frames];
        animationFramesRight = new TextureRegion[frames];

        tmpFrames = TextureRegion.split(tex, frameWidth, frameHeight);
        int counter = 0;

        for(int row=0; row<tex.getHeight()/frameHeight; row++)
        {
            for (int col = 0; col<tex.getWidth()/frameWidth; col++)
            {
                    counter++;
                    if(counter<=frames)
                        animationFramesDown[col] = tmpFrames[row][col];
                    if(counter>frames && counter<=frames*2)
                        animationFramesLeft[col] = tmpFrames[row][col];
                    if(counter>frames*2 && counter<=frames*3)
                        animationFramesRight[col] = tmpFrames[row][col];
                    if(counter>frames*3 && counter<=frames*4)
                        animationFramesUp[col] = tmpFrames[row][col];
            }
        }

        animationDown = new Animation(0.1f, animationFramesDown);
        animationLeft = new Animation(0.1f, animationFramesLeft);
        animationRight = new Animation(0.1f, animationFramesRight);
        animationUp = new Animation(0.1f, animationFramesUp);
        animation = new Animation(0, animationFramesUp);
    }
*/
    /*public void animacionesConSprites(int frames)
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
        animation = new Animation(0, animationFramesUp);

    }*/

    public void dispose()
    {
        super.dispose();
        animacion.dispose();
    }

}
