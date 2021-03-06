
public class FastCollinearPoints {
    private int numberOfSegments;
    private Node lineSegments = null;
    private Node first = null;
    private SloPoint[] sloPoints;
    private SloPoint[] aux;

    private class Node
    {
        LineSegment segment;
        Node next;
    }

    private class SloPoint{
        Point point;
        double slope;
    }

    public FastCollinearPoints(Point[] points){
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

        sloPoints = new SloPoint[N];
        aux = new SloPoint[N];
        for(int p = 0; p < N; p++){
            sloPoints[p] = new SloPoint();
            sloPoints[p].point = points[p];
        }

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++) sloPoints[j].slope = points[i].slopeTo(sloPoints[j].point);
            mergeSort(sloPoints, aux, 0, N-1);
            findSegments();
        }
    }     // finds all line segments containing 4 or more points

    private void findSegments(){
        for(int a = 1; a < sloPoints.length; a++){
            int b = a+1;
            while(b < sloPoints.length && sloPoints[b].slope == sloPoints[b-1].slope) b++;
            int c = b - a;
            if(c >= 3){
                Point[] exam = new Point[c+1];
                exam[0] = sloPoints[0].point;
                for(int d = 1, e = a; d < c+1; d++, e++) exam[d] = sloPoints[e].point;
                insertionSort(exam);
                if(exam[0] == sloPoints[0].point){
                    Node old = lineSegments;
                    lineSegments = new Node();
                    lineSegments.segment = new LineSegment(exam[0], exam[c]);
                    if(first == null) first = lineSegments;
                    else old.next = lineSegments;
                    numberOfSegments++;
                }
                a = b-1;
            }
        }
    }

    private void merge(SloPoint[] a, SloPoint[] aux, int lo, int mid, int hi){
        for(int k = lo; k <= hi; k++) aux[k] = a[k];
        int i = lo, j = mid+1;
        for(int k = lo; k <= hi; k++){
            if(i > mid) a[k] = aux[j++];
            else if(j > hi) a[k] = aux[i++];
            else if(aux[j].slope < aux[i].slope) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    private void mergeSort(SloPoint[] a, SloPoint[] aux, int lo, int hi){
        if(hi - lo <= 7){
            insertionSort(a, lo, hi);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        mergeSort(a, aux, lo, mid);
        mergeSort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private void insertionSort(SloPoint[] a, int lo, int hi){
        for(int i = lo; i < hi+1; i++){
            for(int j = i; j > lo; j--){
                if(a[j].slope < a[j-1].slope){
                    SloPoint swap = a[j];
                    a[j] = a[j-1];
                    a[j-1] = swap;
                }else break;
            }
        }
    }

    private void insertionSort(Point[] a){
        for(int i = 0; i < a.length; i++){
            for(int j = i; j > 0; j--){
                if(a[j].compareTo(a[j-1]) < 0){
                    Point swap = a[j];
                    a[j] = a[j-1];
                    a[j-1] = swap;
                }else break;
            }
        }
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