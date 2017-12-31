import vpt.Image;

/**
 * Created by Recep Sivri on 6.11.2017.
 */
public class LBPV {

    public Image convolutionLbp(Image img)
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
                img3.setXYByte(i-1,j-1,result);
            }
        }
        return img3;
    }
    public int [] returnLBPVHistogram(Image image)
    {
        int lbpv[]=new int[256];
        Image img=convolutionLbp(image);
        Image img2=img.newInstance(img.getXDim()+2,img.getYDim()+2,1);

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

        for(int k=0;k<256;++k)
        {
            for(int i=1;i<img2.getXDim()-1;++i)
            {
                for(int j=1;j<img2.getYDim()-1;++j)
                {
                    if(img2.getXYByte(i,j)==k)
                    {
                        lbpv[k]=lbpv[k]+returnVariance(img2,i,j);
                    }
                }
            }
        }

        return lbpv;
    }
    private int returnVariance(Image image,int i ,int j)
    {
        int average=0;
        int variance=0;
        for(int a=i-1;a<=i+1;++a)
        {
            for(int b=j-1;b<=j+1;++b)
            {
                average=average+image.getXYByte(a,b);
            }
        }
        average=average/9;

        for(int a=i-1;a<=i+1;++a)
        {
            for(int b=j-1;b<=j+1;++b)
            {
                variance=variance+(image.getXYByte(a,b)-average)*(image.getXYByte(a,b)-average);
            }
        }
        return variance/9;
    }

}
