#include<iostream>
#include<math.h>
#include<iomanip>
using namespace std;
const double pi = acos(-1.0);
const int N = 400100;
int order[N];
struct complex{
    double re;
    double im;
    complex(double re=0,double im=0){
        this->re = re;
        this->im = im;
    }
    complex operator+ (complex c){
        double re = this->re + c.re;
        double im = this->im + c.im;
        return complex(re, im);
    }
    complex operator* (complex c){
        double re = this->re*c.re - this->im*c.im;
        double im = this->re*c.im + this->im*c.re;
        return complex(re, im);
    }
    complex operator- (complex c){
        double re = this->re - c.re;
        double im = this->im - c.im;
        return complex(re, im);
    }
}co1[N],co2[N],co3[N];

void FFT(complex* co,int size, int inverse){
    for(int i=0;i<size;i++){
        if(i<order[i]){
            swap(co[i],co[order[i]]);
        }
    }
    for(int mid=1;mid<size;mid*=2){
        complex v(cos(pi/mid),inverse*sin(pi/mid));
        for(int step=2*mid,k=0;k<size;k+=step){
            complex w(1,0);
            for(int j=0;j<mid;j++){
                complex x = co[k+j];
                complex y = co[k+j+mid]*w;
                co[k+j] = x + y;
                co[k+j+mid] = x - y;
                w = w*v;
            }
        }
    }
}

int main(){
   int n;
   int bit=0;
   int size=1;
   cin>>n;
   while(size<=2*n){
    size=size<<1;
    bit++;
   }
   for(int i=1;i<=n;i++){
    double re;
    cin>>re;
    co1[i].re= re;
    co3[n-i].re= re;
    co2[i].re= 1.0/i/i;
   }
   for(int i=0;i<size;i++){
    order[i] = i;
   }
   for(int i=0;i<size;i++){
    order[i] = (order[i>>1]>>1)|((i&1)<<(bit-1));
   }
   FFT(co1,size,1),FFT(co2,size,1),FFT(co3,size,1);
   for(int i=0;i<size;i++){
    co1[i] = co1[i]*co2[i];
    co3[i] = co3[i]*co2[i];
   }
   FFT(co1,size,-1),FFT(co3,size,-1);
   for(int i=1;i<=n;i++){
    cout<<fixed<<setprecision(3)<<(co1[i].re-co3[n-i].re)/size<<endl;
   }
}
