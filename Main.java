package ass7.hw2;

public class Main {

    public static void main(String[] args) {
        SeparateChainHashing<String, Integer> table = new SeparateChainHashing<>();
        String[] keys = {"apple", "pin-apple", "tomato", "potato", "queue", "list", "tree", "leaf", "node", "car", "rat", "bat"};
        int value = 0;
        for (String key : keys) {
            table.put(key, value++);
        }
        //////////////////
        if(keys.length != table.size()) {
            System.out.println("[Size] test Failure. Items number does not math, expected: " + keys.length + ", actual: " + table.size());
            return;
        } else {
            System.out.println("[Size] test OK, expected: " + keys.length + ", actual: " + table.size());
        }
        //////////////////
        for (String key : keys) {
            if(table.containsKey(key) == false) {
                System.out.println("[ContainsKey] failure. Key=" + key + " not found.");
                return;
            }
        }
        System.out.println("[ContainsKey] OK. All expected keys inside table.");
        //////////////////
        for (int val = 0; val < value; ++val) {
            if(table.containsValue(val) == false) {
                System.out.println("[ContainsValue] failure. Value=" + val + " not found.");
                return;
            }
        }
        System.out.println("[ContainsValue] OK. All expected values inside table.");
        //////////////////
        System.out.println("Table items:");
        for (String key : table.keys() ) System.out.println("Key=" + key + ", value=" + table.get(key));
        // Update existing keys
        table.put("apple", 14);
        table.put("apple", 24);
        table.put("pin-apple", 8);
        table.put("queue", 12);

        if(table.get("apple") != 24 || table.get("pin-apple") != 8 || table.get("queue") != 12) {
            System.out.println("[Update existing pairs] Failure.");
            return;
        }
        System.out.println("[Update existing pairs] OK.");
        System.out.println("\nUpdated table items:");
        for (String key : table.keys() ) System.out.println("Key=" + key + ", value=" + table.get(key));
        // Remove items
        table.delete("queue");
        table.delete("apple");
        table.delete("rat");
        table.delete("bat");
        // Check these keys should be not found in the table but all old values should be in
        for (String key : keys) {
            if( table.containsKey(key) == false && !key.equals("queue") && !key.equals("apple") && !key.equals("rat") && !key.equals("bat") ) {
                System.out.println("[ContainsKey] failure. Key=" + key + " not found.");
                return;
            }
        }
        System.out.println("[ContainsKey] OK. All expected keys inside table.");
        System.out.println("\nResult table items:");
        for (String key : table.keys() ) System.out.println("Key=" + key + ", value=" + table.get(key));


    }
}
