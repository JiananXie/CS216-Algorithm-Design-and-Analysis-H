#include<iostream>
#include<math.h>
#include<iomanip>
using namespace std;
const double pi = acos(-1.0);
const int N = 530000;
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
}a[N],b[N],c[N];

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
    if(inverse==-1){
        for(int i=0;i<size;i++){
            co[i].re=co[i].re/size;
        }
    }
}

int convert(char c){
    if(c=='1'){
        return 2;
    }else if(c=='0'){
        return 1;
    }else{
        return 0;
    }
}

int main(){
   string s;
   int bit=0;
   int size=1;
   cin>>s;
   int n=s.length();
   int k=n/2;
   double A[k],B[k];
   while(size<n){
    size=size<<1;
    bit++;
   }
    for(int i=0;i<k;i++){
        A[i]=convert(s[i]);
        B[i]=convert(s[n-i-1]);
    }
   for(int i=0;i<size;i++){
    order[i] = i;
   }
   for(int i=0;i<size;i++){
    order[i] = (order[i>>1]>>1)|((i&1)<<(bit-1));
   }
   for(int i=0;i<k;i++){
       a[i].re=A[i]*A[i]*A[i];
       b[i].re=B[i];
   }
   FFT(a,size,1),FFT(b,size,1);
   for(int i=0;i<size;i++){
        c[i]=c[i]+a[i]*b[i];
        a[i].re=a[i].im=0;
        b[i].re=b[i].im=0;
   }
   for(int i=0;i<k;i++){
        a[i].re=A[i]*A[i];
        b[i].re=B[i]*B[i];
   }
   FFT(a,size,1),FFT(b,size,1);
   complex two(2,0);
   for(int i=0;i<size;i++){
        c[i]=c[i]-a[i]*b[i]*two;
        a[i].re=a[i].im=0;
        b[i].re=b[i].im=0;
   }
   for(int i=0;i<k;i++){
        a[i].re=A[i];
        b[i].re=B[i]*B[i]*B[i];
   }
   FFT(a,size,1),FFT(b,size,1);
   for(int i=0;i<size;i++){
        c[i]=c[i]+a[i]*b[i];
   }
   FFT(c,size,-1);
   long long ans=0;
   for(int i=1;i<=k;i++){
       if(c[i-1].re<0.000000001){
           ans=ans^((long long)i*i);
       }
   }
   cout<<ans<<endl;
}
