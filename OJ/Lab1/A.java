import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n=in.nextInt();
        man[] men=new man[n+1];
        woman[] women=new woman[n+1];
        Map<String,Integer> map_m=new HashMap<>();
        Map<String,Integer> map_w=new HashMap<>();
        Queue<man> queue=new LinkedList<>();
        int[] engage=new int[n+1];
        for(int i=1;i<=n;i++){
            String name=in.next();
            men[i]=new man(i,name,n);
            map_m.put(name,i);
            queue.add(men[i]);
        }
        for(int i=1;i<=n;i++){
            String name=in.next();
            women[i]=new woman(i,name,n);
            map_w.put(name,i);
        }
        for(int i=1;i<=n;i++) {
            for (int j = 1; j <= n; j++) {
                men[i].preference[j] = map_w.get(in.next());
            }
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                women[i].preference[j]= map_m.get(in.next());
                women[i].preference_inverse[women[i].preference[j]]=j;
            }
        }
        while(!queue.isEmpty()){
            man m=queue.poll();
            m.count++;
            if(m.count==n+1) break;
            else{
                woman w=women[m.preference[m.count]];
                if(w.husband==0){
                    engage[m.k]=w.k;
                    w.husband=m.k;
                }else{
                    if(w.preference_inverse[m.k]<w.preference_inverse[w.husband]){
                        engage[m.k]=w.k;
                        queue.add(men[w.husband]);
                        w.husband=m.k;
                    }else{
                        queue.add(m);
                    }
                }
            }
        }
        for(int i=1;i<=n;i++){
            out.print(men[i].name+" "+women[engage[i]].name);
            out.println("");
        }
        out.close();
    }
}
class man{
    int k;
    String name;
    int[] preference;
    int count=0;
    int wife=0;
    public man(int k, String name, int n) {
        this.k = k;
        this.name = name;
        this.preference = new int[n+1];
    }
}
class woman{
    int k;
    String name;
    int[] preference;
    int[] preference_inverse;
    int husband=0;
    public woman(int k, String name, int n) {
        this.k = k;
        this.name = name;
        this.preference = new int[n+1];
        this.preference_inverse = new int[n+1];
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
