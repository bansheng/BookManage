package application.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Window;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * ������������ݺ���ʾ�б���ƥ����ʾ��Ϣ
 * 
 *
 */
public class AutoCompleteTextField
{
	private TextField textField;
	private final static int LIST_MAX_SIZE = 10;
	private final static int LIST_CELL_HEIGHT = 24;
	
	/** pinyin4j ������ ����ƥ���������� */
	private HanyuPinyinOutputFormat pinyinFormat = new HanyuPinyinOutputFormat();

	/** �����洢��ʾ ��������Ϣ�б� */
	private ObservableList<String> showCacheDataList = FXCollections.<String> observableArrayList();

	/** �����洢�������Ϣ�б� ��дindexOf��������ȡƥ�䵽������ */
	private List<String> cacheDataList = new ArrayList<String>()
	{
		private static final long serialVersionUID = 281687373227150590L;

		@Override
		public int indexOf(Object searchContext)
		{
			showCacheDataList.clear();
			int size = cacheDataList.size();
			
			if(null == searchContext || searchContext.toString().equals("")) {
				removeDuplicate(cacheDataList);
				int size1 = cacheDataList.size();
				for (int i = 0; i < size1; i++) {
					showCacheDataList.add(cacheDataList.get(i));
					
				}
				return 0;
			}
			
			for (int i = 0; i < size; i++)
			{
				char[] charArry = cacheDataList.get(i).toCharArray();
				StringBuilder sbPinyin = new StringBuilder();
				String indexPinyin = new String();
				for (char ch : charArry)
				{
					// ����������ת��Ϊƴ�� ��������
					try
					{
						String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(ch, pinyinFormat);
						sbPinyin.append(null != pinyin ? pinyin[0] : ch);
						if(null != pinyin) {
							indexPinyin = indexPinyin + pinyin[0].charAt(0);
						}
					} catch (BadHanyuPinyinOutputFormatCombination e)
					{
						sbPinyin.append(ch);
					}
				}
				if (cacheDataList.get(i).contains(searchContext.toString().toLowerCase())
						|| sbPinyin.toString().contains(searchContext.toString().toLowerCase()) || 
						indexPinyin.contains(searchContext.toString().toLowerCase()))
				{
					showCacheDataList.add(cacheDataList.get(i));
				}
			}
			return -1;
		};
	};

	/** �������������� */
	private SimpleStringProperty inputContent = new SimpleStringProperty();

	/** �������ݺ���ʾ��pop */
	private Popup popShowList = new Popup();

	/** �������ݺ���ʾ����ʾ��Ϣ�б� */
	private ListView<String> autoTipList = new ListView<String>();

	AutoCompleteTextField(TextField textField, List<String> cacheDataList)
	{
		if (null == textField)
		{
			throw new RuntimeException("textField ����Ϊ��");
		}
		this.textField = textField;
		if (null != cacheDataList)
		{
			this.cacheDataList.addAll(cacheDataList);
		}
		configure();
		confListnenr();
	}

	AutoCompleteTextField(TextField textField)
	{
		this(textField, null);
	}

	public void setCacheDataList(List<String> cacheDataList)
	{
		this.cacheDataList.clear();
		this.cacheDataList.addAll(cacheDataList);
	}

	/**
	 * 
	 * ��Ӽ����¼�
	 * 
	 */
	private void confListnenr()
	{
		textField.textProperty().bindBidirectional(inputContent);

//		textField.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>()
//		{
//			@Override
//			public void handle(ActionEvent event)
//			{
//				cacheDataList.add(inputContent.get()); // setOnAction�¼���Ż���Ч���˴��ǵ����ťʱ���ı��������ݴ��뵽cacheDataList��
//				removeDuplicate(cacheDataList);
//			}
//			
//		});
		
		textField.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				configureListContext(inputContent.get());
			}
			
		});
		
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if(event.getCode() == KeyCode.ALT) 
				{
					configureListContext(inputContent.get());
				}
			}
			
		});

		inputContent.addListener(new ChangeListener<String>()
		{

			@Override
			public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue)
			{
				if(newValue != "" && newValue!=null)
					configureListContext(newValue);    //���ı��������ݷ����仯ʱ�ᴥ�����¼������ı��������ݽ���ƥ��
			}
		});

		autoTipList.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				selectedItem();
			}
		});

		autoTipList.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent event)
			{
				if(event.getCode() == KeyCode.ENTER) 
				{
					selectedItem();
				}
			}
		});
	}
	
	/**
	 * ��ȡѡ�е�list���ݵ������
	 */
	private void selectedItem() {
		inputContent.set(autoTipList.getSelectionModel().getSelectedItem());
		textField.end();
		popShowList.hide();
	}

	/**
	 * ���������������������ʾ��Ϣ
	 */
	public void configureListContext(String tipContent)
	{
		cacheDataList.indexOf(tipContent);
		if(!showCacheDataList.isEmpty() && textField.isFocused()) {
			showTipPop();
		} else {
			popShowList.hide();
		}
	}

	/**
	 * �����齨
	 */
	private void configure()
	{
		pinyinFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pinyinFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

		popShowList.setAutoHide(true);
		popShowList.getContent().add(autoTipList);
  		autoTipList.setItems(showCacheDataList);
	}
	
	public void removeDuplicate(List<String> cacheDataList2) {
		HashSet<String> h  =   new  HashSet<String>(cacheDataList2); 
	    cacheDataList2.clear(); 
	    cacheDataList2.addAll(h); 
	}
	

	/**
	 * ��ȡpop��ʾ�Ĵ���
	 */
	public final Window getWindow()
	{
		return getScene().getWindow();
	}

	/**
	 * ��ȡtextField Scene
	 */
	public final Scene getScene()
	{
		return textField.getScene();
	}

	/**
	 * ��ʾpop
	 */
	public final void showTipPop()
	{
		autoTipList.setPrefWidth(textField.getWidth() - 3);
		if(showCacheDataList.size() < LIST_MAX_SIZE) {
			autoTipList.setPrefHeight(showCacheDataList.size() * LIST_CELL_HEIGHT + 3);
		} else {
			autoTipList.setPrefHeight(LIST_MAX_SIZE * LIST_CELL_HEIGHT + 3);
		}
		Window window = getWindow();
		Scene scene = getScene();
		Point2D fieldPosition = textField.localToScene(0, 0);
		popShowList.show(window, window.getX() + fieldPosition.getX() + scene.getX(), window.getY() + fieldPosition.getY() + scene.getY() + textField.getHeight());
		autoTipList.getSelectionModel().clearSelection();
		autoTipList.getFocusModel().focus(-1);
	}
}

