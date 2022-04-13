package com.company.tutorial3.graphicaleditor.visuals;

import com.company.tutorial3.graphicaleditor.model.PaletteNode.PaletteNodeType;
import com.company.tutorial3.graphicaleditor.parts.PaletteNodePart;
import com.company.tutorial3.graphicaleditor.view.Storage;

import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class PaletteNodeVisual extends Region {
	
	private PaletteNodePart paletteNodePart;
	private Color borderColor = new Color(200 / 255.0, 200 / 255.0, 200 / 255.0, 1);
	private Border selectedBorder = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, new BorderWidths(2), Insets.EMPTY));
	private Border defaultBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY));

	public PaletteNodeVisual(PaletteNodePart paletteNodePart) {
		this.paletteNodePart = paletteNodePart;
		
		PaletteNodeType paletteNodeType = paletteNodePart.getContent().getPaletteNodeType();
		if (paletteNodeType == PaletteNodeType.ARC) {
			getChildren().add(createImageView("icons/road.png", 30));
			Tooltip.install(this, new Tooltip(Storage.messages.obj_ARC));
		}else if (paletteNodeType == PaletteNodeType.NODE) {
			getChildren().add(createImageView("icons/node.png", 30));
			Tooltip.install(this, new Tooltip(Storage.messages.obj_NODE));
		}
	}
	
	public void setDrawAsSelected(boolean drawAsSelected) {
		setBorder(drawAsSelected ? selectedBorder : defaultBorder);
	}
	
	protected ImageView createImageView(String path, double fitWidth) {
		ImageView iv2 = new ImageView();
        iv2.setImage(new Image(path));
        iv2.setFitWidth(fitWidth);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);
        return iv2;
	}
	
	public PaletteNodePart getPaletteNodePart() {
		return paletteNodePart;
	}
}


