/**
 * Created by Kevin on 3/17/2017.
 */

public enum turnPhase {
    BUILD,
    TILE_PLACEMENT,
    FOUND_SETTLEMENT,
    EXPAND_SETTLEMENT,
    PLACE_TOTORO;

    public int getTurnPhase() {
        switch(turnPhase.valueOf(this.name())) {
            case TILE_PLACEMENT:
                return 0; // phase 1
            case FOUND_SETTLEMENT:
            case BUILD:
            case EXPAND_SETTLEMENT:
            case PLACE_TOTORO:
                return 1; // phase 2
            default:
                throw new RuntimeException("Reached unknown turn phase");
        }
    }
}