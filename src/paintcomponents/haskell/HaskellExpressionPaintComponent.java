package paintcomponents.haskell;

import java.util.NoSuchElementException;

import paintcomponents.NoConnectingLineSegmentException;
import paintcomponents.data.DataFromPoint;
import paintcomponents.data.DataFromPointDataProvider;
import paintcomponents.data.DataFromPointNoDataProviderException;
import paintcomponents.data.DataFromPointProviderCannotProvideDataException;
import paintcomponents.data.DataTextIOPaintComponent;
import paintcomponents.data.DataToPoint;

public class HaskellExpressionPaintComponent extends DataTextIOPaintComponent {

	private HaskellTypeParser typeParser;
	private String displayingExpr;

	public HaskellExpressionPaintComponent(String displayingText, int x,
			int y) {
		super(displayingText, x, y);
		this.displayingExpr = displayingText;
		this.typeParser = new HaskellTypeParser(displayingExpr);
		init();
	}

	private void init() {

		// parse the type first
		boolean success = typeParser.parseType();
		if (!success) {
			setDisplayingText(typeParser.getMessage());
			return;
		}

		// build UI
		/*
		 * Line 0 : name of command Line 1 : command output as a whole, on the
		 * right Line 2 ~ 2 + n - 1: arguments Line 2 + n : return type
		 * 
		 */

		StringBuilder builder = new StringBuilder();
		builder.append(this.displayingExpr + "\n");
		builder.append(this.displayingExpr + " :: "
				+ typeParser.getDisplayingExprType() + " >>>>" + "\n");
		addFromPoint(new DataFromPointDataProvider() {

			@Override
			public Object provideInformationToDataFromPoint(
					DataFromPoint dataFromPoint) {
				// TODO implement this
				return displayingExpr;
			}

			@Override
			public boolean canProvideInformationToDataFromPoint(
					DataFromPoint dataFromPoint) {
				return true;
			}
		}, 1, typeParser.getDisplayingExprType());

		// arguments
		for (int i = 0; i < typeParser.getArguments().size(); i++) {
			builder.append("arg" + i + " :: " + typeParser.getArguments().get(i)
					+ "\n");
			addToPoint(i + 2, typeParser.getArguments().get(i));

		}
		// return value
		builder.append("Return Value :: " + this.typeParser.getReturnType()
				+ " >>> " + "\n");
		addFromPoint(new DataFromPointDataProvider() {

			@Override
			public Object provideInformationToDataFromPoint(
					DataFromPoint dataFromPoint) {
				String expr = displayingExpr;
				for (DataToPoint toPoint : getToPoints()) {
					try {
						// TODO condsider the validity of to string
						// treating every argument as a string
						String arg = toPoint.fetchData().toString();
						// if string contains space, wrap in parens (pattern
						// match against a string not followed by a right
						// parenthesis
						if (arg.split(" (?![^(]*\\))").length != 1) {
							arg = "(" + arg + ")";
						}

						expr += " " + arg;

					} catch (NoSuchElementException
							| NoConnectingLineSegmentException
							| DataFromPointNoDataProviderException
							| DataFromPointProviderCannotProvideDataException e) {
						e.printStackTrace();
						// break on the first occurance, to allow for currying
						break;
					}

				}
				return expr;
			}

			@Override
			public boolean canProvideInformationToDataFromPoint(
					DataFromPoint dataFromPoint) {
				return true;
			}
		}, 2 + typeParser.getArguments().size(),
				this.typeParser.getReturnType());
		setDisplayingText(builder.toString());

	}

}
