#include<iostream>
#include<string>
using namespace std;
int main(){
    int n;
    string s;
    cin>>n;
    cin>>s;
    int dp[n][n]={1};
    for(int i=0;i<n;i++){
            dp[i][i]=1;
    }
    for(int len=2;len<=n;len++){
        for(int i=0;i+len-1<n;i++){
            int j=i+len-1;
            if(s[i]==s[j]){
                dp[i][j]=dp[i+1][j-1]+1;
            }else{
                dp[i][j]=min(dp[i+1][j],dp[i][j-1])+1;
            }
            for(int k=i;k<=j;k++){
                dp[i][j]=min(dp[i][j],dp[i][k]+dp[k][j]-1);
            }
    }
    }
    cout<<dp[0][n-1]<<endl;
}
