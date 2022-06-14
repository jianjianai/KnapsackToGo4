package cn.jja8.knapsackToGo4.all.work;

/**
 * work的参数设置
 * */
public class SetUp {
    public static class Lang{
        /** 加载完成 */
        public  String loadingFinish = "欢迎！";
        /** 正在加载 */
        public  String isLoading = "正在加载你的数据，请稍等..<数>";
    }
    /** 语言 */
    public Lang lang = new Lang();
    /** 锁检查间隔（毫秒） */
    public long LockDetectionInterval = 250;
}
