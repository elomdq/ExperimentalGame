package com.helloworld.box2dprueba.states;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.helloworld.box2dprueba.utils.TiledObjectUtil;

import static com.helloworld.box2dprueba.utils.Constants.PPM;

public class PlayStateGame extends State {

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Box2DDebugRenderer b2dr;
    private World world;
    private Body player, platform;

    private Texture tex;
    private TextureRegion[] animationFrames, animationFramesUp, animationFramesDown, animationFramesLeft, animationFramesRight;
    private TextureRegion currentFrame;
    private TextureRegion [][] tmpFrames;
    private Animation animation, animationUp, animationDown, animationLeft, animationRight;
    private float stateTime;

    private RayHandler rayHandler;
    private PointLight light;
    private float distance = 5;

    private ConeLight coneLight;

    public PlayStateGame(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();

        player = createBox(40, 40,32,32,false, false);

        map = new TmxMapLoader().load("maps/mapita.tmx"); // Devuelve un TiledMap
        tmr = new OrthogonalTiledMapRenderer(map);

        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("colision").getObjects());

        tex = new Texture("images/Male01.png");

        tmpFrames = TextureRegion.split(tex, 32,32);
        animationFramesUp = new TextureRegion[3];
        animationFramesDown = new TextureRegion[3];
        animationFramesLeft = new TextureRegion[3];
        animationFramesRight = new TextureRegion[3];
        stateTime = 0f;

        for(int row=0; row<4; row++)
        {
            for (int col = 0; col<3; col++)
            {
                switch (row)
                {
                    case 0:
                        animationFramesDown[col] = tmpFrames[row][col];
                        break;
                    case 1:
                        animationFramesLeft[col] = tmpFrames[row][col];
                        break;
                    case 2:
                        animationFramesRight[col] = tmpFrames[row][col];
                        break;
                    case 3:
                        animationFramesUp[col] = tmpFrames[row][col];
                        break;
                    default:
                        break;
                }
            }
        }

        animationDown = new Animation(0.1f, animationFramesDown);;
        animationLeft = new Animation(0.1f, animationFramesLeft);;
        animationRight = new Animation(0.1f, animationFramesRight);
        animationUp = new Animation(0.1f, animationFramesUp);
        animation = new Animation(0, animationFramesUp);

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0f);

        //light = new PointLight(rayHandler,100,	Color.WHITE,distance, 0 , 0);
        //light.setSoftnessLength(0f);
        //light.attachToBody(player);

        coneLight = new ConeLight(rayHandler, 100,	Color.WHITE, distance, 0 , 0, player.getAngle(), 30);
        coneLight.attachToBody(player);
        coneLight.setSoftnessLength(0f);

        b2dr.setDrawBodies(false);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float delta) {
        world.step(1/60f, 6, 2);

        inputUpdate(delta);
        cameraUpdate();

        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);

       	/*distance *= 0.999f;
		light.setDistance(distance);*/

        rayHandler.update();
        rayHandler.setCombinedMatrix(camera.combined.scl(PPM), camera.position.x /  PPM, camera.position.y / PPM, camera.viewportWidth, camera.viewportHeight);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();

        b2dr.render(world, camera.combined.scl(PPM));

        rayHandler.render();

        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);

        batch.draw(currentFrame,player.getPosition().x * PPM - (32/2), player.getPosition().y * PPM - (32/2));
        //batch.draw(tex, player.getPosition().x * PPM - (tex.getWidth()/2), player.getPosition().y * PPM - (tex.getHeight()/2));
        batch.end();
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        map.dispose();
        tmr.dispose();
        batch.dispose();
        tex.dispose();
        rayHandler.dispose();
        light.dispose();
        coneLight.dispose();
    }

    public void inputUpdate(float delta)
    {
        int horizontalForce = 0;
        int verticalForce = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            horizontalForce -= 1;
            animation = animationLeft;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            horizontalForce += 1;
            animation = animationRight;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            //player.applyForceToCenter(0, 300, false);
            verticalForce += 1;
            animation = animationUp;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            //player.applyForceToCenter(0, 300, false);
            verticalForce -= 1;
            animation = animationDown;
        }

		/*if(!Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.UP)
				&& !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			animation.setPlayMode();*/

        player.setLinearVelocity(horizontalForce * 5, verticalForce * 5);

        rotatePlayerToMouse(camera);
    }

    public void animationUpdate()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            animation = animationLeft;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            animation = animationRight;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            animation = animationUp;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            animation = animationDown;
        }
    }

    private void rotatePlayerToMouse(OrthographicCamera camera) {

        Vector3 mousePos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        float deltaX = mousePos.x - player.getPosition().x*PPM;
        float deltaY = mousePos.y - player.getPosition().y*PPM;

        float nuevoAngulo = (float) Math.atan2(deltaY, deltaX);

        player.setAngularVelocity(0);
        player.setTransform(player.getPosition().x, player.getPosition().y, nuevoAngulo);
    }

    public Body createBox(int x, int y, int width, int height, boolean isStatic, boolean fixRotation)
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

    public void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = player.getPosition().x * PPM;
        position.y = player.getPosition().y * PPM;

        camera.position.set(position);

        camera.update();
    }

}
