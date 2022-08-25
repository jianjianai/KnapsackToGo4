package cn.jja8.knapsackToGo4.sponge.patronSaint.all.V2.file;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class YamlConfig {
    /**
     * 将对象已yaml的形式保存到文件中
     */
    public static String saveToString(Object o) throws YamlException {
        com.esotericsoftware.yamlbeans.YamlConfig yamlConfig = new com.esotericsoftware.yamlbeans.YamlConfig();
        yamlConfig.writeConfig.setWriteDefaultValues(true);
        yamlConfig.writeConfig.setEscapeUnicode(false);
        yamlConfig.writeConfig.setWriteRootElementTags(false);
        yamlConfig.writeConfig.setWriteRootTags(false);
        yamlConfig.writeConfig.setUseVerbatimTags(false);
        StringWriter stringWriter = new StringWriter();
        YamlWriter yamlWriter = new YamlWriter(stringWriter,yamlConfig);
        yamlWriter.write(o);
        yamlWriter.close();
        return stringWriter.toString();
    }
    /**
     * 将yaml读取到对象
     */
    public static <T> T loadFromString(String s, Class<T> Class) throws IOException {
        YamlReader yamlReader = new YamlReader(new StringReader(s));
        T o = yamlReader.read(Class);
        yamlReader.close();
        return o;
    }
    /**
     * 从文件加载配置，如果没有就创建一个默认配置
     * */
    public static <T> T loadFromFile(File file, T defaults) throws IOException {
        if (file.exists()){
             return (T)YamlConfig.loadFromString(ReadFile.readFormFile(file), defaults.getClass());
        }else {
            ReadFile.WriteToFile(YamlConfig.saveToString(defaults),file);
            return defaults;
        }
    }
}
