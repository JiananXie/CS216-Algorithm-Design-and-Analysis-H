import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        QReader1 in = new QReader1();
        QWriter1 out = new QWriter1();
        int n=in.nextInt();
        int m=in.nextInt();
        student[] students=new student[n+1];
        college[] colleges=new college[m+1];
        Queue<student> queue=new LinkedList<>();
        for(int i=1;i<=n;i++){
            students[i]=new student(i,m);
            queue.add(students[i]);
        }
        for(int i=1;i<=m;i++){
            colleges[i]=new college(i,n);
            colleges[i].cap=in.nextInt();
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                students[i].eva[j]=in.nextInt();
            }
        }
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                colleges[i].eva[j]=in.nextInt();
            }
        }
        while(!queue.isEmpty()){
            student s=queue.poll();
            int max_id=1;
            int max=s.eva[1];
            for(int i=1;i<=m;i++){
                if(s.eva[i]>0 && s.eva[i]>max){
                    max=s.eva[i];
                    max_id=i;
                }
            }
            if(max<0) continue;
            else{
                s.eva[max_id]=-1;
                college c=colleges[max_id];
                if(c.eva[s.k]<0){
                    queue.add(s);
                }else{
                    if(c.count<c.cap){
                        c.count++;
                        c.accept[c.count]=s.k;
                    }else{
                        int min_id=1;
                        int min=c.eva[c.accept[1]];
                        for(int i=1;i<=c.count;i++){
                            if(c.eva[c.accept[i]]<min){
                                min_id=i;
                                min=c.eva[c.accept[i]];
                            }
                        }
                        if(c.eva[s.k]>min){
                            queue.add(students[c.accept[min_id]]);
                            c.accept[min_id]=s.k;
                        }else{
                            queue.add(s);
                        }
                    }
                }
            }
        }
        for(int i=1;i<=m;i++){
                out.print(colleges[i].count);
                if(colleges[i].count!=0){
                    for(int j=1;j<=colleges[i].count;j++){
                        out.print(" "+colleges[i].accept[j]);}
                     }
                out.println("");
        }
        out.close();
    }
}
class student{
    int k;
    int count=0;
    int[] eva;
    public student(int k, int m) {
        this.k = k;
        this.eva = new int[m+1];
    }
}
class college{
    int k;
    int cap;
    int count=0;
    int[] eva;
    int[] accept;
    public college(int k, int n) {
        this.k = k;
        this.eva = new int[n+1];
        this.accept = new int[n+1];
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
