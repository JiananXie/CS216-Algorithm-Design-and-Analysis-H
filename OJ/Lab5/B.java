import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) {
        QReader1 in = new QReader1();
        QWriter1 out = new QWriter1();
        int n=in.nextInt();
        int m=in.nextInt();
        ArrayList<edge> edges=new ArrayList<>();
        int[] pre=new int[n+1];
        int[] in_min=new int[n+1];
        int cnt=n+1;
        int  sum=0;
        int v = 0;
        long I=0;
        for(int i=0;i<m;i++){
            int s=in.nextInt();
            int t=in.nextInt();
            int c=in.nextInt();
            sum+=c;
            edge e=new edge(s,t,c);
            edges.add(e);
        }
        for(int i=0;i<n;i++){
            edge e=new edge(n,i,sum+1);
            edges.add(e);
        }
        int[] isVisited=new int[n+1];
        int[] id=new int[n+1];
        int root=n;
        while(true){
            int count=0;
            for(int i=0;i<cnt;i++){
                in_min[i]=Integer.MAX_VALUE;
            }
            for(int i=0;i<edges.size();i++){
                if(edges.get(i).cost<in_min[edges.get(i).end] && edges.get(i).start!=edges.get(i).end){
                    in_min[edges.get(i).end]=edges.get(i).cost;
                    pre[edges.get(i).end]=edges.get(i).start;
                    if(edges.get(i).start==root){
                        v=i;
                    }
                }
            }
            in_min[root]=0;
            for(int i=0;i<cnt;i++){
                isVisited[i]=-1;
                id[i]=-1;
            }
            for(int i=0;i<cnt;i++){
                I+=in_min[i];
                int cur=i;
                while(isVisited[cur]!=i && id[cur]==-1 && cur!=root){
                    isVisited[cur]=i;
                    cur=pre[cur];
                }
                if(id[cur]==-1 && cur!=root){
                    int item=pre[cur];
                    id[cur]=count;
                    while(item!=cur){
                        id[item]=count;
                        item=pre[item];
                    }
                    count++;
                }
            }
            if(count==0){
                break;
            }else{
                for(int i=0;i<cnt;i++){
                    if(id[i]==-1){
                        id[i]=count;
                        count++;
                    }
                }
                for(int i=0;i<edges.size();i++){
                    int start=edges.get(i).start;
                    int end=edges.get(i).end;
                    edges.get(i).start=id[start];
                    edges.get(i).end=id[end];
                    if(id[start]!=id[end]){
                        edges.get(i).cost-=in_min[end];
                    }
                }
                cnt=count;
                root=id[root];
            }
        }
        if(I>=2*sum+2){
            out.println("impossible");
        }else{
            out.println(I-sum-1+" "+(v-m));
        }
        out.close();
    }
}
class edge{
    int start;
    int end;
    int cost;
    public edge(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
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
