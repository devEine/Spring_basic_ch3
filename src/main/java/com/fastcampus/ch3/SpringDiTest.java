package com.fastcampus.ch3;

import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
class Car{
    public  Car(){} //bean태그에서 property사용할땐 기본생성자 필수
    //기본 생성자로 property의 값 초기화하기 때문

    public Car(String color,int oil,Engine engine,Door[] doors){
        this.color = color;
        this.oil = oil;
        this.engine = engine;
        this.doors = doors;
    }

    @Value("red")
    String color;
    @Value("100")
    int oil;
    @Autowired Engine engine;
    @Autowired Door[] doors;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getOil() {
        return oil;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Door[] getDoors() {
        return doors;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", oil=" + oil +
                ", engine=" + engine +
                ", doors=" + Arrays.toString(doors) +
                '}';
    }
}
@Component("engine") //<bean id="engine" class="com.fastcampus.ch3"/>
class Engine {
}

@Component
class SuperEngine extends Engine{
}

@Component
class TurboEngine extends Engine {
}
@Component
class Door{}
public class SpringDiTest {
    public static void main(String[] args) {
        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
        //밑의 두개는 같은 문장, 형변환을 .getBean메서드의 참조변수로 데이터타입 명시해서도 가능
        //Car car = (Car) ac.getBean("car"); //by-name 이름으로 검색
        Car car = ac.getBean("car", Car.class); //by-name 이름으로 검색
        Car car2 = (Car) ac.getBean(Car.class); //by-name 이름으로 검색


        Engine engine = (Engine) ac.getBean("superEngine");
        //Engine이 상속을 받아 타입이 여러개일땐 type으로 찾으면 안됨, 이름으로 찾아야함
        //Engine engine = (Engine) ac.getBean(Engine.class);
        Door door = (Door) ac.getBean("door");
        //1.set으로 수동으로 car의 iv값 넣어주기
 /*       car.setColor("red");
        car.setOil(100);
        car.setEngine(engine);
        car.setDoors(new Door[]{ac.getBean("door",Door.class), ac.getBean("door", Door.class)});
 */       //2.cofig.xml에서 property태그로 Car bean의 iv값 넣어주기 => setter를 이용한 것


        //매번 새로운 객체 생성 원하면
        //config.xml파일의 bean 태그의 scope="protoType"으로 설정
        System.out.println("car: "+car);
        System.out.println("engine: "+engine);

    }
}
