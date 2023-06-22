import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        int t=input.nextInt();
        int[] test=new int[t+1];
        for(int i=1;i<=t;i++){
            test[i]=input.nextInt();
        }
        for(int i=1;i<=t;i++){
            if(test[i]%6==0){
                System.out.println("Bob");
            }else{
                System.out.println("Alice");
            }
        }
    }
}
