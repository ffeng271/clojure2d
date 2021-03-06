// http://blog.ivank.net/fastest-gaussian-blur.html

package clojure2d.java.filter;

import clojure2d.java.Pixels;
import net.jafama.FastMath;

public final class Blur {

    private static void internalProcessH(int[] scl, int[] tcl, int w, int h, int r) {
        double iarr = 1.0 / (r + r + 1);
        for(int i=0; i<h; i++) {
            int ti = i*w;
            int li = ti;
            int ri = ti+r;
            int fv = scl[ti];
            int lv = scl[ti+w-1];
            int val = (r+1)*fv;
            
            for(int j=0; j<r; j++) val += scl[ti+j];
            
            for(int j=0; j<=r; j++) {
                val += scl[ri++] - fv;
                tcl[ti++] = (int)(0.5+val*iarr);
            }
            
            for(int j=r+1; j<w-r; j++) {
                val += scl[ri++] - scl[li++];
                tcl[ti++] = (int)(0.5+val*iarr);
            }
            
            for(int j=w-r; j<w; j++) {
                val += lv - scl[li++];
                tcl[ti++] = (int)(0.5+val*iarr);
            }
            
        }
    }
    
    private static void internalProcessV(int[] scl, int[] tcl, int w, int h, int r) {
        double iarr = 1.0 / (r + r + 1);
        
        for(int i=0; i<w; i++) {
            int ti = i;
            int li = ti;
            int ri = ti+r*w;
            int fv = scl[ti];
            int lv = scl[ti+w*(h-1)];
            int val = (r+1)*fv;
            
            for(int j=0; j<r; j++) val += scl[ti+j*w];
            
            for(int j=0; j<=r; j++) {
                val += scl[ri] - fv;
                tcl[ti] = (int)(0.5+val*iarr);
                ri+=w;
                ti+=w;
            }
            
            for(int j=r+1; j<h-r; j++) {
                val += scl[ri] - scl[li];
                tcl[ti] = (int)(0.5+val*iarr);
                li+=w;
                ri+=w;
                ti+=w;
            }
            
            for(int j=h-r; j<h; j++) {
                val += lv - scl[li];
                tcl[ti] = (int)(0.5+val*iarr);
                li+=w;
                ti+=w;
            }
        }
    }

    
    public static void horizontalBlur(int[] in, int[] out, int ch, int w, int h, int r) {
        int[] channel = Pixels.getChannel(in, ch);
        int[] target = new int[channel.length];
        internalProcessH(channel, target, w, h, r);
        Pixels.setChannel(out, ch, target);
    }

    public static void verticalBlur(int[] in, int[] out, int ch, int w, int h, int r) {
        int[] channel = Pixels.getChannel(in, ch);
        int[] target = new int[channel.length];
        internalProcessV(channel, target, w, h, r);
        Pixels.setChannel(out, ch, target);
    }
    
    public static void boxBlur(int[] in, int[] out, int ch, int w, int h, int r) {
        int[] channel = Pixels.getChannel(in, ch);
        int[] target = new int[channel.length];
        internalProcessH(channel, target, w, h, r);
        internalProcessV(target, channel, w, h, r);
        Pixels.setChannel(out, ch, channel);
    }

    //

        private static int[] boxesForGauss(double sigma, int n) {
        double wIdeal = FastMath.sqrt((12.0*sigma*sigma/n)+1.0);
        int wl = (int)FastMath.floor(wIdeal);
        if( (wl & 1) == 0) { wl--; }
        int wu = wl+2;
				
        double mIdeal = (12.0*sigma*sigma - n*wl*wl - ((n*wl)<<2) - 3.0*n)/(-4*wl - 4);
        int m = (int)FastMath.round(mIdeal);
				
        int[] sizes = new int[n];
        for(int i=0; i<n; i++) sizes[i] = i<m?wl:wu;

        return sizes;
    }

    public static void gaussianBlur(int[] in, int[] out, int ch, int w, int h, int r) {
        int[] channel = Pixels.getChannel(in, ch);
        int[] target = new int[channel.length];
        int[] bxs = boxesForGauss(r,3);
        internalProcessH(channel, target, w, h, (bxs[0]-1)/2);
        internalProcessV(target, channel, w, h, (bxs[0]-1)/2);
        internalProcessH(channel, target, w, h, (bxs[1]-1)/2);
        internalProcessV(target, channel, w, h, (bxs[1]-1)/2);
        internalProcessH(channel, target, w, h, (bxs[2]-1)/2);
        internalProcessV(target, channel, w, h, (bxs[2]-1)/2);
        Pixels.setChannel(out, ch, channel);
    }

    
}
