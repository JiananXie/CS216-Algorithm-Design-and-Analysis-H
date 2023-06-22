import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        int n=input.nextInt();
        int k=input.nextInt();
        int mid;
        if(n==1){
            System.out.println(1);
        }else{
            if(n%2==1){
                mid=(n+1)/2;
            }else{
                mid=n/2;
            }
            if(k<=mid)System.out.println(2*k);
            else System.out.println(2*(n+1-k));
        }
    }
}
