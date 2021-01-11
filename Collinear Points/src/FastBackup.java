public class FastBackup {
    private int numberOfSegments;
    private LineSegment[] segments;

    private class SloPoint{
        Point point;
        double slope;
    }

    public FastBackup(Point[] points){
        segments = new LineSegment[points.length];
        for(int p = 0; p < points.length; p++){
            SloPoint[] a = new SloPoint[points.length-1];
            for(int i = 0, j = 0; i < a.length; i++){
                a[i] = new SloPoint();
                if(i == p){
                    a[i].point = points[j+1];
                    a[i].slope = points[p].slopeTo(points[j+1]);
                    j+=2;
                    continue;
                }
                a[i].point = points[j];
                a[i].slope = points[p].slopeTo(points[j++]);
            }
            SloPoint[] aux = new SloPoint[a.length];
            sort(a, aux, 0, a.length-1);
            int count = 1;
            for(int c = 0; c < a.length-1; c++){
                if(a[c].slope == a[c+1].slope){
                    count++;
                    if(c+1 == a.length-1 && count == 3){
                        findSegments(points,a,count,p,c);
                    }
                }else{
                    if(count == 3){
                        findSegments(points,a,count,p,c);
                        count = 1;
                    }else{
                        count = 1;
                    }
                }
            }
        }
        LineSegment[] auxSegments = new LineSegment[numberOfSegments];
        for(int n = 0; n < numberOfSegments; n++) auxSegments[n] = segments[n];
        segments = auxSegments;
    }     // finds all line segments containing 4 or more points

    private void findSegments(Point[] points, SloPoint[] a, int count, int p, int c){
        SloPoint[] findM = new SloPoint[count+1];
        findM[0] = new SloPoint();
        findM[0].point = points[p];
        for(int i = 1, j = c+1-count; i < count+1; i++, j++) findM[i] = a[j];
        int min = 0, max = 0;
        for(int n = 1; n < count; n++){
            if(findM[0].point.compareTo(findM[n].point) < 0) max = n;
            else min = n;
//                                else if(findM[0].point.compareTo(findM[n].point) > 0) min = n;
        }
        LineSegment addMe = new LineSegment(findM[min].point, findM[max].point);
        if(checkSegments(segments,addMe)) segments[numberOfSegments++] = addMe;
    }

    private boolean checkSegments(LineSegment[] segments, LineSegment addMe){
        for(int s = 0; s < segments.length; s++){
            if(segments[s] == null) return true;
            else if(segments[s] == addMe) return false;
        }
        return true;
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

    private void sort(SloPoint[] a, SloPoint[] aux, int lo, int hi){
        if(hi - lo <= 7){
            insertionSort(a, lo, hi);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private void insertionSort(SloPoint[] a, int lo, int hi){
        for(int i = lo; i < hi+1; i++){
            for(int j = i; j > lo; j--){
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