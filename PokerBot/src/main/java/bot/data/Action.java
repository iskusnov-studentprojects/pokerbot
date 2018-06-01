package bot.data;

public class Action {
    ActionTypes type;
    int value;

    public Action(ActionTypes type, int value) {
        this.type = type;
        this.value = value;
    }

    public ActionTypes getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
