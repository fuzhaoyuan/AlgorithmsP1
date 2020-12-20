import java.util.Comparator;

public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points){
        segments = new LineSegment[points.length];
        Point[] exam = new Point[4];
        double[] slopes = new double[3];
        for(int one = 0; one < points.length-3; one++){
            exam[0] = points[one];
            for(int two = one+1; two < points.length-2; two++){
                exam[1] = points[two];
                for(int three = two+1; three < points.length-1; three++){
                    exam[2] = points[three];
                    for(int four = three+1; four < points.length; four++){
                        exam[3] = points[four];
                        //examine 4 points
                        for(int i = 0; i < 3; i++) slopes[i] = exam[0].slopeTo(exam[i+1]);
                        if(allMatch(slopes)){
                            int min = 0, max = 0;
                            for(int n = 0; n < 4; n++){
                                if(exam[0].compareTo(exam[n]) < 0) max = n;
                                else if(exam[0].compareTo(exam[n]) > 0) min = n;
                            }
                            segments[numberOfSegments++] = new LineSegment(exam[min], exam[max]);
                        }
                    }
                }
            }
        }
        LineSegment[] auxSegments = new LineSegment[numberOfSegments];
        for(int n = 0; n < numberOfSegments; n++) auxSegments[n] = segments[n];
        segments = auxSegments;
    }    // finds all line segments containing 4 points

    private boolean allMatch(double[] a){
        int match = 0;
        for(int n = 1; n < a.length; n++){
            if(a[0] == a[n]) match++;
            else break;
        }
        return match == a.length - 1;
    }

    public int numberOfSegments(){
        return numberOfSegments;
    }        // the number of line segments

    public LineSegment[] segments(){
        return segments;
    }                // the line segments
}