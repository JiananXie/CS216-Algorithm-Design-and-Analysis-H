import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
public class Main{
    public static void main(String[] args) throws IOException{
        QReader1 in = new QReader1();
        QWriter1 out = new QWriter1();
        int n = in.nextInt();
        int r = in.nextInt();
        Node[] nodes = new Node[n + 1];
        PriorityQueue<Node> heap=new PriorityQueue<>();
        long ans=0;
        for (int i = 1; i <= n; i++) {
            int cost = in.nextInt();
            ans+=cost;
            nodes[i] = new Node(cost);
            heap.add(nodes[i]);
        }
        for (int i = 1; i <= n - 1; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            nodes[v].father=nodes[u];
        }
        while(!heap.isEmpty()){
            Node top=heap.poll();
            if(!top.isVisited){
                if(top.father!=null){
                    top.isVisited=true;
                    while(top.father.isVisited){
                        top.father=top.father.father;
                    }
                    heap.remove(top.father);
                    int total_node=top.k+top.father.k;
                    double total_cost=top.total_cost+top.father.total_cost;
                    double ave_cost=total_cost/total_node;
                    ans+=top.father.k*top.total_cost;
                    top.father.ave_cost=ave_cost;
                    top.father.total_cost=total_cost;
                    top.father.k=total_node;
                    heap.add(top.father);
                }
            }
        }
        out.println(ans);
        out.close();
    }
}
class Node implements Comparable<Node>{
    int k=1;
    double ave_cost;
    double total_cost;
    boolean isVisited=false;
    Node father=null;
    public Node(double cost) {
        this.ave_cost=cost;
        this.total_cost=cost;
    }
    @Override
    public int compareTo(Node o) {
        if(this.ave_cost>=o.ave_cost){
            return -1;
        }else{
            return 1;
        }
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
