public class BruteCollinearPoints {
    private int numberOfSegments;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points){
        for(int n = 0; n < points.length; n++){
            for(int i = n + 1; i < points.length; i++){

            }
        }
    }    // finds all line segments containing 4 points

    public int numberOfSegments(){
        return numberOfSegments;
    }        // the number of line segments

    public LineSegment[] segments(){
        return segments;
    }                // the line segments
}