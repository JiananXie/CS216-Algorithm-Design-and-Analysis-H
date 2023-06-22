#include<iostream>
#include<cstring>
#include<queue>
using namespace std;
int level[500],girls[500];
int to[1000050],nxt[1000050],head[1000050],now[1000050];
long long w[1000050];
int cnt=0;
int n,m,k;
int A_or_B[500];
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
void cut_edge(int x){
    A_or_B[x]=1;
    for(int i=head[x];i!=-1;i=nxt[i]){
        int u=to[i];
        if(!A_or_B[u] && w[i]>0){
            cut_edge(u);
        }
    }
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
    cin>>n>>m>>k;
    int s=0,t=499;
    memset(girls,0,sizeof(girls));
    memset(head,-1,sizeof(head));
    memset(A_or_B,0,sizeof(A_or_B));
    for(int i=0;i<k;i++){
        cin>>girls[i];
    }
    for(int i=0;i<m;i++){
        int u,v;
        cin>>u>>v;
        if(u==0){
            add_edge(u,v,1);
            add_edge(v,u,0);
        }else if(v==0){
            add_edge(v,u,1);
            add_edge(u,v,0);
        }else{
            add_edge(u,v,1);
            add_edge(v,u,1);
        }
    }
    for(int i=0;i<k;i++){
        add_edge(girls[i],t,1);
        add_edge(t,girls[i],0);
    }
    cout<<Dinitz(s,t)<<endl;
    // cut_edge(s);
    // for(int i=0;i<n;i++){
    //     for(int j=0;j<n;j++){
    //         if(A_or_B[i] && !A_or_B[j]){
    //             cout<<"cut_edge from "<<i<<" to "<<j<<endl;
    //         }
    //     }
    // }
}
