import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main{
    static int size=0;
    public static void main(String[] args) {
        QReader in = new QReader();
        int n=in.nextInt();
        int k=in.nextInt();
        node[] nodes=new node[n+1];
        edge[] edges=new edge[n*(n-1)/2+1];
        edge[] heap=new edge[n*(n-1)/2+1];
        for(int i=1;i<=n;i++){
             int x=in.nextInt();
             int y=in.nextInt();
             nodes[i]=new node(x,y);
        }
        int cnt=1;
        edge min=new edge(0,0,Double.MAX_VALUE);
        for(int i=1;i<=n;i++){
            for(int j=i+1;j<=n;j++){
               double weight=Math.sqrt(Math.pow((nodes[i].x-nodes[j].x),2)+Math.pow((nodes[i].y-nodes[j].y),2));
               edges[cnt]=new edge(i,j,weight);
               if(weight<min.weight) min=edges[cnt];
               nodes[i].edgeArray.add(edges[cnt]);
               nodes[j].edgeArray.add(edges[cnt]);
                cnt++;
            }
        }
        edge[] ans=new edge[n*(n-1)/2+1];
        cnt=1;
        insertion(heap,min);
        while(size!=0) {
            edge top = heap[1];
            deletion(heap);
            if (!nodes[top.v1].isVisited && !nodes[top.v2].isVisited) {
                ans[cnt]=top;
                cnt++;
                nodes[top.v1].isVisited = true;
                nodes[top.v2].isVisited = true;
                for (int i = 0; i < nodes[top.v1].edgeArray.size(); i++) {
                    if (!nodes[nodes[top.v1].edgeArray.get(i).v1].isVisited || !nodes[nodes[top.v1].edgeArray.get(i).v2].isVisited)
                        insertion(heap, nodes[top.v1].edgeArray.get(i));
                }
                for (int i = 0; i < nodes[top.v2].edgeArray.size(); i++) {
                    if (!nodes[nodes[top.v2].edgeArray.get(i).v1].isVisited || !nodes[nodes[top.v2].edgeArray.get(i).v2].isVisited)
                        insertion(heap, nodes[top.v2].edgeArray.get(i));
                }
            } else if (!nodes[top.v1].isVisited) {
                ans[cnt]=top;
                cnt++;
                nodes[top.v1].isVisited = true;
                for (int i = 0; i < nodes[top.v1].edgeArray.size(); i++) {
                    if (!nodes[nodes[top.v1].edgeArray.get(i).v1].isVisited || !nodes[nodes[top.v1].edgeArray.get(i).v2].isVisited)
                        insertion(heap, nodes[top.v1].edgeArray.get(i));
                }
            } else if (!nodes[top.v2].isVisited) {
                ans[cnt]=top;
                cnt++;
                nodes[top.v2].isVisited = true;
                for (int i = 0; i < nodes[top.v2].edgeArray.size(); i++) {
                    if (!nodes[nodes[top.v2].edgeArray.get(i).v1].isVisited || !nodes[nodes[top.v2].edgeArray.get(i).v2].isVisited)
                        insertion(heap, nodes[top.v2].edgeArray.get(i));
                }
            }
        }
        merge(ans,1,cnt-1);
        System.out.printf("%.2f",ans[k-1].weight);
    }
    public static void merge(edge[] a,int l,int r){
        if(l>=r){
            return;
        }else {
            int m=l+(r-l)/2;
            merge(a, l, m);
            merge(a,m+1,r);
            mergesort(a,l,r,m);
        }
    }
    public static void mergesort(edge[] a,int l,int r,int m){
        edge[] b=new edge[r-l+1];
        int i=l,j=m+1,cnt=0;
        while(i<=m & j<=r){
            if(a[i].weight>a[j].weight){
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
    public static void insertion(edge[] heap, edge e ){
        size++;
        heap[size]=e;
        int now=size;
        int parent=size/2;
        while(parent>=1){
            if(heap[parent].weight>e.weight ){
                edge temp=heap[now];
                heap[now]=heap[parent];
                heap[parent]=temp;
            }else{
                break;
            }
            now=parent;
            parent/=2;
        }
    }
    public static void deletion(edge[] heap){
        heap[1]=heap[size];
        size--;
        int now=1;
        while(2*now<=size){
            if(2*now+1<=size){
                if(heap[2*now].weight<heap[2*now+1].weight && heap[now].weight>heap[2*now].weight){
                    edge temp=heap[now];
                    heap[now]=heap[2*now];
                    heap[2*now]=temp;
                    now=2*now;
                }else if(heap[2*now].weight>=heap[2*now+1].weight && heap[now].weight>heap[2*now+1].weight){
                    edge temp=heap[now];
                    heap[now]=heap[2*now+1];
                    heap[2*now+1]=temp;
                    now=2*now+1;
                }else{
                    break;
                }
            }else if(heap[now].weight>heap[2*now].weight){
                edge temp=heap[now];
                heap[now]=heap[2*now];
                heap[2*now]=temp;
                now=2*now;
            }else {
                break;
            }
        }
    }
}
class node{
    int x;
    int y;
    boolean isVisited=false;
    ArrayList<edge> edgeArray=new ArrayList<>();
    public node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
class edge{
    int v1;
    int  v2;
    double weight;
    public edge(int v1, int v2, double weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
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
