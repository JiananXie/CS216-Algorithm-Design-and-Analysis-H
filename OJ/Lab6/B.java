import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static point[] points_x;
    static point[] points_y;
    static point[] points_consider;
    public static void main(String[] args) throws FileNotFoundException {
//        System.setIn(new FileInputStream("src/in.txt"));
//        long start=System.currentTimeMillis();
        QReader1 in = new QReader1();
        QWriter1 out = new QWriter1();
        n=in.nextInt();
        points_x=new point[n];
        points_y=new point[n];
        points_consider=new point[n];
        for(int i=0;i<n;i++){
            long x=in.nextLong();
            long y=in.nextLong();
            point p=new point(x,y);
            points_x[i]=p;
            points_y[i]=p;
        }
        merge_x(points_x,0,n-1);
        merge_y(points_y,0,n-1);
//        long end=System.currentTimeMillis();
        long ans=closest_pair(0,n-1);
//        System.out.printf("time:%d ms\n",end-start);
        out.println(ans);
        out.close();
    }
    public static long closest_pair(int left,int right){
        if(right-left==0){
            return Long.MAX_VALUE;
        }else if(right-left==1){
            return euclidean_dist(points_x[left],points_x[right]);
        }else if(right-left==2) {
            long d1=euclidean_dist(points_x[left],points_x[left+1]);
            long d2=euclidean_dist(points_x[left+1],points_x[right]);
            long d3=euclidean_dist(points_x[right],points_x[left]);
            long d_min=d1<d2?d1:d2;
            d_min=d_min>d3? d3:d_min;
            return d_min;
        }else{
                int half=left+(right-left)/2;
                long d1=closest_pair(left,half);
                long d2=closest_pair(half+1,right);
                long d_min=Math.min(d1,d2);
                int cnt=0;
                for(int i=0;i<n;i++) {
                    if (Math.pow(points_y[i].x-points_x[half].x,2)<=d_min) {
                        points_consider[cnt] = points_y[i];
                        cnt++;
                    }
                }
                for(int i=0;i<cnt;i++){
                    int bound=(cnt-i<7)? cnt-i:7;
                    for(int j=i+1;j<i+bound;j++){
                        if(Math.pow(points_consider[j].y-points_consider[i].y,2)>=d_min){
                            break;
                        }else{
                            long d=euclidean_dist(points_consider[i],points_consider[j]);
                            if(d<d_min) d_min=d;
                        }
                    }
                }
                return d_min;
            }
        }
    public static long euclidean_dist(point p1, point p2){
        return (p1.x - p2.x) * (p1.x - p2.x)+(p1.y - p2.y) * (p1.y - p2.y);
    }
    public static void merge_x(point[] a,int l,int r){
        if(l>=r){
            return;
        }else {
            int m=l+(r-l)/2;
            merge_x(a, l, m);
            merge_x(a,m+1,r);
            mergesort_x(a,l,r,m);
        }
    }
    public static void merge_y(point[] a,int l,int r){
        if(l>=r){
            return;
        }else {
            int m=l+(r-l)/2;
            merge_y(a, l, m);
            merge_y(a,m+1,r);
            mergesort_y(a,l,r,m);
        }
    }
    public static void mergesort_x(point[] a,int l,int r,int m){
        point[] b=new point[r-l+1];
        int i=l,j=m+1,cnt=0;
        while(i<=m & j<=r){
            if(a[i].x <a[j].x){
                b[cnt]=a[i];
                cnt++;
                i++;
            }else {
                b[cnt]=a[j];
                cnt++;
                j++;
            }
        }
        while(i<=m){
            b[cnt]=a[i];
            cnt++;
            i++;
        }
        while (j<=r){
            b[cnt]=a[j];
            cnt++;
            j++;
        }
        for(i=l;i<=r;i++){
            a[i]=b[i-l];
        }
    }
    public static void mergesort_y(point[] a,int l,int r,int m){
        point[] b=new point[r-l+1];
        int i=l,j=m+1,cnt=0;
        while(i<=m & j<=r){
            if(a[i].y <a[j].y){
                b[cnt]=a[i];
                cnt++;
                i++;
            }else {
                b[cnt]=a[j];
                cnt++;
                j++;
            }
        }
        while(i<=m){
            b[cnt]=a[i];
            cnt++;
            i++;
        }
        while (j<=r){
            b[cnt]=a[j];
            cnt++;
            j++;
        }
        for(i=l;i<=r;i++){
            a[i]=b[i-l];
        }
    }
}
class point{
    long x;
    long y;
    public point(long x, long y) {
        this.x = x;
        this.y = y;
    }
}

class QReader1 {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private StringTokenizer tokenizer = new StringTokenizer("");

    private String innerNextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean hasNext() {
        while (!tokenizer.hasMoreTokens()) {
            String nextLine = innerNextLine();
            if (nextLine == null) {
                return false;
            }
            tokenizer = new StringTokenizer(nextLine);
        }
        return true;
    }

    public String nextLine() {
        tokenizer = new StringTokenizer("");
        return innerNextLine();
    }

    public String next() {
        hasNext();
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }
}

class QWriter1 implements Closeable {
    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(Object object) {
        try {
            writer.write(object.toString());
        } catch (IOException e) {
            return;
        }
    }

    public void println(Object object) {
        try {
            writer.write(object.toString());
            writer.write("\n");
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            return;
        }
    }
}
