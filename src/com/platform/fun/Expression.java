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
    	// 表达式求值
	static public Any getExpressionValue(String sExpression,Transaction Sqlca) throws Exception
	{
		//add by byhu 20050807 否则会报表达式书写错误3
		if(sExpression==null || sExpression.equals("")) return new Any("");
		
		int iExpressionLen,iScanPos = 0;   //表达式长度和当前扫描位置
		ID idCurrent,idLast,idOperator;
		Any anyOperand,anyOperateResult = new Any(0);
		Stack StackOperand  = new Stack() ; //操作数堆栈
		Stack StackOperator = new Stack() ; //运算符堆栈
		
		
		idLast = new ID("开始","","","",0);
		iExpressionLen = sExpression.length();
		//System.out.println("getExpressionValue:"+sExpression);
		try
		{
			while(iScanPos < iExpressionLen)
			{
				idCurrent = IDManager.getID(sExpression,iScanPos);
				
				if (idCurrent.Name.equalsIgnoreCase("操作数"))       //!!
				{
					//将操作数作为一个Any对象入栈，类型为ID属性,值为IDValue
					StackOperand.push(new Any(idCurrent.Attribute,idCurrent.Value));					
					
				}else if (idCurrent.Name.equalsIgnoreCase("运算符")) //!!
				{
					//对负号进行处理,如果上一个为标示符不是操作数，则"-"应该为负号					
					if (idCurrent.Content.equalsIgnoreCase("-") && !idLast.Name.equalsIgnoreCase("操作数") && !idLast.Name.equalsIgnoreCase("右括号") && !idLast.Name.equalsIgnoreCase("数组结束") && !idLast.Name.equalsIgnoreCase("外部函数"))
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
							throw new ExpressionException("Expression:表达式书写错误1" + sExpression );
						}						
					}else
					{
						
						//如果当前操作符栈为空，直接入栈
						if (StackOperator.empty())
						{
							StackOperator.push(idCurrent);
						}else
						{
							//如果当前操作符运算次序大于等于栈顶操作符，则直接入栈
							idLast = (ID) StackOperator.lastElement();
							if (idCurrent.Order > idLast.Order)
							{
								StackOperator.push(idCurrent);
	
							}else 
							{
	
								//进行计算，直到当前操作符运算次序大于等于栈顶操作符或运算符栈为空
	
								do 
								{
									idOperator = (ID) StackOperator.pop(); //取运算符
									anyOperateResult = getOperateResult(StackOperand,idOperator);
									StackOperand.push(anyOperateResult);	   //将计算结果入操作数栈										
									
									if (!StackOperator.empty()) //取运算符栈顶运算符
									{
										idOperator = (ID) StackOperator.lastElement(); 
									}
	
								}while(idCurrent.Order <= idOperator.Order && !StackOperator.empty() );
	
								StackOperator.push(idCurrent); //将当前运算符入栈
							}
						}
					}	

				}else if (idCurrent.Name.equalsIgnoreCase("左括号") || idCurrent.Name.equalsIgnoreCase("函数"))  //!!
				{
					StackOperator.push(idCurrent);

				}else if (idCurrent.Name.equalsIgnoreCase("右括号"))  //!!
				{
					//进行计算，直到算完"左括号" 或 "函数"

					do 
					{
						idOperator = (ID) StackOperator.pop(); //取运算符
						anyOperateResult = getOperateResult(StackOperand,idOperator);
						StackOperand.push(anyOperateResult);	   //将计算结果入操作数栈										

					}while(!idOperator.Name.equalsIgnoreCase("左括号") && !idOperator.Name.equalsIgnoreCase("函数"));

				}else if (idCurrent.Name.equalsIgnoreCase("数组开始"))  //!!
				{
					StackOperator.push(idCurrent);

				}else if (idCurrent.Name.equalsIgnoreCase("数组结束"))  //!!
				{
					//进行数组生成，直到"{"
					String sOperandType = "";
					Vector vOperandList = new Vector();					
				
					do 
					{
						idOperator = (ID) StackOperator.pop(); //取运算符
						if (idOperator.Name.equalsIgnoreCase("运算符")) 
						{
							anyOperateResult = getOperateResult(StackOperand,idOperator);
							StackOperand.push(anyOperateResult);	   //将计算结果入操作数栈
						}else //否则逐个将数组元素取出并合成
						{
							anyOperand = popOperand(StackOperand);
							if (sOperandType.equals("")) sOperandType = anyOperand.getType();
							else if(!sOperandType.equals(anyOperand.getType()))
							{
								throw new ExpressionException("Expression:表达式中数组元素类型不一致" + sExpression);
							}	
							vOperandList.insertElementAt(anyOperand,0);
						}								
													
					}while(!idOperator.Name.equalsIgnoreCase("数组开始"));
					
					if (vOperandList.size()>0) anyOperateResult = new Any(sOperandType+"[]",vOperandList);
					else anyOperateResult = new Any("Null","");
					
					StackOperand.push(anyOperateResult);	   //将数组压入操作数栈

				}else if (idCurrent.Name.equalsIgnoreCase("分隔符")) //!!
				{
					//进行计算，直到遇到"函数"或"数组开始"
					ID idLastOperator = (ID) StackOperator.lastElement();
					
					while (!idLastOperator.Name.equalsIgnoreCase("函数") && !idLastOperator.Name.equalsIgnoreCase("数组开始") && !idLastOperator.Name.equalsIgnoreCase("分隔符"))
					{
						idOperator = (ID) StackOperator.pop(); //取运算符

						anyOperateResult = getOperateResult(StackOperand,idOperator);
						StackOperand.push(anyOperateResult);	   //将计算结果入操作数栈										

						idLastOperator = (ID)StackOperator.lastElement();	   
					}
					
					//将分隔符入栈
					StackOperator.push(idCurrent);  	   

				}else if (idCurrent.Name.equalsIgnoreCase("外部函数"))   //直接入操作数栈
				{
					anyOperateResult = new Any(idCurrent.Content,Sqlca);
					StackOperand.push(anyOperateResult);

				}else if (idCurrent.Name.equalsIgnoreCase("忽略"))   //直接继续
				{
					iScanPos += idCurrent.Content.length();
					continue;

				}else 
				{
					// add by byhu 20050812
					iScanPos++;
					continue;
					//throw new ExpressionException("Expression:表达式书写错误2（无法识别关键字类型）:"+idCurrent.Content+"，表达式：" + sExpression);
				}

				iScanPos += idCurrent.Content.length();
				idLast = idCurrent;
			}

			//如果操作符栈不为空,继续计算
			while(!StackOperator.empty())
			{
				idOperator = (ID) StackOperator.pop();

				anyOperateResult = getOperateResult(StackOperand,idOperator);
				StackOperand.push(anyOperateResult);	   //将计算结果入操作数栈										

			}

			//取运算数栈中最后的结果
			if (StackOperand.size() == 1)
			{
				anyOperateResult = popOperand(StackOperand);
			}else
			{
//				System.out.println("statck:"+StackOperand.size());
//				System.out.println("operand:"+popOperand(StackOperand).toStringValue());
				throw new ExpressionException("Expression:表达式书写错误3" + sExpression);
				
			}			

		}catch(EmptyStackException e)
		{
			throw new ExpressionException("Expression:表达式中运算符与操作数数量不匹配" + sExpression);
			
		}catch(AnyException e)
		{
			throw new ExpressionException(e.getMessage() + sExpression,e.getErrorLevel());
		}
		
		if(anyOperateResult.getType().equalsIgnoreCase("Method")) anyOperateResult = anyOperateResult.methodValue();
		return anyOperateResult;	
	}
	
	
	//从指定运算数栈取数，进行指定运算
	static private Any getOperateResult(Stack stackOperand,ID idOperator) throws Exception
	{
		Any anyOperand1,anyOperand2,anyOperand3,anyOperand4,anyOperateResult = null;

				
		if (idOperator.Attribute.equalsIgnoreCase("四目")) //取四个操作数
		{
			anyOperand4 = popOperand(stackOperand);
			anyOperand3 = popOperand(stackOperand);
			anyOperand2 = popOperand(stackOperand);
			anyOperand1 = popOperand(stackOperand);
			anyOperateResult = anyOperand1.operateWith(idOperator.Content,anyOperand2,anyOperand3,anyOperand4);
	
		}else if (idOperator.Attribute.equalsIgnoreCase("三目")) //取三个操作数
		{
			anyOperand3 = popOperand(stackOperand);
			anyOperand2 = popOperand(stackOperand);
			anyOperand1 = popOperand(stackOperand);
			anyOperateResult = anyOperand1.operateWith(idOperator.Content,anyOperand2,anyOperand3);

		}else if (idOperator.Attribute.equalsIgnoreCase("双目")) //取两个操作数
		{
			anyOperand2 = popOperand(stackOperand);
			anyOperand1 = popOperand(stackOperand);
			anyOperateResult = anyOperand1.operateWith(idOperator.Content,anyOperand2);

		}else if (idOperator.Attribute.equalsIgnoreCase("单目")) //取一个操作数
		{
			anyOperand1 = popOperand(stackOperand);
			anyOperateResult = anyOperand1.operateWith(idOperator.Content);
		}else //如果是分隔符,否则直接返回当前栈顶 
		{
			anyOperateResult = popOperand(stackOperand);
		}	

		return anyOperateResult;
	}	
	
	//从指定运算符栈取数，如果为外部函数则进行执行后返回
	static private Any popOperand(Stack stackOperand) throws Exception
	{
		Any anyOperand = null;
		
		anyOperand = (Any) stackOperand.pop();	
		
		//2004.7.18 RCZhu 外部函数不再执行，在运算时进行执行计算
	//	if (anyOperand.getType().equalsIgnoreCase("Method"))
	//	{
	//		anyOperand = anyOperand.methodValue();			
	//	}		
		return anyOperand;
	}	
	
		
	//对表达式中的方法进行预处理,方法格式形如!ClassName.MethodName(sArgsValueList)
	
	static public String pretreatMethod (String sExpression,Transaction Sqlca) throws Exception
	{
		String sMethod,sValue;
		
		sExpression = sExpression.trim();		
		
		int iPos = sExpression.indexOf("!",0);
		int iCycle = 0;
		while ( iPos != -1)
		{
			iCycle++;
			if(iCycle>100) throw new ExpressionException("Expression中的方法数超过了100个。");
			
			int iArgsEnd   = StringFunction.indexOf(sExpression,")","'","'",iPos);
			
			
			sMethod = sExpression.substring(iPos,iArgsEnd + 1);						
			
			//System.out.println("PretreatMethod: Expression:"+sExpression+" sMethod:"+sMethod);
			
			Any anyValue = ASMethod.executeMethod(sMethod,Sqlca);			

		 	if (anyValue == null) throw new ExpressionException("Expression PretreatMethod: 数值运算中，出现Null值！",2);
		 	
		 	sValue = anyValue.toString();
			
		 	//byhu 20050807,如果方法等于表达式长度，直接执行了返回，否则会循环执行个不停（StringFunction.replace()改成了正则表达式，有点小问题）
			if(iArgsEnd == sExpression.length()-1) return sValue; 
		 	
		 	//将该方法的所有出现替换为该值
			sExpression = StringFunction.replace(sExpression,sMethod,sValue);
			iPos = sExpression.indexOf("!",iPos + sValue.length());
		}
		return sExpression;
	}
	
	//对表达式中的预定义常量进行预处理,一般可定义为#Name
	static public String pretreatConstant (String sExpression,String[][] sConstant) throws Exception
	{
		sExpression = sExpression.trim();
		//替换常量
		for(int i = 0;i < sConstant.length ;i++)
		{			
			sExpression = StringFunction.replace(sExpression,sConstant[i][0],sConstant[i][1]);
		}
		
		return sExpression;	
	}
	
	//对表达式中的预定义常量进行预处理,一般可定义为#Name
	static public String pretreatConstant (String sExpression,ASValuePool vpConstant) throws Exception
	{
		sExpression = sExpression.trim();
		Object[] sKeys = vpConstant.getKeys();
		//替换常量
		for(int i = 0;i < sKeys.length ;i++)
		{
			if(sKeys[i]!=null && ((String)sKeys[i]).indexOf("#")!=0) sKeys[i]="#"+(String)sKeys[i];
			sExpression = StringFunction.replace(sExpression,(String)sKeys[i],(String)vpConstant.getAttribute((String)sKeys[i]));
		}
		
		return sExpression;	
	}
}


