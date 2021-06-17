package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import static com.helloworld.box2dprueba.utils.Constants.PPM;
import com.helloworld.box2dprueba.utils.ICollision;
import com.helloworld.box2dprueba.animaciones.CharacterAnimation;
import com.helloworld.box2dprueba.entities.AISteeringBehavior;

public abstract class Character extends Entity implements ICollision {

    private CharacterAnimation animation;
    //private float alpha;
    private AISteeringBehavior steeringBehavior;


    //constructor
    public Character(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames)
    {
        super(world, batch, x, y, width, height, isStatic, fixRotation);
        this.animation = new CharacterAnimation(texturePath, frameWidth, frameHeight, frames);
        animation.crearAnimacion();

        steeringBehavior = new AISteeringBehavior(this.getBody(), 1);

    }

    //setters & getters

    public CharacterAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(CharacterAnimation animation) {
        this.animation = animation;
    }




    //otros metodos

    //Hace un update del stateTime y del frame que deberia renderizarse en ese valor de tiempo
    public void updateAnimation(float delta)
    {
        this.animation.updateStateTime(delta);
        this.animation.setCurrentFrame();

    }

    //Tomo la posicion del Body del Personaje y actualizo la posicion de renderizado del Sprite
    public void updateFramePosition()
    {
        this.animation.getCurrentFrame().setPosition(this.getBody().getPosition().x * PPM - (32/2),this.getBody().getPosition().y * PPM - (32/2));
    }

    //Con este metodo actualizamos todoo lo referente al personaje sea Jugador o Enemigo
    public void update(float delta)
    {
        updateAnimation(delta);
        updateFramePosition();
    }

    //Con este metodo renderizo el frame preciso actualizado
    public abstract void render();

    public void dispose()
    {
        animation.dispose();
        super.dispose();
    }

    public AISteeringBehavior getSteeringBehavior() {
        return steeringBehavior;
    }
}
