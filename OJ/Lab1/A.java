import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static int capacity,t;
    static node first=new node();
    static node last=new node();
    static HashMap<Integer,node> map=new HashMap<>(capacity);
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        capacity = in.nextInt();
        int m = in.nextInt();
        t = in.nextInt();
        first.next=last;
        last.prev=first;
        for(int i=0;i<m;i++){
            int op=in.nextInt();
            check(i);
            if(op==1){
                int k=in.nextInt();
                int value=get(k,i);
                out.println(value);
            }else{
                int k=in.nextInt();
                int value=in.nextInt();
                put(k,value,i);
            }
        }
        if(first.next!=last){
            node cur=last.prev;
            while(cur!=first){
                out.print(cur.value+" ");
                cur=cur.prev;
            }
        }
        out.close();
    }
    public static int get(int key, int time){
        node n=map.get(key);
        if(n!=null){
            n.time=time;
            n.prev.next=n.next;
            n.next.prev=n.prev;
            n.next=first.next;
            first.next.prev=n;
            first.next=n;
            n.prev=first;
            return n.value;
        }else{
            return -1;
        }
    }

    public static void put(int key,int value,int time){
        node n=map.get(key);
        if(n!=null){
            n.time=time;
            n.value=value;
            n.prev.next=n.next;
            n.next.prev=n.prev;
            n.next=first.next;
            first.next.prev=n;
            first.next=n;
            n.prev=first;
        }else{
            if(map.size()==capacity){
                map.remove(last.prev.key);
                last.prev.prev.next=last.prev.next;
                last.prev.next.prev=last.prev.prev;
            }
            node n_node=new node(key,value);
            n_node.time=time;
            map.put(key,n_node);
            n_node.next=first.next;
            first.next.prev=n_node;
            first.next=n_node;
            n_node.prev=first;
        }
    }
    public static void check(int time){
        node cur=last.prev;
        while(cur!=first){
            if(time-cur.time>t){
                map.remove(cur.key);
                cur.prev.next=cur.next;
                cur.next.prev=cur.prev;
            }else{
                break;
            }
            cur=cur.prev;
        }
    }
}
class node{
    int key;
    int value;
    int time=0;
    node prev;
    node next;
    public node() {
    }
    public node(int key, int value) {
        this.key = key;
        this.value = value;
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
