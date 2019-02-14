package com.example.demoswagger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FactoryApplicationTest {


    /**
     * 抽象工厂
     * 一个总工厂（用来生产小工厂）
     * 产品接口（子类实例生产产品）
     * 工厂生产者（生产工厂）
     */
    @Test//抽象工厂模式（多工厂生成多产品）
    public void gongchang() {
        //获取工厂shape
        AbstractFactory shapeFaxtory = FactoryProducer.getFactory("shape");
        Shape circle = shapeFaxtory.getShape("Circle");
        circle.draw();
    }
}

//抽象工厂
abstract class AbstractFactory{
    abstract Shape getShape(String shapeType);
    abstract Color getColor(String colorType);
}

//子工厂
class ShapeFactory extends AbstractFactory{
    @Override
    Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if(shapeType.equalsIgnoreCase("CIRCLE")){
            return new Circle();
        } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
            return new Rectangle();
        } else if(shapeType.equalsIgnoreCase("SQUARE")){
            return new Square();
        }
        return null;
    }

    @Override
    Color getColor(String colorType) {
        return null;
    }
}
//子工厂
class ColorFactory extends AbstractFactory{
    @Override
    Shape getShape(String shapeType) {
        return null;
    }

    @Override
    Color getColor(String color) {
        if(color == null){
            return null;
        }
        if(color.equalsIgnoreCase("RED")){
            return new Red();
        } else if(color.equalsIgnoreCase("GREEN")){
            return new Green();
        } else if(color.equalsIgnoreCase("BLUE")){
            return new Blue();
        }
        return null;
    }
}

//产品a
interface Shape{
    void draw();
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
class Square  implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
class Circle  implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}


//产品b
interface Color{
    void fill();
}
class Red implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Red::fill() method.");
    }
}
class Green  implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Green::fill() method.");
    }
}
class Blue  implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Blue::fill() method.");
    }
}

//工厂生产者
class FactoryProducer{
    static AbstractFactory getFactory(String factoryType){
        if("shape".equals(factoryType)){
            return new ShapeFactory();
        } else if ("color".equals(factoryType)) {
            return new ColorFactory();
        }
        return null;
    }
}
