#include<iostream>
#include<cmath>
using namespace std;
int main(){
    int n,c;
    int dp[48000];
    cin>>n>>c;
    int s[n],v[n],w[n];
    int maxi=0;
    for(int i=0;i<n;i++){
        cin>>s[i];
        cin>>v[i];
        cin>>w[i];
        if(s[i]>maxi)maxi=s[i];
    }
    int cnt=1;
    int size=n*log2(maxi)+n;
    int V[size],W[size];
    for(int i=0;i<n;i++){
        int k=1;
        while(k<=s[i]){
            V[cnt]=k*v[i];
            W[cnt]=k*w[i];
            cnt++;
            s[i]-=k;
            k<<=1;
        }
        if(s[i]>0){
            V[cnt]=s[i]*v[i];
            W[cnt]=s[i]*w[i];
            cnt++;
        }
    }
    for(int i=1;i<cnt;i++){
        for(int j=c;j>=V[i];j--){
            dp[j]=max(dp[j],dp[j-V[i]]+W[i]);
        }
    }
    cout<<dp[c]<<endl;
}
