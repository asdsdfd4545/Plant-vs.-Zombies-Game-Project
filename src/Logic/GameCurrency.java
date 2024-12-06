package Logic;

public class GameCurrency {
    private static int money;
    private static final int MAX_MONEY = 1000;

    public static void setMoney() {
    	GameCurrency.money = MAX_MONEY;
    }

    public static int getMoney() {
        return money;
    }

    public static int getMaxMoney() {
        return MAX_MONEY;
    }

    // ใช้เงินตามชนิดของ Plant
    public static boolean spend(int cost) {
       
        if (money >= cost) {
            money -= cost; // หักเงิน
            return true;   // ใช้งานสำเร็จ
        }
        return false; // เงินไม่พอ
    }

    // เพิ่มเงิน
    public static void addMoney(int amount) {
        if (amount > 0) {
            money = Math.min(money + amount, MAX_MONEY);
        }
    }


}
