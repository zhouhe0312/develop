package cn.powertime.iatp.commons;

public class Safelevel {

    /**
     * 密码强度计算
     * @param password
     * @return
     */
    public static int score(String password) {
        int score = 0;
        // 密码长度:
        // 5 分: 小于等于4个字符
        // 10 分: 5 到7 字符
        // 25 分: 大于等于8个字符
        if (password.length() >= 8) {
            score += 25;
        } else if (password.length() >= 5) {
            score += 10;
        } else if (password.length() <= 4) {
            score += 5;
        }
        int upplet = 0;
        int dowlet = 0;
        int numlet = 0;
        int othlet = 0;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
                upplet++;
            } else if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z') {
                dowlet++;
            } else if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
                numlet++;
            } else if((password.charAt(i) >= 0x21 && password.charAt(i) <= 0x2F) || (password.charAt(i) >= 0x3A && password.charAt(i) <= 0x40) || (password.charAt(i) >= 0x5B && password.charAt(i) <= 0x60) || (password.charAt(i) >= 0x7B && password.charAt(i) <= 0x7E)){
                othlet++;
            }
        }
        // 字母
        // 10 分: 全都是小（大）写字母
        // 20 分: 大小写混合字母
        if ((upplet != 0 && dowlet == 0) || (upplet == 0 && dowlet != 0)) {
            score += 10;
        } else if (upplet != 0 && dowlet != 0) {
            score += 20;
        }
        // 数字
        // 10 分: 1 个数字
        // 20 分: 大于1 个数字
        if (numlet == 1) {
            score += 10;
        } else if (numlet > 1) {
            score += 20;
        }
        // 符号(ASCII码表可以在UltraEdit的菜单view->ASCII Table查看)
        // !"#$%&'()*+,-./     (ASCII码：0x21~0x2F)
        // :;<=>?@             (ASCII<=><=><=><=><=>码：0x3A~0x40)
        // [\]^_`              (ASCII码：0x5B~0x60)
        // {|}~                (ASCII码：0x7B~0x7E)
        // 10 分: 1 个符号
        // 25 分: 大于1 个符号
        if (othlet == 1) {
            score += 10;
        } else if (othlet > 1) {
            score += 25;
        }
        //奖励:
        //  2 分: 字母和数字
        //  3 分: 字母、数字和符号
        //  5 分: 大小写字母、数字和符号
        if (numlet != 0 && upplet != 0 && dowlet != 0 && othlet != 0) {
            score += 5;
        } else if (numlet != 0 && (upplet != 0 || dowlet != 0) && othlet != 0) {
            score += 3;
        } else if (numlet != 0 && (upplet != 0 || dowlet != 0)) {
            score += 2;
        }
        //最后的评分标准:
        // >= 90: 非常安全(VERY_WEAK)
        // >= 80: 安全(WEAK)
        // >= 70: 非常强(VERY_STRONG)
        // >= 60: 强(STRONG)
        // >= 50: 一般(AVERAGE)
        // >= 25: 弱(WEAK)
        // >= 0:  非常弱(VERY_WEAK)
        return score;
    }

}
