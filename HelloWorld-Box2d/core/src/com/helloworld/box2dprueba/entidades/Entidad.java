package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public abstract class Entidad {

    private Body body;
    private SpriteBatch batch;
    //private Texture texture;



    //Contructor
    //Se le pasa una referencia del mundo donde se va a crear (porque el mundo crea la referencia del body a asignar)
    //Se le pasa posicion con X e Y, ancho y alto, Si es cuerpo estatico o dinamico, y si no va a rotar
    public Entidad(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation)
    {
        body = createBox(world, x, y, width, height, isStatic, fixRotation);
        this.batch = batch;
    }


    //setters & getters

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    //Otros metodos

    public Body createBox(World world, float x, float y, int width, int height, boolean isStatic, boolean fixRotation)
    {
        Body body;
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

        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/PPM, height/2/PPM);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = 1f;

        body.createFixture(fixDef).setUserData(this);

        shape.dispose();

        //body.setTransform(body.getPosition(), 90 * MathUtils.degreesToRadians);

        return body;
    }

    public void dispose()
    {
       // texture.dispose();
        batch.dispose();
    }

}
