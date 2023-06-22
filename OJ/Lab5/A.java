import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static  char[][] dest={{'1','1','1','1','1'},{'0','1','1','1','1'},{'0','0','*','1','1'},{'0','0','0','0','1'},{'0','0','0','0','0'}};
    static int[] direct_x={-2,-2,-1,-1,1,1,2,2};
    static int[] direct_y={-1,1,2,-2,-2,2,-1,1};
    static int limit=15;
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int T=in.nextInt();
        for(int c=1;c<=T;c++) {
          int blank_x=-1,blank_y=-1;
          char[][] board=new char[5][5];
          for(int i=0;i<5;i++){
              String line=in.next();
              for(int j=0;j<5;j++){
                  board[i][j]=line.charAt(j);
                  if(board[i][j]=='*'){
                      blank_x=i;
                      blank_y=j;
                  }
              }
          }
          int step=0;
          while(step<=limit){
              if(IDAstar(0,board,blank_x,blank_y,step)){
                  out.println(step);
                  break;
              }
              step++;
          }
          if(step==limit+1){
              out.println("-1");
          }
        }
        out.close();
    }
    public static int h(char[][] board){
        int heu=0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(board[i][j] != '*' && board[i][j]!=dest[i][j]){
                    heu++;
                }
            }
        }
        return heu;
    }
    public static boolean IDAstar(int cur,char[][] board,int blank_x,int blank_y,int step){
        if(h(board)==0){
            return true;
        }else if(cur+h(board)<=step){
            for(int k=0;k<8;k++){
                int x=blank_x+direct_x[k];
                int y=blank_y+direct_y[k];
                if(x<0 || x>4 || y<0 || y>4){
                    continue;
                }
                char temp=board[blank_x][blank_y];
                board[blank_x][blank_y]=board[x][y];
                board[x][y]=temp;
               if(IDAstar(cur+1,board,x,y,step)) return true;
                temp=board[blank_x][blank_y];
                board[blank_x][blank_y]=board[x][y];
                board[x][y]=temp;
            }
        }
        return false;
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
