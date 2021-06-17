package com.helloworld.box2dprueba.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TiledObjectUtil {

    public static void parseTiledObjectLayer(World world, MapObjects polyObjects)
    {
        for (MapObject currentObject: polyObjects) {
            Shape shape;
            if(currentObject instanceof PolygonMapObject)
            {
                shape = createPolygone( (PolygonMapObject) currentObject); //casteo a polygonMapObject, probablemente porque nose que otras extensiones puede haber
            }
            else
            {
                continue;
            }

            Body body;
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }

    private  static ChainShape createPolygone(PolygonMapObject polyline)
    {
        float[] vertices = polyline.getPolygon().getTransformedVertices(); //Obtengo un arreglo con los vertices que conforman la polylinea
        Vector2[] worldvertices = new Vector2[vertices.length / 2]; // genero otro arreglo pero de vectores de la mitad de tamaño que el anterior

        for(int i=0; i< worldvertices.length; i++) // filtro los vertices repetidos
        {
            worldvertices[i] = new Vector2(vertices[i*2] / Constants.PPM, vertices[i*2 + 1] / Constants.PPM);
        }

        ChainShape cs = new ChainShape();
        cs.createLoop(worldvertices);

        return cs;
    }

}
