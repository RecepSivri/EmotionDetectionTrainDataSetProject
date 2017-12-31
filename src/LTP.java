import vpt.Image;

/**
 * Created by Recep Sivri on 4.11.2017.
 */
public class LTP {
    private int retrunResultUpper(int b7, int b6 ,int b5, int b4, int b3, int b2 , int b1 , int b0)
    {
        if(b7==-1)
            b7=0;
        if(b6==-1)
            b6=0;
        if(b5==-1)
            b5=0;
        if(b4==-1)
            b4=0;
        if(b3==-1)
            b3=0;
        if(b2==-1)
            b2=0;
        if(b1==-1)
            b1=0;
        if(b0==-1)
            b0=0;

        return b4*128+b5*64+b6*32+b7*16+b0*8+b1*4+b2*2+b3*1;

    }
    private int retrunResultLower(int b7, int b6 ,int b5, int b4, int b3, int b2 , int b1 , int b0)
    {
        if(b7==1)
            b7=0;
        if(b6==1)
            b6=0;
        if(b5==1)
            b5=0;
        if(b4==1)
            b4=0;
        if(b3==1)
            b3=0;
        if(b2==1)
            b2=0;
        if(b1==1)
            b1=0;
        if(b0==1)
            b0=0;

        if(b7==-1)
            b7=1;
        if(b6==-1)
            b6=1;
        if(b5==-1)
            b5=1;
        if(b4==-1)
            b4=1;
        if(b3==-1)
            b3=1;
        if(b2==-1)
            b2=1;
        if(b1==-1)
            b1=1;
        if(b0==-1)
            b0=1;



        return b4*128+b5*64+b6*32+b7*16+b0*8+b1*4+b2*2+b3*1;

    }
    public Image[] convolutionLTP(Image img,int threshold)
    {
        int b0=0,b1=0,b2=0,b3=0,b4=0,b5=0,b6=0,b7=0,result;
        Image img2=img.newInstance(img.getXDim()+2,img.getYDim()+2,1);
        Image img3=img.newInstance(img.getXDim(),img.getYDim(),1);
        Image img4=img.newInstance(img.getXDim(),img.getYDim(),1);
        Image []imageArray=new Image[2];
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
                if(img2.getXYByte(i-1,j-1)>img2.getXYByte(i,j)+threshold)
                    b7=1;
                else
                if(img2.getXYByte(i-1,j-1)<img2.getXYByte(i,j)+threshold&&img2.getXYByte(i-1,j-1)>img2.getXYByte(i,j)-threshold)
                    b7=0;
                else
                if(img2.getXYByte(i-1,j-1)<img2.getXYByte(i,j)-threshold)
                    b7=-1;

                if(img2.getXYByte(i,j-1)>img2.getXYByte(i,j)+threshold)
                    b6=1;
                else
                if(img2.getXYByte(i,j-1)<img2.getXYByte(i,j)+threshold&&img2.getXYByte(i,j-1)>img2.getXYByte(i,j)-threshold)
                    b6=0;
                else
                if(img2.getXYByte(i,j-1)<img2.getXYByte(i,j)-threshold)
                    b6=-1;

                if(img2.getXYByte(i+1,j-1)>img2.getXYByte(i,j)+threshold)
                    b5=1;
                else
                if(img2.getXYByte(i+1,j-1)<img2.getXYByte(i,j)+threshold&&img2.getXYByte(i+1,j-1)>img2.getXYByte(i,j)-threshold)
                    b5=0;
                else
                if(img2.getXYByte(i+1,j-1)<img2.getXYByte(i,j)-threshold)
                    b5=-1;

                if(img2.getXYByte(i+1,j)>img2.getXYByte(i,j)+threshold)
                    b4=1;
                else
                if(img2.getXYByte(i+1,j)<img2.getXYByte(i,j)+threshold&&img2.getXYByte(i+1,j)>img2.getXYByte(i,j)-threshold)
                    b4=0;
                else
                if(img2.getXYByte(i+1,j)<img2.getXYByte(i,j)-threshold)
                    b4=-1;


                if(img2.getXYByte(i+1,j+1)>img2.getXYByte(i,j)+threshold)
                    b3=1;
                else
                if(img2.getXYByte(i+1,j+1)<img2.getXYByte(i,j)+threshold&&img2.getXYByte(i+1,j+1)>img2.getXYByte(i,j)-threshold)
                    b3=0;
                else
                if(img2.getXYByte(i+1,j+1)<img2.getXYByte(i,j)-threshold)
                    b3=-1;

                if(img2.getXYByte(i,j+1)>img2.getXYByte(i,j)+threshold)
                    b2=1;
                else
                if(img2.getXYByte(i,j+1)<img2.getXYByte(i,j)+threshold&&img2.getXYByte(i,j+1)>img2.getXYByte(i,j)-threshold)
                    b2=0;
                else
                if(img2.getXYByte(i,j+1)<img2.getXYByte(i,j)-threshold)
                    b2=-1;


                if(img2.getXYByte(i-1,j+1)>img2.getXYByte(i,j)+threshold)
                    b1=1;
                else
                if(img2.getXYByte(i-1,j+1)<img2.getXYByte(i,j)+threshold&&img2.getXYByte(i-1,j+1)>img2.getXYByte(i,j)-threshold)
                    b1=0;
                else
                if(img2.getXYByte(i-1,j+1)<img2.getXYByte(i,j)-threshold)
                    b1=-1;


                if(img2.getXYByte(i-1,j)>img2.getXYByte(i,j)+threshold)
                    b0=1;
                else
                if(img2.getXYByte(i-1,j)<img2.getXYByte(i,j)+threshold&&img2.getXYByte(i-1,j)>img2.getXYByte(i,j)-threshold)
                    b0=0;
                else
                if(img2.getXYByte(i-1,j)<img2.getXYByte(i,j)-threshold)
                    b0=-1;



                img3.setXYByte(i-1,j-1,retrunResultUpper(b7,b6,b5,b4,b3,b2,b1,b0));
                img4.setXYByte(i-1,j-1,retrunResultLower(b7,b6,b5,b4,b3,b2,b1,b0));
            }
        }
        imageArray[0]=img3;
        imageArray[1]=img4;
        return imageArray;
    }
    int [] returnUpperHistogram(Image img,int threshold)
    {
        Image upper,lower;
        Image []out;
        out=convolutionLTP(img,threshold);
        upper=out[0];
        lower=out[1];

        int []histogram=new int[256];

        for(int i=0;i<upper.getXDim();++i)
            for(int j=0;j<upper.getYDim();++j)
                histogram[upper.getXYByte(i,j)]++;

        return histogram;
    }

    int [] returnLowerHistogram(Image img,int threshold)
    {
        Image upper,lower;
        Image []out;
        out=convolutionLTP(img,threshold);
        upper=out[0];
        lower=out[1];

        int []histogram=new int[256];

        for(int i=0;i<lower.getXDim();++i)
            for(int j=0;j<lower.getYDim();++j)
                histogram[lower.getXYByte(i,j)]++;

        return histogram;
    }
}
