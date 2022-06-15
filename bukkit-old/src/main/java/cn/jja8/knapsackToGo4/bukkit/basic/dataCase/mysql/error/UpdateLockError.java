package cn.jja8.knapsackToGo4.bukkit.basic.dataCase.mysql.error;

import cn.jja8.knapsackToGo4.bukkit.error.KnapsackToGo4Error;

/**
 * 数据库连接失败异常
 * */
public class UpdateLockError extends KnapsackToGo4Error {
    public UpdateLockError(String message) {
        super(message);
    }

    public UpdateLockError(Throwable cause, String message) {
        super(cause, message);
    }
}
