#include<iostream>
#include<vector>
#include<cstring>
using namespace std;
int n, m;
struct edge{
    int end;
    int v;
    int p;
    double w;
};
double dis[7010];
vector<edge> G[7010];
bool vis[7010];
bool spfa(int r){
    vis[r] = true;
    for(int i=0;i<G[r].size();i++){
        int v = G[r][i].end;
        double w = G[r][i].w;
        if(dis[v] > dis[r] + w){
            dis[v] = dis[r] + w;
            if(vis[v]){
                return true;
            }else{
                if(spfa(v)) return true;
            }
        }
    }
    vis[r] = false;
    return false;
}
int main(){
    cin>>n>>m;
    int root = 0;
    int cnt[n+1];
    int begin[m];
    memset(cnt,0,sizeof(cnt));
    for(int i=0;i<m;i++){
        int from,to,v;
        cin>>from>>to>>v;
        edge e;
        e.end = to;
        e.v = v;
        G[from].push_back(e);
        begin[i] = from;
    }
    for(int i=0;i<m;i++){
        int p;
        cin>>p;
        G[begin[i]][cnt[begin[i]]].p = p;
        cnt[begin[i]]++;
    }
    for(int i=1;i<n;i++){
        edge e;
        e.end = i;
        e.v = 0;
        e.p = 0;
        G[root].push_back(e);
    }
    double l = 0.00, r = 200;
    while(l+0.01<r){
        double mid = (r+l)/2;
        memset(dis,127,sizeof(dis));
        memset(vis,false,sizeof(vis));
        dis[0] = 0;
        for(int i=1;i<=n;i++){
            for(int j=0;j<G[i].size();j++){
                G[i][j].w = mid*G[i][j].p - G[i][j].v;
            }
        }
        if(spfa(root)){
            l = mid;
        }else{
            r = mid;
        }
    }
    if(l>0){
        printf("%.1lf",l);
    }else{
        cout<<-1;
    }
}
