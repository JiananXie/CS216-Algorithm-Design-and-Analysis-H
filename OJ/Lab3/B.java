import java.io.*;
import java.util.StringTokenizer;
public class Main {
    public static void main(String[] args) {
        QReader in=new QReader();
        QWriter out=new QWriter();
        int n=in.nextInt();
        int[] a=new int[n+1];
        long sum=0;
        for(int i=1;i<=n;i++){
            a[i]=in.nextInt();
            sum+=a[i];
        }
        long ave=sum/n;
        long[] c=new long[n+1];
        c[1]=0;
        for(int i=2;i<=n;i++){
            c[i]=c[i-1]+ave-a[i];
        }
        merge(c,1,n);
        long ans=0;
        long x=c[(n+1)/2];
        for(int i=1;i<=n;i++){
            ans+=Math.abs(x-c[i]);
        }
        out.println(ans);
        out.close();
    }
    public static void merge(long[] a,int l,int r){
        if(l>=r){
            return;
        }else {
            int m=l+(r-l)/2;
            merge(a, l, m);
            merge(a,m+1,r);
            mergesort(a,l,r,m);
        }
    }
    public static void mergesort(long[] a,int l,int r,int m){
        long[] b=new long[r-l+1];
        int i=l,j=m+1,cnt=0;
        while(i<=m & j<=r){
            if(a[i]<a[j]){
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
class QReader {
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

class QWriter implements Closeable {
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
