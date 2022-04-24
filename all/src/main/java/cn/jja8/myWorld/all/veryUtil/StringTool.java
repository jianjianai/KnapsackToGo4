package cn.jja8.myWorld.all.veryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StringTool {
    /**
     * 替换stringList
     * */
    public static List<String> stringListReplaces(List<String> list,Map<String,String> replaces){
        ArrayList<String> strings = new ArrayList<>();
        for (String string : list) {
            for (String s:replaces.keySet()){
                string = string.replaceAll(s,replaces.get(s));
            }
            strings.add(string);
        }
        return strings;
    }
    /**
     * 替换stringList
     * */
    public static List<String> stringListReplaces(List<String> list,String string,String newString){
        HashMap<String,String> hashMap = new HashMap();
        hashMap.put(string,newString);
        return stringListReplaces(list,hashMap);
    }
}
