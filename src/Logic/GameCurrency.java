package Logic;

public class GameCurrency {
    private static int money;
    private static final int MAX_MONEY = 3000;
    
    // ใช้เงินตามชนิดของ Plant
    public static boolean spend(int cost) {
       
        if (money >= cost) {
            money -= cost; // หักเงิน
            return true;   // ใช้งานสำเร็จ
        }
        return false; // เงินไม่พอ
    }

    public static void setMoney() {
    	GameCurrency.money = MAX_MONEY;
    }

    public static int getMoney() {
        return money;
    }

    public static int getMaxMoney() {
        return MAX_MONEY;
    }

}
