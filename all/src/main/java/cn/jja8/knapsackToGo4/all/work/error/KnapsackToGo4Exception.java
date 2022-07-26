package cn.jja8.knapsackToGo4.all.work.error;

import cn.jja8.knapsackToGo4.all.work.Logger;

public class KnapsackToGo4Exception extends Exception {
    String message;
    Throwable cause;
    Logger logger;
    public KnapsackToGo4Exception(Logger logger,String message) {
        super(message);
        this.logger = logger;
        this.message = message;
    }
    public KnapsackToGo4Exception(Logger logger,Throwable cause, String message) {
        super(message);
        this.logger = logger;
        this.message = message;
        this.cause = cause;
    }

    /**
     * 不推荐
     * */
    public KnapsackToGo4Exception(String message) {
        super(message);
        this.message = message;
    }

    /**
     * 不推荐
     * */
    public KnapsackToGo4Exception(Throwable cause,String message) {
        super(message);
        this.message = message;
        this.cause = cause;
    }

    @Override
    public void printStackTrace() {
        if (logger!=null){
            logger.severe("插件运行时发生异常，原因："+ message);
            logger.severe("下方是详细异常信息，如需反馈请将其完整反馈给开发者。");
        }else {
            System.out.println("插件运行时发生异常，原因："+ message);
            System.out.println("下方是详细异常信息，如需反馈请将其完整反馈给开发者。");
        }
        super.printStackTrace();
        if (cause!=null){
            cause.printStackTrace();
        }
    }
}
