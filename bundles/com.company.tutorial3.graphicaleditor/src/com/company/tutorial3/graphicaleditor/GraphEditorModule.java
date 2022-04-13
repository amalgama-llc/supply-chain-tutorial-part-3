package com.company.tutorial3.graphicaleditor;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.mvc.fx.MvcFxModule;
import org.eclipse.gef.mvc.fx.behaviors.ContentBehavior;
import org.eclipse.gef.mvc.fx.behaviors.ContentPartPool;
import org.eclipse.gef.mvc.fx.behaviors.HoverBehavior;
import org.eclipse.gef.mvc.fx.behaviors.SelectionBehavior;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.models.HoverModel;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.DefaultHoverFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.policies.TransformPolicy;
import org.eclipse.gef.mvc.fx.providers.DefaultAnchorProvider;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.behaviors.ArcClickableAreaBehavior;
import com.company.tutorial3.graphicaleditor.behaviors.ArcClickableOutlineProvider;
import com.company.tutorial3.graphicaleditor.behaviors.ClickDragGesture;
import com.company.tutorial3.graphicaleditor.behaviors.GraphicalEditorSelectionModel;
import com.company.tutorial3.graphicaleditor.behaviors.GridBehavior;
import com.company.tutorial3.graphicaleditor.behaviors.GridModel;
import com.company.tutorial3.graphicaleditor.behaviors.NodeClickableAreaBehavior;
import com.company.tutorial3.graphicaleditor.behaviors.SelectionBoundsProvider;
import com.company.tutorial3.graphicaleditor.behaviors.SelectionHandlePartFactory;
import com.company.tutorial3.graphicaleditor.handlers.BendFirstAnchorageOnArcHandleDragHandler;
import com.company.tutorial3.graphicaleditor.handlers.BendPointDoubleClickHandler;
import com.company.tutorial3.graphicaleditor.handlers.CreateNewArcByClickHandler;
import com.company.tutorial3.graphicaleditor.handlers.CreateNewElementByClickHandler;
import com.company.tutorial3.graphicaleditor.handlers.CreateObjectsFromPaletteHandler;
import com.company.tutorial3.graphicaleditor.handlers.DeleteSelectedOnTypeHandler;
import com.company.tutorial3.graphicaleditor.handlers.FocusAndSelectOnClickHandler;
import com.company.tutorial3.graphicaleditor.handlers.HoverOnHoverHandler;
import com.company.tutorial3.graphicaleditor.handlers.KeyTypeHandler;
import com.company.tutorial3.graphicaleditor.handlers.MarqueeOnDragHandler;
import com.company.tutorial3.graphicaleditor.handlers.PanHandler;
import com.company.tutorial3.graphicaleditor.handlers.RelocateOnDragHandler;
import com.company.tutorial3.graphicaleditor.handlers.ZoomOnScrollHandler;
import com.company.tutorial3.graphicaleditor.parts.ArcPart;
import com.company.tutorial3.graphicaleditor.parts.BendpointPart;
import com.company.tutorial3.graphicaleditor.parts.GraphEditorContentPartFactory;
import com.company.tutorial3.graphicaleditor.parts.HoverFeedbackPart;
import com.company.tutorial3.graphicaleditor.parts.NodePart;
import com.company.tutorial3.graphicaleditor.parts.PaletteNodePart;
import com.company.tutorial3.graphicaleditor.parts.SelectionFeedbackPart;
import com.company.tutorial3.graphicaleditor.policies.BendPointArcPolicy;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;
import com.company.tutorial3.graphicaleditor.policies.ViewportPolicy;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;
import com.company.tutorial3.graphicaleditor.view.PaletteRootPart;
import com.google.inject.multibindings.MapBinder;

public class GraphEditorModule extends MvcFxModule {

	public static final String PALETTE_VIEWER_ROLE = "paletteViewer";
	
	@Override
	protected void bindIViewer() {
		binder().bind(IViewer.class).to(InfiniteCanvasViewer.class);
	}
	
	@Override
	protected void bindSelectionModelAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GraphicalEditorSelectionModel.class).in(AdaptableScopes.typed(IViewer.class));
	}

	@Override
	protected void bindPanOrZoomOnScrollHandlerAsIRootPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role("panOnScroll")).to(ZoomOnScrollHandler.class);
	}
	
	@Override
	protected void bindIContentPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GraphEditorContentPartFactory.class);
	}
	
	@Override
	protected void bindContentBehaviorAsIRootPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ContentBehavior.class);
	}
	
	@Override
	protected void bindChangeViewportPolicyAsIRootPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ViewportPolicy.class);
	}
	
	@Override
	protected void bindFocusAndSelectOnClickHandlerAsIRootPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FocusAndSelectOnClickHandler.class);
	}
	
	@Override
	protected void bindMarqueeOnDragHandlerAsIRootPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role("0")).to(MarqueeOnDragHandler.class);
	}
	
	@Override
	protected void bindIViewerAdaptersForContentViewer(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindIViewerAdaptersForContentViewer(adapterMapBinder);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ItemCreationModel.class);
	}
	
	@Override
	protected void bindGridBehaviorAsIRootPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GridBehavior.class);
	}

	@Override
	protected void bindGridModelAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GridModel.class);
	}
	
	@Override
	protected void bindClickDragGesture() {
		binder().bind(ClickDragGesture.class).in(AdaptableScopes.typed(IDomain.class));
	}
	
	@Override
	protected void bindClickDragGestureAsDomainAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ClickDragGesture.class);
	}
	
	@Override
	protected void bindIRootPartAdaptersForContentViewer(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindIRootPartAdaptersForContentViewer(adapterMapBinder);
		
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateNewElementByClickHandler.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(PanHandler.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(DeleteSelectedOnTypeHandler.class);
		adapterMapBinder.addBinding(AdapterKey.role(DefaultSelectionHandlePartFactory.MULTI_SELECTION_HANDLES_GEOMETRY_PROVIDER)).to(SelectionBoundsProvider.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(KeyTypeHandler.class);
	}
	
	@Override
	protected void bindAbstractContentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindAbstractContentPartAdapters(adapterMapBinder);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FocusAndSelectOnClickHandler.class);
	}

	@Override
	protected void bindIDomainAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindIDomainAdapters(adapterMapBinder);
		adapterMapBinder.addBinding(AdapterKey.role(PALETTE_VIEWER_ROLE)).to(IViewer.class);
	}
	
	@Override
	protected void bindSelectionHandlePartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role(SelectionBehavior.SELECTION_HANDLE_PART_FACTORY)).to(SelectionHandlePartFactory.class);
	}

	private void bindNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {	
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);
		adapterMapBinder.addBinding(AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER)).to(ShapeOutlineProvider.class);
		adapterMapBinder.addBinding(AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER)).to(ShapeOutlineProvider.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TransformPolicy.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateNewArcByClickHandler.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(RelocateOnDragHandler.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(DefaultAnchorProvider.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(NodeClickableAreaBehavior.class);
		
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateNewElementByClickHandler.class);
	}	
	
	private void bindArcPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {			
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);
		adapterMapBinder.addBinding(AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER)).to(ArcClickableOutlineProvider.class);
		adapterMapBinder.addBinding(AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER)).to(ArcClickableOutlineProvider.class);
		adapterMapBinder.addBinding(AdapterKey.role(DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER)).to(ShapeOutlineProvider.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(org.eclipse.gef.mvc.fx.policies.TransformPolicy.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ArcClickableAreaBehavior.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BendPointArcPolicy.class);
		
	}

	private void bindCircleSegmentHandlePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BendPointDoubleClickHandler.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BendFirstAnchorageOnArcHandleDragHandler.class);
	}
	
	@Override
	protected void configure() {
		super.configure();
		binder().bind(IContentPartFactory.class).to(GraphEditorContentPartFactory.class);
		
		binder().bind(org.eclipse.gef.mvc.fx.parts.HoverFeedbackPart.class).to(HoverFeedbackPart.class);
		binder().bind(org.eclipse.gef.mvc.fx.parts.SelectionFeedbackPart.class).to(SelectionFeedbackPart.class);		
		bindNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), NodePart.class, AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE)));	
	
		bindArcPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), ArcPart.class, AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE)));
				
		bindCircleSegmentHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), BendpointPart.class, AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE)));	
		bindPaletteViewerAdapters(AdapterMaps.getAdapterMapBinder(binder(), IViewer.class, AdapterKey.get(IViewer.class, PALETTE_VIEWER_ROLE)));
		bindPaletteRootPartAdaptersInPaletteViewerContext(AdapterMaps.getAdapterMapBinder(binder(), IRootPart.class, AdapterKey.get(IViewer.class, PALETTE_VIEWER_ROLE)));
		bindNodePartAdapterInPaletteViewerContext(AdapterMaps.getAdapterMapBinder(binder(), PaletteNodePart.class, AdapterKey.get(IViewer.class, PALETTE_VIEWER_ROLE)));
	}

	private void bindNodePartAdapterInPaletteViewerContext(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);
		adapterMapBinder.addBinding(AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER)).to(ShapeOutlineProvider.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateObjectsFromPaletteHandler.class);
	}

	private void bindPaletteRootPartAdaptersInPaletteViewerContext(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverBehavior.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ContentBehavior.class);
	}
	
	private void bindPaletteViewerAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {				
		adapterMapBinder.addBinding(AdapterKey.role(PALETTE_VIEWER_ROLE)).to(PaletteRootPart.class).in(AdaptableScopes.typed(IViewer.class));
		adapterMapBinder.addBinding(AdapterKey.role(HoverBehavior.HOVER_FEEDBACK_PART_FACTORY)).to(DefaultHoverFeedbackPartFactory.class);	
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(SelectionModel.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverModel.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ContentPartPool.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(IContentPartFactory.class);
		
	}
	@Override
	protected void bindPinchSpreadGesture() {
		// TODO Auto-generated method stub
		//super.bindPinchSpreadGesture();
	}
	
	@Override
	protected void bindPinchSpreadGestureAsIDomainAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// TODO Auto-generated method stub
		//super.bindPinchSpreadGestureAsIDomainAdapter(adapterMapBinder);
	}
	
	@Override
	protected void bindSnappingFeedbackPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// TODO Auto-generated method stub
		//super.bindSnappingFeedbackPartFactoryAsContentViewerAdapter(adapterMapBinder);
	}
	
	@Override
	protected void bindSnappingModelAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// TODO Auto-generated method stub
		//super.bindSnappingModelAsContentViewerAdapter(adapterMapBinder);
	}
	
	@Override
	protected void bindSnapToSupportAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// TODO Auto-generated method stub
		//super.bindSnapToSupportAsContentViewerAdapter(adapterMapBinder);
	}
	
	@Override
	protected void bindRotateGesture() {
		// TODO Auto-generated method stub
		//super.bindRotateGesture();
	}
	
	@Override
	protected void bindRotateGestureAsDomainAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// TODO Auto-generated method stub
		//super.bindRotateGestureAsDomainAdapter(adapterMapBinder);
	}

	@Override
	protected void bindHoverGesture() {
		// TODO Auto-generated method stub
		//binder().bind(HoverGesture.class);
	}
	
	@Override
	protected void bindSnappingBehaviorAsIRootPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// TODO Auto-generated method stub
		//super.bindSnappingBehaviorAsIRootPartAdapter(adapterMapBinder);
	}
	
	@Override
	protected void bindZoomOnPinchSpreadHandlerAsIRootPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// TODO Auto-generated method stub
		//super.bindZoomOnPinchSpreadHandlerAsIRootPartAdapter(adapterMapBinder);
	}
	
	@Override
	protected void bindPanOnTypeHandlerAsIRootPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// TODO Auto-generated method stub
		//super.bindPanOnTypeHandlerAsIRootPartAdapter(adapterMapBinder);
	}
}


