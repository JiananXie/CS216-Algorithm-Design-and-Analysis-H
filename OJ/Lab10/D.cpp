#include<iostream>
#include<cstring>
#include<queue>
using namespace std;
int dp[500],a[500],x[500];
int n,k;
int level[1000050];
int to[1000050],nxt[1000050],head[1000050],now[1000050];
long long w[1000050];
int cnt=0;
int inf=INT32_MAX;
void add_edge(int u, int v, int value)
{
    to[cnt] = v;
    w[cnt] = value;
    nxt[cnt] = head[u];
    head[u] = cnt++;
}
bool BFS(int s, int t){
    memset(level,-1,sizeof(level));
    queue<int> q;
    q.push(s);
    level[s]=0;
    now[s]=head[s];
    int u;
    while(q.size()!=0){
        u=q.front();
        q.pop();
        for(int v=head[u];v!=-1;v=nxt[v]){
            int p=to[v];
            if(w[v]!=0 && level[p]==-1){
                q.push(p);
                now[p]=head[p];
                level[p]=level[u]+1;
            }
        }
    }
    return (level[t]!=-1);
}

long long DFS(int x,int t,long long flow){
    if(x==t){
        return flow;
    }
    for(int i=now[x];i!=-1;i=nxt[i]){
        now[x]=i;
        int u=to[i];
        if(level[u]!=level[x]+1 || w[i]==0) continue;
        long long bottle=DFS(u,t,min(flow,w[i]));
        if(bottle!=0){
            w[i]-=bottle;
            w[i^1]+=bottle;
            return bottle;
        }
    }
    return 0;
}

long long Dinitz(int s,int t){
    long long ans=0;
    while(BFS(s,t)){
        while(long long p=DFS(s,t,INT32_MAX)){
            ans+=p;
        }
    }
    return ans;
}

int maxlen(){
    int len=0;
    for(int i=1;i<=n;i++){
        dp[i]=1;
    }
    for(int i=1;i<=n;i++){
        for(int j=1;j<i;j++){
            if(a[j]<=a[i]) dp[i]=max(dp[i],dp[j]+1);
        }
    }
    for(int i=1;i<=n;i++){
        len=max(len,dp[i]);
    }
    return len;
}

int main(){
    cin>>n;
    int s=0,t=449;
    for(int i=1;i<=n;i++){
        cin>>a[i];
    }
    for(int i=1;i<=n;i++){
        cin>>x[i];
    }
    k=maxlen();
    memset(head,-1,sizeof(head));
    for(int i=1;i<=n;i++){
        if(dp[i]==1){
            add_edge(s,i,inf);
            add_edge(i,s,0);
        }
        if(dp[i]==k){
            add_edge(i+n,t,inf);
            add_edge(t,i+n,0);
        }
    }
    for(int i=1;i<=n;i++){
        add_edge(i,i+n,x[i]);
        add_edge(i+n,i,0);
    }
    for(int i=1;i<=n;i++){
        for(int j=1;j<i;j++){
            if(dp[i]==dp[j]+1 && a[i]>=a[j]){
                add_edge(j+n,i,inf);
                add_edge(i,j+n,0);
            }
        }
    }

    cout<<k<<endl<<Dinitz(s,t)<<endl;
}
