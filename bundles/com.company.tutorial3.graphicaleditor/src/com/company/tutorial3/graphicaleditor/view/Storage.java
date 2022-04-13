package com.company.tutorial3.graphicaleditor.view;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.gef.mvc.fx.domain.HistoricizingDomain;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.common.localization.Messages;




@Singleton
@Creatable
public class Storage {
	
	@Inject
	@Translation
	public static Messages messages;
	
	@Inject
	private IEventBroker broker;
	
	private HistoricizingDomain historicizingDomain;
	private IViewer contentViewer;

	public IViewer getContentViewer() {
		return contentViewer;
	}

	public void setContentViewer(IViewer contentViewer) {
		this.contentViewer = contentViewer;
	}

	public HistoricizingDomain getHistoricizingDomain() {
		return historicizingDomain;
	}

	public void setHistoricizingDomain(HistoricizingDomain historicizingDomain) {
		this.historicizingDomain = historicizingDomain;
		historicizingDomain.getOperationHistory().addOperationHistoryListener((e)-> {
			broker.send( UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID );
		});
	}

}


