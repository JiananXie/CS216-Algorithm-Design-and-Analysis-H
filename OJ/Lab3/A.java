import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int n=in.nextInt();
        int ans=0;
        game[] games=new game[n+1];
        int ddl_max=0;
        for(int i=1;i<=n;i++){
            int ddl=in.nextInt();
            if(ddl>ddl_max) ddl_max=ddl;
            games[i]=new game(i,ddl);
        }
        boolean[] occupation=new boolean[ddl_max+1];
        for(int i=1;i<=n;i++){
            games[i].fine=in.nextInt();
        }
        merge(games,1,n);
        for(int i=1;i<=n;i++){
            int j;
            for(j=games[i].ddl;j>=1;j--){
                if(!occupation[j]){
                    occupation[j]=true;
                    break;
                }
            }
            if(j==0) ans+=games[i].fine;
        }
        System.out.println(ans);
    }
    public static void merge(game[] a,int l,int r){
        if(l>=r){
            return;
        }else {
            int m=l+(r-l)/2;
            merge(a, l, m);
            merge(a,m+1,r);
            mergesort(a,l,r,m);
        }
    }
    public static void mergesort(game[] a,int l,int r,int m){
        game[] b=new game[r-l+1];
        int i=l,j=m+1,cnt=0;
        while(i<=m & j<=r){
            if(a[i].fine>a[j].fine){
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
class game{
    int k;
    int ddl;
    int fine;
    public game(int k, int ddl) {
        this.k = k;
        this.ddl = ddl;
    }
}
