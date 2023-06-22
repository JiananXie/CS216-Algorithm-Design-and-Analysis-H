#include<iostream>
#include<cstring>
#include<queue>
using namespace std;
int level[220];
int to[40000],nxt[40000],head[40000],now[40000];
long long w[40000];
int cnt=0;
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
    int n,m,s,t;
    cin>>n>>m>>s>>t;
    memset(head,-1,sizeof(head));
    for(int i=0;i<m;i++){
        int u,v,w;
        cin>>u>>v>>w;
        add_edge(u,v,w);
        add_edge(v,u,0);
    }
    cout<<Dinitz(s,t)<<endl;;
}
