public class FastCollinearPoints {
    private int numberOfSegments;
    private LineSegment[] segments;

    private class SloPoint{
        Point point;
        double slope;
    }

    public FastCollinearPoints(Point[] points){
        for(int p = 0; p < points.length; p++){
            SloPoint[] a = new SloPoint[points.length-1];
            for(int q = 0; q != p && q < points.length; q++){
                a[q-1].point = points[q];
                a[q-1].slope = points[p].slopeTo(points[q]);
            }
            SloPoint[] aux = new SloPoint[a.length];

        }
    }     // finds all line segments containing 4 or more points

    private void merge(SloPoint[] a, SloPoint[] aux, int lo,int mid, int hi){
        for(int k = lo; k <= hi; k++) aux[k] = a[k];
        int i = lo, j = mid+1;
        for(int k = lo; k <= hi; k++){
            if(i > mid) a[k] = aux[j++];
            else if(j > hi) a[k] = aux[i++];
            else if(aux[j].slope < aux[i].slope) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    private void sort(SloPoint[] a, SloPoint[] aux, int lo, int hi){
        if(a.length <= 7){
            insertionSort(a);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid, hi);
        merge(a, aux, lo, mid, hi);
    }

    private void insertionSort(SloPoint[] a){
        for(int i = 0; i < a.length; i++){
            for(int j = i; j > 0; j--){
                if(a[j].slope < (a[j-1].slope)){
                    SloPoint swap = a[j];
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
        return segments;
    }                // the line segments
}