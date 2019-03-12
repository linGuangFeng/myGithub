package entity;

import java.lang.reflect.Array;
import java.util.*;

public class CollectionBean {
    List<String> list;
    Set<String> set;

    Map<String,Object> map;
    Properties properties ;
    String[] array;
    public CollectionBean() {
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public CollectionBean(List<String> list, Set<String> set, Map<String, Object> map, Properties properties, String[] array) {
        this.list = list;
        this.set = set;
        this.map = map;
        this.properties = properties;
        this.array = array;
    }


    @Override
    public String toString() {
        return "CollectionBean{：" +
                "\n list=" + list +
                "\n set=" + set +
                "\n map=" + map +
                "\n properties=" + properties +
                "\n array=" + Arrays.toString(array) +
                "\n" +
                '}';
    }
}
