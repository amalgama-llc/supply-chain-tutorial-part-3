package com.company.tutorial3.graphicaleditor.view;

import java.util.Arrays;
import java.util.List;

import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.nebula.widgets.geomap.GeoMapUtil;
import org.eclipse.nebula.widgets.geomap.PointD;
import org.eclipse.nebula.widgets.geomap.TileServer;
import org.eclipse.nebula.widgets.geomap.internal.GeoMapHelper;
import org.eclipse.nebula.widgets.geomap.internal.GeoMapHelperListener;
import org.eclipse.nebula.widgets.geomap.internal.TileRef;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.amalgamasimulation.animation.swt.TiledMapCoordinatesConverter;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.utils.Utils;

import javafx.beans.value.ChangeListener;
import javafx.embed.swt.SWTFXUtils;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class CustomInfiniteCanvas extends InfiniteCanvas {
	
	private ImageView imageView;
	private boolean showMap = false;
	private double currentZoom = 1;
	private PointD lonLatPointD = new PointD(-14.0, 26.0);
	private int baseMapZoom = 15;
	protected GeoMapHelper geoMapHelper;
	protected TiledMapCoordinatesConverter tiledMapCoordinatesConverter;
	protected org.eclipse.swt.graphics.Image mapImage;
	protected double currentScale = 1.0;
	protected double currentX = 0;
	protected double currentY = 0;
	protected Point lastMapPosition = new Point( Double.NaN, Double.NaN );
	protected Point lastMapSize = new Point( 0, 0 );
	protected int lastMapZoom = -1;
	private boolean geoMapInitialized = false;
	private GeoMapHelperListener geoMapHelperListener = this::tileUpdated;
	
	public com.amalgamasimulation.geometry.Point logicToLonLat(double x, double y){
		return tiledMapCoordinatesConverter.logicalPointToLonLat(new com.amalgamasimulation.geometry.Point(x , y));
	}
	
	public com.amalgamasimulation.geometry.Point lonLatToLogic(double x, double y){
		return tiledMapCoordinatesConverter.lonLatToLogicalPoint(new com.amalgamasimulation.geometry.Point(x , y));
	}
	
	public CustomInfiniteCanvas(boolean showMap) {
		super();
		this.showMap = showMap;
		initializeGeoMapHelper();
		horizontalScrollOffsetProperty().addListener(new ChangeListener<Number>() {
			public void changed(javafx.beans.value.ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				onCurrentViewChanged(newValue.doubleValue(), verticalScrollOffsetProperty().get());
			};
		});
		verticalScrollOffsetProperty().addListener(new ChangeListener<Number>() {
			public void changed(javafx.beans.value.ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				onCurrentViewChanged(horizontalScrollOffsetProperty().get(), newValue.doubleValue());
			};
		});
	}
	
	public void initializeBaseMapPoint(double latitude, double longitude, int baseMapZoom) {
		this.lonLatPointD = new PointD(latitude, longitude);
		this.baseMapZoom = baseMapZoom;
		initializeGeoMapHelper();
	}
	
	public boolean isShowMap() {
		return showMap;
	}

	public void setShowMap(boolean showMap) {
		this.showMap = showMap;
		onCurrentViewChanged(horizontalScrollOffsetProperty().get(), verticalScrollOffsetProperty().get());
	}
	
	public double getCurrentZoom() {
		return currentZoom;
	}

	public void setCurrentZoom(double currentZoom) {
		this.currentZoom = currentZoom;
	}

	@Override
	protected void repaintGrid() {
		if (imageView != null) {
			getChildren().remove(imageView);
		}
		if (showMap) {
			org.eclipse.swt.graphics.Image mapImage = getMapImage();
			ImageData data = mapImage.getImageData();
			WritableImage tile2 = new WritableImage(mapImage.getBounds().width, mapImage.getBounds().height);
			SWTFXUtils.toFXImage(data, tile2);
			imageView = new ImageView(tile2);
			imageView.setFitWidth(mapImage.getBounds().width);
			imageView.setFitHeight(mapImage.getBounds().height);
			getChildren().add(0, imageView);
		}
		super.repaintGrid();
	}
	
	private void tileUpdated( TileRef tileRef ) {
		mapImage = null;
		repaintGrid();
	}
	
	public void onCurrentScaleChanged(InfiniteCanvasViewer viewer, double currentScale) {
		onCurrentScaleChanged(viewer, currentScale, 0, 0);
	}
	
	public void onCurrentScaleChanged(InfiniteCanvasViewer viewer, double currentScale, double sceneX, double sceneY) {
		if (this.currentScale != currentScale) {
			if (this.currentScale > currentScale) {
				currentX = currentX + sceneX / this.currentScale;
				currentY = currentY + sceneY / this.currentScale;
			} else {
				currentX = currentX - sceneX / currentScale;
				currentY = currentY - sceneY / currentScale;
			}
			this.currentScale = currentScale;
		}
		mapImage = null;
		if (showMap || imageView != null) {
			repaintGrid();
		}
	}

	private double oldX = Double.NaN;
	private double oldY = Double.NaN;
	
	public void onCurrentViewChanged(double x, double y) {
		if (Double.isNaN(oldX)) {
			currentX = x;
			currentY = y;
			oldX = x;
			oldY = y;
		}
		currentX += (x - oldX) / currentScale;
		currentY += (y - oldY) / currentScale;
		oldX = x;
		oldY = y;
		mapImage = null;
		if (showMap || imageView != null) {
			repaintGrid();	
		}
	}
	
	private void initializeGeoMapHelper() {
		org.eclipse.swt.graphics.Point mapPosition = GeoMapUtil.computePosition(lonLatPointD, baseMapZoom);	
		tiledMapCoordinatesConverter = new TiledMapCoordinatesConverter(new Point(mapPosition.x, mapPosition.y), baseMapZoom);
	}
	
	private org.eclipse.swt.graphics.Image getMapImage() {
		if (!geoMapInitialized) {
			org.eclipse.swt.graphics.Point mapPosition = GeoMapUtil.computePosition(lonLatPointD, baseMapZoom);	
			Point baseMapPoint = new Point(mapPosition.x, mapPosition.y); 
			tiledMapCoordinatesConverter = new TiledMapCoordinatesConverter(baseMapPoint, baseMapZoom);
			geoMapHelper = new GeoMapHelper(Display.getCurrent(), new org.eclipse.swt.graphics.Point((int)baseMapPoint.getX(), (int)baseMapPoint.getY()), baseMapZoom, 1000);
			geoMapHelper.setTileServer(new TileServer("http://a.tile.openstreetmap.org/", 18));
			geoMapHelper.addGeoMapHelperListener(geoMapHelperListener);
			geoMapInitialized = true;
		}
		int mapZoom = tiledMapCoordinatesConverter.getMapZoom( currentScale );
		Point mapPosition = tiledMapCoordinatesConverter.logicalPointToMapPosition( new Point( -currentX, -currentY ), currentScale );
		Point mapSize = new Point( Math.max( 1, getWidth() ),  Math.max( 1, getHeight() ) );
		if( 	mapImage == null
				|| lastMapZoom != mapZoom
				|| !lastMapPosition.equals( mapPosition )
				|| mapSize.getX() > lastMapSize.getX()
				|| mapSize.getY() > lastMapSize.getY() ) {
			if( mapImage != null ) {
				mapImage.dispose();
			}
			mapImage = new org.eclipse.swt.graphics.Image( Display.getCurrent(), new Rectangle( 0, 0, (int)mapSize.getX(), (int)mapSize.getY() ) );
			//mapImage = new Image( Display.getCurrent(), getBounds() );
			GC mapGC = new GC( mapImage );
			geoMapHelper.setMapPosition( (int)mapPosition.getX(), (int)mapPosition.getY() );
			geoMapHelper.setZoom( mapZoom );
			geoMapHelper.paint( mapGC, null, new org.eclipse.swt.graphics.Point( (int)mapSize.getX(), (int)mapSize.getY() ) );
			lastMapZoom = mapZoom;
			lastMapPosition = mapPosition;
			lastMapSize = mapSize;
			mapGC.dispose();
		}
		return mapImage;
	}
	
	public void navigateTo(InfiniteCanvasViewer viewer, Node child, boolean calculateZoom) {
		navigateTo(viewer, Arrays.asList(new Node[]{child}), calculateZoom);
	}
	
	public void navigateTo(InfiniteCanvasViewer viewer, List<Node> childs, boolean calculateZoom) {
		if (!childs.isEmpty()) {
			if(calculateZoom) {
				viewer.setZoom(calculateZoom(viewer, childs));
			}
			double minX = Double.POSITIVE_INFINITY;
			double maxX = Double.NEGATIVE_INFINITY;
			double minY = Double.POSITIVE_INFINITY;
			double maxY = Double.NEGATIVE_INFINITY;
			for (Node node : childs) {
				Bounds bounds = node.localToScene(node.getBoundsInLocal());
				minX = Math.min(minX, bounds.getMinX());
				maxX = Math.max(maxX, bounds.getMaxX());
				minY = Math.min(minY, bounds.getMinY());
				maxY = Math.max(maxY, bounds.getMaxY());
			}
			double height = maxY - minY;
			double width = maxX - minX;
			setVerticalScrollOffset(getVerticalScrollOffset() - minY + (getHeight() - height) / 2);
			setHorizontalScrollOffset(getHorizontalScrollOffset() - minX + (getWidth() - width) / 2);
		}
	}
	
	private double calculateZoom(InfiniteCanvasViewer viewer, List<Node> childs) {
		if (childs.isEmpty()) {
			return 1;
		}
		double minX = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		for (Node node : childs) {
			Bounds bounds = node.localToScene(node.getBoundsInLocal());
			minX = Math.min(minX, bounds.getMinX());
			maxX = Math.max(maxX, bounds.getMaxX());
			minY = Math.min(minY, bounds.getMinY());
			maxY = Math.max(maxY, bounds.getMaxY());
		}
		double[] validZooms = new double[] {0.007812, 0.015625, 0.03125, 0.0625, 0.125, 0.25, 0.5, 1, 2};
		double targetZoom = Math.min(Utils.zidz(getHeight() * currentZoom, maxY - minY), Utils.zidz(getWidth() * currentZoom, maxX - minX)) / 2;
		for (double validZoom : validZooms) {
			if (validZoom >= targetZoom || validZoom == validZooms[validZooms.length - 1]) {
				targetZoom = validZoom;
				break;
			}
		}
		return targetZoom;
	}
}


