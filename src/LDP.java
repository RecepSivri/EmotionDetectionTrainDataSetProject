import vpt.Image;

import java.util.*;

/**
 * Created by Recep Sivri on 10.11.2017.
 */
public class LDP {
    public Image[] convolutionLbp(Image img)
    {
        int b0,b1,b2,b3,b4,b5,b6,b7,result=0;
        Image img2=img.newInstance(img.getXDim()+2,img.getYDim()+2,1);
        Image [] images=new Image[8];
        for(int n=0;n<images.length;++n)
            images[n]=img.newInstance(img.getXDim(),img.getYDim(),1);
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

                result=returnNorthEastM1(img2,i,j);
                images[0].setXYByte(a,b,returnEastM0(img2,i,j));
                images[1].setXYByte(a,b,returnNorthEastM1(img2,i,j));
                images[2].setXYByte(a,b,returnNorthM2(img2,i,j));
                images[3].setXYByte(a,b,returnNorthWestM3(img2,i,j));
                images[4].setXYByte(a,b,returnWestM4(img2,i,j));
                images[5].setXYByte(a,b,returnSouthWestM5(img2,i,j));
                images[6].setXYByte(a,b,returnSouthM6(img2,i,j));
                images[7].setXYByte(a,b,returnSouthEastM7(img2,i,j));
                ++b;
            }
            b=0;
            ++a;
        }
        return images;
    }
    private int returnEastM0(Image img2 ,int i,int j)
    {
        return img2.getXYByte(i-1,j-1)*-3+img2.getXYByte(i,j-1)*-3+img2.getXYByte(i+1,j-1)*5+
                img2.getXYByte(i-1,j)*-3+img2.getXYByte(i,j)*0+img2.getXYByte(i+1,j)*5+img2.getXYByte(i-1,j+1)*-3+
                img2.getXYByte(i,j+1)*-3+img2.getXYByte(i+1,j+1)*5;
    }
    private int returnNorthEastM1(Image img2 ,int i,int j)
    {
        return img2.getXYByte(i-1,j-1)*-3+img2.getXYByte(i,j-1)*5+img2.getXYByte(i+1,j-1)*5+
                img2.getXYByte(i-1,j)*-3+img2.getXYByte(i,j)*0+img2.getXYByte(i+1,j)*5+img2.getXYByte(i-1,j+1)*-3+
                img2.getXYByte(i,j+1)*-3+img2.getXYByte(i+1,j+1)*-3;
    }
    private int returnNorthM2(Image img2 ,int i,int j)
    {
        return img2.getXYByte(i-1,j-1)*5+img2.getXYByte(i,j-1)*5+img2.getXYByte(i+1,j-1)*5+
                img2.getXYByte(i-1,j)*-3+img2.getXYByte(i,j)*0+img2.getXYByte(i+1,j)*-3
                +img2.getXYByte(i-1,j+1)*-3+ img2.getXYByte(i,j+1)*-3+img2.getXYByte(i+1,j+1)*-3;
    }
    private int returnNorthWestM3(Image img2 ,int i,int j)
    {
        return img2.getXYByte(i-1,j-1)*5+img2.getXYByte(i,j-1)*5+img2.getXYByte(i+1,j-1)*-3+
                img2.getXYByte(i-1,j)*5+img2.getXYByte(i,j)*0+img2.getXYByte(i+1,j)*-3
                +img2.getXYByte(i-1,j+1)*-3+ img2.getXYByte(i,j+1)*-3+img2.getXYByte(i+1,j+1)*-3;
    }
    private int returnWestM4(Image img2 ,int i,int j)
    {
        return img2.getXYByte(i-1,j-1)*5+img2.getXYByte(i,j-1)*-3+img2.getXYByte(i+1,j-1)*-3+
                img2.getXYByte(i-1,j)*5+img2.getXYByte(i,j)*0+img2.getXYByte(i+1,j)*-3
                +img2.getXYByte(i-1,j+1)*5+ img2.getXYByte(i,j+1)*-3+img2.getXYByte(i+1,j+1)*-3;
    }
    private int returnSouthWestM5(Image img2 ,int i,int j)
    {
        return img2.getXYByte(i-1,j-1)*-3+img2.getXYByte(i,j-1)*-3+img2.getXYByte(i+1,j-1)*-3+
                img2.getXYByte(i-1,j)*5+img2.getXYByte(i,j)*0+img2.getXYByte(i+1,j)*-3
                +img2.getXYByte(i-1,j+1)*5+ img2.getXYByte(i,j+1)*5+img2.getXYByte(i+1,j+1)*-3;
    }
    private int returnSouthM6(Image img2 ,int i,int j)
    {
        return img2.getXYByte(i-1,j-1)*-3+img2.getXYByte(i,j-1)*-3+img2.getXYByte(i+1,j-1)*-3+
                img2.getXYByte(i-1,j)*-3+img2.getXYByte(i,j)*0+img2.getXYByte(i+1,j)*-3
                +img2.getXYByte(i-1,j+1)*5+ img2.getXYByte(i,j+1)*5+img2.getXYByte(i+1,j+1)*5;
    }
    private int returnSouthEastM7(Image img2 ,int i,int j)
    {
        return img2.getXYByte(i-1,j-1)*-3+img2.getXYByte(i,j-1)*-3+img2.getXYByte(i+1,j-1)*-3+
                img2.getXYByte(i-1,j)*-3+img2.getXYByte(i,j)*0+img2.getXYByte(i+1,j)*5
                +img2.getXYByte(i-1,j+1)*-3+ img2.getXYByte(i,j+1)*5+img2.getXYByte(i+1,j+1)*5;
    }
    public Image returnLdp(Image img)
    {
        Image []images;
        ArrayList numbers=new ArrayList();
        ArrayList container=new ArrayList();
        HashMap<Integer,Integer> values=new HashMap<Integer, Integer>();
        images=convolutionLbp(img);
        Image img3=img.newInstance(img.getXDim(),img.getYDim(),1);
        int b0,b1,b2,b3,b4,b5,b6,b7,result;
        for(int i=0;i<img.getXDim();++i)
        {
            for (int j = 0; j < img.getYDim(); ++j)
            {
                b0=images[0].getXYByte(i,j);
                b1=images[1].getXYByte(i,j);
                b2=images[2].getXYByte(i,j);
                b3=images[3].getXYByte(i,j);
                b4=images[4].getXYByte(i,j);
                b5=images[5].getXYByte(i,j);
                b6=images[6].getXYByte(i,j);
                b7=images[7].getXYByte(i,j);

                if(b0<0)
                    b0=b0*-1;

                if(b1<0)
                    b1=b1*-1;

                if(b2<0)
                    b2=b2*-1;

                if(b3<0)
                    b3=b3*-1;

                if(b4<0)
                    b4=b4*-1;

                if(b5<0)
                    b5=b5*-1;

                if(b6<0)
                    b6=b6*-1;

                if(b7<0)
                    b7=b7*-1;


                numbers.add(b0);
                numbers.add(b1);
                numbers.add(b2);
                numbers.add(b3);
                numbers.add(b4);
                numbers.add(b5);
                numbers.add(b6);
                numbers.add(b7);

                values.put(0,b0);
                values.put(1,b1);
                values.put(2,b2);
                values.put(3,b3);
                values.put(4,b4);
                values.put(5,b5);
                values.put(6,b6);
                values.put(7,b7);
                Collections.sort(numbers);
                int a=0;
                int b=0;
                for(int g=numbers.size()-1;g>=0;--g)
                {
                    if(a>b)
                    {
                        for(int h=0;h<8;++h)
                        {
                            if(values.get(h).equals(numbers.get(g)))
                            {
                                values.replace(h,1);
                                container.add(h);
                                break;
                            }
                        }
                    }

                    ++b;
                }
                for(int h=0;h<8;++h)
                {
                    if(!container.contains((Integer)h))
                        values.replace(h,0);
                }
                b0=values.get(0);
                b1=values.get(1);
                b2=values.get(2);
                b3=values.get(3);
                b4=values.get(4);
                b5=values.get(5);
                b6=values.get(6);
                b7=values.get(7);

                b7=b7*1;
                b6=b6*2;
                b5=b5*4;
                b4=b4*8;
                b3=b3*16;
                b2=b2*32;
                b1=b1*64;
                b0=b0*128;
                result=b0+b1+b2+b3+b4+b5+b6+b7;
                img3.setXYByte(i,j,result);


            }

        }

        return img3;
    }
}
