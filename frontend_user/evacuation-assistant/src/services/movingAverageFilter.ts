/**
 * This class is responsible for calculating an average of all
 * found signals over a period of time.
 *
 * It also cuts of the bottom and top 10% of all signals to get
 * a more accurate estimated signal.
 */
export class MovingAverageFilter {
    private window: number[];
    private currentIndex: number;
    private windowSize: number;
    private cutoffPercentage: number;

    constructor(windowSize: number, cutoffPercentage: number) {
        this.window = new Array(windowSize).fill(0);
        this.currentIndex = 0;
        this.windowSize = windowSize;
        this.cutoffPercentage = cutoffPercentage;
    }

    addValue(value: number) {
        this.window[this.currentIndex] = value;
        this.currentIndex = (this.currentIndex + 1) % this.windowSize;
    }

    getFilteredValue(): number {
        // Sort the window array in ascending order
        const sorted = this.window.slice().sort((a, b) => a - b);

        // Calculate the number of elements to remove from the top and bottom
        const numToRemove = Math.floor(this.windowSize * this.cutoffPercentage / 100);

        // Compute the sum of the middle values
        let sum = 0;
        for (let i = numToRemove; i < this.windowSize - numToRemove; i++) {
            sum += sorted[i];
        }

        // Compute the average of the middle values
        const avg = sum / (this.windowSize - 2 * numToRemove);

        return avg;
    }
}
