package com.helloworld.box2dprueba.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public abstract class Entidad {

    private Body cuerpo;
    private Texture textura;


    //Constructor
    public Entidad(World mundo, float posX, float posY, float largo, float ancho,String textura){
        crearCuerpo(mundo,posX,posY,largo,ancho);
        this.textura =new Texture(textura);
    }


    //Getter & Setter
    public Body getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(Body cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Texture getTextura() {
        return textura;
    }

    public void setTextura(Texture textura) {
        this.textura = textura;
    }


    //Otros métodos
    private void crearCuerpo(World mundo, float posX, float posY, float largo, float ancho) {

        BodyDef cuerpoDef = new BodyDef(); //Caracteristicas del cuerpo de la clase

        cuerpoDef.fixedRotation = true;

        if(this instanceof Jugador || this instanceof Enemigo)
            cuerpoDef.type = BodyDef.BodyType.DynamicBody;//Si se trata de un jugador o enemigo lo crea dinámico
        else
            cuerpoDef.type = BodyDef.BodyType.StaticBody;//Caso contrario lo crea estático

        cuerpoDef.position.set(posX/PPM, posY/PPM);//Desfine una posicion inicial

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(largo/2/PPM,ancho/2/PPM);//Define la forma del "hitbox"

        FixtureDef fixDef = new FixtureDef();//Asigna la forma anteriormente creada
        fixDef.shape = forma;
        fixDef.density = 1.0f;
        this.cuerpo = mundo.createBody(cuerpoDef);//Le asigna la forma ya seteada al cuerpo
        this.cuerpo.createFixture(fixDef).setUserData(this);//Le asigna los datos de la instancia al fixture (para coliciones)
    }
}
