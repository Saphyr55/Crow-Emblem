package fr.saphyr.ce.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CEMath {

    public static boolean almostEqual(float a, float b, float eps){
        return Math.abs(a-b)<eps;
    }

    public static boolean almostEqual(double a, double b, double eps){
        return Math.abs(a-b)<eps;
    }

    public static boolean almostEqual(Vector2 a, Vector2 b, double eps){
        return almostEqual(a.x, b.x, eps) && almostEqual(a.y, b.y, eps);
    }

    public static boolean almostEqual(Vector3 a, Vector3 b, double eps){
        return almostEqual(a.x, b.x, eps) && almostEqual(a.y, b.y, eps);
    }
}
