package actions;

import ui.PaintPanel;

import java.lang.reflect.Method;

import javax.swing.JOptionPane;

import actions.menu.ActionsMenuBarTitles;
import paintcomponents.java.lazy.ClassPaintComponent;
import paintcomponents.java.interactive.InstanceOperationComponent;
import paintcomponents.java.lazy.MethodPaintComponent;

public class AddInstanceMethodAction extends PaintAction {

	public AddInstanceMethodAction(PaintPanel panel) {
		super(panel);
	}

	@Override
	public boolean canPerformAction() {
		if (panel.getSelectTool().getSelectedComponents().size() != 1) {
			return false;
		}
		if (panel.getSelectTool().getSelectedComponents()
				.get(0) instanceof InstanceOperationComponent) {
			return true;
		}
		return false;
	}

	@Override
	public void performAction() {
		InstanceOperationComponent insComp = 
				(InstanceOperationComponent)panel.getSelectTool().
				getSelectedComponents().get(0);
		Method[] methods = insComp.getDisplayingClass().getMethods();
		
		int desiaredConstructorIndex = Integer
				.parseInt(JOptionPane.showInputDialog(
						"Please enter the index of the constructor you would like to use: \n\n\n"
								+ getMethodsSelectionUI(methods)));
		insComp.addMethodPaintComponent(methods[desiaredConstructorIndex], panel);
		panel.repaint();
	}

	@Override
	public String locationString() {
		// TODO Auto-generated method stub
		return ActionsMenuBarTitles.Lazy().Add().Add_Instance_Method().toString();
	}
	
	public String getMethodsSelectionUI(Method[] methods) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < methods.length; i++) {
			Method constructor = methods[i];
			builder.append(i + " : " + constructor.toString() + "\n");
		}
		return builder.toString();

	}
	
}
