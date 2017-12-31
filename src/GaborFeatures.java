import vpt.Image;

import static java.lang.Math.*;

/**
 * Created by Recep Sivri on 12.11.2017.
 */
public class GaborFeatures {

    Image MirrorPadding(Image img ,int size)
    {
        Image img2=img.newInstance(img.getXDim()+size-1,img.getYDim()+size-1,1);

        for(int i=0;i<img.getXDim();++i)//Görüntüyü  ortaya doldurur.
            for(int j=0;j<img.getYDim();++j)
                img2.setXYByte(i+size/2,j+size/2,img.getXYByte(i,j));


        for(int j=img.getYDim()-size/2;j<img.getYDim();++j)//kuzey yönünü doldurur.
            for(int i=0;i<img.getXDim();++i)
                img2.setXYByte(i+size/2,j-(img.getYDim()-size/2),img.getXYByte(i,j));


        for(int j=img.getYDim()-size/2;j<img.getYDim();++j)//kuzey batı yönü
            for(int i=0;i<size/2;++i)
                img2.setXYByte(i,j-(img.getYDim()-size/2),img.getXYByte(i,j));

        for(int j=img.getYDim()-size/2;j<img.getYDim();++j)//kuzey doğu yönü
            for(int i=img.getXDim()-size/2;i<img.getXDim();++i)
                img2.setXYByte(i+size,j-(img.getYDim()-size/2),img.getXYByte(i,j));


        for(int j=0;j<size/2;++j)//Güney yönü
            for(int i=0;i<img.getXDim();++i)
                img2.setXYByte(i+size/2,j+(img.getYDim())+size/2-1,img.getXYByte(i,j));


        for(int j=0;j<size/2;++j)//güney batı yönü
            for(int i=0;i<size/2;++i)
                img2.setXYByte(i,j+(img.getYDim()+size/2-1),img.getXYByte(i,j));

        for(int j=0;j<size/2;++j)//kuzey doğu yönü
            for(int i=img.getXDim()-size/2;i<img.getXDim();++i)
                img2.setXYByte(i+size-1,j+(img.getYDim())+size/2-1,img.getXYByte(i,j));

        for(int j=0;j<img.getYDim();++j)// batı yönü
            for(int i=img.getXDim()-size/2;i<img.getXDim();++i)
                img2.setXYByte(i-(img.getXDim()-size/2),j+size/2,img.getXYByte(i,j));

        for(int j=0;j<img.getYDim();++j)// doğu yönü
            for(int i=0;i<size/2;++i)
                img2.setXYByte(i+(img.getXDim()+size/2)-1,j+size/2,img.getXYByte(i,j));
        return img2;
    }
    Image implementgabor(Image image,int size)
    {
        Image img,image2;
        double total=0;
        image2=image.newInstance(image.getXDim(),image.getYDim(),1);
        img=MirrorPadding(image,size);
        double [][]kernel;
        kernel=getKernel(size,0,0.1,0,8,2);
        for(int i=0;i<image.getXDim();++i)
        {
            for(int j=0;j<image.getYDim();++j)
            {
                total=0;
                for(int a=-size/2;a<=size/2;++a)
                {
                    for(int b=-size/2;b<=size/2;++b)
                    {
                        total=total+img.getXYByte(i+size/2+a,j+size/2+b)*kernel[b+size/2][a+size/2];
                    }
                }
                image2.setXYByte(i,j,(int)sqrt(total*total));
            }
        }
        return image2;
    }
    double[][] getKernel(int size,int theta,double gama , int fi, int lambda ,int sigma)
    {
        double[][] kernel=new double[size][];

        for(int i=0;i<size;++i)
            kernel[i]=new double[size];
        double sx,sy;
        for(int x=-(size)/2;x <(size)/2;x++) {
            for(int y= -(size)/2;y <(size)/2;y++) {

                sx=x*cos(theta)+y*sin(theta);
                sy=-x*sin(theta)+y*cos(theta);

                kernel[x+size/2][y+size/2] = exp(-(sx*sx+gama*gama*sy*sy)/(2*sigma*sigma))*cos(2*3.14*sx/lambda +fi);
            }
        }
        return kernel;
    }
}
