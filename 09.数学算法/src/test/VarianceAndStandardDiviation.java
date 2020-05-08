package test;



import java.text.DecimalFormat;
import java.util.Random;
/**
 * ��׼�����
 * @author wangJin
 *
 */
public class VarianceAndStandardDiviation {
	private final static double dmax=999;//Double.MAX_VALUE;//Double���͵����ֵ��̫���doubleֵ����˻�ﵽ�����
	private final static double dmin=Double.MIN_VALUE;//Double���͵���Сֵ
	private final static int n=100;//������ȡ100��doubl���ķ���ͱ�׼��
	
	public static void main(String[] args){
		Random random = new Random();	
		double[] x=new double[n];
		for(int i=0;i<n;i++){//�������n��double��
			x[i]=Double.valueOf(Math.floor(random.nextDouble()*(dmax-dmin)));
			System.out.println(x[i]);
		}
		//����doubl�ַ��������ʽ�����Կ�ѧ���������	
		DecimalFormat df=new DecimalFormat("#,##0.00");//��ʽ������
		//���㷽��
		double dV=Variance(x);
		System.out.println("����="+df.format(dV));
		//�����׼��
		double dS=StandardDiviation(x);
		System.out.println("��׼��="+df.format(dS));
	}
	
	//����s^2=[(x1-x)^2 +...(xn-x)^2]/n
	public static double Variance(double[] x) { 
		int m=x.length;
		double sum=0;
		for(int i=0;i<m;i++){//���
		    sum+=x[i];
		}
		double dAve=sum/m;//��ƽ��ֵ
		double dVar=0;
		for(int i=0;i<m;i++){//�󷽲�
			dVar+=(x[i]-dAve)*(x[i]-dAve);
		}
		return dVar/m;
	}
	
	//��׼���=sqrt(s^2)
	public static double StandardDiviation(double[] x) { 
		int m=x.length;
		double sum=0;
		for(int i=0;i<m;i++){//���
		    sum+=x[i];
		}
		double dAve=sum/m;//��ƽ��ֵ
		double dVar=0;
		for(int i=0;i<m;i++){//�󷽲�
			dVar+=(x[i]-dAve)*(x[i]-dAve);
		}
		return Math.sqrt(dVar/m);	
	}
}