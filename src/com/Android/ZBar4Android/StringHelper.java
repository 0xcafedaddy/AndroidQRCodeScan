/*
 * �ַ������࣬���ڶԷ��ص��ַ������н�������þ�����ȷ��������ݣ�����������ַ��
 */
package com.Android.ZBar4Android;

public class StringHelper 
{
    private String[] mDictionary=new String[]{"N","ORG","TIL","URL","ENAIL","NOTE","ADR"};//�������ݴʵ�
    private String[] mValues=new String[]{"����","��֯","ְλ","�����ʼ�","����","��ַ"};//����ʵ��Ӧֵ
    private StringBuilder mBuilder;
    private String mString;
    
    //���캯��
    public StringHelper(String s)
    {
       this.mString=s;
       mBuilder=new StringBuilder();
    }
    //��������
    public String SplitFormDict()
    {
    	int mStartIndex=0;
    	int mEndIndex=0;
    	//�����������
    	for(int i=0;i<mDictionary.length;i++)
    	{
    	  if(mString.indexOf(mDictionary[i])>-1)
    	  {
    		mStartIndex=mString.indexOf(mDictionary[0]);
    		mEndIndex=mString.indexOf(";", mStartIndex);
    		mBuilder.append(mValues[i]+":"+mString.substring(mStartIndex, mEndIndex)+"\n");
    	  }
    	  else
    	  {
    		  return mString;
    	  }
    	}
    	return mBuilder.toString();
    }
    
}
