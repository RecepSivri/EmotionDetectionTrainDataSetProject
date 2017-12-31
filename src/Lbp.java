import vpt.Image;

import java.util.ArrayList;

/**
 * Created by Recep Sivri on 21.10.2017.
 */
public class Lbp {
    public int [] returnPropertyVectorNonUniform(Image img)
    {
        int[]uniform = new int[256];
        for (int i = 0; i < img.getYDim(); ++i) {
            for (int j = 0; j < img.getXDim(); ++j) {
                uniform[img.getXYByte(j, i)]++;
            }
        }
        return uniform;

    }
    public int [] returnPropertyVectorUniform(Image img)
    {
        UniformValues uniformPixels=new UniformValues();
        int[] uniform = new int[59];
        for (int i = 0; i < img.getYDim(); ++i) {
            for (int j = 0; j < img.getXDim(); ++j) {
                if(isValidPixel(img.getXYByte(j, i)))
                    uniform[uniformPixels.uniforms.get(img.getXYByte(j, i))]++;
                else
                    uniform[58]++;
            }
        }
        return uniform;

    }
    public  Image convolutionLbp(Image img)
    {
        int b0,b1,b2,b3,b4,b5,b6,b7,result;
        Image img2=img.newInstance(img.getXDim()+2,img.getYDim()+2,1);
        Image img3=img.newInstance(img.getXDim(),img.getYDim(),1);


        for(int i=0;i<img.getXDim();++i)
        {
            for(int j=0;j<img.getYDim();++j)
            {
                img2.setXYByte(i+1,j+1,img.getXYByte(i,j));
            }
        }
        for(int i=0;i<img.getXDim();++i)
        {
            img2.setXYByte(i,0,img.getXYByte(i,img.getYDim()-1));
            img2.setXYByte(i,img2.getYDim()-1,img.getXYByte(i,0));
        }
        for(int i=0;i<img.getYDim();++i)
        {
            img2.setXYByte(0,i,img.getXYByte(img.getXDim()-1,i));
            img2.setXYByte(img2.getXDim()-1,i,img.getXYByte(0,i));
        }
        int a=0,b=0;
        for(int i=1;i<img2.getXDim()-1;++i)
        {
            for(int j=1;j<img2.getYDim()-1;++j)
            {
                if(img2.getXYByte(i-1,j-1)-img2.getXYByte(i,j)>=0)
                    b7=1;
                else
                    b7=0;

                if(img2.getXYByte(i,j-1)-img2.getXYByte(i,j)>=0)
                    b6=1;
                else
                    b6=0;

                if(img2.getXYByte(i+1,j-1)-img2.getXYByte(i,j)>=0)
                    b5=1;
                else
                    b5=0;

                if(img2.getXYByte(i+1,j)-img2.getXYByte(i,j)>=0)
                    b4=1;
                else
                    b4=0;

                if(img2.getXYByte(i+1,j+1)-img2.getXYByte(i,j)>=0)
                    b3=1;
                else
                    b3=0;

                if(img2.getXYByte(i,j+1)-img2.getXYByte(i,j)>=0)
                    b2=1;
                else
                    b2=0;

                if(img2.getXYByte(i-1,j+1)-img2.getXYByte(i,j)>=0)
                    b1=1;
                else
                    b1=0;

                if(img2.getXYByte(i-1,j)-img2.getXYByte(i,j)>=0)
                    b0=1;
                else
                    b0=0;

                b7=b7*128;
                b6=b6*64;
                b5=b5*32;
                b4=b4*16;
                b3=b3*8;
                b2=b2*4;
                b1=b1*2;
                b0=b0*1;
                result=b0+b1+b2+b3+b4+b5+b6+b7;
                img3.setXYByte(a,b,result);
                ++b;

            }
            b=0;
            ++a;
        }
        return img3;
    }
    private boolean isValidPixel(int value)
    {
        if(value==0||value==1||value==2||value==3||value==4||value==6||value==7||value==8||value==12||value==14||value==15||value==16
                ||value==24||value==28||value==30||value==31||value==32||value==48||value==56||value==60||value==62||value==63||value==64
                ||value==96||value==112||value==120||value==124||value==126||value==127||value==128||value==129||value==131||value==135
                ||value==143||value==159||value==191||value==192||value==193||value==195||value==199||value==207||value==223||value==224
                ||value==225||value==227||value==231||value==239||value==240||value==241||value==243||value==247||value==248||value==249
                ||value==251||value==252||value==253||value==254||value==255)
        {
            return true;
        }
        else
            return false;
    }
}
