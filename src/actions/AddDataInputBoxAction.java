package actions;

import actions.edit.undoredo.SharedUndoRedoActionManager;
import actions.edit.undoredo.UndoRedoableInterface;
import actions.menu.ActionsMenuBarTitles;
import paintcomponents.data.DataInputTextfieldPaintComponent;
import ui.PaintPanel;

public class AddDataInputBoxAction extends PaintAction {

	public AddDataInputBoxAction(PaintPanel panel) {
		super(panel);
	}

	@Override
	public boolean canPerformAction() {
		return true;
	}

	@Override
	public void performAction() {
		DataInputTextfieldPaintComponent comp = new DataInputTextfieldPaintComponent("Data Input", panel.getWidth() /2, panel.getHeight()/2);
		panel.addPaintComponent(comp);
		
		
		//push action to the manager
		SharedUndoRedoActionManager.getSharedInstance().pushUndoableAction(new UndoRedoableInterface() {
			
			@Override
			public void undoAction() {
				comp.remove(panel);
				panel.repaint();
			}
			
			@Override
			public void redoAction() {
				panel.addPaintComponent(comp);
				panel.repaint();
				
			}

			@Override
			protected String commandName() {
				return "add data inputBox";
			}

			@Override
			protected String commandDescription() {
				// TODO Auto-generated method stub
				return "add a string input";
			}
		});
		panel.repaint();
	}

	@Override
	public String locationString() {
		return ActionsMenuBarTitles.Data().Input_Box().Add().toString();
	}

}
