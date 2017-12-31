/**
 * Created by Recep Sivri on 21.11.2017.
 */

public class NextPixel implements Comparable<NextPixel> {
    public int[] coordinates;
    public double cost;
    public double gradientCost;
    public NextPixel(double cost, int[] coordinates){
        this.cost =cost;
        this.coordinates = coordinates;
    }
    public NextPixel(double cost,double gradientCost, int[] coordinates){
        this.cost =cost;
        this.gradientCost = gradientCost;
        this.coordinates = coordinates;
    }

    public int compareTo(NextPixel other){
        if( cost < other.cost){
            return -1;
        }else{
            if( cost > other.cost){
                return +1;
            }else{
                return 0;
            }
        }
    }

}