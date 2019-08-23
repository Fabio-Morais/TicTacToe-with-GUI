package sample;


public class Move {
    private int xCoordinates;
    private int yCoordinates;
    private int simulationResult;
    private boolean isDraw;

    public Move(){
        this.xCoordinates = -10;
        this.yCoordinates = -10;
        this.simulationResult = 0;
        this.isDraw = false;
    }

    /**
     * @return the xCoordinates
     */
    public int getxCoordinates() {
        return xCoordinates;
    }

    /**
     * @param xCoordinates the xCoordinates to set
     */
    public void setxCoordinates(int xCoordinates) {
        this.xCoordinates = xCoordinates;
    }

    /**
     * @return the yCoordinates
     */
    public int getyCoordinates() {
        return yCoordinates;
    }

    /**
     * @param yCoordinates the yCoordinates to set
     */
    public void setyCoordinates(int yCoordinates) {
        this.yCoordinates = yCoordinates;
    }

    /**
     * @return the simulationResult
     */
    public int getSimulationResult() {
        return simulationResult;
    }

    /**
     * @param simulationResult the simulationResult to set
     */
    public void setSimulationResult(int simulationResult) {
        this.simulationResult = simulationResult;
    }

    /**
     * @return the isDraw
     */
    public boolean isDraw() {
        return isDraw;
    }

    /**
     * @param isDraw the isDraw to set
     */
    public void setIsDraw(boolean isDraw) {
        this.isDraw = isDraw;
    }
}
