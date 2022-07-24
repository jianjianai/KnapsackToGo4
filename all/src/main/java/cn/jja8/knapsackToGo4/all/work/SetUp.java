package cn.jja8.knapsackToGo4.all.work;

/**
 * work的参数设置
 * */
public class SetUp {
    public static class Lang{
        /** 加载完成 */
        public String loadingFinish = "欢迎！";
        /** 正在加载 */
        public String isLoading = "正在加载你的数据,请稍等..<数>";
        public String waiting = "等待其他服务器保存数据..<数>";
        /** 错误 */
        public String isLoadingError = "服务器开小差了，请联系管理员叫醒他哦..<数>";
        public String isDataError = "数据似乎出错了,请联系管理员恢复数据..<数>";
    }
    /** 语言 */
    public Lang lang = new Lang();
    /** 锁检查间隔（毫秒） */
    public long LockDetectionInterval = 250;
    /** 自动保存（单位秒） 小于等于0为关闭 **/
    public long AutoSave = 250;
}

