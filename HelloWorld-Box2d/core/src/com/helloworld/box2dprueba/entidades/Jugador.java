package com.helloworld.box2dprueba.entidades;


import box2dLight.ConeLight;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Fixture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.objetos.*;

import java.util.ArrayList;
import java.util.List;

import static com.helloworld.box2dprueba.utils.Constants.*;

public class Jugador extends Personaje {

    private Integer vidas;
    private Iluminacion luminaria;
    private List<ItemEquipable> inventario;
    private boolean tagged;

    private float sec;

    public Jugador(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames) {
        super(world, batch, x, y, width, height, isStatic, fixRotation, texturePath, frameWidth, frameHeight, frames);
        inventario = new ArrayList<>();
        this.vidas = CANTIDAD_VIDAS;

    }


    //Setters & getters
    public Integer getVidas() {
        return vidas;
    }

    public void setVidas(Integer vidas) {
        this.vidas = vidas;
    }

    public List<ItemEquipable> getInventario() {
        return inventario;
    }

    public void setInventario(List<ItemEquipable> inventario) {
        this.inventario = inventario;
    }

    public void setIluminacion(Iluminacion luminaria) {
        this.luminaria = luminaria;
    }

    public Iluminacion getIluminacion() {
        return this.luminaria;
    }


    //Otros metodos
    public void render() {
        this.getAnimacion().getCurrentFrame().draw(this.getBatch(), this.getAlpha());
    }

    public void update(float delta) {
        inputUpdate(delta);
        //updateLuminariaPosition();
        super.update(delta);

        setTagged();
    }

    public void dispose() {
        luminaria.dispose();
        super.dispose();
    }
    
    public void inputUpdate(float delta) {

        int horizontalForce = 0;
        int verticalForce = 0;


        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            horizontalForce -= 1;
            this.getAnimacion().setAnimacionActual(this.getAnimacion().getAnimationLeft());
            this.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            horizontalForce += 1;
            this.getAnimacion().setAnimacionActual(this.getAnimacion().getAnimationRight());
            this.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            verticalForce += 1;
            this.getAnimacion().setAnimacionActual(this.getAnimacion().getAnimationUp());
            this.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            verticalForce -= 1;
            this.getAnimacion().setAnimacionActual(this.getAnimacion().getAnimationDown());
            this.getAnimacion().getAnimacionActual().setFrameDuration(0.1f);
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.W)
                && !Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A))
            this.getAnimacion().getAnimacionActual().setFrameDuration(0);


        this.getBody().setLinearVelocity(horizontalForce * 5, verticalForce * 5);



        if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
            if(this.getCantidadDeFaroles()>0) {
                ((Farol) inventario.remove(getFarolFromInventario())).desequipar(this);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            if (this.getCantidadDeBandages() > 0 && this.getVidas() < 3) {
                this.setVidas(getVidas() + ((Bandage) inventario.remove(getBandageFromInventario())).getHealAmmount());
            }
        }


        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            if(this.getCantidadDeBaterias()>0){

                ConeLight linterna = ((Linterna)luminaria).getLinterna();

                linterna.setDistance( linterna.getDistance() + (ENERGIA_BATERIAS*DISTANCIA_LUMINARIA)/PPM );

                ((Bateria)inventario.get(getBateriaFromInventario())).setDisponibleParaUsar(false);
            }
        }
    }


    /**
     * Método que retorna la cantidad de llaves que
     * hay en el inventario del jugador
     *
     * @return
     */
    public int getCantidadDeLlaves() {

        int cantidadDeLlaves = 0;

        for (ItemEquipable item : inventario) {
            if (item instanceof Llave)
                cantidadDeLlaves++;
        }

        return cantidadDeLlaves;
    }

    /**
     * Método que retorna la cantidad de baterias
     * que hay en el inventario del jugador
     *
     * @return
     */
    public int getCantidadDeBaterias() {

        int cantidadDeBaterias = 0;

        for(ItemEquipable item : inventario){
            if(item instanceof Bateria && ((Bateria)item).isDisponibleParaUsar())
                cantidadDeBaterias++;
        }

        return cantidadDeBaterias;
    }


    /**
     * Método que retorna la cantidad de faroles
     * que hay en el inventario del jugador
     *
     * @return
     */
    public int getCantidadDeFaroles() {

        int cantidadDeFaroles = 0;

        for (ItemEquipable item : inventario) {
            if (item instanceof Farol)
                cantidadDeFaroles++;
        }

        return cantidadDeFaroles;
    }

    /**
     * Método que retorna la cantidad de vendas
     * que hay en el inventario del jugador
     *
     * @return
     */
    public int getCantidadDeBandages() {

        int cantidadDeBandages = 0;

        for (ItemEquipable item : inventario) {
            if (item instanceof Bandage)
                cantidadDeBandages++;
        }

        return cantidadDeBandages;
    }

    /**
     * Método que busca un farol en
     * el inventario y lo retorna
     *
     * @return
     */
    private int getFarolFromInventario() {

        int index = -1;

        for (ItemEquipable item : inventario) {
            if (item instanceof Farol) {
                index = inventario.indexOf(item);
                break;
            }
        }

        return index;
    }

    /**
     * Método que busca una batería en
     * el inventario y lo retorna
     *
     * @return
     */
    private int getBateriaFromInventario(){

        int index = -1;

        for(ItemEquipable item : inventario){
            if(item instanceof Bateria && ((Bateria)item).isDisponibleParaUsar()){
                index = inventario.indexOf(item);
                break;
            }
        }

        return index;
    }

    /**
     * Método utilizado para eliminar las llaves una vez que
     * son utilizadas para abrir la puerta de salida.
     */
    private void useKeys() {
        while (getCantidadDeLlaves() > 0) {
            inventario.remove(keyToDelete());
        }
    }

    /**
     * Método busca una llave en el inventario y la retorna.
     */
    private Llave keyToDelete() {

        Llave key = null;

        for (ItemEquipable item : inventario) {
            if (item instanceof Llave)
                key = (Llave) item;
        }

        return key;
    }

    @Override
    public void collision(Fixture fixture) {

        //Comportamiento que tendrá con un cofre
        if(fixture.getUserData() instanceof Cofre){

            //Este comportamiento quizas haya que definirlo al finalizar la colision
            //y luego de que el jugador haya "clickeado" en el objeto que contiene.
            inventario.add(((Cofre) fixture.getUserData()).getItem());

            ((Cofre) fixture.getUserData()).setItem(null);
        }

        //Comportamiento que tendrá con una puerta
        if(fixture.getUserData() instanceof Puerta){
            if(this.getCantidadDeLlaves() == CANTIDAD_LLAVES){
                useKeys();
            }
        }

        //Comportamiento que tendrá con un farol
        if(fixture.getUserData() instanceof Farol){
            if(!((Farol)fixture.getUserData()).getEstaEquipado() && !this.getInventario().contains(((Farol)fixture.getUserData()))){
                inventario.add((Farol)fixture.getUserData());
            }
        }

        //Comportamiento que tendrá con un enemigo
        if(fixture.getUserData() instanceof Enemigo){
            if(this.vidas > 0 && !tagged){
                this.vidas--;
                this.tagged = true;
            }
        }
    }

    public void useVenda() {
        for (ItemEquipable item : inventario) {
            if (item instanceof Bandage) {
                this.inventario.remove(item);
                this.setVidas(getVidas() + 1);
                break;
            }
        }
    }


    /**
     * Método que busca una venda en
     * el inventario y lo retorna
     *
     * @return
     */
    private int getBandageFromInventario() {

        int index = -1;

        for (ItemEquipable item : inventario) {
            if (item instanceof Bandage) {
                index = inventario.indexOf(item);
                break;
            }
        }

        return index;
    }


/**
 * Evalua si el jugador recibio daño
 *
 * vuelve a hacerlo vulnerable luego de 4s
 *
 */
    public void setTagged() {
        if (sec >= 4) {
            tagged = false;
            sec = 0;
        } else if (tagged) {
            sec += Gdx.graphics.getDeltaTime();
        }
    }

    public boolean isTagged() {
        return tagged;
    }
}
