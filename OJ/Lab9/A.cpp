#include<iostream>
#include<vector>
using namespace std;
long long add[100010];
long long sub[100010];
long long v[100010];
vector<int> G[100010];
void DFS(int k,int parent){
    if(G[k].size()==1 && k!=1){
        if(v[k]>=0){
            sub[k]=v[k];
        }else{
            add[k]=-v[k];
        }
        return;
    }
    long long sub_max=0;
    long long add_max=0;
    for(int u:G[k]){
        if(u!=parent){
            DFS(u,k);
            if(add[u]>add_max){
                add_max=add[u];
            }
            if(sub[u]>sub_max){
                sub_max=sub[u];
            }
        }
    }
    v[k]=v[k]+add_max-sub_max;
    if(v[k]<0){
        add[k]=add_max-v[k];
        sub[k]=sub_max;
    }else{
        sub[k]=sub_max+v[k];
        add[k]=add_max;
    }
    return;
}
int main(){
    int n;
    cin>>n;
    for(int i=1;i<n;i++){
        int a,b;
        cin>>a>>b;
        G[a].push_back(b);
        G[b].push_back(a);
    }
    for(int i=1;i<=n;i++){
        cin>>v[i];
    }
    DFS(1,0);
    cout<<add[1]+sub[1]<<endl;
}
