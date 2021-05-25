package com.helloworld.box2dprueba.objetos;

import box2dLight.PointLight;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.utils.ICollision;

//ToDO: Pensar una forma para que el farol se renderize una vez que es colocado (quizas implementando una interfaz
//      que sea "Static", que tambien podria usar cofre y puerta, ya que siempre tendrán comportamiento estático).

public class Farol extends Iluminacion implements ICollision {

    private PointLight luz;
    private boolean isEquipped;


    //Constructor
    public Farol(World world, int x, int y, int width, int height, boolean isStatic, boolean fixRotation) {
        super(world, x, y, width, height, isStatic, fixRotation);
    }


    //Getter & Setter
    public PointLight getLuz() {
        return luz;
    }

    public void setLuz(PointLight luz) {
        this.luz = luz;
    }

    @Override
    public void collision(Fixture fixture) {
        if(!isEquipped){
            //lo renderiza en el mundo
        }
    }
}
