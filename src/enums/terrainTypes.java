package enums;

/**
 * Created by KJ on 3/13/2017.
 */
public enum terrainTypes {
    JUNGLE,
    LAKE,
    GRASSLANDS,
    ROCKY,
    VOLCANO;

    public int getHabitablity() {
        switch(terrainTypes.valueOf(this.name())) {
            case JUNGLE:
            case LAKE:
            case GRASSLANDS:
            case ROCKY:
                return 1; // habitable
            case VOLCANO:
                return 0; // uninhabitable
            default:
                throw new RuntimeException("Reached unknown terrain type");
        }
    }
}
