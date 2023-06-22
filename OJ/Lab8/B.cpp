#include<iostream>
#include<cstring>
#include<cmath>
using namespace std;
const int p=12345678;
int main(){
    int n,m,k;
    cin>>n>>m>>k;
    int dp[n+1][m+1][21][21]    ;
    memset(dp,0,sizeof(dp));
    dp[0][0][0][0]=1;
    for(int i=0;i<=n;i++){
        for(int j=0;j<=m;j++){
            for(int k1=0;k1<=k;k1++){
                for(int k2=0;k2<=k;k2++){
                       if(i+1<=n && k1+1<=k){
                       dp[i+1][j][k1+1][max(k2-1,0)]=(dp[i+1][j][k1+1][max(0,k2-1)]+dp[i][j][k1][k2]%p)%p;
                       }
                       if(j+1<=m && k2+1<=k){
                       dp[i][j+1][max(0,k1-1)][k2+1]=(dp[i][j+1][max(0,k1-1)][k2+1]+dp[i][j][k1][k2]%p)%p;
                       }
                }
             }
        }
    }
    int ans=0;
    for(int k1=0;k1<=k;k1++){
        for(int k2=0;k2<=k;k2++){
            ans=ans%p;
            ans+=dp[n][m][k1][k2]%p;
        }
     }
    cout<<ans<<endl;
}
