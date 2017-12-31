import com.sun.xml.internal.ws.api.policy.ModelGenerator;
import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;
import vpt.algorithms.linear.Sobel;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.output.prediction.PlainText;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws Exception {

        /*
        ArrayList<Image> a;
        Lbp b=new Lbp();
        a=ImageSplitter.splitImage(Load.invoke("src//data//KA.AN1.39_result.png"),8);
        for(int i=0;i<a.size();++i)
        {
           Display2D.invoke(b.convolutionLbp(a.get(i)) );
        }
        */
       /* Lbp a=new Lbp();
        Display2D.invoke(a.convolutionLbp(Load.invoke("src//data//KA.AN1.39_result.png")));*/

        /*Train t=new Train();
        int index=1;
        t.trainForSystem(index,2);
        System.out.println("\n\n\n\n\n\n------Test--------\n\n\n\n\n");
        t.testForSystem(index,2);

        converter.convertArrf("train.csv","train.arff");
        converter.convertArrf("test.csv","test.arff");*/


       /* TrainLTP t=new TrainLTP();
        int index=10;
        int flag=2;
        int threshold=5;
        t.trainForSystem(index,flag,threshold);
        System.out.println("\n\n\n\n\n\n------Test--------\n\n\n\n\n");
        t.testForSystem(index,flag,threshold);

        converter.convertArrf("train.csv","train.arff");
        converter.convertArrf("test.csv","test.arff");
*/
        /*int index=3;
        int split=16;
        TrainKxKLBP t2=new TrainKxKLBP();
        t2.trainForSystem(index,split,2);
        t2.testForSystem(index,split,2);
        converter.convertArrf("train.csv","train.arff");
        converter.convertArrf("test.csv","test.arff");*/


        /*TrainKxKLTP t=new TrainKxKLTP();
        int index=10;
        int split=8;
        int flag=2;
        int threshold=6;
        t.trainForSystem(index,split,flag,threshold);
        t.testForSystem(index,split,flag,threshold);

        converter.convertArrf("train.csv","train.arff");
        converter.convertArrf("test.csv","test.arff");*/




/*
        Lbp lbp2=new Lbp();

        int[] a=lbp2.returnPropertyVectorNonUniform(lbp2.convolutionLbp(Load.invoke("src//data//KA.AN1.39_result.png")));
        int total=0;
        for(int g=0;g<a.length;++g)
            total=total+a[g];
        System.out.println(total);*/

        /*
        LBPV lbpv=new LBPV();
        int arr[];
        Display2D.invoke( lbpv.convolutionLbp(Load.invoke("src//lenaTest1.jpg")));
        arr=lbpv.returnLBPVHistogram(Load.invoke("src//lenaTest1.jpg"));

        for(int i=0;i<256;++i)
            System.out.println(i+"         "+arr[i]);
        */


        /*LDP ldp=new LDP();
        Display2D.invoke(ldp.returnLdp(Load.invoke("src//data//KA.AN1.39_result.png")));
        */

        /*
        GaborFeatures gabor=new GaborFeatures();
        Display2D.invoke(gabor.implementgabor(Load.invoke("src//lenaTest1.jpg"),7));
        */

        KMeansSetCluster K=new KMeansSetCluster();
        int number=6;
        K.trainData(number);

        String dataset = "train.arff";
        converter.convertArrf("train.csv","train.arff");
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(dataset);

        Instances data = source.getDataSet();

        SimpleKMeans2 model = new SimpleKMeans2();
        int histogramsize=8;
        model.setNumClusters(histogramsize);
        int []histograms=new int[histogramsize];
        for(int i=0;i<histogramsize;++i)
            histograms[i]=0;
        model.buildClusterer(data);
        model.m_initialStartPoints.size();
        for (int i = 0; i < model.m_initialStartPoints.numInstances(); i++) {
            System.out.println("Cluster " + i + ": " +model. m_initialStartPoints.instance(i));
        }
        K.createHistogramsTrainSet(number,model,histograms,histogramsize);
        K.createHistogramsTestSet(number,model,histograms,histogramsize);



       /* LandUseClassifier qwe=new LandUseClassifier("train2.arff","test2.arff",5);
        qwe.doClassification();*/

        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("train2.csv"));
        Instances train = loader.getDataSet();

        loader.setSource(new File("test2.csv"));
        Instances test = loader.getDataSet();


        Classifier  cls= new RandomForest();
        train.setClassIndex(train.numAttributes() -1 );
        test.setClassIndex(test.numAttributes() -1 );
        cls.buildClassifier(train);

        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(cls, test);
        System.out.println(eval.toMatrixString());



    }








}