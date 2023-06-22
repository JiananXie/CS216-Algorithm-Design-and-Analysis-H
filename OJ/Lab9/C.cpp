#include<iostream>
#include<algorithm>
#include<string.h>
using namespace std;
int prime[8]={2,3,5,7,11,13,17,19};
long long dp[260][260],dp1[260][260],dp2[260][260];
struct note{
    int value;
    int bigPrime=0;
    int binary=0;
}notes[510];
bool cmp(note n1, note n2){
    return n1.bigPrime < n2.bigPrime;
}
int main(){
    int n,p;
    cin>>n>>p;
    for(int i=2;i<=n;i++){
        notes[i].value = i;
        for(int j=0;j<8;j++){
            if(notes[i].value%prime[j]==0){
                notes[i].binary |= 1<<j;
            }
            while(notes[i].value%prime[j]==0){
                notes[i].value /= prime[j];
            }
        }
        if(notes[i].value!=1){
            notes[i].bigPrime = notes[i].value;
        }
    } 
    dp[0][0] = 1;  
    sort(notes+2,notes+n+1,cmp);
    for(int i=2;i<=n;i++){
        if(i==2 || notes[i].bigPrime!=notes[i-1].bigPrime ||notes[i].bigPrime==0){
            memcpy(dp1,dp,sizeof(dp1));
            memcpy(dp2,dp,sizeof(dp2));
        }
        for(int a=255;a>=0;a--){
            for(int b=255;b>=0;b--){
                if((a&b)==0 && (a&notes[i].binary)==0){
                    dp2[a][b|notes[i].binary]+=dp2[a][b];
                    dp2[a][b|notes[i].binary]%=p;
                }
                if((a&b)==0 && (b&notes[i].binary)==0){
                    dp1[a|notes[i].binary][b]+=dp1[a][b];
                    dp1[a|notes[i].binary][b]%=p;
                }
            }
        }
        if(i==n || notes[i].bigPrime!=notes[i+1].bigPrime || notes[i].bigPrime==0){
            for(int a=255;a>=0;a--){
                for(int b=255;b>=0;b--){
                   if((a&b)==0) dp[a][b] = ((dp1[a][b] + dp2[a][b])%p - dp[a][b]+p)%p;
                }
            }
        }
    }
    long long ans=0;
    for(int a=255;a>=0;a--){
        for(int b=255;b>=0;b--){
            if((a&b)==0 && dp[a][b]!=0) ans=(ans+dp[a][b])%p;
        }
    }
    cout<<ans<<endl;
}
