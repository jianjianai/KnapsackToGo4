package cn.jja8.knapsackToGo4.all.work.error;


import cn.jja8.knapsackToGo4.all.work.Logger;

public class NoPlayerLockException extends KnapsackToGo4Exception{
    public NoPlayerLockException(Logger logger, String message) {
        super(logger, message);
    }
}
