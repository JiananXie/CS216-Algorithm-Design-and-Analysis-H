#include<iostream>
#include<cstring>
#include<queue>
#include<list>
using namespace std;
int n,m;
int level[1000005];
int to[1000005],nxt[1000005],head[1000005],now[1000005];
long long w[1000005];
int a[1000005];
int cnt=0,cnt_v1=0,cnt_v2=0;
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

int main(){
    cin>>m>>n;
    for(int i=1;i<=m*n;i++){
        cin>>a[i];
    }
    int s=0;
    int t=m*n+1;
    long long sum=0;
    memset(head,-1,sizeof(head));
    for(int i=1;i<=m*n;i++){
        int x=(i-1)/n+1;
        int y=(i-1)%n+1;
        if((x+y)%2==0){
            add_edge(s,i,a[i]);
            add_edge(i,s,0);
            if(x>1){
                add_edge(i,i-n,INT32_MAX);
                add_edge(i-n,i,0);
            }
            if(x<m){
                add_edge(i,i+n,INT32_MAX);
                add_edge(i+n,i,0);
            }
            if(y>1){
                add_edge(i,i-1,INT32_MAX);
                add_edge(i-1,i,0);
            }
            if(y<n){
                add_edge(i,i+1,INT32_MAX);
                add_edge(i+1,i,0);
            }
        }else{
            add_edge(i,t,a[i]);
            add_edge(t,i,0);
        }
        sum+=a[i];
    }
    cout<<sum-Dinitz(s,t)<<endl;
}
