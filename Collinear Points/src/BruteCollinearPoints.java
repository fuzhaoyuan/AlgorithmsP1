import java.util.Comparator;

public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private Node lineSegments = null;
    private Node first = null;

    private class Node
    {
        LineSegment segment;
        Node next;
    }

    public BruteCollinearPoints(Point[] points){
        if(points == null) throw new IllegalArgumentException();
        int N = points.length;
        for(int i = 0; i < N; i++){
            if(points[i] == null) throw new IllegalArgumentException();
            for(int j = 0; j < N; j++){
                if(j == i) continue;
                if(points[j] == null) throw new IllegalArgumentException();
                if(points[j].compareTo(points[i]) == 0) throw new IllegalArgumentException();
            }
        }

        Point[] exam = new Point[4];

        for(int one = 0; one < N-3; one++){
            exam[0] = points[one];

            for(int two = one+1; two < N-2; two++){
                exam[1] = points[two];

                for(int three = two+1; three < N-1; three++){
                    exam[2] = points[three];
                    Comparator<Point> slopeOrder = exam[0].slopeOrder();
                    if(slopeOrder.compare(exam[1],exam[2]) == 0){

                        for(int four = three+1; four < N; four++){
                            exam[3] = points[four];
                            if(slopeOrder.compare(exam[2],exam[3]) == 0){
                                Point[] result = insertionSort(exam);
                                Node old = lineSegments;
                                lineSegments = new Node();
                                lineSegments.segment = new LineSegment(result[0], result[3]);
                                if(first == null) first = lineSegments;
                                else old.next = lineSegments;
                                numberOfSegments++;
                            }
                        }
                    }
                }
            }
        }
    }    // finds all line segments containing 4 points

    private Point[] insertionSort(Point[] exam){
        Point[] result = new Point[4];
        for(int n = 0; n < 4; n++) result[n] = exam[n];
        for(int i = 0; i < result.length; i++){
            for(int j = i; j > 0; j--){
                if(result[j].compareTo(result[j-1]) < 0){
                    Point swap = result[j];
                    result[j] = result[j-1];
                    result[j-1] = swap;
                }else break;
            }
        }
        return result;
    }

    public int numberOfSegments(){
        return numberOfSegments;
    }        // the number of line segments

    public LineSegment[] segments(){
        LineSegment[] segments = new LineSegment[numberOfSegments];
        Node current = first;
        for(int i = 0; i < numberOfSegments; i++){
            segments[i] = current.segment;
            current = current.next;
        }
        return segments;
    }                // the line segments
}