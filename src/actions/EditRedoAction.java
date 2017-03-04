package actions;

import ui.PaintPanel;
import actions.edit.undoredo.SharedUndoRedoActionManager;
import actions.menu.ActionsMenuBarTitles;

public class EditRedoAction extends PaintAction {

	SharedUndoRedoActionManager manager = SharedUndoRedoActionManager.getSharedInstance();
	
	public EditRedoAction(PaintPanel panel) {
		super(panel);
	}

	@Override
	public boolean canPerformAction() {
		return manager.canRedo();
	}

	@Override
	public void performAction() {
		manager.redo();
	}

	@Override
	public String locationString() {
		return ActionsMenuBarTitles.Edit().Redo().toString();
	}

}
