package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public abstract class Entidad {

    private Body body;
    private Texture texture;



    //Contructor
    //Se le pasa una referencia del mundo donde se va a crear (porque el mundo crea la referencia del body a asignar)
    //Se le pasa posicion con X e Y, ancho y alto, Si es cuerpo estatico o dinamico, y si no va a rotar
    public Entidad(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation)
    {
        body = createBox(world, x, y, width, height, isStatic, fixRotation);
    }

    public Entidad(Body body, Texture texture)
    {
        this.body = body;
        this.texture = texture;
    }


    //setters & getters

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }


    //Otros metodos

    public Body createBox(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation)
    {
        Body pBody;
        BodyDef def = new BodyDef();

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x/PPM, y/PPM);

        if(fixRotation)
            def.fixedRotation = true;
        else
            def.fixedRotation = false;

        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/PPM, height/2/PPM);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();

        return pBody;
    }

    public void dispose()
    {
        texture.dispose();
    }

}
