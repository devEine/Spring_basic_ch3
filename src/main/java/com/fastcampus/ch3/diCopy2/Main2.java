package com.fastcampus.ch3.diCopy2;


import org.springframework.context.annotation.Bean;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class Car{ }
class SportsCar extends Car{}
class Truck extends  Car{}
class Engine{}

class ApppContext {
    Map map; //객체 저장소

    ApppContext(){
        map = new HashMap();

        try {
            Properties p = new Properties();
            p.load((new FileReader("config.txt")));

            //Propertise 내용을 map에 저장
            map = new HashMap(p);

            //반복문으로 클래스이름을 얻어서 객체 생성해서 다시 map에 저장
            for(Object key : map.keySet()){ //map에 저장된 key이름 반복문 돌려서 Object객체에 저장
                Class clazz = Class.forName((String)map.get(key));
                map.put(key,clazz.newInstance());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Object getBean(String key){
        return  map.get(key);
    }
}

public class Main2 {
    public static void main(String[] args) throws  Exception{
        ApppContext ac = new ApppContext();

        Car car = (Car)ac.getBean("car");
        Engine engine = (Engine) ac.getBean("engine");

        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
    }

}
