package com.fastcampus.ch3.diCopy3;


import com.google.common.reflect.ClassPath;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@org.springframework.stereotype.Component
class Car{ }
@org.springframework.stereotype.Component class SportsCar extends Car{}
@org.springframework.stereotype.Component class Truck extends  Car{}
@org.springframework.stereotype.Component class Engine{}

//===============class AppContext===============
class ApppContext {
    Map map; //객체 저장소

    ApppContext() {
        map = new HashMap();
        doComponentScan();
    }

    private  void doComponentScan(){
        //패키지 내의 클래스 목록 가져온다

        //반복문으로 클래스를 하나씩 일어와서 @Component이 붙어 있는지 확인

        //@component가 붙어있으면 객체를 생성해서 map에 저장
        try {
            ClassLoader classLoader = ApppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy3");

            for(ClassPath.ClassInfo classInfo : set){
                Class clazz = classInfo.load();
                Component component = (Component) clazz.getAnnotation(Component.class);
                if(component != null){ //@Component 애너테이션이 있으면 map에 저장
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //by name : 객체 이름으로 찾기
    Object getBean(String key){
        return  map.get(key);
    }

    //by type : 객체 타입으로 찾기
    Object getBean(Class clazz){
        for(Object obj : map.values()){
            if(clazz.isInstance(obj)){
                return  obj;
            }
        }
        return  null;
    }
}
//===============class AppContext===============

public class Main3 {
    public static void main(String[] args) throws  Exception{
        ApppContext ac = new ApppContext();

        Car car = (Car)ac.getBean("car"); //by name
        Car car2 = (Car)ac.getBean(Car.class); // by type
        Engine engine = (Engine) ac.getBean("engine");
        Engine engine2 = (Engine) ac.getBean(Engine.class);


        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
    }

}
