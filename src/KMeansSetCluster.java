import vpt.Image;
import vpt.algorithms.io.Load;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Recep Sivri on 26.11.2017.
 */
public class KMeansSetCluster {

    public void trainData(int value)
    {
        HashMap<Integer,String> imagesValue=new HashMap<Integer,String>();
        imagesValue.put(1,"KA");
        imagesValue.put(2,"KL");
        imagesValue.put(3,"KM");
        imagesValue.put(4,"KR");
        imagesValue.put(5,"MK");
        imagesValue.put(6,"NA");
        imagesValue.put(7,"NM");
        imagesValue.put(8,"TM");
        imagesValue.put(9,"UY");
        imagesValue.put(10,"YM");
        int []Ulbpform;
        Lbp lbp1=new Lbp();
        Image img;
        ArrayList<Image> res;
        InverseUniform un=new InverseUniform();

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("train.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();

        for( int i=0;i<un.inverseUniforms.size();++i)
        {
            sb.append("u"+un.inverseUniforms.get(i));
            sb.append(',');
        }
        sb.append("emotion");
        sb.append("\n");

        File folder = new File("src//data");
        File[] listOfFiles = folder.listFiles();



        for ( int  i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()&&!listOfFiles[i].getName().substring(0,2).equals(imagesValue.get(value))) {

                System.out.println(listOfFiles[i].getName()+"------"+listOfFiles[i].getName().substring(3,5)+
                        "------"+listOfFiles[i].getName().substring(0,2));
                img = Load.invoke("src//data//"+listOfFiles[i].getName());
                res=ImageSplitter.splitImage(img,8);

                for(int j=0;j<res.size();++j)
                {
                    Ulbpform=lbp1.returnPropertyVectorUniform(res.get(j));
                    for( int l=0;l<Ulbpform.length;++l)
                    {
                        sb.append(Ulbpform[l]);
                        sb.append(',');
                    }
                    sb.append(returnEmotion(listOfFiles[i].getName().substring(3,5)));
                    sb.append("\n");
                }
            }
        }
        pw.write(sb.toString());
        pw.close();

    }
    private String returnEmotion(String em)
    {
        if(em.equals("AN"))
            return "ANGRY";
        else
        if(em.equals("DI"))
            return "DISTINGUISH";
        else
        if(em.equals("FE"))
            return "FEAR";
        else
        if(em.equals("HA"))
            return "HAPPY";
        else
        if(em.equals("NE"))
            return "NEUTRAL";
        else
        if(em.equals("SA"))
            return "SAD";
        else
        if(em.equals("SU"))
            return "SUPRISED";
        else
            return "NULL";
    }
    public void createHistogramsTrainSet(int number,SimpleKMeans2 model,int [] histograms,int size) throws Exception {
        Image img;
        int index=0;
        ArrayList<Double> mins=new ArrayList<Double>();
        ArrayList<Image> res;
        HashMap<Integer,String> imagesValue=new HashMap<Integer,String>();
        imagesValue.put(1,"KA");
        imagesValue.put(2,"KL");
        imagesValue.put(3,"KM");
        imagesValue.put(4,"KR");
        imagesValue.put(5,"MK");
        imagesValue.put(6,"NA");
        imagesValue.put(7,"NM");
        imagesValue.put(8,"TM");
        imagesValue.put(9,"UY");
        imagesValue.put(10,"YM");
        int []Ulbpform;
        Lbp lbp1=new Lbp();
        StringBuilder sb = new StringBuilder();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("train2.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < size; ++i) {
            sb.append(i);
            sb.append(",");
        }
        sb.append("emotion");
        sb.append("\n");

        int c=0;

        File folder = new File("src//data");
        File[] listOfFiles = folder.listFiles();
        for ( int  i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()&&!listOfFiles[i].getName().substring(0,2).equals(imagesValue.get(number))) {

                /*System.out.println(listOfFiles[i].getName()+"------"+listOfFiles[i].getName().substring(3,5)+
                        "------"+listOfFiles[i].getName().substring(0,2));*/
                img = Load.invoke("src//data//"+listOfFiles[i].getName());
                res=ImageSplitter.splitImage(img,8);
                double total=0;
                double min=0;
                double min2=0;

                ++c;
                String[] arr;
                for(int j=0;j<8;++j)
                {
                    //System.out.println("----------------------section-------------------------");
                    for(int n=0;n<model.m_initialStartPoints.numInstances();++n)
                    {
                        arr = model.m_initialStartPoints.instance(n).toString().split(",");
                        Ulbpform=lbp1.returnPropertyVectorUniform(res.get(j));
                        total=0;

                        for (int m = 0; m < arr.length - 1; ++m)
                        {
                            int val=Integer.parseInt(arr[m]);
                            total = total + (Ulbpform[m] - val) * (Ulbpform[m] - val);
                        }


                        total=Math.sqrt(total);
                        if(n==0)
                            min=total;
                        else
                        if(min>total)
                            min=total;
                        mins.add(total);
                        //System.out.println(n+"     "+total);
                        total=0;
                    }
                    for(int a=0;a<mins.size();++a)
                    {
                        if(mins.get(a).equals(min))
                        {
                            histograms[a]++;
                            index=a;
                        }

                    }
                    mins.clear();

                }
                for (int l = 0; l < histograms.length; ++l) {
                    sb.append(histograms[l]);
                    sb.append(",");
                }
                sb.append(model.m_initialStartPoints.instance(index).toString().split(",")[59]);
                sb.append("\n");
                for(int a=0;a<histograms.length;++a)
                    histograms[a]=0;

            }

        }
        pw.write(sb.toString());
        pw.close();
        converter.convertArrf("train2.csv","train2.arff");
    }
    public void createHistogramsTestSet(int number,SimpleKMeans2 model,int [] histograms,int size) throws Exception {
        Image img;
        int index=0;
        ArrayList<Double> mins=new ArrayList<Double>();
        ArrayList<Image> res;
        HashMap<Integer,String> imagesValue=new HashMap<Integer,String>();
        imagesValue.put(1,"KA");
        imagesValue.put(2,"KL");
        imagesValue.put(3,"KM");
        imagesValue.put(4,"KR");
        imagesValue.put(5,"MK");
        imagesValue.put(6,"NA");
        imagesValue.put(7,"NM");
        imagesValue.put(8,"TM");
        imagesValue.put(9,"UY");
        imagesValue.put(10,"YM");
        int []Ulbpform;
        Lbp lbp1=new Lbp();
        StringBuilder sb = new StringBuilder();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("test2.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < size; ++i) {
            sb.append(i);
            sb.append(",");
        }
        sb.append("emotion");
        sb.append("\n");

        int c=0;

        File folder = new File("src//data");
        File[] listOfFiles = folder.listFiles();
        for ( int  i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()&&listOfFiles[i].getName().substring(0,2).equals(imagesValue.get(number))) {

                /*System.out.println(listOfFiles[i].getName()+"------"+listOfFiles[i].getName().substring(3,5)+
                        "------"+listOfFiles[i].getName().substring(0,2));*/
                img = Load.invoke("src//data//"+listOfFiles[i].getName());
                res=ImageSplitter.splitImage(img,8);
                double total=0;
                double min=0;
                double min2=0;

                ++c;
                String[] arr;
                for(int j=0;j<8;++j)
                {
                    //System.out.println("----------------------section-------------------------");
                    for(int n=0;n<model.m_initialStartPoints.numInstances();++n)
                    {
                        arr = model.m_initialStartPoints.instance(n).toString().split(",");
                        Ulbpform=lbp1.returnPropertyVectorUniform(res.get(j));
                        total=0;

                        for (int m = 0; m < arr.length - 1; ++m)
                        {
                            int val=Integer.parseInt(arr[m]);
                            total = total + (Ulbpform[m] - val) * (Ulbpform[m] - val);
                        }


                        total=Math.sqrt(total);
                        if(n==0)
                            min=total;
                        else
                        if(min>total)
                            min=total;
                        mins.add(total);
                        //System.out.println(n+"     "+total);
                        total=0;
                    }
                    for(int a=0;a<mins.size();++a)
                    {
                        if(mins.get(a).equals(min))
                        {
                            histograms[a]++;
                            index=a;
                        }

                    }
                    mins.clear();

                }
                for (int l = 0; l < histograms.length; ++l) {
                    sb.append(histograms[l]);
                    sb.append(",");
                }
                sb.append(model.m_initialStartPoints.instance(index).toString().split(",")[59]);
                sb.append("\n");
                for(int a=0;a<histograms.length;++a)
                    histograms[a]=0;

            }

        }
        pw.write(sb.toString());
        pw.close();
        converter.convertArrf("test2.csv","test2.arff");
    }
}
