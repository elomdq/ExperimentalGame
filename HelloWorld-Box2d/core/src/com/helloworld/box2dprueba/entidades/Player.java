package com.helloworld.box2dprueba.entidades;


import box2dLight.ConeLight;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Fixture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.physics.box2d.World;
import com.helloworld.box2dprueba.objetos.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.helloworld.box2dprueba.utils.Constants.*;

public class Player extends Character {

    private Integer health;
    private Iluminacion lighting;
    private List<ItemEquipable> inventory;
    private boolean tagged;

    private Map<String, Music> soundEffects;

    private float sec;


    public Player(World world, SpriteBatch batch, float x, float y, int width, int height, boolean isStatic, boolean fixRotation, String texturePath, int frameWidth, int frameHeight, int frames) {
        super(world, batch, x, y, width, height, isStatic, fixRotation, texturePath, frameWidth, frameHeight, frames);
        inventory = new ArrayList<>();
        this.health = CANTIDAD_VIDAS;
        soundEffects= new HashMap<>();
        soundEffects.put("bandage", Gdx.audio.newMusic(Gdx.files.internal("sounds/Bandage.mp3")));
        soundEffects.put("lantern", Gdx.audio.newMusic(Gdx.files.internal("sounds/Lantern.mp3")));
        soundEffects.put("batery", Gdx.audio.newMusic(Gdx.files.internal("sounds/Batery.mp3")));
        soundEffects.put("damage", Gdx.audio.newMusic(Gdx.files.internal("sounds/Hit.mp3")));

    }


    //Setters & getters
    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public List<ItemEquipable> getInventory() {
        return inventory;
    }

    public void setInventory(List<ItemEquipable> inventory) {
        this.inventory = inventory;
    }

    public void setIluminacion(Iluminacion luminaria) {
        this.lighting = luminaria;
    }

    public Iluminacion getIluminacion() {
        return this.lighting;
    }


    //Otros metodos
    public void render() {
        this.getAnimation().getCurrentFrame().draw(this.getBatch(), this.getAlpha());
    }

    public void update(float delta) {
        inputUpdate(delta);
        //updateLuminariaPosition();
        super.update(delta);

        setTagged();
    }

    public void dispose() {
        lighting.dispose();
        super.dispose();
    }
    
    public void inputUpdate(float delta) {

        int horizontalForce = 0;
        int verticalForce = 0;


        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            horizontalForce -= 1;
            this.getAnimation().setAnimacionActual(this.getAnimation().getAnimationLeft());
            this.getAnimation().getAnimacionActual().setFrameDuration(0.1f);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            horizontalForce += 1;
            this.getAnimation().setAnimacionActual(this.getAnimation().getAnimationRight());
            this.getAnimation().getAnimacionActual().setFrameDuration(0.1f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            verticalForce += 1;
            this.getAnimation().setAnimacionActual(this.getAnimation().getAnimationUp());
            this.getAnimation().getAnimacionActual().setFrameDuration(0.1f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            verticalForce -= 1;
            this.getAnimation().setAnimacionActual(this.getAnimation().getAnimationDown());
            this.getAnimation().getAnimacionActual().setFrameDuration(0.1f);
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.W)
                && !Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A))
            this.getAnimation().getAnimacionActual().setFrameDuration(0);


        this.getBody().setLinearVelocity(horizontalForce * 5, verticalForce * 5);



        if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
            if(this.getCantidadDeFaroles()>0) {
                ((Lantern) inventory.remove(getFarolFromInventario())).desequipar(this);
                soundEffects.get("lantern").play();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            if (this.getCantidadDeBandages() > 0 && this.getHealth() < 3) {
                this.setHealth(getHealth() + ((Bandage) inventory.remove(getBandageFromInventario())).getHealAmmount());
                soundEffects.get("bandage").play();
            }
        }


        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            if(this.getCantidadDeBaterias()>0){

                ConeLight linterna = ((Flashlight) lighting).getLight();

                linterna.setDistance( linterna.getDistance() + (ENERGIA_BATERIAS*DISTANCIA_LUMINARIA)/PPM );

                ((Batery) inventory.get(getBateriaFromInventario())).setUsable(false);
                soundEffects.get("batery").play();
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

        for (ItemEquipable item : inventory) {
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

        for(ItemEquipable item : inventory){
            if(item instanceof Batery && ((Batery)item).isUsable())
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

        for (ItemEquipable item : inventory) {
            if (item instanceof Lantern)
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

        for (ItemEquipable item : inventory) {
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

        for (ItemEquipable item : inventory) {
            if (item instanceof Lantern) {
                index = inventory.indexOf(item);
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

        for(ItemEquipable item : inventory){
            if(item instanceof Batery && ((Batery)item).isUsable()){
                index = inventory.indexOf(item);
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
            inventory.remove(keyToDelete());
        }
    }

    /**
     * Método busca una llave en el inventario y la retorna.
     */
    private Llave keyToDelete() {

        Llave key = null;

        for (ItemEquipable item : inventory) {
            if (item instanceof Llave)
                key = (Llave) item;
        }

        return key;
    }

    @Override
    public void collision(Fixture fixture) {

        //Comportamiento que tendrá con un cofre
        if(fixture.getUserData() instanceof Chest){

            //Este comportamiento quizas haya que definirlo al finalizar la colision
            //y luego de que el jugador haya "clickeado" en el objeto que contiene.
            inventory.add(((Chest) fixture.getUserData()).getItem());

            ((Chest) fixture.getUserData()).setItem(null);
        }

        //Comportamiento que tendrá con una puerta
        /*if(fixture.getUserData() instanceof Puerta){
            if(this.getCantidadDeLlaves() == CANTIDAD_LLAVES){
                useKeys();
            }
        }*/

        //Comportamiento que tendrá con un farol
        if(fixture.getUserData() instanceof Lantern){
            if(!((Lantern)fixture.getUserData()).getEstaEquipado() && !this.getInventory().contains(((Lantern)fixture.getUserData()))){
                inventory.add((Lantern)fixture.getUserData());
            }
        }

        //Comportamiento que tendrá con un enemigo
        if(fixture.getUserData() instanceof Enemy){
            if(this.health > 0 && !tagged){
                this.health--;
                this.tagged = true;
                soundEffects.get("damage").play();
            }
        }
    }

    public void useVenda() {
        for (ItemEquipable item : inventory) {
            if (item instanceof Bandage) {
                this.inventory.remove(item);
                this.setHealth(getHealth() + 1);
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

        for (ItemEquipable item : inventory) {
            if (item instanceof Bandage) {
                index = inventory.indexOf(item);
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
