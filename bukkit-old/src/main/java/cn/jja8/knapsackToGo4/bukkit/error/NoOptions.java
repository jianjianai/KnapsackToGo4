package cn.jja8.knapsackToGo4.bukkit.error;
/**
 * 配置文件选项不存在是抛出的异常
 * */
public class NoOptions extends ConfigLoadException{
    public NoOptions(String message) {
        super(message);
    }

    public NoOptions(Throwable cause, String message) {
        super(cause, message);
    }
}
