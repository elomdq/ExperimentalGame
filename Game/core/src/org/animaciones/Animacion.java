package org.animaciones;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;

public class Animacion {

    TextureAtlas textureAtlas;
    private List<Sprite> frames;
    private Sprite currentFrame; //Frame actualmente en pantalla
    private int frameWidth, frameHeight, animationFrames;
    private float stateTime;
    private com.badlogic.gdx.graphics.g2d.Animation<Sprite> actualAnimation;

    //Constructor
    public Animacion(String texturePath, int frameWidth, int frameHeight, int frames)
    {
        this.stateTime = 0f;
        this.textureAtlas = new TextureAtlas(texturePath);
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        this.animationFrames = frames;
    }

    //setter & getters
    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public List<Sprite> getFrames() {
        return frames;
    }

    public void setFrames(List<Sprite> frames) {
        this.frames = frames;
    }

    public Sprite getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame()
    {
        this.currentFrame = actualAnimation.getKeyFrame(this.stateTime, true);
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

    public com.badlogic.gdx.graphics.g2d.Animation<Sprite> getActualAnimation() {
        return actualAnimation;
    }

    public void setActualAnimation(com.badlogic.gdx.graphics.g2d.Animation<Sprite> actualAnimation) {
        this.actualAnimation = actualAnimation;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public int getAnimationFrames() {
        return animationFrames;
    }

    public void setAnimationFrames(int animationFrames) {
        this.animationFrames = animationFrames;
    }


    // Otros metodos
    public void crearAnimacion()
    {
        this.frames = new ArrayList<>();

        for (TextureAtlas.AtlasRegion region:
                textureAtlas.getRegions()) {
            frames.add(new Sprite(region));
        }

        actualAnimation = new com.badlogic.gdx.graphics.g2d.Animation(0.0f, frames);
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
