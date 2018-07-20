package com.platform.fun;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Vector;

import com.amarsoft.amarscript.ASMethod;
import com.amarsoft.amarscript.Any;
import com.amarsoft.amarscript.AnyException;
import com.amarsoft.amarscript.ExpressionException;
import com.amarsoft.amarscript.ID;
import com.amarsoft.amarscript.IDManager;
import com.amarsoft.are.sql.Transaction;
import com.amarsoft.are.util.ASValuePool;
import com.amarsoft.are.util.StringFunction;

public class Expression extends Object
{
    	// ���ʽ��ֵ
	static public Any getExpressionValue(String sExpression,Transaction Sqlca) throws Exception
	{
		//add by byhu 20050807 ����ᱨ���ʽ��д����3
		if(sExpression==null || sExpression.equals("")) return new Any("");
		
		int iExpressionLen,iScanPos = 0;   //���ʽ���Ⱥ͵�ǰɨ��λ��
		ID idCurrent,idLast,idOperator;
		Any anyOperand,anyOperateResult = new Any(0);
		Stack StackOperand  = new Stack() ; //��������ջ
		Stack StackOperator = new Stack() ; //�������ջ
		
		
		idLast = new ID("��ʼ","","","",0);
		iExpressionLen = sExpression.length();
		//System.out.println("getExpressionValue:"+sExpression);
		try
		{
			while(iScanPos < iExpressionLen)
			{
				idCurrent = IDManager.getID(sExpression,iScanPos);
				
				if (idCurrent.Name.equalsIgnoreCase("������"))       //!!
				{
					//����������Ϊһ��Any������ջ������ΪID����,ֵΪIDValue
					StackOperand.push(new Any(idCurrent.Attribute,idCurrent.Value));					
					
				}else if (idCurrent.Name.equalsIgnoreCase("�����")) //!!
				{
					//�Ը��Ž��д���,�����һ��Ϊ��ʾ�����ǲ���������"-"Ӧ��Ϊ����					
					if (idCurrent.Content.equalsIgnoreCase("-") && !idLast.Name.equalsIgnoreCase("������") && !idLast.Name.equalsIgnoreCase("������") && !idLast.Name.equalsIgnoreCase("�������") && !idLast.Name.equalsIgnoreCase("�ⲿ����"))
					{
						ID idNext = IDManager.getID(sExpression,iScanPos + 1);
						if (idNext.Attribute.equalsIgnoreCase("Number"))
						{
							idNext.Content = "-" + idNext.Content;
							idNext.Value   = "-" + idNext.Value;
							idCurrent = idNext;
							StackOperand.push(new Any(idCurrent.Attribute,idCurrent.Value));
						}else
						{
							throw new ExpressionException("Expression:���ʽ��д����1" + sExpression );
						}						
					}else
					{
						
						//�����ǰ������ջΪ�գ�ֱ����ջ
						if (StackOperator.empty())
						{
							StackOperator.push(idCurrent);
						}else
						{
							//�����ǰ���������������ڵ���ջ������������ֱ����ջ
							idLast = (ID) StackOperator.lastElement();
							if (idCurrent.Order > idLast.Order)
							{
								StackOperator.push(idCurrent);
	
							}else 
							{
	
								//���м��㣬ֱ����ǰ���������������ڵ���ջ���������������ջΪ��
	
								do 
								{
									idOperator = (ID) StackOperator.pop(); //ȡ�����
									anyOperateResult = getOperateResult(StackOperand,idOperator);
									StackOperand.push(anyOperateResult);	   //���������������ջ										
									
									if (!StackOperator.empty()) //ȡ�����ջ�������
									{
										idOperator = (ID) StackOperator.lastElement(); 
									}
	
								}while(idCurrent.Order <= idOperator.Order && !StackOperator.empty() );
	
								StackOperator.push(idCurrent); //����ǰ�������ջ
							}
						}
					}	

				}else if (idCurrent.Name.equalsIgnoreCase("������") || idCurrent.Name.equalsIgnoreCase("����"))  //!!
				{
					StackOperator.push(idCurrent);

				}else if (idCurrent.Name.equalsIgnoreCase("������"))  //!!
				{
					//���м��㣬ֱ������"������" �� "����"

					do 
					{
						idOperator = (ID) StackOperator.pop(); //ȡ�����
						anyOperateResult = getOperateResult(StackOperand,idOperator);
						StackOperand.push(anyOperateResult);	   //���������������ջ										

					}while(!idOperator.Name.equalsIgnoreCase("������") && !idOperator.Name.equalsIgnoreCase("����"));

				}else if (idCurrent.Name.equalsIgnoreCase("���鿪ʼ"))  //!!
				{
					StackOperator.push(idCurrent);

				}else if (idCurrent.Name.equalsIgnoreCase("�������"))  //!!
				{
					//�����������ɣ�ֱ��"{"
					String sOperandType = "";
					Vector vOperandList = new Vector();					
				
					do 
					{
						idOperator = (ID) StackOperator.pop(); //ȡ�����
						if (idOperator.Name.equalsIgnoreCase("�����")) 
						{
							anyOperateResult = getOperateResult(StackOperand,idOperator);
							StackOperand.push(anyOperateResult);	   //���������������ջ
						}else //�������������Ԫ��ȡ�����ϳ�
						{
							anyOperand = popOperand(StackOperand);
							if (sOperandType.equals("")) sOperandType = anyOperand.getType();
							else if(!sOperandType.equals(anyOperand.getType()))
							{
								throw new ExpressionException("Expression:���ʽ������Ԫ�����Ͳ�һ��" + sExpression);
							}	
							vOperandList.insertElementAt(anyOperand,0);
						}								
													
					}while(!idOperator.Name.equalsIgnoreCase("���鿪ʼ"));
					
					if (vOperandList.size()>0) anyOperateResult = new Any(sOperandType+"[]",vOperandList);
					else anyOperateResult = new Any("Null","");
					
					StackOperand.push(anyOperateResult);	   //������ѹ�������ջ

				}else if (idCurrent.Name.equalsIgnoreCase("�ָ���")) //!!
				{
					//���м��㣬ֱ������"����"��"���鿪ʼ"
					ID idLastOperator = (ID) StackOperator.lastElement();
					
					while (!idLastOperator.Name.equalsIgnoreCase("����") && !idLastOperator.Name.equalsIgnoreCase("���鿪ʼ") && !idLastOperator.Name.equalsIgnoreCase("�ָ���"))
					{
						idOperator = (ID) StackOperator.pop(); //ȡ�����

						anyOperateResult = getOperateResult(StackOperand,idOperator);
						StackOperand.push(anyOperateResult);	   //���������������ջ										

						idLastOperator = (ID)StackOperator.lastElement();	   
					}
					
					//���ָ�����ջ
					StackOperator.push(idCurrent);  	   

				}else if (idCurrent.Name.equalsIgnoreCase("�ⲿ����"))   //ֱ���������ջ
				{
					anyOperateResult = new Any(idCurrent.Content,Sqlca);
					StackOperand.push(anyOperateResult);

				}else if (idCurrent.Name.equalsIgnoreCase("����"))   //ֱ�Ӽ���
				{
					iScanPos += idCurrent.Content.length();
					continue;

				}else 
				{
					// add by byhu 20050812
					iScanPos++;
					continue;
					//throw new ExpressionException("Expression:���ʽ��д����2���޷�ʶ��ؼ������ͣ�:"+idCurrent.Content+"�����ʽ��" + sExpression);
				}

				iScanPos += idCurrent.Content.length();
				idLast = idCurrent;
			}

			//���������ջ��Ϊ��,��������
			while(!StackOperator.empty())
			{
				idOperator = (ID) StackOperator.pop();

				anyOperateResult = getOperateResult(StackOperand,idOperator);
				StackOperand.push(anyOperateResult);	   //���������������ջ										

			}

			//ȡ������ջ�����Ľ��
			if (StackOperand.size() == 1)
			{
				anyOperateResult = popOperand(StackOperand);
			}else
			{
//				System.out.println("statck:"+StackOperand.size());
//				System.out.println("operand:"+popOperand(StackOperand).toStringValue());
				throw new ExpressionException("Expression:���ʽ��д����3" + sExpression);
				
			}			

		}catch(EmptyStackException e)
		{
			throw new ExpressionException("Expression:���ʽ��������������������ƥ��" + sExpression);
			
		}catch(AnyException e)
		{
			throw new ExpressionException(e.getMessage() + sExpression,e.getErrorLevel());
		}
		
		if(anyOperateResult.getType().equalsIgnoreCase("Method")) anyOperateResult = anyOperateResult.methodValue();
		return anyOperateResult;	
	}
	
	
	//��ָ��������ջȡ��������ָ������
	static private Any getOperateResult(Stack stackOperand,ID idOperator) throws Exception
	{
		Any anyOperand1,anyOperand2,anyOperand3,anyOperand4,anyOperateResult = null;

				
		if (idOperator.Attribute.equalsIgnoreCase("��Ŀ")) //ȡ�ĸ�������
		{
			anyOperand4 = popOperand(stackOperand);
			anyOperand3 = popOperand(stackOperand);
			anyOperand2 = popOperand(stackOperand);
			anyOperand1 = popOperand(stackOperand);
			anyOperateResult = anyOperand1.operateWith(idOperator.Content,anyOperand2,anyOperand3,anyOperand4);
	
		}else if (idOperator.Attribute.equalsIgnoreCase("��Ŀ")) //ȡ����������
		{
			anyOperand3 = popOperand(stackOperand);
			anyOperand2 = popOperand(stackOperand);
			anyOperand1 = popOperand(stackOperand);
			anyOperateResult = anyOperand1.operateWith(idOperator.Content,anyOperand2,anyOperand3);

		}else if (idOperator.Attribute.equalsIgnoreCase("˫Ŀ")) //ȡ����������
		{
			anyOperand2 = popOperand(stackOperand);
			anyOperand1 = popOperand(stackOperand);
			anyOperateResult = anyOperand1.operateWith(idOperator.Content,anyOperand2);

		}else if (idOperator.Attribute.equalsIgnoreCase("��Ŀ")) //ȡһ��������
		{
			anyOperand1 = popOperand(stackOperand);
			anyOperateResult = anyOperand1.operateWith(idOperator.Content);
		}else //����Ƿָ���,����ֱ�ӷ��ص�ǰջ�� 
		{
			anyOperateResult = popOperand(stackOperand);
		}	

		return anyOperateResult;
	}	
	
	//��ָ�������ջȡ�������Ϊ�ⲿ���������ִ�к󷵻�
	static private Any popOperand(Stack stackOperand) throws Exception
	{
		Any anyOperand = null;
		
		anyOperand = (Any) stackOperand.pop();	
		
		//2004.7.18 RCZhu �ⲿ��������ִ�У�������ʱ����ִ�м���
	//	if (anyOperand.getType().equalsIgnoreCase("Method"))
	//	{
	//		anyOperand = anyOperand.methodValue();			
	//	}		
		return anyOperand;
	}	
	
		
	//�Ա��ʽ�еķ�������Ԥ����,������ʽ����!ClassName.MethodName(sArgsValueList)
	
	static public String pretreatMethod (String sExpression,Transaction Sqlca) throws Exception
	{
		String sMethod,sValue;
		
		sExpression = sExpression.trim();		
		
		int iPos = sExpression.indexOf("!",0);
		int iCycle = 0;
		while ( iPos != -1)
		{
			iCycle++;
			if(iCycle>100) throw new ExpressionException("Expression�еķ�����������100����");
			
			int iArgsEnd   = StringFunction.indexOf(sExpression,")","'","'",iPos);
			
			
			sMethod = sExpression.substring(iPos,iArgsEnd + 1);						
			
			//System.out.println("PretreatMethod: Expression:"+sExpression+" sMethod:"+sMethod);
			
			Any anyValue = ASMethod.executeMethod(sMethod,Sqlca);			

		 	if (anyValue == null) throw new ExpressionException("Expression PretreatMethod: ��ֵ�����У�����Nullֵ��",2);
		 	
		 	sValue = anyValue.toString();
			
		 	//byhu 20050807,����������ڱ��ʽ���ȣ�ֱ��ִ���˷��أ������ѭ��ִ�и���ͣ��StringFunction.replace()�ĳ���������ʽ���е�С���⣩
			if(iArgsEnd == sExpression.length()-1) return sValue; 
		 	
		 	//���÷��������г����滻Ϊ��ֵ
			sExpression = StringFunction.replace(sExpression,sMethod,sValue);
			iPos = sExpression.indexOf("!",iPos + sValue.length());
		}
		return sExpression;
	}
	
	//�Ա��ʽ�е�Ԥ���峣������Ԥ����,һ��ɶ���Ϊ#Name
	static public String pretreatConstant (String sExpression,String[][] sConstant) throws Exception
	{
		sExpression = sExpression.trim();
		//�滻����
		for(int i = 0;i < sConstant.length ;i++)
		{			
			sExpression = StringFunction.replace(sExpression,sConstant[i][0],sConstant[i][1]);
		}
		
		return sExpression;	
	}
	
	//�Ա��ʽ�е�Ԥ���峣������Ԥ����,һ��ɶ���Ϊ#Name
	static public String pretreatConstant (String sExpression,ASValuePool vpConstant) throws Exception
	{
		sExpression = sExpression.trim();
		Object[] sKeys = vpConstant.getKeys();
		//�滻����
		for(int i = 0;i < sKeys.length ;i++)
		{
			if(sKeys[i]!=null && ((String)sKeys[i]).indexOf("#")!=0) sKeys[i]="#"+(String)sKeys[i];
			sExpression = StringFunction.replace(sExpression,(String)sKeys[i],(String)vpConstant.getAttribute((String)sKeys[i]));
		}
		
		return sExpression;	
	}
}


