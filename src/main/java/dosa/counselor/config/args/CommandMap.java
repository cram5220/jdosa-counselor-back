package dosa.counselor.config.args;


import java.util.*;

public class CommandMap {
    Map<String,Object> map = new HashMap<String, Object>();
    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

    public Object get(String key){
        return map.get(key);
    }

    public void put(String key, Object value){
        map.put(key, value);
    }

    public Object remove(String key){
        return map.remove(key);
    }

    public boolean containsKey(Object key){
        return map.containsValue(key);
    }

    public boolean containsValue(Object value){
        return map.containsValue(value);
    }

    public void clear(){
        map.clear();
    }

    public Set<Map.Entry<String, Object>> entrySet(){
        return map.entrySet();
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public void putAll(Map<? extends String, ?extends Object> m){
        map.putAll(m);
    }

    public Map<String,Object> getMap(){
        return map;
    }

    public List<Map<String,Object>> getList(){
        return list;
    }

}