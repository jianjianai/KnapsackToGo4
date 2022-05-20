package cn.jja8.knapsackToGo4.bukkit.error;

import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;

public class KnapsackToGo4Exception extends Exception {
    String message;
    public KnapsackToGo4Exception(String message) {
        super(message);
        this.message = message;
    }
    public KnapsackToGo4Exception(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    @Override
    public void printStackTrace() {
        KnapsackToGo4.knapsackToGo4.getLogger().severe("插件运行时发生异常，原因："+ message);
        KnapsackToGo4.knapsackToGo4.getLogger().severe("下方是详细异常信息，如需反馈请将其完整反馈给开发者。");
        super.printStackTrace();
    }
}
