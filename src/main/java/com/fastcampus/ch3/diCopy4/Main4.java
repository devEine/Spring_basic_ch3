package com.fastcampus.ch3.diCopy4;


import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
class Car{
    @Autowired Engine engine;
    @Autowired Door door;

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }
}
@Component
class SportsCar extends Car{}
@Component
class Truck extends  Car{}
@Component
class Engine{}
@Component
class Door{

}

//===============class AppContext===============
class ApppContext {
    Map map; //객체 저장소

    ApppContext() {
        map = new HashMap();
        doComponentScan();
    }
    //map에 저장된 객체의 iv중에 @Autowired가 붙어 있으면
    //map에서 iv의 타입에 맞는 객체를 찾아서 연결(객체의 주소를 iv에 저장)
    private  void doAutoWired(){

            for(Object bean: map.values()){
                for(Field fid : bean.getClass().getDeclaredFields()){
/*                    if(fid.getAnnotations(Autowired.class)!=null){
                       fid.set(bean,getBean(fid.getType()));
                    }*/
                }
            }

    }

    private  void doComponentScan(){
        //패키지 내의 클래스 목록 가져온다

        //반복문으로 클래스를 하나씩 일어와서 @Component이 붙어 있는지 확인

        //@component가 붙어있으면 객체를 생성해서 map에 저장
        try {
            ClassLoader classLoader = ApppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy4");

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

public class Main4 {
    public static void main(String[] args) throws  Exception{
        ApppContext ac = new ApppContext();

        Car car = (Car)ac.getBean("car"); //by name
        Engine engine = (Engine) ac.getBean("engine");
        Door door = (Door)ac.getBean(Door.class);



        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
        System.out.println("door = " + door);
    }

}
