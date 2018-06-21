package application.model;

import java.util.List;

import javafx.scene.control.TextField;

/**
 * �Զ���ʾ���������,�����������ȥ������ʾ�ؼ�
 */
public class AutoCompleteTextFieldBuilder
{
	/**
	 * 
	 * <p>
	 * ��textField�ؼ�ת��Ϊ����ʾ
	 * </p>
	 */
	public static final AutoCompleteTextField build(TextField textField, List<String> cacheData)
	{
		return new AutoCompleteTextField(textField, cacheData);
	}
	
	public static final AutoCompleteTextField build(TextField textField)
	{
		return new AutoCompleteTextField(textField);
	}

}

