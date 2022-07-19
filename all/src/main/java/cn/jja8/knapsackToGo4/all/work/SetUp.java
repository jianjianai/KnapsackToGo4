package cn.jja8.knapsackToGo4.all.work;

/**
 * work的参数设置
 * */
public class SetUp {
    public static class Lang{
        /** 加载完成 */
        public  String loadingFinish = "欢迎！";
        /** 正在加载 */
        public  String isLoading = "等待其他服务器保存数据..<数>";
    }
    /** 语言 */
    public Lang lang = new Lang();
    /** 锁检查间隔（毫秒） */
    public long LockDetectionInterval = 250;
    /** 自动保存（单位秒） **/
    public long AutoSave = 250;
}

