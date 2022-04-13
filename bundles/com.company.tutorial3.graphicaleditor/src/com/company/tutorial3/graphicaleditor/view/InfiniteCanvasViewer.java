package com.company.tutorial3.graphicaleditor.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.gef.common.activate.ActivatableSupport;
import org.eclipse.gef.common.activate.IActivatable;
import org.eclipse.gef.common.adapt.AdaptableSupport;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.InjectAdapters;
import org.eclipse.gef.common.beans.property.ReadOnlyListWrapperEx;
import org.eclipse.gef.common.beans.property.ReadOnlyMapWrapperEx;
import org.eclipse.gef.common.collections.CollectionUtils;
import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.handlers.CursorSupport;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.parts.ScenarioPart;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;
import com.company.tutorial3.graphicaleditor.policies.ViewportPolicy;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel.Type;
import com.google.common.reflect.TypeToken;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

public class InfiniteCanvasViewer implements IViewer {

	private CustomInfiniteCanvas infiniteCanvas;
	private boolean isInitialized = false;
	private boolean isWindowFocused = false;
	private boolean isFocusOwnerFocused = false;
	private ReadOnlyBooleanWrapper viewerFocusedProperty = new ReadOnlyBooleanWrapper(false);
	private ObservableList<Object> contents = CollectionUtils.observableArrayList();
	private ReadOnlyListWrapper<Object> contentsProperty = new ReadOnlyListWrapperEx<>(this, CONTENTS_PROPERTY, contents);
	private List<Consumer<Object>> objectClickListeners = new ArrayList<>();
	private List<Consumer<List<Object>>> objectsSelectionListeners = new ArrayList<>();
	private List<BiConsumer<Double, Double>> mouseMoveListeners = new ArrayList<>();
	
	private Runnable doAfterRemoveObject;
	
	public void escKeyTyped() {
		getAdapter(ItemCreationModel.class).setType(Type.NONE, this);
		deselectAll();
	}

	protected ViewportPolicy getViewportPolicy() {
		return getAdapter(ViewportPolicy.class);
	}
	
	public CustomInfiniteCanvas getInfiniteCanvas() {
		return infiniteCanvas;
	}

	public void setDoAfterRemoveObject(Runnable doAfterRemoveObject) {
		this.doAfterRemoveObject = doAfterRemoveObject;
	}

	public void deleteObject() {
		if(doAfterRemoveObject != null) {
			doAfterRemoveObject.run();
		}
	}
	
	public void setWaitingForSelection(Predicate<Object> filter) {
		ItemCreationModel creationModel = getAdapter(ItemCreationModel.class);
		if (filter != null) {
			creationModel.setSelectionType(filter, this);
		} else if (isWaitingForSelection()) {
			creationModel.setType(Type.NONE, this);
		}
	}
	public boolean isWaitingForSelection() {
		ItemCreationModel creationModel = getAdapter(ItemCreationModel.class);
		return creationModel.getType() == Type.SELECTION;
	}
	
	private boolean  showMap = false;
	public void setShowMap(boolean showMap) {
		this.showMap = showMap;
		if (infiniteCanvas != null) {
			infiniteCanvas.setShowMap(showMap);
		}
	}
	
	public void deselectAll() {
		SelectionModel selectionModel = getAdapter(SelectionModel.class);
		selectionModel.clearSelection();
	}
	
	public void adjustWindow() {
		List<Node> visuals = new ArrayList<>();
		for (IContentPart<?> contentPart : getContentPartMap().values()) {
			visuals.add(contentPart.getVisual());
		}
		infiniteCanvas.navigateTo(this, visuals, true);
	}
	
	public void navigateTo(Object object, boolean calculateZoom) {
		navigateTo(object instanceof List ? (List<Object>)object : Arrays.asList(new Object[] {object}), calculateZoom);
	}
	
	public void navigateTo(List<Object> objects, boolean calculateZoom) {
		navigateTo(objects, true, calculateZoom);
	}
	
	public void navigateTo(List<Object> objects, boolean select, boolean calculateZoom) {
		SelectionModel selectionModel = getAdapter(SelectionModel.class);
		selectionModel.clearSelection();
		List<IContentPart<?>> contentParts = new ArrayList<>();
		List<Node> visuals = new ArrayList<>();
		for (Object object : objects) {
			IContentPart<?> contentPart = getContentPartMap().get(object);
			if (contentPart != null) {
				contentParts.add(contentPart);
				visuals.add(contentPart.getVisual());
			}
		}
		if (!contentParts.isEmpty()) {
			if (select) {
				selectionModel.setSelection(contentParts);
			}
			infiniteCanvas.navigateTo(this, visuals, calculateZoom);
		}
	}
	
	protected CursorSupport getCursorSupport() {
		return getAdapter(CursorSupport.class);
	}
	
	public void addObjectClickListener(Consumer<Object> listener) {
		objectClickListeners.add(listener);
	}
	
	public void removeObjectClickListener(Consumer<Object> listener) {
		objectClickListeners.remove(listener);
	}
	
	public void addObjectsSelectionListener(Consumer<List<Object>> listener) {
		objectsSelectionListeners.add(listener);
	}
	
	public void removeObjectsSelectionListener(Consumer<List<Object>> listener) {
		objectsSelectionListeners.remove(listener);
	}
	
	public void addMouseMoveListener(BiConsumer<Double, Double> listener) {
		mouseMoveListeners.add(listener);
	}
	
	public void removeMouseMoveListener(BiConsumer<Double, Double> listener) {
		mouseMoveListeners.remove(listener);
	}

	private BooleanBinding viewerFocusedPropertyBinding = new BooleanBinding() {
		@Override
		protected boolean computeValue() {
			return isWindowFocused && isFocusOwnerFocused;
		}
	};

	private ChangeListener<Window> windowObserver = new ChangeListener<Window>() {
		@Override
		public void changed(ObservableValue<? extends Window> observable, Window oldValue, Window newValue) {
			onWindowChanged(oldValue, newValue);
		}
	};

	private ChangeListener<Boolean> windowFocusedObserver = new ChangeListener<Boolean>() {
		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			onWindowFocusedChanged(oldValue, newValue);
		}
	};

	private ChangeListener<Node> focusOwnerObserver = new ChangeListener<Node>() {
		@Override
		public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
			if (oldValue != newValue) {
				onFocusOwnerChanged(oldValue, newValue);
			}
		}
	};
	
	public void setZoom(double zoom) {
		ViewportPolicy viewportPolicy = getRootPart().getAdapter(ViewportPolicy.class);
		viewportPolicy.init();
		viewportPolicy.zoomAbsolute(zoom);
		viewportPolicy.commit();
	}

	private ChangeListener<Boolean> focusOwnerFocusedObserver = new ChangeListener<Boolean>() {
		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			// XXX: If a new focusOwner is set and the old focusOwner was
			// focused, it will fire a "focused" change from true to false. Such
			// events are disregarded, so that the viewer does not lose its
			// focus if it will gain it immediately afterwards (which is handled
			// within #focusOwnerChanged()).
			if (observable == getCanvas().getScene().getFocusOwner()) {
				if (oldValue == null ? newValue != null : !oldValue.equals(newValue)) {
					onFocusOwnerFocusedChanged(oldValue, newValue);
				}
			}
		}
	};

	private ChangeListener<Scene> sceneListener = new ChangeListener<Scene>() {
		@Override
		public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
			onSceneChanged(oldValue, newValue);
		}
	};

	private ActivatableSupport acs = new ActivatableSupport(this);

	private AdaptableSupport<IViewer> ads = new AdaptableSupport<>(this);

	// XXX: Use HashMap for contentPartMap so that equals() is used for
	// containment tests, which is also used when working with lists. The
	// implementation needs to match the implementation that is used within
	// ContentBehavior.
	private ObservableMap<Object, IContentPart<? extends Node>> contentPartMap = FXCollections.observableMap(new HashMap<>());
	private ReadOnlyMapProperty<Object, IContentPart<? extends Node>> contentPartMapProperty;
	private ObservableMap<Node, IVisualPart<? extends Node>> visualPartMap = FXCollections.observableMap(new IdentityHashMap<>());
	private ReadOnlyMapProperty<Node, IVisualPart<? extends Node>> visualPartMapProperty;

	private ReadOnlyObjectWrapper<IDomain> domainProperty = new ReadOnlyObjectWrapper<>();

	/**
	 * Creates a new {@link InfiniteCanvasViewer}.
	 */
	public InfiniteCanvasViewer() {
		super();
		// add binding to viewer focused property to have its value computed
		// based on the values of:
		// - window focused
		// - focusOwner
		// - focusOwner focused
		viewerFocusedProperty.bind(viewerFocusedPropertyBinding);
	}

	@Override
	public final void activate() {
		acs.activate(null, this::doActivate);
	}

	/**
	 * Activates the adapters registered at this {@link InfiniteCanvasViewer}.
	 */
	protected void activateAdapters() {
		// XXX: We keep a sorted map of adapters so activation
		// is performed in a deterministic order
		new TreeMap<>(ads.getAdapters()).values().forEach((adapter) -> {
			if (adapter instanceof IActivatable) {
				((IActivatable) adapter).activate();
			}
		});
	}

	@Override
	public final ReadOnlyBooleanProperty activeProperty() {
		return acs.activeProperty();
	}

	@Override
	public ReadOnlyObjectProperty<IDomain> adaptableProperty() {
		return domainProperty.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyMapProperty<AdapterKey<?>, Object> adaptersProperty() {
		return ads.adaptersProperty();
	}

	@Override
	public ReadOnlyMapProperty<Object, IContentPart<? extends Node>> contentPartMapProperty() {
		if (contentPartMapProperty == null) {
			contentPartMapProperty = new ReadOnlyMapWrapperEx<>(this, CONTENT_PART_MAP_PROPERTY, contentPartMap);
		}
		return contentPartMapProperty;
	}

	@Override
	public ReadOnlyListProperty<Object> contentsProperty() {
		return contentsProperty.getReadOnlyProperty();
	}

	@Override
	public final void deactivate() {
		acs.deactivate(this::doDeactivate, null);
	}

	/**
	 * Deactivates the adapters registered at this {@link InfiniteCanvasViewer}.
	 */
	protected void deactivateAdapters() {
		// XXX: We keep a sorted map of adapters so deactivation
		// is performed in a deterministic order
		new TreeMap<>(ads.getAdapters()).values().forEach((adapter) -> {
			if (adapter instanceof IActivatable) {
				((IActivatable) adapter).deactivate();
			}
		});
	}

	@Override
	public void dispose() {
		// ensure all listeners are properly unregistered
		if (infiniteCanvas != null) {
			if (infiniteCanvas.getScene() != null) {
				onSceneChanged(infiniteCanvas.getScene(), null);
			}
			infiniteCanvas.sceneProperty().removeListener(sceneListener);
			infiniteCanvas = null;
		}

		// unbind viewer focused property
		viewerFocusedProperty.unbind();
		viewerFocusedProperty = null;

		// dispose adapters (including root part and models)
		ads.dispose();
		ads = null;

		// clear content part map
		if (!contentPartMap.isEmpty()) {
			throw new IllegalStateException("Content part map was not properly cleared!");
		}
		contentPartMap = null;

		// clear visual part map
		if (!visualPartMap.isEmpty()) {
			throw new IllegalStateException("Visual part map was not properly cleared!");
		}
		visualPartMap = null;

		// unset activatable support
		acs = null;
	}

	protected void doActivate() {
		if (getDomain() == null) {
			throw new IllegalStateException("Domain has to be set before activation.");
		}
		if (getRootPart() == null) {
			throw new IllegalStateException("RootPart has to be set before activation.");
		}
		if (infiniteCanvas == null || infiniteCanvas.getScene() == null) {
			throw new IllegalStateException("Viewer controls have to be hooked (to scene) before activation.");
		}
		activateAdapters();
	}

	protected void doDeactivate() {
		deactivateAdapters();
	}

	@Override
	public IDomain getAdaptable() {
		return domainProperty.get();
	}

	@Override
	public <T> T getAdapter(AdapterKey<T> key) {
		return ads.getAdapter(key);
	}

	@Override
	public <T> T getAdapter(Class<T> classKey) {
		return ads.getAdapter(classKey);
	}

	@Override
	public <T> T getAdapter(TypeToken<T> key) {
		return ads.getAdapter(key);
	}

	@Override
	public <T> AdapterKey<T> getAdapterKey(T adapter) {
		return ads.getAdapterKey(adapter);
	}

	@Override
	public ObservableMap<AdapterKey<?>, Object> getAdapters() {
		return ads.getAdapters();
	}

	@Override
	public <T> Map<AdapterKey<? extends T>, T> getAdapters(Class<? super T> classKey) {
		return ads.getAdapters(classKey);
	}

	@Override
	public <T> Map<AdapterKey<? extends T>, T> getAdapters(TypeToken<? super T> key) {
		return ads.getAdapters(key);
	}

	@Override
	public CustomInfiniteCanvas getCanvas() {
		if (infiniteCanvas == null) {
			infiniteCanvas = new CustomInfiniteCanvas(showMap);
			infiniteCanvas.sceneProperty().addListener(sceneListener);
			infiniteCanvas.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					IVisualPart<? extends javafx.scene.Node> part = getRootPart().getChildrenUnmodifiable().get(0);
					if (part instanceof ScenarioPart) {
						Point2D mouseInLocal = part.getVisual().sceneToLocal(arg0.getSceneX(), arg0.getSceneY());
						for (BiConsumer<Double, Double> listener : mouseMoveListeners) {
							listener.accept(mouseInLocal.getX(), mouseInLocal.getY());
						}
					}
				}
			});
			infiniteCanvas.getContentGroup().getChildren().addAll((Parent)getRootPart().getVisual());
		}
		return infiniteCanvas;
	}
	
	public void onObjectClick(Object selectedObject) {
		for (Consumer<Object> listener : objectClickListeners) {
			listener.accept(selectedObject);
		}
		setWaitingForSelection(null);
	}
	
	public void onObjectsSelection(List<Object> selectedObjects) {
		List<Object> contents = selectedObjects.stream().map( f -> ((IContentPart<?>)f).getContent()).collect(Collectors.toList());
		for (Consumer<List<Object>> listener : objectsSelectionListeners) {
			listener.accept(contents);
		}
	}

	/**
	 * @see IViewer#getContentPartMap()
	 */
	@Override
	public Map<Object, IContentPart<? extends Node>> getContentPartMap() {
		return contentPartMap;
	}

	@Override
	public ObservableList<Object> getContents() {
		return contents;
	}

	@Override
	public IDomain getDomain() {
		return domainProperty.get();
	}

	@SuppressWarnings("serial")
	@Override
	public IRootPart<? extends Node> getRootPart() {
		return ads.getAdapter(new TypeToken<IRootPart<? extends Node>>() {});
	}

	/**
	 * Returns the {@link Scene} in which the {@link InfiniteCanvas} of this
	 * {@link InfiniteCanvasViewer} is displayed.
	 *
	 * @return The {@link Scene} in which the {@link InfiniteCanvas} of this
	 *         {@link InfiniteCanvasViewer} is displayed.
	 */
	public Scene getScene() {
		return getCanvas().getScene();
	}

	/**
	 * @see IViewer#getVisualPartMap()
	 */
	@Override
	public Map<Node, IVisualPart<? extends Node>> getVisualPartMap() {
		return visualPartMap;
	}

	@Override
	public final boolean isActive() {
		return acs.isActive();
	}

	@Override
	public boolean isViewerFocused() {
		return viewerFocusedProperty.get();
	}

	private void onFocusOwnerChanged(Node oldFocusOwner, Node newFocusOwner) {
		if (oldFocusOwner != null && NodeUtils.isNested(getCanvas(), oldFocusOwner)) {
			oldFocusOwner.focusedProperty().removeListener(focusOwnerFocusedObserver);
		}
		if (newFocusOwner != null && NodeUtils.isNested(getCanvas(), newFocusOwner)) {
			newFocusOwner.focusedProperty().addListener(focusOwnerFocusedObserver);
			// check if viewer is focused
			if (Boolean.TRUE.equals(newFocusOwner.focusedProperty().get())) {
				isFocusOwnerFocused = true;
				viewerFocusedPropertyBinding.invalidate();
			}
		} else {
			// viewer unfocused
			isFocusOwnerFocused = false;
			viewerFocusedPropertyBinding.invalidate();
		}
	}

	private void onFocusOwnerFocusedChanged(Boolean oldValue, Boolean newValue) {
		isFocusOwnerFocused = Boolean.TRUE.equals(newValue);
		viewerFocusedPropertyBinding.invalidate();
	}

	private void onSceneChanged(Scene oldScene, Scene newScene) {
		Window oldWindow = null;
		Window newWindow = null;
		Node oldFocusOwner = null;
		Node newFocusOwner = null;
		if (oldScene != null) {
			oldWindow = oldScene.windowProperty().get();
			oldScene.windowProperty().removeListener(windowObserver);
			oldFocusOwner = oldScene.focusOwnerProperty().get();
			oldScene.focusOwnerProperty().removeListener(focusOwnerObserver);
		}
		if (newScene != null) {
			newWindow = newScene.windowProperty().get();
			newScene.windowProperty().addListener(windowObserver);
			newFocusOwner = newScene.focusOwnerProperty().get();
			newScene.focusOwnerProperty().addListener(focusOwnerObserver);
		}
		onWindowChanged(oldWindow, newWindow);
		onFocusOwnerChanged(oldFocusOwner, newFocusOwner);
	}

	private void onWindowChanged(Window oldValue, Window newValue) {
		if (oldValue != null) {
			oldValue.focusedProperty().removeListener(windowFocusedObserver);
		}
		if (newValue != null) {
			newValue.focusedProperty().addListener(windowFocusedObserver);
			// check if window is focused
			if (Boolean.TRUE.equals(newValue.focusedProperty().get())) {
				isWindowFocused = true;
				viewerFocusedPropertyBinding.invalidate();
			}
		} else {
			// window unfocused
			isInitialized = false;
			isWindowFocused = false;
			viewerFocusedPropertyBinding.invalidate();
		}
	}

	private void onWindowFocusedChanged(Boolean oldValue, Boolean newValue) {
		isWindowFocused = Boolean.TRUE.equals(newValue);
		viewerFocusedPropertyBinding.invalidate();
		if (!isInitialized) {
			// XXX: When the embedded scene is opened, the viewer needs to
			// request focus for the root visual once so that a focus owner is
			// set. This could also possibly be done in the FocusBehavior, but
			// keeping it here we can limit 'knowledge' about the embedded
			// window.
			getRootPart().getVisual().requestFocus();
			isInitialized = true;
		}
	}

	@Override
	public void reveal(IVisualPart<? extends Node> visualPart) {
		if (visualPart == null) {
			getCanvas().setHorizontalScrollOffset(0);
			getCanvas().setVerticalScrollOffset(0);
		} else {
			getCanvas().reveal(visualPart.getVisual());
		}
	}

	@Override
	public void setAdaptable(IDomain domain) {
		domainProperty.set(domain);
	}

	@Override
	public <T> void setAdapter(T adapter) {
		ads.setAdapter(adapter);
	}

	@Override
	public <T> void setAdapter(T adapter, String role) {
		ads.setAdapter(adapter, role);
	}

	@Override
	public <T> void setAdapter(TypeToken<T> adapterType, T adapter) {
		ads.setAdapter(adapterType, adapter);
	}

	@InjectAdapters
	@Override
	public <T> void setAdapter(TypeToken<T> adapterType, T adapter, String role) {
		ads.setAdapter(adapterType, adapter, role);
	}

	@Override
	public <T> void unsetAdapter(T adapter) {
		ads.unsetAdapter(adapter);
	}

	@Override
	public ReadOnlyBooleanProperty viewerFocusedProperty() {
		return viewerFocusedProperty.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyMapProperty<Node, IVisualPart<? extends Node>> visualPartMapProperty() {
		if (visualPartMapProperty == null) {
			visualPartMapProperty = new ReadOnlyMapWrapperEx<>(this, VISUAL_PART_MAP_PROPERTY, visualPartMap);
		}
		return visualPartMapProperty;
	}

}


