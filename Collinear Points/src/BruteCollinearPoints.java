import java.util.Comparator;

public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points){
        if(points == null) throw new IllegalArgumentException();
        segments = new LineSegment[points.length-3];
        Point[] exam = new Point[4];

        for(int one = 0; one < points.length-3; one++){
            if(points[one] == null) throw new IllegalArgumentException();
            exam[0] = points[one];

            for(int two = one+1; two < points.length-2; two++){
                if(points[two] == null || points[two] == points[one]) throw new IllegalArgumentException();
                exam[1] = points[two];

                for(int three = two+1; three < points.length-1; three++){
                    if(points[three] == null || points[three] == points[two] || points[three] == points[one]) throw new IllegalArgumentException();
                    exam[2] = points[three];

                    for(int four = three+1; four < points.length; four++){
                        if(points[four] == null || points[four] == points[three] || points[four] == points[two] || points[four] == points[one]) throw new IllegalArgumentException();
                        Comparator<Point> slopeOrder = exam[0].slopeOrder();
                        if(slopeOrder.compare(exam[1],exam[2]) == 0){
                            exam[3] = points[four];
                            if(slopeOrder.compare(exam[2],exam[3]) == 0){
                                insertionSort(exam);
                                segments[numberOfSegments++] = new LineSegment(exam[0], exam[3]);
                            }
                        }
                    }
                }
            }
        }
        LineSegment[] auxSegments = new LineSegment[numberOfSegments];
        for(int n = 0; n < numberOfSegments; n++) auxSegments[n] = segments[n];
        segments = auxSegments;
    }    // finds all line segments containing 4 points

    private void insertionSort(Point[] exam){
        for(int i = 0; i < exam.length; i++){
            for(int j = i; j > 0; j--){
                if(exam[j].compareTo(exam[j-1]) < 0){
                    Point swap = exam[j];
                    exam[j] = exam[j-1];
                    exam[j-1] = swap;
                }else break;
            }
        }
    }

    public int numberOfSegments(){
        return numberOfSegments;
    }        // the number of line segments

    public LineSegment[] segments(){
        return segments;
    }                // the line segments
}